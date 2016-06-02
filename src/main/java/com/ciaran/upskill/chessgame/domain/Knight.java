package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.ChessBoard;
import com.ciaran.upskill.chessgame.IllegalMoveException;
import com.ciaran.upskill.chessgame.UtilClass;

public class Knight extends ChessPiece {

    public Knight(Coordinate coordinate, String colour){
        this.type = "knight";
        this.coordinate = coordinate;
        this.colour = colour;
    }


    public boolean validateMove(ChessBoard chessBoard, Coordinate finishPosition) {
        int xAxisDiff = finishPosition.getXaxis()-coordinate.getXaxis();
        int yAxisDiff = finishPosition.getYaxis()-coordinate.getYaxis();
        if (UtilClass.modulo(xAxisDiff)==2){
            if(UtilClass.modulo(yAxisDiff)==1){
                return true;
            }
        }
        if (UtilClass.modulo(xAxisDiff)==1){
            if(UtilClass.modulo(yAxisDiff)==2){
                return true;
            }
        }
        return false;
    }


    public ChessPiece movePiece(ChessBoard chessBoard, Coordinate finishPosition) throws IllegalMoveException {
        if (!validateMove(chessBoard,finishPosition)){
            throw new IllegalMoveException();
        };
        ChessPiece removedPiece = chessBoard.getPieceByLocation(finishPosition);
        if(removedPiece!=null){
            chessBoard.removePiece(removedPiece);
        }
        setCoordinate(finishPosition);
        return removedPiece;
    }
}
