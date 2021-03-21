package com.ejercicio;

import com.ejercicio.rest.DnaRest;
//import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert.*;
import org.junit.Test;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;


@SpringBootTest
class EjercicioApplicationTests {

    @Autowired
    private DnaRest controller;

    @Test
    public void contextLoads() {
    }

}
