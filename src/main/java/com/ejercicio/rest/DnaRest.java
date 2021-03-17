package com.ejercicio.rest;

import com.ejercicio.dao.dna;
import com.ejercicio.dao.Analisis;
import org.json.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.String;


@RestController
@RequestMapping("adn")

public class DnaRest {

    @Autowired
    private dna dna;
    HttpHeaders headers = new HttpHeaders();
    //METODOS HTTP - SOLICITUD AL SERVIDOR
    // GET,POST,DELETE,PUT -> 200 - 500 - 404

    @PostMapping("/mutant") //localhost:8080/dna/mutant
    public ResponseEntity<?> mutantchk (@RequestBody String dna) throws JSONException {
        JSONObject jsonBody = new JSONObject(dna);
        JSONArray arr = jsonBody.getJSONArray("dna");
        String [] sarr = Analisis.toStringArray(arr);
        headers.setContentType(MediaType.TEXT_PLAIN);
        if (Analisis.isMutant(sarr)){
            return new ResponseEntity<>("ADN CORRESPONDE A HUMANO MUTANTE",headers, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("ADN CORRESPONDE A HUMANO NO MUTANTE",headers, HttpStatus.FORBIDDEN);
        }
    }
}
