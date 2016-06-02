package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.ChessBoard;
import com.ciaran.upskill.chessgame.IllegalMoveException;
import com.ciaran.upskill.chessgame.UtilClass;

public class Bishop extends ChessPiece {

    public Bishop(Coordinate coordinate, String colour){
        this.type = "bishop";
        this.coordinate = coordinate;
        this.colour = colour;
    }

    public boolean validateMove(ChessBoard chessBoard, Coordinate finishPosition) {
        int xAxisDiff = finishPosition.getXaxis()-coordinate.getXaxis();
        int yAxisDiff = finishPosition.getYaxis()-coordinate.getYaxis();
        if (UtilClass.modulo(xAxisDiff)!=UtilClass.modulo(yAxisDiff)){
            System.out.println("Bishop must move in a diagonal direction");
            return false;
        }
        Coordinate roamingCoordinate = new Coordinate('A', '1');
        roamingCoordinate.setXaxis(coordinate.getXaxis());
        roamingCoordinate.setYaxis(coordinate.getYaxis());
        while(!roamingCoordinate.equals(finishPosition)){
            if(!roamingCoordinate.equals(coordinate)){
                if (chessBoard.getPieceByLocation(roamingCoordinate)!=null){
                    return false;
                }
            }
            if (xAxisDiff>0){
                roamingCoordinate.moveXAxisUp();
            } else {
                roamingCoordinate.moveXAxisDown();
            }
            if (yAxisDiff>0){
                roamingCoordinate.moveYAxisUp();
            } else {
                roamingCoordinate.moveYAxisDown();
            }
        }
        return true;
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
