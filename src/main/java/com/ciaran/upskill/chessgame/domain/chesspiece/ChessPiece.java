package com.ciaran.upskill.chessgame.domain.chesspiece;

import com.ciaran.upskill.chessgame.Colour;
import com.ciaran.upskill.chessgame.domain.ChessBoard;
import com.ciaran.upskill.chessgame.exceptions.IllegalMoveException;
import com.ciaran.upskill.chessgame.domain.BoardCell;

public abstract class ChessPiece {

    protected ChessPieceType type;
    protected BoardCell boardCell;
    protected Colour colour;

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public ChessPieceType getType() {
        return type;
    }

    public void setType(ChessPieceType type) {
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
