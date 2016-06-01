package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.ChessBoard;
import com.ciaran.upskill.chessgame.UtilClass;

public class Rook extends ChessPiece {

    private  boolean moved;

    public Rook (Coordinate coordinate, String colour){
        this.type = "rook";
        this.coordinate = coordinate;
        this.colour = colour;
        moved = false;
    }


    public boolean validateMove(ChessBoard chessBoard, Coordinate finishPosition) {
        int xAxisDiff = finishPosition.getXaxis()-coordinate.getXaxis();
        int yAxisDiff = finishPosition.getYaxis()-coordinate.getYaxis();
        if (UtilClass.modulo(xAxisDiff)>0&&UtilClass.modulo(yAxisDiff)>0){
            System.out.println("Rook must move along only 1 axis");
            return false;
        }
        Coordinate roamingCoordinate = new Coordinate('A', '1');
        roamingCoordinate.setXaxis(coordinate.getXaxis());
        roamingCoordinate.setYaxis(coordinate.getYaxis());
        while(roamingCoordinate!=finishPosition){
            if(roamingCoordinate!=coordinate){
                if (chessBoard.getPieceByLocation(roamingCoordinate)!=null){
                    return false;
                }
            }
            if (xAxisDiff>0){
                roamingCoordinate.moveXAxisUp();
            } else if (xAxisDiff<0) {
                roamingCoordinate.moveXAxisDown();
            }
            if (yAxisDiff>0){
                roamingCoordinate.moveYAxisUp();
            } else if (yAxisDiff<0){
                roamingCoordinate.moveYAxisDown();
            }
        }
        return true;
    }


    public ChessPiece movePiece(ChessBoard chessBoard, Coordinate finishPosition) {
        validateMove(chessBoard,finishPosition);
        ChessPiece removedPiece = chessBoard.getPieceByLocation(finishPosition);
        if(removedPiece!=null){
            chessBoard.removePiece(removedPiece);
        }
        setCoordinate(finishPosition);
        moved = true;
        return removedPiece;
    }

    public boolean hasMoved() {
        return moved;
    }
}
