package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.ChessBoard;
import com.ciaran.upskill.chessgame.IllegalMoveException;
import com.ciaran.upskill.chessgame.UtilClass;

public class Pawn extends ChessPiece {

    private String direction;
    private boolean hasMoved;

    public Pawn(Coordinate coordinate, String colour, String direction){
        this.type = "pawn";
        this.coordinate = coordinate;
        this.colour = colour;
        this.direction = direction;
        hasMoved = false;
    }

    public boolean validateMove(ChessBoard chessBoard, Coordinate finishPosition) {
        int xAxisDiff = finishPosition.getXaxis() - coordinate.getXaxis();
        int yAxisDiff = finishPosition.getYaxis() - coordinate.getYaxis();
        if ((xAxisDiff > 0 && direction.matches("down")) || (xAxisDiff < 0 && direction.matches("up"))) {
            System.out.println("Pawn must move forward");
            return false;
        }
        if (hasMoved&&UtilClass.modulo(xAxisDiff)==2){
            System.out.println("Pawn can't move 2 spaces if it has already moved");
            return false;
        }
        if(UtilClass.modulo(yAxisDiff)>1||UtilClass.modulo(xAxisDiff)>2||(yAxisDiff!=0&&xAxisDiff!=1)||xAxisDiff==0){
            System.out.println("Illegal - Pawn move");
        }
        if (UtilClass.modulo(yAxisDiff)==1){
            if (chessBoard.getPieceByLocation(finishPosition)==null){
                int yAxis = finishPosition.getYaxis();
                finishPosition.setYaxis(coordinate.getYaxis());
                ChessPiece chessPiece = chessBoard.getPieceByLocation(finishPosition);
                finishPosition.setYaxis(yAxis);
                if(chessPiece!=null){
                    if (!chessPiece.getType().matches("pawn")){
                        System.out.println("Illegal - Pawn move");
                        return false;
                    }
                } else {
                    System.out.println("Illegal - Pawn move");
                    return false;
                }
            }
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

    public String getDirection() {
        return direction;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }


    public ChessPiece movePiece(ChessBoard chessBoard, Coordinate finishPosition) throws IllegalMoveException {
        if (validateMove(chessBoard,finishPosition)){
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
