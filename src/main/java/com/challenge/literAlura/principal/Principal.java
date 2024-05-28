package com.challenge.literAlura.principal;

import com.challenge.literAlura.ConsumeApi;

import java.util.Scanner;

public class Principal {
    private final Scanner scanIn = new Scanner(System.in);
    private final ConsumeApi api = new ConsumeApi();

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
        var isContinue = true;
        while (isContinue) {
            System.out.println(menu);
            var option = scanIn.nextInt();
            switch (option) {
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    //listRegisteredBooks();
                    break;
                case 3:
                    //listRegisteredAuthors
                    break;
                case 4:
                    //listAuthorsAliveInAGivenYear():
                case 5:
                    //listBooksByLanguages()
                    break;
                case 0:
                    isContinue = false;
                default:
                    System.out.println("No ingreso un valor valido");
            }
        }
    }

    private void searchBookByTitle() {
        var data = api.getData("");
        System.out.println(data);
    }
}
