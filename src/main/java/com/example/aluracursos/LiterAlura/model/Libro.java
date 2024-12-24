package com.example.aluracursos.LiterAlura.model;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table (name = "libros")
public class Libro {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (unique = true)
   private String titulo;
    @ManyToMany
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
   private List<Autor> autor;
   private List<String> idioma;
   private Double numeroDeDescargas;

    public Libro(){}
    public Libro(DatosLibro datos) {
        this.titulo=datos.titulo();
        this.autor=datos.autor();
        this.idioma=datos.idiomas();
        this.numeroDeDescargas=datos.numeroDeDescargas();

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getIdioma() {
        return idioma;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public List<String> getIdiomas() {
        return idioma;
    }

    public void setIdioma(List<String> idiomas) {
        this.idioma = idiomas;
    }

    public List<Autor> getAutor() {
        return autor;
    }

    public void setAutor(List<Autor> autor) {
        this.autor = autor;
    }


    @Override
    public String toString() {

        String autores = autor.stream()
                .map(Autor::getNombre)
                .reduce((a1, a2) -> a1 + ", " + a2)
                .orElse("Sin autor");
       String idiomas=idioma.stream()
               .reduce((i1,i2)->i1+","+i2).orElse("Sin idioma");

        return "----------------Libro------------------------- \n" +
                "titulo=" + titulo + '\n' +
                "autor=" + autores+'\n' +
                "idiomas=" + idioma+'\n' +
                "numeroDeDescargas=" + numeroDeDescargas +'\n' +
                "-----------------------------------------------\n" ;
    }
}
