package com.ciaran.upskill.chessgame.domain.chesspiece;

import com.ciaran.upskill.chessgame.domain.ChessBoard;
import com.ciaran.upskill.chessgame.IllegalMoveException;
import com.ciaran.upskill.chessgame.UtilClass;
import com.ciaran.upskill.chessgame.domain.BoardCell;

public class Rook extends ChessPiece {

    private  boolean moved;

    public Rook (BoardCell boardCell, String colour){
        this.type = "rook";
        this.boardCell = boardCell;
        this.colour = colour;
        moved = false;
    }


    public boolean validateMove(ChessBoard chessBoard, BoardCell finishPosition) {
        int xAxisDiff = finishPosition.getXaxis()- boardCell.getXaxis();
        int yAxisDiff = finishPosition.getYaxis()- boardCell.getYaxis();
        if (UtilClass.modulo(xAxisDiff)>0&&UtilClass.modulo(yAxisDiff)>0){
            System.out.println("Rook must move along only 1 axis");
            return false;
        }
        BoardCell roamingBoardCell = new BoardCell('A', '1');
        roamingBoardCell.setXaxis(boardCell.getXaxis());
        roamingBoardCell.setYaxis(boardCell.getYaxis());
        while(!roamingBoardCell.equals(finishPosition)){
            if(!roamingBoardCell.equals(boardCell)){
                if (chessBoard.getPieceByLocation(roamingBoardCell)!=null){
                    return false;
                }
            }
            if (xAxisDiff>0){
                roamingBoardCell.moveXAxisUp();
            } else if (xAxisDiff<0) {
                roamingBoardCell.moveXAxisDown();
            }
            if (yAxisDiff>0){
                roamingBoardCell.moveYAxisUp();
            } else if (yAxisDiff<0){
                roamingBoardCell.moveYAxisDown();
            }
        }
        return true;
    }


    public ChessPiece movePiece(ChessBoard chessBoard, BoardCell finishPosition) throws IllegalMoveException {
        if (!validateMove(chessBoard,finishPosition)){
            throw new IllegalMoveException();
        };
        ChessPiece removedPiece = chessBoard.getPieceByLocation(finishPosition);
        if(removedPiece!=null){
            chessBoard.removePiece(removedPiece);
        }
        setBoardCell(finishPosition);
        moved = true;
        return removedPiece;
    }

    public boolean hasMoved() {
        return moved;
    }
}
