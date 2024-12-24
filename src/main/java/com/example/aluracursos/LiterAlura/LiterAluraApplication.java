package com.example.aluracursos.LiterAlura;

import com.example.aluracursos.LiterAlura.Principal.Principal;
import com.example.aluracursos.LiterAlura.repository.AutorRepository;
import com.example.aluracursos.LiterAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.PrimitiveIterator;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {
	private  final Principal principal;

	public LiterAluraApplication(Principal principal) {
		this.principal=principal;
	}


    public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		principal.muestraElMenu();
	}
}
