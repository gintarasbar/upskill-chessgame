package com.ciaran.upskill.chessgame.domain.chesspiece;

public enum ChessPieceType {
    BISHOP,
    KING,
    KNIGHT,
    PAWN,
    QUEEN,
    ROOK;

    @Override
    public String toString() {
        if (this == null){
            return "";
        }
        if (this.equals(BISHOP)){
            return "Bishop";
        }
        if (this.equals(KING)){
            return "King";
        }
        if (this.equals(KNIGHT)){
            return "Knight";
        }
        if (this.equals(PAWN)){
            return "Pawn";
        }
        if (this.equals(QUEEN)){
            return "Queen";
        }
        if (this.equals(ROOK)){
            return "Rook";
        }
        return "";
    }
}
