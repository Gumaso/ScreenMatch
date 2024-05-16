package com.gumaso.ScreenMatch.services;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> tClass);
}

