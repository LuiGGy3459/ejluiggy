package com.ejercicio.logic;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.StringUtils;
import java.lang.*;
import java.util.List;



public class Analisis {

    public static int strcheck(char[] cstr) { //metodo que chequea la aparicion de AAAA/CCCC/GGGG/TTTT
         String str = new String (cstr);
         int coincidencias = 0;

         coincidencias += StringUtils.countOccurrencesOf(str,"AAAA");
         if (coincidencias<2){
             coincidencias += StringUtils.countOccurrencesOf(str,"CCCC");
         }
         if (coincidencias<2){
             coincidencias += StringUtils.countOccurrencesOf(str,"GGGG");
         }
         if (coincidencias<2){
             coincidencias += StringUtils.countOccurrencesOf(str,"TTTT");
         }
         return coincidencias;
    }


    public static String formatlist (List list) { //Formatea la Lista para mejor apreciacion
         String mList = list.toString();

         mList = mList.replace("[[", "[");
         mList = mList.replace(", ", "\n");
         mList = mList.replace("]]", "]");
         return mList;
    }

    public static String[] toStringArray(JSONArray array) { //Convierte JSONArray a un String Array
        if(array==null)
            return null;

        String[] arr=new String[array.length()];
        for(int i=0; i<arr.length; i++) {
            arr[i]=array.optString(i);
        }
        return arr;
    }

    public static String dnachk(JSONObject dnadata) throws JSONException { //Chequea integridad y Longitud de ADN
        JSONArray arr = dnadata.getJSONArray("dna");
        String [] sarr = toStringArray(arr);

        char [][] dnaarr = new char[sarr.length][sarr.length]; // se asume el arreglo N*N

        int i,c;
        int lenvalidance = 0; //Valida la longitud del Arreglo.
        int charvalidance = 0; //valida la integridad del ADN ("ACGT")

        for (i=0;i<(sarr.length-1);i++){
            if (sarr[i].length() == sarr[i+1].length()){
                lenvalidance++;
            }
        }
        if (lenvalidance == (sarr.length - 1)) { //verificamos que el Arreglo sea NxN
            for (i = 0; i < sarr.length; i++) {
                for (c = 0; c < sarr.length; c++) {
                    dnaarr[i][c] = sarr[i].charAt(c); //convierto a char array de NxN
                    dnaarr[i][c] = Character.toUpperCase(dnaarr[i][c]); //Convertimos a Mayus
                    if (dnaarr[i][c] == ('A') || dnaarr[i][c] == ('C') || dnaarr[i][c] == ('G') || dnaarr[i][c] == ('T')) {
                        charvalidance++;
                    }
                }
            }
            if (charvalidance == sarr.length*sarr.length) {
                return "ok"; //Arreglo cumple todos los requisitos
            } else
            {
                return "errcv"; //Arreglo ADN no cumple integridad "ACGT"
            }
        } else
        {
            return "errlv"; //Arreglo ADN no cumple Longitud NxN
        }
    }

    public static boolean isMutant(String dna[]) { //Analiza si el ADN dado corresponde a un Humano o Mutante

        int columna, fila, i, j, N;
        boolean res = false;
        int coincidence = 0;

        columna = dna.length; //Devuelvo el tamaño del array (M)
        fila = dna[0].length(); //Devuelvo el tamaño del string (N)
        char[][] arreglo = new char[fila][columna]; //arreglo donde se hara split del string
        char[] cstr = new char[fila];
        N = fila = columna; //ya que el arreglo se supone NxN
        try {
            if (columna == fila) { //Verificamos que el Arreglo sea NxN
                for (i = 0; i < N; i++) {
                    for (j = 0; j < N; j++) {
                        arreglo[i][j] = dna[i].charAt(j); //convierto a char array de NxN
                        arreglo[i][j] = Character.toUpperCase(arreglo[i][j]); //Convertimos a Mayus
                    }
                }

                /* Corroboracion Horizontal */
                for (j = 0; j < N; j++) {
                    for (i = 0; i < N; i++) {
                        cstr[i] = arreglo[j][i]; //Conversion Horizontal
                    }
                    coincidence += strcheck(cstr); //Chequeamos el Arreglo Horizontal por Coincidencias
                }
                /*--------------------------*/

                if (coincidence < 2) { //Ejecutamos la C. Vertical si no hay aun 2 Concidencias
                    /* Corroboracion Vertical */
                    for (j = 0; j < N; j++) {
                        for (i = 0; i < N; i++) {
                            cstr[i] = arreglo[i][j]; //Conversion Vertical - Horizontal
                        }
                        coincidence += strcheck(cstr); //Chequeamos el Arreglo Horizontal por Coincidencias
                    }
                }
                /*------------------------*/

                if (coincidence < 2) { //Ejecutamos la C. Diagonal si no hay aun 2 Concidencias
                    /* Corroboracion Diagonal */
                    for (j = 0; j <= (N - 4); j++) {
                        for (i = 0; i < (N - j); i++) {
                            cstr[i] = arreglo[i + j][i]; //Conversion Diagonal A
                        }
                        coincidence += strcheck(cstr); //Chequeamos el Arreglo Horizontal por Coincidencias
                    }

                    for (j = 1; j <= (N - 4); j++) {
                        for (i = 0; i < (N - j); i++) {
                            cstr[i] = arreglo[i][i + j]; //Conversion Diagonal B
                        }
                        coincidence += strcheck(cstr); //Chequeamos el Arreglo Horizontal por Coincidencias
                    }
                }
                /*------------------------*/

                if (coincidence < 2) { //Ejecutamos la C. Diagonal Invertida si no hay aun 2 Concidencias
                    /* Corroboracion Diagonal Invertida */
                    for (j = N - 1; j > (N - 4); j--) {
                        for (i = 0; i <= j; i++) {
                            cstr[i] = arreglo[j - i][i]; //Conversion Diagonal Invertida A
                        }
                        coincidence += strcheck(cstr); //Chequeamos el Arreglo Horizontal por Coincidencias
                    }
                    for (j = 1; j <= (N - 4); j++) {
                        for (i = N - 1; i >= j; i--) {
                            cstr[i] = arreglo[j][i]; //Conversion Diagonal Invertida B
                        }
                        coincidence += strcheck(cstr); //Chequeamos el Arreglo Horizontal por Coincidencias
                    }
                }

                /*------------------------*/

                //System.out.println(coincidence);
                if (coincidence > 1) {
                    res = true;
                } else {
                    res = false;
                }

            } else {
                System.out.println("La Matriz de ADN no es NxN");
            }
        } catch(Exception err) {
             //System.out.println("El error es:" + err);
             //return "La Matriz de ADN no es NxN";
        }
        return res;
    }
}
