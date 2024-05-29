package com.challenge.literAlura.services;

public interface IConverData {
    <T> T obtenerDatos(String json, Class<T> clase);
}
