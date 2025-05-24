package co.jmarango.funcional.lector;

import co.jmarango.funcional.Alimento;

import java.util.List;

public interface LectorArchivo {
    List<Alimento> obtenerAlimentos() throws Exception;
}
