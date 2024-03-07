package com.mandacarubroker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Mandacarubroker.
 *
 * @author Equipe de desenvolvimento mandacaru
 */

@SpringBootApplication
public class MandacarubrokerApplication {
    /**
     * Ponto de entrada da aplicação.
     *
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        SpringApplication.run(MandacarubrokerApplication.class, args);
    }
}