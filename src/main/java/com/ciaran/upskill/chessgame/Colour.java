package com.ciaran.upskill.chessgame;

public enum Colour {
    BLACK,
    WHITE;

    @Override
    public String toString() {
        if (this == null){
            return "";
        }
        if (this == BLACK){
            return "Black";
        } else {
            return "White";
        }
    }
}
