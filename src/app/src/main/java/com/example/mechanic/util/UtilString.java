package com.example.mechanic.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UtilString {
    public static String toTitledCase(String str){

        String[] words = str.split("\\s");
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < words.length; i++){
            sb.append(words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase());
            sb.append(" ");
        }

        return sb.toString();
    }

    public static String converterListaParaString(List<String> lista) {
        if (lista == null || lista.isEmpty()) {
            return null;
        } else if (lista.size() == 1) {
            return lista.get(0);
        } else {
            // Se houver mais de um elemento, combinamos todos, exceto o último, com vírgulas
            String elementosIniciais = lista.subList(0, lista.size() - 1).stream().collect(Collectors.joining(", "));

            // Concatenamos com " e " e o último elemento
            return elementosIniciais + " e " + lista.get(lista.size() - 1);
        }
    }

    public static List<String> separarPalavras(String entrada) {
        if (entrada == null) {
            return null;
        }
        if (entrada.contains(";")) {
            return Arrays.asList(entrada.split(";"));
        } else {
            return Arrays.asList(entrada);
        }
    }

    public static String[] converterArrayLongParaString(long[] arrayLong) {
        String[] arrayString = new String[arrayLong.length];
        for (int i = 0; i < arrayLong.length; i++) {
            arrayString[i] = String.valueOf(arrayLong[i]);
        }
        return arrayString;
    }

    public static List<Long> converterStringParaListaLong(String str) {

        if(str==null){
            return null;
        }

        List<Long> listaDeValores = new ArrayList<>();

        String[] valoresSeparados = str.split(";");

        for (String valorStr : valoresSeparados) {
            if (valorStr != null && !valorStr.trim().isEmpty()) {
                try {
                    Long valorLong = Long.parseLong(valorStr.trim());
                    listaDeValores.add(valorLong);
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter valor para Long: " + valorStr);
                }
            }
        }

        return listaDeValores;
    }

    public static String joinStringsUntil159Length(List<String> strings) {
        if (strings == null || strings.size() == 0) {
            return null;
        }

        if (strings.size() == 1) {
            return strings.get(0);
        }

        int maxLength = 159;
        StringBuilder result = new StringBuilder();
        int currentLength = 0;

        for (int i = 0; i < strings.size(); i++) {
            String str = strings.get(i);
            int wordLength = str.length() + 2; // Considera o comprimento da palavra e ", " ou " e "

            if (i == strings.size() - 1) {
                // Última palavra, junta com " e " se possível
                if (currentLength + wordLength <= maxLength) {
                    if (result.length() > 0) {
                        result.append(" e ");
                        currentLength += 3; // Considera o comprimento de " e "
                    }
                    result.append(str);
                    currentLength += wordLength;
                } else {
                    // Se a última palavra não couber, interrompe o loop
                    break;
                }
            } else {
                // Para as palavras anteriores, junta com ", " se possível
                if (currentLength + wordLength <= maxLength) {
                    if (result.length() > 0) {
                        result.append(", ");
                        currentLength += 2; // Considera o comprimento da vírgula e do espaço
                    }
                    result.append(str);
                    currentLength += wordLength;
                } else {
                    // Se a palavra atual não couber, interrompe o loop
                    break;
                }
            }
        }

        return result.toString();
    }
}
