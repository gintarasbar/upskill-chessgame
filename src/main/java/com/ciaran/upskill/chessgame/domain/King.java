package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.ChessBoard;
import com.ciaran.upskill.chessgame.IllegalMoveException;
import com.ciaran.upskill.chessgame.UtilClass;

import java.util.ArrayList;

public class King extends ChessPiece {

    boolean moved;

    public King(Coordinate coordinate, String colour){
        this.type = "king";
        this.coordinate = coordinate;
        this.colour = colour;
        moved = false;
    }


    public boolean isInCheck(ChessBoard chessboard) {
        Coordinate roamingCoordinate = new Coordinate('A', '1');

        //CheckHorizontal
        roamingCoordinate.setXaxis(this.getCoordinate().getXaxis());
        roamingCoordinate.setYaxis(this.getCoordinate().getYaxis());
        ChessPiece chessPiece = null;
        roamingCoordinate.moveXAxisDown();
        while(roamingCoordinate.getXaxis()>0&&chessPiece==null){
            chessPiece = chessboard.getPieceByLocation(roamingCoordinate);
            if (chessPiece != null){
                if(!chessPiece.getColour().matches(colour)){
                    if(chessPiece.getType().matches("queen")||chessPiece.getType().matches("rook")){
                        return true;
                    }
                }
            }
            roamingCoordinate.moveXAxisDown();
        }
        roamingCoordinate.setXaxis(this.getCoordinate().getXaxis());
        roamingCoordinate.setYaxis(this.getCoordinate().getYaxis());
        chessPiece = null;
        roamingCoordinate.moveXAxisUp();
        while(roamingCoordinate.getXaxis()<9&&chessPiece==null){
            chessPiece = chessboard.getPieceByLocation(roamingCoordinate);
            if (chessPiece != null){
                if(!chessPiece.getColour().matches(colour)){
                    if(chessPiece.getType().matches("queen")||chessPiece.getType().matches("rook")){
                        return true;
                    }
                }
            }
            roamingCoordinate.moveXAxisUp();
        }

        //CheckVertical
        roamingCoordinate.setXaxis(this.getCoordinate().getXaxis());
        roamingCoordinate.setYaxis(this.getCoordinate().getYaxis());
        chessPiece = null;
        roamingCoordinate.moveYAxisDown();
        while(roamingCoordinate.getYaxis()>0&&chessPiece==null){
            chessPiece = chessboard.getPieceByLocation(roamingCoordinate);
            if (chessPiece != null){
                if(!chessPiece.getColour().matches(colour)){
                    if(chessPiece.getType().matches("queen")||chessPiece.getType().matches("rook")){
                        return true;
                    }
                }
            }
            roamingCoordinate.moveYAxisDown();
        }
        roamingCoordinate.setXaxis(this.getCoordinate().getXaxis());
        roamingCoordinate.setYaxis(this.getCoordinate().getYaxis());
        chessPiece = null;
        roamingCoordinate.moveYAxisUp();
        while(roamingCoordinate.getYaxis()<9&&chessPiece==null){
            chessPiece = chessboard.getPieceByLocation(roamingCoordinate);
            if (chessPiece != null){
                if(!chessPiece.getColour().matches(colour)){
                    if(chessPiece.getType().matches("queen")||chessPiece.getType().matches("rook")){
                        return true;
                    }
                }
            }
            roamingCoordinate.moveYAxisUp();
        }
        //CheckDiagonals
        //Down&Left
        roamingCoordinate.setXaxis(this.getCoordinate().getXaxis());
        roamingCoordinate.setYaxis(this.getCoordinate().getYaxis());
        chessPiece = null;
        roamingCoordinate.moveXAxisDown();
        roamingCoordinate.moveYAxisDown();
        int i = 1;
        while(roamingCoordinate.getXaxis()>0&&roamingCoordinate.getYaxis()>0&&chessPiece==null){
            chessPiece = chessboard.getPieceByLocation(roamingCoordinate);
            if (chessPiece != null){
                if(!chessPiece.getColour().matches(colour)){
                    if(chessPiece.getType().matches("queen")||chessPiece.getType().matches("bishop")){
                        return true;
                    }
                    if(i==1){
                        if(chessPiece.getType().matches("pawn")) {
                            Pawn pawn = (Pawn) chessPiece;
                            if (pawn.getDirection().matches("up")){
                                return true;
                            }
                        }
                    }
                }
            }
            roamingCoordinate.moveXAxisDown();
            roamingCoordinate.moveYAxisDown();
            i++;
        }
        //Down&&Right
        roamingCoordinate.setXaxis(this.getCoordinate().getXaxis());
        roamingCoordinate.setYaxis(this.getCoordinate().getYaxis());
        chessPiece = null;
        roamingCoordinate.moveXAxisDown();
        roamingCoordinate.moveYAxisUp();
        i = 1;
        while(roamingCoordinate.getXaxis()>0&&roamingCoordinate.getYaxis()<9&&chessPiece==null){
            chessPiece = chessboard.getPieceByLocation(roamingCoordinate);
            if (chessPiece != null){
                if(!chessPiece.getColour().matches(colour)){
                    if(chessPiece.getType().matches("queen")||chessPiece.getType().matches("bishop")){
                        return true;
                    }
                    if(i==1){
                        if(chessPiece.getType().matches("pawn")) {
                            Pawn pawn = (Pawn) chessPiece;
                            if (pawn.getDirection().matches("down")){
                                return true;
                            }
                        }
                    }
                }
            }
            roamingCoordinate.moveXAxisDown();
            roamingCoordinate.moveYAxisUp();
            i++;
        }
        //Up&Left
        roamingCoordinate.setXaxis(this.getCoordinate().getXaxis());
        roamingCoordinate.setYaxis(this.getCoordinate().getYaxis());
        chessPiece = null;
        roamingCoordinate.moveXAxisUp();
        roamingCoordinate.moveYAxisDown();
        i = 1;
        while(roamingCoordinate.getXaxis()<9&&roamingCoordinate.getYaxis()>0&&chessPiece==null){
            chessPiece = chessboard.getPieceByLocation(roamingCoordinate);
            if (chessPiece != null){
                if(!chessPiece.getColour().matches(colour)){
                    if(chessPiece.getType().matches("queen")||chessPiece.getType().matches("bishop")){
                        return true;
                    }
                    if(i==1){
                        if(chessPiece.getType().matches("pawn")) {
                            Pawn pawn = (Pawn) chessPiece;
                            if (pawn.getDirection().matches("up")){
                                return true;
                            }
                        }
                    }
                }
            }
            roamingCoordinate.moveXAxisUp();
            roamingCoordinate.moveYAxisDown();
            i++;
        }
        roamingCoordinate.setXaxis(this.getCoordinate().getXaxis());
        roamingCoordinate.setYaxis(this.getCoordinate().getYaxis());
        chessPiece = null;
        roamingCoordinate.moveXAxisUp();
        roamingCoordinate.moveYAxisUp();
        i = 1;
        while(roamingCoordinate.getXaxis()<9&&roamingCoordinate.getYaxis()<9&&chessPiece==null){
            chessPiece = chessboard.getPieceByLocation(roamingCoordinate);
            if (chessPiece != null){
                if(!chessPiece.getColour().matches(colour)){
                    if(chessPiece.getType().matches("queen")||chessPiece.getType().matches("bishop")){
                        return true;
                    }
                    if(i==1){
                        if(chessPiece.getType().matches("pawn")) {
                            Pawn pawn = (Pawn) chessPiece;
                            if (pawn.getDirection().matches("down")){
                                return true;
                            }
                        }
                    }
                }
            }
            roamingCoordinate.moveXAxisUp();
            roamingCoordinate.moveYAxisUp();
            i++;
        }
        //CheckForKnights
        ArrayList<Coordinate> coordinateArrayList = new ArrayList<Coordinate>();
        coordinateArrayList.add(new Coordinate(this.coordinate.getXaxis()+1,this.coordinate.getYaxis()+2));
        coordinateArrayList.add(new Coordinate(this.coordinate.getXaxis()+1,this.coordinate.getYaxis()-2));
        coordinateArrayList.add(new Coordinate(this.coordinate.getXaxis()-1,this.coordinate.getYaxis()+2));
        coordinateArrayList.add(new Coordinate(this.coordinate.getXaxis()-1,this.coordinate.getYaxis()-2));
        coordinateArrayList.add(new Coordinate(this.coordinate.getXaxis()+2,this.coordinate.getYaxis()+1));
        coordinateArrayList.add(new Coordinate(this.coordinate.getXaxis()+2,this.coordinate.getYaxis()-1));
        coordinateArrayList.add(new Coordinate(this.coordinate.getXaxis()-2,this.coordinate.getYaxis()+1));
        coordinateArrayList.add(new Coordinate(this.coordinate.getXaxis()-2,this.coordinate.getYaxis()-1));
        for (Coordinate coordinate : coordinateArrayList){
            chessPiece = chessboard.getPieceByLocation(coordinate);
            if(chessPiece!=null){
                if(!chessPiece.getColour().matches(colour)) {
                    if (chessPiece.getType().matches("knight")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean validateMove(ChessBoard chessBoard, Coordinate finishPosition) {
        int xAxisDiff = finishPosition.getXaxis()-coordinate.getXaxis();
        int yAxisDiff = finishPosition.getYaxis()-coordinate.getYaxis();
        if (UtilClass.modulo(xAxisDiff)==2){
            if (UtilClass.modulo(yAxisDiff)>0){
                return false;
            }
            Coordinate rookCoordinate = new Coordinate('A', '1');
            rookCoordinate.setYaxis(coordinate.getYaxis());
            if(xAxisDiff>0){
                rookCoordinate.setXaxis(8);
            } else {
                rookCoordinate.setXaxis(1);
            }
            ChessPiece chessPiece = chessBoard.getPieceByLocation(rookCoordinate);
            if (!chessPiece.getType().matches("rook")){
                return false;
            }
            Rook rook = (Rook) chessPiece;
            if (rook.hasMoved()||hasMoved()){
                return false;
            }
            Coordinate roamingCoordinate = new Coordinate('A', '1');
            roamingCoordinate.setXaxis(coordinate.getXaxis());
            roamingCoordinate.setYaxis(coordinate.getYaxis());
            while(roamingCoordinate!=rookCoordinate){
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
            }

        }
        return true;
    }

    private boolean hasMoved() {
        return moved;
    }


    public ChessPiece movePiece(ChessBoard chessBoard, Coordinate finishPosition) throws IllegalMoveException {
        validateMove(chessBoard,finishPosition);
        int xAxisDiff = finishPosition.getXaxis() - coordinate.getXaxis();
        if (UtilClass.modulo(xAxisDiff)==2){
            for(int i = 0; i<2;i++) {
                if (xAxisDiff > 0) {
                    coordinate.moveXAxisUp();
                    if (isInCheck(chessBoard)) {
                        throw new IllegalMoveException();
                    }
                } else {
                    coordinate.moveXAxisDown();
                    if (isInCheck(chessBoard)) {
                        throw new IllegalMoveException();
                    }
                }
            }

        }else {
            if (isInCheck(chessBoard)) {
                throw new IllegalMoveException();
            }
        }
        ChessPiece removedPiece = chessBoard.getPieceByLocation(finishPosition);
        if(removedPiece!=null){
            chessBoard.removePiece(removedPiece);
        }
        moved = true;
        return removedPiece;
    }


}
