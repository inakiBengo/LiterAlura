package com.challenge.literAlura.principal;

import com.challenge.literAlura.models.*;
import com.challenge.literAlura.repositorys.Repository;
import com.challenge.literAlura.services.ConsumeApi;
import com.challenge.literAlura.services.ConvertData;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private final String URL_BASE = "https://gutendex.com/books/?";
    private final Scanner scanIn = new Scanner(System.in);
    private final ConsumeApi api = new ConsumeApi();
    private final ConvertData convert = new ConvertData();
    private final Repository repository;
    private final String COLOR_ALERT = "\u001B[33m";
    private final String COLOR_INFO = "\u001B[32m";
    private final String COLOR_PURPLE = "\u001B[36m";
    private final String COLOR_RESET = "\u001B[0m";

    public Principal(Repository repository) {
        this.repository = repository;
    }

    public void showMenu () {
        var menu = COLOR_PURPLE+"""
                Elija la opción ingresando un numero:
                1- Buscar autor por nombre
                2- Buscar libro por titulo
                3- Listar libros registrados
                4- Listar autores registrados
                5- Listar autores vivos en un determinado año
                6- Listar libros por idioma
                
                0- Salir
                """+COLOR_RESET;
        var isContinue = true;
        while (isContinue) {
            System.out.println(menu);
            var option = scanIn.nextInt();
            switch (option) {
                case 1:
                    searchAuthorByName();
                    break;
                case 2:
                    searchBookByTitle();
                    break;
                case 3:
                    listRegisteredBooks();
                    break;
                case 4:
                    listRegisteredAuthors();
                    break;
                case 5:
                    listAuthorsAliveInAGivenYear();
                    break;
                case 6:
                    listBooksByLanguage();
                    break;
                case 0:
                    isContinue = false;
                default:
                    System.out.println("No ingreso un valor valido");
            }
        }
    }

    private Optional<DataBook> searchApi (String inputTitle) {
        var text = inputTitle.replace(" ","%20");
        var json = api.getData(URL_BASE+"search="+text);
        var data = convert.obtenerDatos(json, DataResponse.class);
        return data.results().stream().findFirst();
    }

    private void printAuthor (Author author) {
        System.out.printf(COLOR_PURPLE+"""
                --------Autor Encontrado--------
                Nombre: %s
                Nacimiento: %s
                Muerte: %s
                %n"""+COLOR_RESET,
                author.getName(),author.getBirth(),author.getDeath());
    }

    private void printBook (Book book, String nameAuthor) {
        System.out.printf(COLOR_PURPLE+"""
                --------Libro Encontrado--------
                Titulo: %s
                Autor: %s
                Copyright: %s
                Idiomas: %s
                Descargas: %s
                %n"""+COLOR_RESET,
                book.getTitle(), nameAuthor, book.getCopyright(),
                book.getLanguage(), book.getDownloads());
    }

    private void searchAuthorByName () {
        // Obtener datos del autor
        scanIn.nextLine();
        System.out.println("Que autor desea buscar?");
        var inputTitle = scanIn.nextLine();
        Optional<DataBook> AuthorsFinder = searchApi(inputTitle);

        // Validar si hay datos de respuesta
        if (AuthorsFinder.isEmpty()) {
            System.out.println(COLOR_ALERT+"No se encontró un autor con el nombre: "+ inputTitle +COLOR_RESET);
            return;
        }
        Author author = new Author(AuthorsFinder.get().authors().get(0));

        // Enviar mensaje al usuario
        printAuthor(author);

        // Validar si ya existe el autor en base de datos
        List<Author> authors = repository.getByAuthorName(author.getName());
        /// Revisar, si authors tiene datos dentro podria ser el autor buscado o no ///
        if (authors.isEmpty()) {
            // guardar en base de datos
            repository.save(author);
            System.out.println(COLOR_INFO+"Se agrego un autor a la base de datos"+COLOR_RESET);
        }
    }

    private void searchBookByTitle() {
        // Obtener datos del libro
        scanIn.nextLine();
        System.out.println("Que titulo desea buscar?");
        var inputTitle = scanIn.nextLine();
        Optional<DataBook> BooksFinder = searchApi(inputTitle);

        // Validar si hay datos de respuesta
        if (BooksFinder.isEmpty()) {
            System.out.println(COLOR_ALERT+"No se encontró un libro con el titulo: "+ inputTitle +COLOR_RESET);
            return;
        }
        Book book = new Book(BooksFinder.get());
        Author author = new Author(BooksFinder.get().authors().get(0));

        // Mandar mensaje al usuario
        printBook(book, author.getName());

        // Buscar autor en base de datos
        List<Author> authorsDataBase = repository.getByAuthorName(author.getName());
        Optional<Author> authorOptional = authorsDataBase.stream()
                .filter(a -> a.getName().toLowerCase().contains(author.getName().toLowerCase()))
                .findFirst();

        // Validar si ya existe el autor en base de datos
        if (authorOptional.isEmpty()) {
            // guardar en base de datos
            repository.save(author);
            author.setBooks(book);
            repository.save(author);
            System.out.println(COLOR_INFO+"Se guardo un autor y un libro"+COLOR_RESET);
        } else {
            // guardar los libros con el autor encontrado
            Author authorDataBase = authorOptional.get();
            Optional<Book> bookOptional = authorDataBase.getBooks().stream()
                    .filter(b -> b.getTitle().equalsIgnoreCase(book.getTitle()))
                    .findFirst();
            if (bookOptional.isEmpty()) {
                authorDataBase.setBooks(book);
                repository.save(authorDataBase);
                System.out.println(COLOR_INFO+"Se guardo un libro"+COLOR_RESET);
            } else {
                System.out.println(COLOR_ALERT+"Este libro ya fue agregado a la base de datos"+COLOR_RESET);
            }
        }
    }

    public void listRegisteredBooks () {
        List<Author> allAuthors = repository.findAll();
        allAuthors.forEach(a -> a.getBooks()
                    .forEach(b -> printBook(b, a.getName())));
    }

    public void listRegisteredAuthors () {
        List<Author> allAuthors = repository.findAll();
        allAuthors.forEach(this::printAuthor);
    }

    public void listAuthorsAliveInAGivenYear () {
        System.out.println("Escribe un año");
        var year = scanIn.nextInt();
        List<Author> authors = repository.getByAuthorBirth(year);
        if (authors.isEmpty()) {
            System.out.println(COLOR_ALERT+
                    "No se encontró un autor vivo en la fecha "+year+" en la base de datos"
                    +COLOR_RESET);
            return;
        }
        authors.forEach(this::printAuthor);
    }

    public void listBooksByLanguage () {
        StringBuilder buildLanguages = new StringBuilder();
        for(Languages language : Languages.values()) {
            if (!buildLanguages.isEmpty()) {
                buildLanguages.append(", "); // Separador
            }
            buildLanguages.append(language);
        }
        scanIn.nextLine();
        System.out.println("Ingrese el idioma a buscar");
        System.out.println(buildLanguages);
        try {
            var language = Languages.fromString(scanIn.nextLine());
            List<Book> books = repository.getBooksByLanguage(language);
            if (books.isEmpty()) {
                System.out.println(COLOR_ALERT+
                        "No se encontraron libros con el idioma "+language+" en la base de datos."
                +COLOR_RESET);
            }
            books.forEach(b -> printBook(b, b.getAuthor().getName()));
        } catch (IllegalArgumentException err) {
            System.out.println(COLOR_ALERT+err.getMessage()+COLOR_RESET);
        }
    }
}


