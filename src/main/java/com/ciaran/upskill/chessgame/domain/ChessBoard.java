package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.exceptions.IllegalMoveException;
import com.ciaran.upskill.chessgame.domain.chesspiece.Bishop;
import com.ciaran.upskill.chessgame.domain.chesspiece.ChessPiece;
import com.ciaran.upskill.chessgame.domain.chesspiece.King;
import com.ciaran.upskill.chessgame.domain.chesspiece.Knight;
import com.ciaran.upskill.chessgame.domain.chesspiece.Pawn;
import com.ciaran.upskill.chessgame.domain.chesspiece.Queen;
import com.ciaran.upskill.chessgame.domain.chesspiece.Rook;

import java.util.ArrayList;

public class ChessBoard {

    private ArrayList<ChessPiece> piecesOnBoard;

    public ChessBoard (){
        piecesOnBoard = new ArrayList<ChessPiece>();

    }

    public void setUpBoard(){
        piecesOnBoard.add(new Pawn(new BoardCell(1,2), "white", "up"));
        piecesOnBoard.add(new Pawn(new BoardCell(2,2), "white", "up"));
        piecesOnBoard.add(new Pawn(new BoardCell(3,2), "white", "up"));
        piecesOnBoard.add(new Pawn(new BoardCell(4,2), "white", "up"));
        piecesOnBoard.add(new Pawn(new BoardCell(5,2), "white", "up"));
        piecesOnBoard.add(new Pawn(new BoardCell(6,2), "white", "up"));
        piecesOnBoard.add(new Pawn(new BoardCell(7,2), "white", "up"));
        piecesOnBoard.add(new Pawn(new BoardCell(8,2), "white", "up"));
        piecesOnBoard.add(new Rook(new BoardCell(1,1), "white"));
        piecesOnBoard.add(new Rook(new BoardCell(8,1), "white"));
        piecesOnBoard.add(new Knight(new BoardCell(2,1), "white"));
        piecesOnBoard.add(new Knight(new BoardCell(7,1), "white"));
        piecesOnBoard.add(new Bishop(new BoardCell(3,1), "white"));
        piecesOnBoard.add(new Bishop(new BoardCell(6,1), "white"));
        piecesOnBoard.add(new King(new BoardCell(5,1), "white"));
        piecesOnBoard.add(new Queen(new BoardCell(4,1), "white"));
        piecesOnBoard.add(new Pawn(new BoardCell(1,7), "black", "down"));
        piecesOnBoard.add(new Pawn(new BoardCell(2,7), "black", "down"));
        piecesOnBoard.add(new Pawn(new BoardCell(3,7), "black", "down"));
        piecesOnBoard.add(new Pawn(new BoardCell(4,7), "black", "down"));
        piecesOnBoard.add(new Pawn(new BoardCell(5,7), "black", "down"));
        piecesOnBoard.add(new Pawn(new BoardCell(6,7), "black", "down"));
        piecesOnBoard.add(new Pawn(new BoardCell(7,7), "black", "down"));
        piecesOnBoard.add(new Pawn(new BoardCell(8,7), "black", "down"));
        piecesOnBoard.add(new Rook(new BoardCell(1,8), "black"));
        piecesOnBoard.add(new Rook(new BoardCell(8,8), "black"));
        piecesOnBoard.add(new Knight(new BoardCell(2,8), "black"));
        piecesOnBoard.add(new Knight(new BoardCell(7,8), "black"));
        piecesOnBoard.add(new Bishop(new BoardCell(3,8), "black"));
        piecesOnBoard.add(new Bishop(new BoardCell(6,8), "black"));
        piecesOnBoard.add(new King(new BoardCell(5,8), "black"));
        piecesOnBoard.add(new Queen(new BoardCell(4,8), "black"));
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
        ChessPiece removedPiece = getPieceByLocation(finishPosition);
        if (removedPiece!= null ? removedPiece.getColour().matches(colour) : false)return false;
        try {
            removedPiece = chessPiece.move(this, finishPosition);
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

    public ArrayList<ChessPiece> findPiecesOneMoveAway(BoardCell boardcell, String colour){

        BoardCell roamingBoardCell = null;
        ArrayList<ChessPiece> piecesOneMoveAway = new ArrayList<ChessPiece>();
        ChessPiece chessPiece = null;
        int xModifier = 0;
        int yModifier = 0;

        //CheckHorizontal&Vertical
        for(int i = 0; i<4; i++){
            roamingBoardCell = new BoardCell(boardcell.getXaxis(),boardcell.getYaxis());
            chessPiece = null;
            switch (i){
                case 0: //Left
                    xModifier = 1;
                    yModifier= 0;
                    break;
                case 1: //Right
                    xModifier = -1;
                    yModifier= 0;
                    break;
                case 2: //Up
                    xModifier = 0;
                    yModifier= 1;
                    break;
                case 3: //Down
                    xModifier = 0;
                    yModifier= -1;
                    break;
            }
            roamingBoardCell.increment(xModifier,yModifier);
            int j = 1;
            while(roamingBoardCell.isValid()&&chessPiece==null){
                chessPiece = this.getPieceByLocation(roamingBoardCell);
                if (chessPiece != null){
                    if(!chessPiece.getColour().matches(colour)){
                        if(chessPiece.getType().matches("queen")||chessPiece.getType().matches("rook")){
                            piecesOneMoveAway.add(chessPiece);
                        }
                        if(j==1){
                            if (chessPiece.getType().matches("king")){
                                piecesOneMoveAway.add(chessPiece);
                            }
                        }
                    }
                }
                roamingBoardCell.increment(xModifier,yModifier);
                j++;
            }
        }
        //CheckDiagonals
        for(int i = 0; i<4; i++){
            roamingBoardCell = new BoardCell(boardcell.getXaxis(),boardcell.getYaxis());
            chessPiece = null;
            String direction = null;
            switch (i){
                case 0: //Up&Right
                    xModifier = 1;
                    yModifier= 1;
                    direction = "down";
                    break;
                case 1: //Up&Left
                    xModifier = -1;
                    yModifier= 1;
                    direction = "down";
                    break;
                case 2: //Down&Right
                    xModifier = 1;
                    yModifier= -1;
                    direction = "up";
                    break;
                case 3: //Down&Left
                    xModifier = -1;
                    yModifier= -1;
                    direction = "up";
                    break;
            }
            roamingBoardCell.increment(xModifier,yModifier);
            int j = 1;
            while(roamingBoardCell.isValid()&&chessPiece==null){
                chessPiece = this.getPieceByLocation(roamingBoardCell);
                if (chessPiece != null){
                    if(!chessPiece.getColour().matches(colour)){
                        if(chessPiece.getType().matches("queen")||chessPiece.getType().matches("bishop")){
                            piecesOneMoveAway.add(chessPiece);
                        }
                        if(j==1){
                            if(chessPiece.getType().matches("pawn")) {
                                Pawn pawn = (Pawn) chessPiece;
                                if (pawn.getDirection().matches(direction)){
                                    piecesOneMoveAway.add(chessPiece);
                                }
                            } else if (chessPiece.getType().matches("king")){
                                piecesOneMoveAway.add(chessPiece);
                            }
                        }
                    }
                }
                roamingBoardCell.increment(xModifier,yModifier);
                j++;
            }
        }
        //CheckForKnights
        chessPiece = null;
        ArrayList<BoardCell> boardCellArrayList = new ArrayList<BoardCell>();
        boardCellArrayList.add(new BoardCell(boardcell.getXaxis()+1,boardcell.getYaxis()+2));
        boardCellArrayList.add(new BoardCell(boardcell.getXaxis()+1,boardcell.getYaxis()-2));
        boardCellArrayList.add(new BoardCell(boardcell.getXaxis()-1,boardcell.getYaxis()+2));
        boardCellArrayList.add(new BoardCell(boardcell.getXaxis()-1,boardcell.getYaxis()-2));
        boardCellArrayList.add(new BoardCell(boardcell.getXaxis()+2,boardcell.getYaxis()+1));
        boardCellArrayList.add(new BoardCell(boardcell.getXaxis()+2,boardcell.getYaxis()-1));
        boardCellArrayList.add(new BoardCell(boardcell.getXaxis()-2,boardcell.getYaxis()+1));
        boardCellArrayList.add(new BoardCell(boardcell.getXaxis()-2,boardcell.getYaxis()-1));
        for (BoardCell knightCell : boardCellArrayList){
            if (knightCell.isValid()){
                chessPiece = this.getPieceByLocation(knightCell);
            }
            if(chessPiece!=null){
                if(!chessPiece.getColour().matches(colour)) {
                    if (chessPiece.getType().matches("knight")) {
                        piecesOneMoveAway.add(chessPiece);
                    }
                }
            }
        }
        return piecesOneMoveAway;
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
        BoardCell boardCell = chesspiece.getBoardCell();
        if(getPieceByLocation(boardCell)!= null||!boardCell.isValid()){
            System.out.println("Can't add piece");
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
