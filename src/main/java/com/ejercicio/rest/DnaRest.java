package com.ejercicio.rest;

import com.ejercicio.dao.dna;
import com.ejercicio.logic.Analisis;
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

    @PostMapping("/mutant") //server/mutant -- Permite la Carga y Analisis de ADN
    public ResponseEntity<?> mutantchk (@RequestBody String adn) throws JSONException {
        JSONObject jsonBody = new JSONObject(adn);
        JSONArray arr = jsonBody.getJSONArray("dna");
        boolean mutres;
        String dnares = Analisis.dnachk(jsonBody);

        if (dnares == "ok") { //solo analizamos y guardamos el DNA si se cumplen los requisitos de Longitud e Integridad
            String[] sarr = Analisis.toStringArray(arr);
            mutres = Analisis.isMutant(sarr); //guardamos para no tener necesidad de invocar 2 veces.

            Dna adndata = new Dna(0, arr.toString(), mutres);
            //System.out.println(jsonBody.toString());
            dna.save(adndata);

            headers.setContentType(MediaType.TEXT_PLAIN);
            if (mutres) {
                return new ResponseEntity<>("ADN CORRESPONDE A HUMANO MUTANTE", headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("ADN CORRESPONDE A HUMANO NO MUTANTE", headers, HttpStatus.FORBIDDEN);
            }
        } else if (dnares == "errcv" ) //Mensaje de Error por Integridad de ADN
        {
            return new ResponseEntity<>("ADN NO INTEGRO: Solo se admite: 'A-T-C-G'", headers, HttpStatus.NOT_ACCEPTABLE);
        } else //Mensaje de Error por Longitud de ADN
        {
            return new ResponseEntity<>("LONGITUD ADN INCOMPATIBLE: Solo de admiten NxN con N>3", headers, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/stats") //server/stats -- Devuelve la estadistica y Ratio de Mutantes / Humanos
    public ResponseEntity<?> state() throws JSONException {
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject body = new JSONObject();
        body.put("Cant. Mutantes",dna.CountMutant());
        body.put("Cant. Humanos",dna.CountHuman());
        if (dna.CountHuman() != 0) {
            float ratio = (float)dna.CountMutant() / (float)dna.CountHuman();
            String ratio20 = String.format("%.02f", ratio);
            body.put("Ratio",ratio20);
        } else
        {
            body.put("Ratio","inf");
        }
        return new ResponseEntity(body.toString(), headers, HttpStatus.OK);
    }

    @GetMapping("/erase") //server/erase -- Limpia la Base de datos
    public ResponseEntity<?> deldb(){
        dna.deleteAll();
        return new ResponseEntity("DB: borrada correctamente", headers, HttpStatus.OK);
    }

    @GetMapping("/mlist") //server/mlist -- Lista todos los ADN mutantes
    public ResponseEntity<?> mlist() {

        if (dna.ListMutant().isEmpty())
            return new ResponseEntity("No Hay Mutantes Registrados :O", headers, HttpStatus.OK);
        else
            return new ResponseEntity("Listado Mutantes:\n\n" + Analisis.formatlist(dna.ListMutant()),headers, HttpStatus.OK);

    }
}
