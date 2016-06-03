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

    /*public ArrayList<ChessPiece> findPiecesOneMoveAway(BoardCell boardCell, String colour){
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
    }*/

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
