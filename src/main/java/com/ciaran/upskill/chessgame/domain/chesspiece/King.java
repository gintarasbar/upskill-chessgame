package com.ciaran.upskill.chessgame.domain.chesspiece;

import com.ciaran.upskill.chessgame.domain.ChessBoard;
import com.ciaran.upskill.chessgame.IllegalMoveException;
import com.ciaran.upskill.chessgame.domain.BoardCell;

import java.util.ArrayList;

import static com.ciaran.upskill.chessgame.UtilClass.modulo;

public class King extends ChessPiece {

    boolean moved;

    public King(BoardCell boardCell, String colour){
        this.type = "king";
        this.boardCell = boardCell;
        this.colour = colour;
        moved = false;
    }


    public boolean isInCheck(ChessBoard chessboard) {
        BoardCell roamingBoardCell = new BoardCell('A', '1');

        //CheckHorizontal
        roamingBoardCell.setXaxis(this.getBoardCell().getXaxis());
        roamingBoardCell.setYaxis(this.getBoardCell().getYaxis());
        ChessPiece chessPiece = null;
        roamingBoardCell.moveXAxisDown();
        while(roamingBoardCell.getXaxis()>0&&chessPiece==null){
            chessPiece = chessboard.getPieceByLocation(roamingBoardCell);
            if (chessPiece != null){
                if(!chessPiece.getColour().matches(colour)){
                    if(chessPiece.getType().matches("queen")||chessPiece.getType().matches("rook")){
                        return true;
                    }
                }
            }
            roamingBoardCell.moveXAxisDown();
        }
        roamingBoardCell.setXaxis(this.getBoardCell().getXaxis());
        roamingBoardCell.setYaxis(this.getBoardCell().getYaxis());
        chessPiece = null;
        roamingBoardCell.moveXAxisUp();
        while(roamingBoardCell.getXaxis()<9&&chessPiece==null){
            chessPiece = chessboard.getPieceByLocation(roamingBoardCell);
            if (chessPiece != null){
                if(!chessPiece.getColour().matches(colour)){
                    if(chessPiece.getType().matches("queen")||chessPiece.getType().matches("rook")){
                        return true;
                    }
                }
            }
            roamingBoardCell.moveXAxisUp();
        }

        //CheckVertical
        roamingBoardCell.setXaxis(this.getBoardCell().getXaxis());
        roamingBoardCell.setYaxis(this.getBoardCell().getYaxis());
        chessPiece = null;
        roamingBoardCell.moveYAxisDown();
        while(roamingBoardCell.getYaxis()>0&&chessPiece==null){
            chessPiece = chessboard.getPieceByLocation(roamingBoardCell);
            if (chessPiece != null){
                if(!chessPiece.getColour().matches(colour)){
                    if(chessPiece.getType().matches("queen")||chessPiece.getType().matches("rook")){
                        return true;
                    }
                }
            }
            roamingBoardCell.moveYAxisDown();
        }
        roamingBoardCell.setXaxis(this.getBoardCell().getXaxis());
        roamingBoardCell.setYaxis(this.getBoardCell().getYaxis());
        chessPiece = null;
        roamingBoardCell.moveYAxisUp();
        while(roamingBoardCell.getYaxis()<9&&chessPiece==null){
            chessPiece = chessboard.getPieceByLocation(roamingBoardCell);
            if (chessPiece != null){
                if(!chessPiece.getColour().matches(colour)){
                    if(chessPiece.getType().matches("queen")||chessPiece.getType().matches("rook")){
                        return true;
                    }
                }
            }
            roamingBoardCell.moveYAxisUp();
        }
        //CheckDiagonals
        //Down&Left
        roamingBoardCell.setXaxis(this.getBoardCell().getXaxis());
        roamingBoardCell.setYaxis(this.getBoardCell().getYaxis());
        chessPiece = null;
        roamingBoardCell.moveXAxisDown();
        roamingBoardCell.moveYAxisDown();
        int i = 1;
        while(roamingBoardCell.getXaxis()>0&& roamingBoardCell.getYaxis()>0&&chessPiece==null){
            chessPiece = chessboard.getPieceByLocation(roamingBoardCell);
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
            roamingBoardCell.moveXAxisDown();
            roamingBoardCell.moveYAxisDown();
            i++;
        }
        //Down&&Right
        roamingBoardCell.setXaxis(this.getBoardCell().getXaxis());
        roamingBoardCell.setYaxis(this.getBoardCell().getYaxis());
        chessPiece = null;
        roamingBoardCell.moveXAxisDown();
        roamingBoardCell.moveYAxisUp();
        i = 1;
        while(roamingBoardCell.getXaxis()>0&& roamingBoardCell.getYaxis()<9&&chessPiece==null){
            chessPiece = chessboard.getPieceByLocation(roamingBoardCell);
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
            roamingBoardCell.moveXAxisDown();
            roamingBoardCell.moveYAxisUp();
            i++;
        }
        //Up&Left
        roamingBoardCell.setXaxis(this.getBoardCell().getXaxis());
        roamingBoardCell.setYaxis(this.getBoardCell().getYaxis());
        chessPiece = null;
        roamingBoardCell.moveXAxisUp();
        roamingBoardCell.moveYAxisDown();
        i = 1;
        while(roamingBoardCell.getXaxis()<9&& roamingBoardCell.getYaxis()>0&&chessPiece==null){
            chessPiece = chessboard.getPieceByLocation(roamingBoardCell);
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
            roamingBoardCell.moveXAxisUp();
            roamingBoardCell.moveYAxisDown();
            i++;
        }
        //Up&Right
        roamingBoardCell.setXaxis(this.getBoardCell().getXaxis());
        roamingBoardCell.setYaxis(this.getBoardCell().getYaxis());
        chessPiece = null;
        roamingBoardCell.moveXAxisUp();
        roamingBoardCell.moveYAxisUp();
        i = 1;
        while(roamingBoardCell.getXaxis()<9&& roamingBoardCell.getYaxis()<9&&chessPiece==null){
            chessPiece = chessboard.getPieceByLocation(roamingBoardCell);
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
            roamingBoardCell.moveXAxisUp();
            roamingBoardCell.moveYAxisUp();
            i++;
        }
        //CheckForKnights
        ArrayList<BoardCell> boardCellArrayList = new ArrayList<BoardCell>();
        boardCellArrayList.add(new BoardCell(this.boardCell.getXaxis()+1,this.boardCell.getYaxis()+2));
        boardCellArrayList.add(new BoardCell(this.boardCell.getXaxis()+1,this.boardCell.getYaxis()-2));
        boardCellArrayList.add(new BoardCell(this.boardCell.getXaxis()-1,this.boardCell.getYaxis()+2));
        boardCellArrayList.add(new BoardCell(this.boardCell.getXaxis()-1,this.boardCell.getYaxis()-2));
        boardCellArrayList.add(new BoardCell(this.boardCell.getXaxis()+2,this.boardCell.getYaxis()+1));
        boardCellArrayList.add(new BoardCell(this.boardCell.getXaxis()+2,this.boardCell.getYaxis()-1));
        boardCellArrayList.add(new BoardCell(this.boardCell.getXaxis()-2,this.boardCell.getYaxis()+1));
        boardCellArrayList.add(new BoardCell(this.boardCell.getXaxis()-2,this.boardCell.getYaxis()-1));
        for (BoardCell boardCell : boardCellArrayList){
            chessPiece = chessboard.getPieceByLocation(boardCell);
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

    public boolean validateMove(ChessBoard chessBoard, BoardCell finishPosition) {
        int xAxisDiff = finishPosition.getXaxis()- boardCell.getXaxis();
        int yAxisDiff = finishPosition.getYaxis()- boardCell.getYaxis();
        //Validates Castling
        if (modulo(xAxisDiff)==2){
            if (modulo(yAxisDiff)>0||moved){
                return false;
            }
            BoardCell rookBoardCell = new BoardCell(0, boardCell.getYaxis());
            if(xAxisDiff>0){
                rookBoardCell.setXaxis(8);
            } else {
                rookBoardCell.setXaxis(1);
            }
            ChessPiece chessPiece = chessBoard.getPieceByLocation(rookBoardCell);
            if (!chessPiece.getType().matches("rook")){
                return false;
            }
            Rook rook = (Rook) chessPiece;
            if (rook.hasMoved()){
                return false;
            }
            BoardCell roamingBoardCell = new BoardCell(boardCell.getXaxis(), boardCell.getYaxis());
            while(!roamingBoardCell.equals(rookBoardCell)){
                if(!roamingBoardCell.equals(boardCell)){
                    if (chessBoard.getPieceByLocation(roamingBoardCell)!=null){
                        return false;
                    }
                }
                if (xAxisDiff>0){
                    roamingBoardCell.moveXAxisUp();
                } else {
                    roamingBoardCell.moveXAxisDown();
                }
            }

        } else if (modulo(xAxisDiff)>1||modulo(yAxisDiff)>1){
            return false;
        }
        return true;
    }

    private boolean hasMoved() {
        return moved;
    }


    public ChessPiece movePiece(ChessBoard chessBoard, BoardCell finishPosition) throws IllegalMoveException {
        if (!validateMove(chessBoard,finishPosition)){
            throw new IllegalMoveException();
        };
        int xAxisDiff = finishPosition.getXaxis() - boardCell.getXaxis();
        ChessPiece removedPiece = null;
        if (modulo(xAxisDiff)==2){
            int rookXaxis = (xAxisDiff/modulo(xAxisDiff))+ boardCell.getXaxis();
            for(int i = 0; i<2;i++) {
                if (xAxisDiff > 0) {
                    boardCell.moveXAxisUp();
                    if (isInCheck(chessBoard)) {
                        throw new IllegalMoveException();
                    }
                } else {
                    boardCell.moveXAxisDown();
                    if (isInCheck(chessBoard)) {
                        throw new IllegalMoveException();
                    }
                }
            }
            BoardCell rookBoardCell = new BoardCell(0, boardCell.getYaxis());
            if(xAxisDiff>0){
                rookBoardCell.setXaxis(8);
            } else {
                rookBoardCell.setXaxis(1);
            }
            chessBoard.getPieceByLocation(rookBoardCell).boardCell = new BoardCell(rookXaxis, boardCell.getYaxis());

        }else {
            removedPiece = chessBoard.getPieceByLocation(finishPosition);
            if(removedPiece!=null){
                chessBoard.removePiece(removedPiece);
            }
            boardCell = finishPosition;
        }
        moved = true;
        return removedPiece;
    }


}
