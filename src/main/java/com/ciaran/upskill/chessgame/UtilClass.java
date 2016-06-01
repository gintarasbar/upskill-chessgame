package com.ciaran.upskill.chessgame;

public class UtilClass {

    public static int modulo(int integer) {
        int modulo = integer;
        if (modulo<0){
            modulo = modulo*(-1);
        }
        return modulo;
    }

}
