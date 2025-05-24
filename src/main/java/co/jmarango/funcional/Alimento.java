package co.jmarango.funcional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class Alimento {
    private String nombre;
    private int calorias;
    private float proteinas;
    private float grasas;
    private int calcio;
    private float hierro;
    private int vitaminaA;
    private float vitaminaB1;
    private float vitaminaB2;
    private float niacina;
    private int vitaminaC;

    public float sumaVitaminas() {
        return vitaminaA + vitaminaB1 + vitaminaB2 + vitaminaC + niacina;
    }
}
