package com.ciaran.upskill.chessgame;

import static com.ciaran.upskill.chessgame.Colour.BLACK;
import static com.ciaran.upskill.chessgame.Colour.WHITE;

public class UtilClass {

    public static int modulo(int integer) {
        int modulo = integer;
        if (modulo<0){
            modulo = modulo*(-1);
        }
        return modulo;
    }

    public static Colour switchColour(Colour colour) {
        if (colour==null){
            return null;
        }
        if (colour.equals(BLACK)) {
            return WHITE;
        } else if (colour.equals(WHITE)){
            return BLACK;
        }
        return null;


    }

}
