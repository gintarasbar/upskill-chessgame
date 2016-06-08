package com.ciaran.upskill.chessgame.domain.chesspiece;

import com.ciaran.upskill.chessgame.domain.ChessBoard;
import com.ciaran.upskill.chessgame.exceptions.IllegalMoveException;
import com.ciaran.upskill.chessgame.UtilClass;
import com.ciaran.upskill.chessgame.domain.BoardCell;

import static com.ciaran.upskill.chessgame.UtilClass.modulo;

public class Rook extends ChessPiece {

    private  boolean moved;

    public Rook (BoardCell boardCell, String colour){
        this.type = "Rook";
        this.boardCell = boardCell;
        this.colour = colour;
        moved = false;
    }


    public boolean validateMove(ChessBoard chessBoard, BoardCell finishPosition) {
        int xAxisDiff = finishPosition.getXaxis()- boardCell.getXaxis();
        int yAxisDiff = finishPosition.getYaxis()- boardCell.getYaxis();
        if (modulo(xAxisDiff)>0&& modulo(yAxisDiff)>0){
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
            if(xAxisDiff==0){
                roamingBoardCell.increment(0,yAxisDiff/modulo(yAxisDiff));
            } else {
                roamingBoardCell.increment(xAxisDiff/modulo(xAxisDiff),0);
            }
        }
        return true;
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
        moved = true;
        return removedPiece;
    }

    public boolean hasMoved() {
        return moved;
    }
}
