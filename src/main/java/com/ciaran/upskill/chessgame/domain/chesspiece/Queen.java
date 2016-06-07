package com.ciaran.upskill.chessgame.domain.chesspiece;

import com.ciaran.upskill.chessgame.domain.ChessBoard;
import com.ciaran.upskill.chessgame.exceptions.IllegalMoveException;
import com.ciaran.upskill.chessgame.UtilClass;
import com.ciaran.upskill.chessgame.domain.BoardCell;

public class Queen extends ChessPiece {

    public Queen(BoardCell boardCell, String colour){
        this.type = "queen";
        this.boardCell = boardCell;
        this.colour = colour;
    }


    public boolean validateMove(ChessBoard chessBoard, BoardCell finishPosition) {
        int xAxisDiff = finishPosition.getXaxis()- boardCell.getXaxis();
        int yAxisDiff = finishPosition.getYaxis()- boardCell.getYaxis();
        if (UtilClass.modulo(xAxisDiff)==UtilClass.modulo(yAxisDiff)||xAxisDiff==0||yAxisDiff==0){
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
                } else if (xAxisDiff<0){
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
        return false;
    }


    public ChessPiece move(ChessBoard chessBoard, BoardCell finishPosition) throws IllegalMoveException {
        if (!validateMove(chessBoard,finishPosition)){
            throw new IllegalMoveException();
        };
        ChessPiece removedPiece = chessBoard.getPieceByLocation(finishPosition);
        if(removedPiece!=null){
            chessBoard.removePiece(removedPiece);
        }
        setBoardCell(finishPosition);
        return removedPiece;
    }
}
