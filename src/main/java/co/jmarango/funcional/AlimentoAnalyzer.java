package co.jmarango.funcional;

import co.jmarango.funcional.lector.LectorArchivo;
import co.jmarango.funcional.lector.LectorExcel;
import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Log4j2
public class AlimentoAnalyzer {
    public static void main(String[] args) {
        try {
            LectorArchivo lector = new LectorExcel();
            List<Alimento> alimentos = lector.obtenerAlimentos();
            // 1 pregunta
            double maxGrasa = alimentos.parallelStream()
                    .mapToDouble(Alimento::getGrasas)
                    .max()
                    .orElse(0.0);

            List<Alimento> alimentosMayorGrasa = alimentos.parallelStream()
                    .filter(a -> a.getGrasas() == maxGrasa)
                    .toList();

            StringBuilder resultado1 = new StringBuilder("1. Alimento(s) con mayor grasa (" + maxGrasa + "g):\n");
            alimentosMayorGrasa.forEach(a -> resultado1.append("- ").append(a.getNombre()).append("\n"));
            JOptionPane.showMessageDialog(null, resultado1.toString(), "Pregunta 1", JOptionPane.INFORMATION_MESSAGE);

            Optional<Alimento> mayorVitaminaC = alimentos.parallelStream()
                    .reduce((actual, anterior) -> actual.getVitaminaC() > anterior.getVitaminaC() ? actual : anterior);

            // 2 pregunta
            String resultado2 = mayorVitaminaC
                    .map(a -> "2. Alimento con mayor vitamina C:\n" + a.getNombre() + " con " + a.getVitaminaC() + "mg")
                    .orElse("2. No se encontró alimento con vitamina C");
            JOptionPane.showMessageDialog(null, resultado2, "Pregunta 2", JOptionPane.INFORMATION_MESSAGE);

            // 3 pregunta
            int cantidadTiamina = alimentos.parallelStream()
                    .map(a -> (a.getVitaminaB1() / 1000.0) > 0.1 ? 1 : 0)
                    .reduce(0, Integer::sum);
            JOptionPane.showMessageDialog(null, "3. Alimentos con más de 0.1g de Tiamina:\n " + cantidadTiamina +" alimentos", "Pregunta 3", JOptionPane.INFORMATION_MESSAGE);

            // 4 pregunta
            Optional<Alimento> mayorVitaminas = alimentos.parallelStream()
                    .reduce((actual, anterior) -> actual.sumaVitaminas() > anterior.sumaVitaminas() ? actual : anterior);

            String resultado4 = mayorVitaminas
                    .map(a -> "4. Alimento con mayor cantidad de vitaminas:\n" + a.getNombre() + " con " + a.sumaVitaminas() + " unidades")
                    .orElse("4. No se encontró alimento con vitaminas");
            JOptionPane.showMessageDialog(null, resultado4, "Pregunta 4", JOptionPane.INFORMATION_MESSAGE);

            // 5 pregunta

            List<Alimento> hierroYBajaGrasa = alimentos.parallelStream()
                    .filter(a -> a.getHierro() > 1.0 && a.getGrasas() < 3.0)
                    .toList();

            StringBuilder resultado5 = new StringBuilder("5. Alimentos con >1mg hierro y <3g grasa:\n");
            if (hierroYBajaGrasa.isEmpty()) {
                resultado5.append("Ninguno cumple las condiciones.");
            } else {
                hierroYBajaGrasa.forEach(a -> resultado5.append("- ").append(a.getNombre())
                        .append(" (Hierro: ").append(a.getHierro())
                        .append("mg, Grasas: ").append(a.getGrasas()).append("g)\n"));
            }
            JOptionPane.showMessageDialog(null, resultado5.toString(), "Pregunta 5", JOptionPane.INFORMATION_MESSAGE);

            JOptionPane.showMessageDialog(null, "¡Gracias por utilizar nuestro sistema!", "Fin", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            log.error(e);
            JOptionPane.showMessageDialog(null, "Ocurrió un error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
