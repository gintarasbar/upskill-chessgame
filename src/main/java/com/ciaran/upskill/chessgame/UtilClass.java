package com.ciaran.upskill.chessgame;

public class UtilClass {

    public static int modulo(int integer) {
        int modulo = integer;
        if (modulo<0){
            modulo = modulo*(-1);
        }
        return modulo;
    }

    public static String switchColour(String colour) {
        if (colour==null){
            return null;
        }
        if (colour.matches("Black")) {
            return "White";
        } else if (colour.matches("White")){
            return "Black";
        }
        return null;


    }

}
