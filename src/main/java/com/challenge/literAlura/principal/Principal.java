package com.challenge.literAlura.principal;

import java.util.Scanner;

public class Principal {
    private final Scanner scanIn = new Scanner(System.in);

    public void showMenu () {
        var menu = """
                Elija la opción ingresando un numero:
                1- Buscar libro por titulo
                2- Listar libros registrados
                3- Listar autores registrados
                4- Listar autores vivos en un determinado año
                5- Listar libros por idioma
                
                0- Salir
                """;

    }
}
