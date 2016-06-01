package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.ChessBoard;
import com.ciaran.upskill.chessgame.IllegalMoveException;

public abstract class ChessPiece {

    protected String type;
    protected Coordinate coordinate;
    protected String colour;

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public abstract boolean validateMove(ChessBoard chessBoard, Coordinate finishPosition);

    public abstract ChessPiece movePiece(ChessBoard chessBoard, Coordinate finishPosition) throws IllegalMoveException;
}
