package com.example.aluracursos.LiterAlura.Principal;


import com.example.aluracursos.LiterAlura.model.Autor;
import com.example.aluracursos.LiterAlura.model.Datos;
import com.example.aluracursos.LiterAlura.model.DatosLibro;
import com.example.aluracursos.LiterAlura.model.Libro;
import com.example.aluracursos.LiterAlura.repository.AutorRepository;
import com.example.aluracursos.LiterAlura.repository.LibroRepository;
import com.example.aluracursos.LiterAlura.service.ConsumoAPI;
import com.example.aluracursos.LiterAlura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class Principal {
private Scanner lectura = new Scanner(System.in);
private String URL_BASE="https://gutendex.com/books/";
private ConsumoAPI consumoAPI=new ConsumoAPI();
private ConvierteDatos conversor= new ConvierteDatos();
private LibroRepository libroRepository;
private AutorRepository autorRepository;
private List<Libro> libros=new ArrayList<>();
private List<Autor> autores=new ArrayList<>();

@Autowired
public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository= autorRepository;
    }

    public void muestraElMenu(){
        var op=-1;
        while (op!=0){
            var menu= """
                Elija una opcion
                1- buscar libro por titulo
                2-Listar libros registrados
                3-Listar autores registrados
                4-Listar autores vivos en un determinado año
                5-Listar libros por idioma
                0-Salir
                """;
            System.out.println(menu);
            op=lectura.nextInt();
            lectura.nextLine();

            switch (op){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivosEnAño();
                    break;
                case 5:
                    listarLibroPorIdioma();
                case 0:
                    System.out.println("cerrando aplicación");
                    break;

                default:
                    System.out.println("opción invalida");

            }
        }

    }



    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var tituloLibro = lectura.nextLine();

        try {
            var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
            var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

            Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                    .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                    .findFirst();

            if (libroBuscado.isPresent()) {
                Libro libro = new Libro(libroBuscado.get());


                autores = new ArrayList<>(libro.getAutor());

                for (Autor autor : autores) {
                    if (autor.getNombre() != null && !autor.getNombre().isEmpty()) {
                        List<Autor> autorExistente = autorRepository.findByNombreContainsIgnoreCase(autor.getNombre());
                        if (autorExistente.isEmpty()) {
                            autor = autorRepository.save(autor);
                        } else {
                            autor = autorExistente.get(0);
                        }
                    }
                }


                try {
                    libroRepository.save(libro);
                    System.out.println(libro);
                } catch (DataIntegrityViolationException e) {
                    System.out.println("Libro ya registrado");
                }



            } else {
                System.out.println("Libro no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ocurrió un error durante la búsqueda: " + e.getMessage());
        }
    }



    private void listarLibros() {
        libros=libroRepository.findAllWithAuthors();

        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void listarAutores() {
        autores=autorRepository.findAll();

        autores.forEach(System.out::println);
    }


    private void listarAutoresVivosEnAño() {
        System.out.println("Ingrese el año que desea buscar los autores");
        var anio=lectura.nextInt();
        lectura.nextLine();

        autores=autorRepository.buscarAutoresVivosEnDeterminadoAño(anio);


        autores.forEach(System.out::println);
    }
    private void listarLibroPorIdioma() {
    var menuIdiomas= """
            Ingrese el idioma para buscra los libros
            es- español
            en-ingles
            fr-frances
            pt-portugues
            """;
        System.out.println(menuIdiomas);
        var idioma=lectura.nextLine();


         libros=libroRepository.findByIdiomasAllWithAuthors(idioma);
         libros.forEach(System.out::println);

    }



}
