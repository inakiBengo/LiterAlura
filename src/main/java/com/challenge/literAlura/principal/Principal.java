package com.challenge.literAlura.principal;

import com.challenge.literAlura.models.DataAuthor;
import com.challenge.literAlura.models.DataBook;
import com.challenge.literAlura.services.ConsumeApi;
import com.challenge.literAlura.services.ConvertData;
import com.challenge.literAlura.models.DataResponse;

import javax.xml.crypto.Data;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private final String URL_BASE = "https://gutendex.com/books/?";
    private final Scanner scanIn = new Scanner(System.in);
    private final ConsumeApi api = new ConsumeApi();
    private final ConvertData convert = new ConvertData();

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
        scanIn.nextLine();
        System.out.println("Por favor ingrese el titulo del libro");
        var title = scanIn.nextLine();
        var json = api.getData(URL_BASE+"search="+title);
        var data = convert.obtenerDatos(json, DataResponse.class);
        Optional<DataBook> bookFinder = data.results().stream().findFirst();
        if (bookFinder.isPresent()) {
            DataBook book = bookFinder.get();
            Optional<DataAuthor> author = book.authors().stream().findFirst();
            String languages = String.join("", book.languages());
            if (author.isPresent()) {
                System.out.printf("""
                        --------Libro Encontrado--------
                        Titulo: %s
                        Autor: %s
                        Copyright: %s
                        Idiomas: %s
                        Descargas: %s
                        %n""",
                        book.title(), author.get().name(), book.copyright(), languages, book.downloads());
            } else {
                System.out.println("No se encontró un autor para el titulo: " + title);
            }
        } else {
            System.out.println("No se encontraron resultados con el titulo: " + title);
        }
    }
}
