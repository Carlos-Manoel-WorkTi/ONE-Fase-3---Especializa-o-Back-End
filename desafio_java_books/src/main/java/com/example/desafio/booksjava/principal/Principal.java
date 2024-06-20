package com.example.desafio.booksjava.principal;

import com.example.desafio.booksjava.model.Author;
import com.example.desafio.booksjava.model.Book;
import com.example.desafio.booksjava.model.BookResponse;
import com.example.desafio.booksjava.model.Languages;
import com.example.desafio.booksjava.repository.BookRepository;
import com.example.desafio.booksjava.service.ServiceResponseApi;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private BookRepository bookRepository;

    public Principal(BookRepository bookRepository) {
        this.bookRepository = bookRepository;

    }

    public void init() throws IOException {

        Scanner read = new Scanner(System.in);
        ServiceResponseApi api = new ServiceResponseApi();

        while (true){
            System.out.println("""
                \n____________________________________________________
                Seja Bem-vindo(a) ao JavaBooks

                [1] Pesquisar por livro:
                [2] Listar livros salvos:
                [3] Listar autores salvos:
                [4] Listar autores vivos em determinado ano:
                [5] Lista livros por idioma:
                [0] Para sair!
                ____________________________________________________
                Digite o numero:\s""");

                String opt = read.nextLine();

                switch (opt){
                    case "1":
                        searchBook(api,read);
                        break;
                    case "2":
                        getAllBooks();
                        break;
                    case "3":
                        getAllAuthors();
                        break;
                    case "4":
                        getAuthorByYear(read);
                        break;

                    case "5":
                        searchBylang(read);
                        break;
                    case "0":
                        System.out.println("Saindo do programa...");
                        return;
                }

            }



        }

    public void searchBook(ServiceResponseApi api, Scanner read) throws IOException {
        System.out.println("____________________________________________________");
        System.out.println("Qual livro voçe deseja procurar?  \n");
        String name = read.nextLine();
        System.out.println("Procurando...  \n");
        BookResponse response = api.getDate(name.replace(" ","%20"), BookResponse.class);

        if (!response.getResults().isEmpty()) {
            Book firstBook = response.getResults().getFirst();
            System.out.println("____________________________________________________");
            System.out.println("Título: " + firstBook.getShortenedTitle() );

            System.out.print("Autores: ");
            firstBook.getAuthors().forEach(author -> System.out.print(author.getAuthor() + " "));
            System.out.println();  // Adiciona uma nova linha após a lista de autores

            System.out.print("Idioma: ");
            firstBook.getLanguages().forEach(lang -> System.out.print(lang.getLang() + " "));
            System.out.println();  // Adiciona uma nova linha após a lista de idiomas

            System.out.println("Downloads: " + firstBook.getDownload());
            System.out.println("____________________________________________________");
            System.out.println(firstBook.getLanguages().getFirst().getLang());

            //SALVE IN THE BD
            List<Author> listaAuthors = new ArrayList<>();
            List<Languages> listaLang = new ArrayList<>();

            firstBook.getAuthors().forEach(author -> {
                Author novoAutor = new Author(author.getAuthor(),author.getBirth_year(),author.getDeath_year());
                novoAutor.setBook(firstBook);
                listaAuthors.add(novoAutor);
            });
            firstBook.getLanguages().forEach(lang -> {
                Languages novoIdioma = new Languages(lang.getLang());
                novoIdioma.setBook(firstBook);
                listaLang.add(novoIdioma);
            });

            firstBook.setAuthors(listaAuthors);
            firstBook.setLanguages(listaLang);

            // Salve o livro
            bookRepository.save(firstBook);

        }else{
            System.out.println("\n____________________________________________________");
            System.out.println("Nenhum livro encontrado.");
        }
    }

    public void getAllBooks(){
        List<Book> allBooks = bookRepository.allBooks();


        allBooks.forEach(b -> {
            System.out.println("_________________LIVRO______________________\n");
            String authorName = b.getAuthors().isEmpty() ? "N/A" : b.getAuthors().getFirst().getAuthor();
            String lang = b.getLanguages().isEmpty() ? "N/A" : b.getLanguages().getFirst().getLang();
            System.out.println("TITULO: " + b.getShortenedTitle() + "\n" +
                    "Auhtor: " + authorName + "\n" +
                    "Langs: " + lang + "\n" +
                    "Download: " + b.getDownload() + "\n"
            );
            System.out.println("_____________________________________________\n");
        });




    }

    public void getAllAuthors(){
        List<Book> allBooks = bookRepository.allBooks();
        System.out.println("_________________AUTORES______________________\n");
        allBooks.forEach(book -> {
            book.getAuthors().forEach(author ->
                    System.out.println(
                            "NOME: " + author.getAuthor() + "\n" +
                            "Ano de nascimento: " + author.getBirth_year() + "\n" +
                            "Ano de falecimento: " + author.getBirth_year() + "\n" +
                            "Obras: [" + book.getShortenedTitle() + "]" + "\n" +
                                    "----------------------------------"
                    )
            );
        });
    }

    public void getAuthorByYear(Scanner read){
        System.out.println("Qual é o ano inicial?  \n");
        String year = read.nextLine();
        List<Book> allBooks = bookRepository.findBooksByAuthorsAliveInYear(Integer.parseInt(year));
        System.out.println("_________________AUTORES VIVOS______________________\n");
        if (allBooks.isEmpty()) {
            System.out.println("Não há autores vivos nessa data: " + year + ".");
            return;
        }
        System.out.println("Ano incial: " + year +"\n");
        allBooks.forEach(book -> {
            book.getAuthors().forEach(author ->
                    System.out.println(
                            "NOME: " + author.getAuthor() + "\n" +
                                    "Ano de nascimento: " + author.getBirth_year() + "\n" +
                                    "Ano de falecimento: " + author.getDeath_year() + "\n" +
                                    "Obras: [" + book.getShortenedTitle() + "]" + "\n" +
                                    "----------------------------------"
                    )
            );
        });
    }

    public void searchBylang(Scanner read){

        System.out.println("""
                \n____________________________________________________
                Escolha o idioma para a busca:

                [1] Portugues:
                [2] Ingles:
                [3] Frances:
                [4] Alemao:
                [0] outro:
                ____________________________________________________
                Digite o numero:\s""");

        String idioma = "";
        switch (read.nextLine()) {
            case "1":
                idioma = "pt";
                break;
            case "2":
                idioma = "en";
                break;
            case "3":
                idioma = "fr";
                break;
            case "4":
                idioma = "gr";
                break;
            case "0":
                System.out.println("Digite o idioma desejado:");
                idioma = read.nextLine();
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }
        List<Book> allBooks = bookRepository.findBooksByLanguage(idioma);
        System.out.println("_________________LIVRO______________________\n");

        if (allBooks.isEmpty()) {
            System.out.println("Não há livros disponíveis no idioma " + idioma + ".");
            return;
        }
        System.out.println("Idioma:" + idioma + "\n");
        allBooks.forEach(b -> {
            String authorName = b.getAuthors().isEmpty() ? "N/A" : b.getAuthors().getFirst().getAuthor();
            String lang = b.getLanguages().isEmpty() ? "N/A" : b.getLanguages().getFirst().getLang();
            System.out.println("TITULO: " + b.getShortenedTitle() + "\n" +
                    "Auhtor: " + authorName + "\n" +
                    "Langs: " + lang + "\n" +
                    "Download: " + b.getDownload() + "\n"
            );
            System.out.println("_____________________________________________\n");
        });
    }

}
