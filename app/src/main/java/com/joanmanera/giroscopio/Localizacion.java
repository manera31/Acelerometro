package com.joanmanera.giroscopio;

public class Localizacion {

    private String nombre;
    private double longitud;
    private double latitud;

    public Localizacion(String nombre, double longitud, double latitud) {
        this.nombre = nombre;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public String getNombre() {
        return nombre;
    }

    public double getLongitud() {
        return longitud;
    }

    public double getLatitud() {
        return latitud;
    }
}
