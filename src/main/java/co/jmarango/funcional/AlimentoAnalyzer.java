package co.jmarango.funcional;

import co.jmarango.funcional.lector.LectorArchivo;
import co.jmarango.funcional.lector.LectorExcel;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;

@Log4j2
public class AlimentoAnalyzer {
    public static void main(String[] args) {
        log.info("Iniciando análisis de alimentos");
        try {
            LectorArchivo lector = new LectorExcel();
            List<Alimento> alimentos = lector.obtenerAlimentos();
            Optional<Alimento> mayorGrasa = alimentos.parallelStream()
                    .reduce(
                            (actual, anterior) -> actual.getGrasas() > anterior.getGrasas() ? actual : anterior
                    );
            mayorGrasa.ifPresent(alimento -> log.info("Alimento con mayor grasa: {} con {}g de grasa", alimento.getNombre(), alimento.getGrasas()));

            Optional<Alimento> mayorVitaminaC = alimentos.parallelStream()
                    .reduce((actual, anterior) -> actual.getVitaminaC() > anterior.getVitaminaC() ? actual : anterior);
            mayorVitaminaC.ifPresent(alimento -> log.info("Alimento con mayor vitamina C: {} con {}mg de vitamina C", alimento.getNombre(), alimento.getVitaminaC()));

            int cantidadTiamina = alimentos.parallelStream()
                    .map(a -> a.getVitaminaB1() > 0.1 ? 1 : 0) // map: si cumple condición, devuelve 1; si no, 0
                    .reduce(0, Integer::sum); // reduce: suma todos los 1
            // se puede tambien usar filter y count en la API de Streams
            log.info("Cantidad de alimentos con mas de 0.1 de Tiamina: {}", cantidadTiamina);

            Optional<Alimento> mayorVitaminas = alimentos.parallelStream()
                    .reduce((actual, anterior) -> actual.sumaVitaminas() > anterior.sumaVitaminas() ? actual : anterior);
            mayorVitaminas.ifPresent(alimento -> log.info("Alimento con mayor cantidad de vitaminas: {} con {} unidades de vitaminas", alimento.getNombre(), alimento.sumaVitaminas()));

            List<Alimento> hierroYBajaGrasa = alimentos.parallelStream()
                    .filter(a -> a.getHierro() > 1.0 && a.getGrasas() < 3.0)
                    .toList();
            log.info("Alimentos con más de 1mg de hierro y menos de 3g de grasa:");
            hierroYBajaGrasa.forEach(alimento -> log.info("{} (Hierro: {}, grasas: {})", alimento.getNombre(), alimento.getHierro(), alimento.getGrasas()));
        } catch (Exception e) {
            log.error(e);
        }
    }

}
