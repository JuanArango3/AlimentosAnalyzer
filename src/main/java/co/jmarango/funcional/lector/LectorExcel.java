package co.jmarango.funcional.lector;

import co.jmarango.funcional.Alimento;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class LectorExcel implements LectorArchivo {
    private static final String archivo = "Dataset.xlsx";
    @Override
    public List<Alimento> obtenerAlimentos() throws Exception {
        List<Alimento> alimentos = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(archivo)) {
            Workbook libro = new XSSFWorkbook(fis);

            Sheet hoja = libro.getSheetAt(0);

            for (Row fila : hoja) {
                String nombre = getString(fila.getCell(0)).trim();
                int calorias = (int) getNumeric(fila.getCell(1));
                float proteinas = (float) getNumeric(fila.getCell(2));
                float grasas = (float) getNumeric(fila.getCell(3));
                int calcio = (int) getNumeric(fila.getCell(4));
                float hierro = (float) getNumeric(fila.getCell(5));
                int vitaminaA = (int) getNumeric(fila.getCell(6));
                float vitaminaB1 = (float) getNumeric(fila.getCell(7));
                float vitaminaB2 = (float) getNumeric(fila.getCell(8));
                float niacina = (float) getNumeric(fila.getCell(9));
                int vitaminaC = (int) getNumeric(fila.getCell(10));

                alimentos.add(new Alimento(nombre, calorias, proteinas, grasas, calcio,
                        hierro, vitaminaA, vitaminaB1, vitaminaB2, niacina, vitaminaC));
            }
        }

        return alimentos;
    }

    private static String getString(Cell cell) {
        return cell == null ? "" : cell.getStringCellValue();
    }

    private static double getNumeric(Cell cell) {
        if (cell == null) return 0.0;
        return switch (cell.getCellType()) {
            case NUMERIC -> cell.getNumericCellValue();
            case STRING -> {
                try {
                    yield Double.parseDouble(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    yield 0.0;
                }
            }
            default -> 0.0;
        };
    }
}
