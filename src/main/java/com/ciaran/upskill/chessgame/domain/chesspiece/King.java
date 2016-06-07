package com.ciaran.upskill.chessgame.domain.chesspiece;

import com.ciaran.upskill.chessgame.domain.ChessBoard;
import com.ciaran.upskill.chessgame.exceptions.IllegalMoveException;
import com.ciaran.upskill.chessgame.domain.BoardCell;

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
        if (chessboard.findPiecesOneMoveAway(boardCell, colour).isEmpty()){
            return false;
        }
        return true;
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


    public ChessPiece move(ChessBoard chessBoard, BoardCell finishPosition) throws IllegalMoveException {
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
