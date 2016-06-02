package com.ciaran.upskill.chessgame;

import com.ciaran.upskill.chessgame.domain.Bishop;
import com.ciaran.upskill.chessgame.domain.ChessPiece;
import com.ciaran.upskill.chessgame.domain.Coordinate;
import com.ciaran.upskill.chessgame.domain.King;
import com.ciaran.upskill.chessgame.domain.Knight;
import com.ciaran.upskill.chessgame.domain.Pawn;
import com.ciaran.upskill.chessgame.domain.Queen;
import com.ciaran.upskill.chessgame.domain.Rook;

import java.util.ArrayList;

public class ChessBoard {

    ArrayList<ChessPiece> piecesOnBoard;

    public ChessBoard (){
        piecesOnBoard = new ArrayList<ChessPiece>();

    }

    public void newGame(){
        piecesOnBoard.add(new Pawn(new Coordinate('B', '1'), "white", "up"));
        piecesOnBoard.add(new Pawn(new Coordinate('B', '2'), "white", "up"));
        piecesOnBoard.add(new Pawn(new Coordinate('B', '3'), "white", "up"));
        piecesOnBoard.add(new Pawn(new Coordinate('B', '4'), "white", "up"));
        piecesOnBoard.add(new Pawn(new Coordinate('B', '5'), "white", "up"));
        piecesOnBoard.add(new Pawn(new Coordinate('B', '6'), "white", "up"));
        piecesOnBoard.add(new Pawn(new Coordinate('B', '7'), "white", "up"));
        piecesOnBoard.add(new Pawn(new Coordinate('B', '8'), "white", "up"));
        piecesOnBoard.add(new Rook(new Coordinate('A', '1'), "white"));
        piecesOnBoard.add(new Rook(new Coordinate('A', '8'), "white"));
        piecesOnBoard.add(new Knight(new Coordinate('A', '2'), "white"));
        piecesOnBoard.add(new Knight(new Coordinate('A', '7'), "white"));
        piecesOnBoard.add(new Bishop(new Coordinate('A', '3'), "white"));
        piecesOnBoard.add(new Bishop(new Coordinate('A', '6'), "white"));
        piecesOnBoard.add(new King(new Coordinate('A', '4'), "white"));
        piecesOnBoard.add(new Queen(new Coordinate('A', '5'), "white"));
        piecesOnBoard.add(new Pawn(new Coordinate('G', '1'), "black", "down"));
        piecesOnBoard.add(new Pawn(new Coordinate('G', '2'), "black", "down"));
        piecesOnBoard.add(new Pawn(new Coordinate('G', '3'), "black", "down"));
        piecesOnBoard.add(new Pawn(new Coordinate('G', '4'), "black", "down"));
        piecesOnBoard.add(new Pawn(new Coordinate('G', '5'), "black", "down"));
        piecesOnBoard.add(new Pawn(new Coordinate('G', '6'), "black", "down"));
        piecesOnBoard.add(new Pawn(new Coordinate('G', '7'), "black", "down"));
        piecesOnBoard.add(new Pawn(new Coordinate('G', '8'), "black", "down"));
        piecesOnBoard.add(new Rook(new Coordinate('H', '1'), "black"));
        piecesOnBoard.add(new Rook(new Coordinate('H', '8'), "black"));
        piecesOnBoard.add(new Knight(new Coordinate('H', '2'), "black"));
        piecesOnBoard.add(new Knight(new Coordinate('H', '7'), "black"));
        piecesOnBoard.add(new Bishop(new Coordinate('H', '3'), "black"));
        piecesOnBoard.add(new Bishop(new Coordinate('H', '6'), "black"));
        piecesOnBoard.add(new King(new Coordinate('H', '4'), "black"));
        piecesOnBoard.add(new Queen(new Coordinate('H', '5'), "black"));
    }

    public ChessPiece getPieceByLocation(Coordinate coordinate){
        for(ChessPiece chesspiece : piecesOnBoard){
            if (coordinate.equals(chesspiece.getCoordinate())){
                return chesspiece;
            };
        }
        return null;
    }

    public boolean movePiece(Coordinate startPosition, Coordinate finishPosition, String colour){
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
            chessPiece.setCoordinate(startPosition);
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
        if(getPieceByLocation(chesspiece.getCoordinate())!= null){
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
