package com.ciaran.upskill.chessgame.domain.chesspiece;

import com.ciaran.upskill.chessgame.domain.ChessBoard;
import com.ciaran.upskill.chessgame.IllegalMoveException;
import com.ciaran.upskill.chessgame.domain.BoardCell;

public abstract class ChessPiece {

    protected String type;
    protected BoardCell boardCell;
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

    public BoardCell getBoardCell() {
        return boardCell;
    }

    public void setBoardCell(BoardCell boardCell) {
        this.boardCell = boardCell;
    }

    public abstract boolean validateMove(ChessBoard chessBoard, BoardCell finishPosition);

    public abstract ChessPiece move(ChessBoard chessBoard, BoardCell finishPosition) throws IllegalMoveException;
}
