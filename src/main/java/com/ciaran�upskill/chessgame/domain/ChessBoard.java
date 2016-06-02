package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.IllegalMoveException;
import com.ciaran.upskill.chessgame.domain.chesspiece.Bishop;
import com.ciaran.upskill.chessgame.domain.chesspiece.ChessPiece;
import com.ciaran.upskill.chessgame.domain.chesspiece.King;
import com.ciaran.upskill.chessgame.domain.chesspiece.Knight;
import com.ciaran.upskill.chessgame.domain.chesspiece.Pawn;
import com.ciaran.upskill.chessgame.domain.chesspiece.Queen;
import com.ciaran.upskill.chessgame.domain.chesspiece.Rook;

import java.util.ArrayList;

public class ChessBoard {

    ArrayList<ChessPiece> piecesOnBoard;

    public ChessBoard (){
        piecesOnBoard = new ArrayList<ChessPiece>();

    }

    public void newGame(){
        piecesOnBoard.add(new Pawn(new BoardCell(2,1), "white", "up"));
        piecesOnBoard.add(new Pawn(new BoardCell(2,2), "white", "up"));
        piecesOnBoard.add(new Pawn(new BoardCell(2,3), "white", "up"));
        piecesOnBoard.add(new Pawn(new BoardCell(2,4), "white", "up"));
        piecesOnBoard.add(new Pawn(new BoardCell(2,5), "white", "up"));
        piecesOnBoard.add(new Pawn(new BoardCell(2,6), "white", "up"));
        piecesOnBoard.add(new Pawn(new BoardCell(2,7), "white", "up"));
        piecesOnBoard.add(new Pawn(new BoardCell(2,8), "white", "up"));
        piecesOnBoard.add(new Rook(new BoardCell(1,1), "white"));
        piecesOnBoard.add(new Rook(new BoardCell(1,8), "white"));
        piecesOnBoard.add(new Knight(new BoardCell(1,2), "white"));
        piecesOnBoard.add(new Knight(new BoardCell(1,7), "white"));
        piecesOnBoard.add(new Bishop(new BoardCell(1,3), "white"));
        piecesOnBoard.add(new Bishop(new BoardCell(1,6), "white"));
        piecesOnBoard.add(new King(new BoardCell(1,4), "white"));
        piecesOnBoard.add(new Queen(new BoardCell(1,5), "white"));
        piecesOnBoard.add(new Pawn(new BoardCell(7,1), "black", "down"));
        piecesOnBoard.add(new Pawn(new BoardCell(7,2), "black", "down"));
        piecesOnBoard.add(new Pawn(new BoardCell(7,3), "black", "down"));
        piecesOnBoard.add(new Pawn(new BoardCell(7,4), "black", "down"));
        piecesOnBoard.add(new Pawn(new BoardCell(7,5), "black", "down"));
        piecesOnBoard.add(new Pawn(new BoardCell(7,6), "black", "down"));
        piecesOnBoard.add(new Pawn(new BoardCell(7,7), "black", "down"));
        piecesOnBoard.add(new Pawn(new BoardCell(7,8), "black", "down"));
        piecesOnBoard.add(new Rook(new BoardCell(8,1), "black"));
        piecesOnBoard.add(new Rook(new BoardCell(8,8), "black"));
        piecesOnBoard.add(new Knight(new BoardCell(8,2), "black"));
        piecesOnBoard.add(new Knight(new BoardCell(8,7), "black"));
        piecesOnBoard.add(new Bishop(new BoardCell(8,3), "black"));
        piecesOnBoard.add(new Bishop(new BoardCell(8,6), "black"));
        piecesOnBoard.add(new King(new BoardCell(8,4), "black"));
        piecesOnBoard.add(new Queen(new BoardCell(8,5), "black"));
    }

    public ChessPiece getPieceByLocation(BoardCell boardCell){
        for(ChessPiece chesspiece : piecesOnBoard){
            if (boardCell.equals(chesspiece.getBoardCell())){
                return chesspiece;
            };
        }
        return null;
    }

    public boolean movePiece(BoardCell startPosition, BoardCell finishPosition, String colour){
        ChessPiece chessPiece = getPieceByLocation(startPosition);
        //Check moving corrrect colour piece
        if(!chessPiece.getColour().matches(colour)){
            System.out.println("This move is invalid - you can only move a piece of your own colour!");
            return false;
        }
        if(startPosition.equals(finishPosition)){
            System.out.println("This move is invalid - you must move a piece!");
            return false;
        }
        ChessPiece removedPiece = null;
        try {
            removedPiece = chessPiece.movePiece(this, finishPosition);
        } catch (IllegalMoveException e) {
            System.out.println(e.getMessage());
            return false;
        }
        if(getKing(colour).isInCheck(this)){
            System.out.println("This move is invalid - you can't put your own king in check");
            chessPiece.setBoardCell(startPosition);
            if(removedPiece != null){
                addPiece(removedPiece);
            }
            return false;
        }
        return true;
    }

    public King getKing(String colour) {
        for(ChessPiece chesspiece : piecesOnBoard){
            if (chesspiece.getType().matches("king")){
                if(chesspiece.getColour().matches(colour)){
                    return (King) chesspiece;
                }

            }
        }
        return null;
    }

    public boolean removePiece(ChessPiece chesspiece) {
        int index = piecesOnBoard.indexOf(chesspiece);
        if (index == -1){
            System.out.println("Can't remove piece -  piece not found");
            return false;
        }
        piecesOnBoard.remove(index);
        return true;
    }

    public boolean addPiece(ChessPiece chesspiece) {
        if(getPieceByLocation(chesspiece.getBoardCell())!= null){
            System.out.println("Can't add piece - a chesspiece already exists in this space");
            return false;
        }
        piecesOnBoard.add(chesspiece);
        return true;
    }

    //for testing
    public boolean contains(ChessPiece chessPiece){
        for (ChessPiece pieceOnBoard : piecesOnBoard){
            if (pieceOnBoard.equals(chessPiece)){
                return true;
            }
        }
        return false;
    }
}
