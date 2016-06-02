package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.ChessBoard;
import com.ciaran.upskill.chessgame.IllegalMoveException;
import com.ciaran.upskill.chessgame.UtilClass;

import static com.ciaran.upskill.chessgame.UtilClass.modulo;

public class Pawn extends ChessPiece {

    private final Coordinate startPosition;
    private String direction;
    private boolean hasMoved;

    public Pawn(Coordinate coordinate, String colour, String direction){
        this.type = "pawn";
        this.coordinate = coordinate;
        this.startPosition = coordinate;
        this.colour = colour;
        this.direction = direction;
        hasMoved = false;
    }

    public boolean validateMove(ChessBoard chessBoard, Coordinate finishPosition) {
        int xAxisDiff = finishPosition.getXaxis() - coordinate.getXaxis();
        int yAxisDiff = finishPosition.getYaxis() - coordinate.getYaxis();
        if(modulo(xAxisDiff)>1|| modulo(yAxisDiff)>2||(xAxisDiff!=0&&yAxisDiff!=1)||yAxisDiff==0){
            System.out.println("Illegal - Pawn move");
            return false;
        }
        if ((yAxisDiff > 0 && direction.matches("down")) || (yAxisDiff < 0 && direction.matches("up"))) {
            System.out.println("Pawn must move forward");
            return false;
        }
        if (modulo(xAxisDiff)==1){
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
                    if (modulo(coordinate.getYaxis()-startPosition.getYaxis())!=3){
                        System.out.println("Illegal - Pawn move");
                        return false;
                    }
                } else {
                    System.out.println("Illegal - Pawn move");
                    return false;
                }
            }
        }
        if(modulo(xAxisDiff)==0 && chessBoard.getPieceByLocation(finishPosition)!=null){
            System.out.println("Illegal - Pawn move, you can only pieces diagonally");
            return false;
        }
        if (modulo(yAxisDiff)==2) {
            if (hasMoved) {
                System.out.println("Pawn can't move 2 spaces if it has already moved");
                return false;
            }
            Coordinate roamingCoordinate = new Coordinate('A', '1');
            roamingCoordinate.setXaxis(coordinate.getXaxis());
            roamingCoordinate.setYaxis(coordinate.getYaxis());
            while (!roamingCoordinate.equals(finishPosition)) {
                if (!roamingCoordinate.equals(coordinate)) {
                    if (chessBoard.getPieceByLocation(roamingCoordinate) != null) {
                        return false;
                    }
                }
                if (yAxisDiff > 0) {
                    roamingCoordinate.moveYAxisUp();
                } else {
                    roamingCoordinate.moveYAxisDown();
                }
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
