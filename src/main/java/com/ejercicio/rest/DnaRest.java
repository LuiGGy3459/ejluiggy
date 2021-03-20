package com.ejercicio.rest;

import com.ejercicio.dao.dna;
import com.ejercicio.dao.Analisis;
import com.ejercicio.model.Dna;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.String;

@RestController
@RequestMapping("/")

public class DnaRest {

    @Autowired
    private dna dna;
    HttpHeaders headers = new HttpHeaders();
    //METODOS HTTP - SOLICITUD AL SERVIDOR
    // GET,POST,DELETE,PUT -> 200 - 500 - 404

    @PostMapping("/mutant") //server/mutant
    public ResponseEntity<?> mutantchk (@RequestBody String adn) throws JSONException {
        JSONObject jsonBody = new JSONObject(adn);
        JSONArray arr = jsonBody.getJSONArray("dna");
        String [] sarr = Analisis.toStringArray(arr);

        Dna adndata = new Dna (0, jsonBody.toString(), Analisis.isMutant(sarr));
        System.out.println(jsonBody.toString());
        dna.save(adndata);

        headers.setContentType(MediaType.TEXT_PLAIN);
        if (Analisis.isMutant(sarr)){
            return new ResponseEntity<>("ADN CORRESPONDE A HUMANO MUTANTE",headers, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("ADN CORRESPONDE A HUMANO NO MUTANTE",headers, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/stats") //server/stats
    public ResponseEntity<?> state() throws JSONException {
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = new JSONObject();
        body.put("Cant. Mutantes",dna.findMutantCount());
        body.put("Cant. Humanos",dna.findHumantCount());
        if (dna.findHumantCount() != 0) {
            float ratio = (float)dna.findMutantCount() / (float)dna.findHumantCount();
            String ratio20 = String.format("%.02f", ratio);
            body.put("Ratio",ratio20);
        } else
        {
            body.put("Ratio","inf");
        }

        return new ResponseEntity(body.toString(), headers, HttpStatus.OK);
    }
}
