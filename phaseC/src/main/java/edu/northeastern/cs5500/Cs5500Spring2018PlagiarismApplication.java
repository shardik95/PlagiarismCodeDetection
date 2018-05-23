package edu.northeastern.cs5500;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
@RestController
public class Cs5500Spring2018PlagiarismApplication {

    private static final Logger logger = LoggerFactory.getLogger(Cs5500Spring2018PlagiarismApplication.class);

    /**
     * Main method
     * @param args command line argument
     */
    public static void main(String[] args) 
    {
        SpringApplication.run(Cs5500Spring2018PlagiarismApplication.class, args);
        logger.debug("--Application Started--");
    }
}


