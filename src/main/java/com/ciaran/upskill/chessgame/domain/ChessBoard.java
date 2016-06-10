package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.Colour;
import com.ciaran.upskill.chessgame.UserInterface;
import com.ciaran.upskill.chessgame.exceptions.IllegalMoveException;
import com.ciaran.upskill.chessgame.domain.chesspiece.Bishop;
import com.ciaran.upskill.chessgame.domain.chesspiece.ChessPiece;
import com.ciaran.upskill.chessgame.domain.chesspiece.King;
import com.ciaran.upskill.chessgame.domain.chesspiece.Knight;
import com.ciaran.upskill.chessgame.domain.chesspiece.Pawn;
import com.ciaran.upskill.chessgame.domain.chesspiece.Queen;
import com.ciaran.upskill.chessgame.domain.chesspiece.Rook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static com.ciaran.upskill.chessgame.Colour.BLACK;
import static com.ciaran.upskill.chessgame.Colour.WHITE;
import static com.ciaran.upskill.chessgame.UtilClass.modulo;
import static com.ciaran.upskill.chessgame.UtilClass.switchColour;
import static com.ciaran.upskill.chessgame.domain.chesspiece.ChessPieceType.BISHOP;
import static com.ciaran.upskill.chessgame.domain.chesspiece.ChessPieceType.KING;
import static com.ciaran.upskill.chessgame.domain.chesspiece.ChessPieceType.KNIGHT;
import static com.ciaran.upskill.chessgame.domain.chesspiece.ChessPieceType.PAWN;
import static com.ciaran.upskill.chessgame.domain.chesspiece.ChessPieceType.QUEEN;
import static com.ciaran.upskill.chessgame.domain.chesspiece.ChessPieceType.ROOK;
import static com.ciaran.upskill.chessgame.domain.chesspiece.Pawn.Direction.DOWN;
import static com.ciaran.upskill.chessgame.domain.chesspiece.Pawn.Direction.UP;

public class ChessBoard {

    private Set<ChessPiece> piecesOnBoard;

    public ChessBoard (){
        piecesOnBoard = new HashSet<ChessPiece>();

    }

    public void setUpBoard(){
        piecesOnBoard.add(new Pawn(new BoardCell(1,2), WHITE, UP));
        piecesOnBoard.add(new Pawn(new BoardCell(2,2), WHITE, UP));
        piecesOnBoard.add(new Pawn(new BoardCell(3,2), WHITE, UP));
        piecesOnBoard.add(new Pawn(new BoardCell(4,2), WHITE, UP));
        piecesOnBoard.add(new Pawn(new BoardCell(5,2), WHITE, UP));
        piecesOnBoard.add(new Pawn(new BoardCell(6,2), WHITE, UP));
        piecesOnBoard.add(new Pawn(new BoardCell(7,2), WHITE, UP));
        piecesOnBoard.add(new Pawn(new BoardCell(8,2), WHITE, UP));
        piecesOnBoard.add(new Rook(new BoardCell(1,1), WHITE));
        piecesOnBoard.add(new Rook(new BoardCell(8,1), WHITE));
        piecesOnBoard.add(new Knight(new BoardCell(2,1), WHITE));
        piecesOnBoard.add(new Knight(new BoardCell(7,1), WHITE));
        piecesOnBoard.add(new Bishop(new BoardCell(3,1), WHITE));
        piecesOnBoard.add(new Bishop(new BoardCell(6,1), WHITE));
        piecesOnBoard.add(new King(new BoardCell(5,1), WHITE));
        piecesOnBoard.add(new Queen(new BoardCell(4,1), WHITE));
        piecesOnBoard.add(new Pawn(new BoardCell(1,7), BLACK, DOWN));
        piecesOnBoard.add(new Pawn(new BoardCell(2,7), BLACK, DOWN));
        piecesOnBoard.add(new Pawn(new BoardCell(3,7), BLACK, DOWN));
        piecesOnBoard.add(new Pawn(new BoardCell(4,7), BLACK, DOWN));
        piecesOnBoard.add(new Pawn(new BoardCell(5,7), BLACK, DOWN));
        piecesOnBoard.add(new Pawn(new BoardCell(6,7), BLACK, DOWN));
        piecesOnBoard.add(new Pawn(new BoardCell(7,7), BLACK, DOWN));
        piecesOnBoard.add(new Pawn(new BoardCell(8,7), BLACK, DOWN));
        piecesOnBoard.add(new Rook(new BoardCell(1,8), BLACK));
        piecesOnBoard.add(new Rook(new BoardCell(8,8), BLACK));
        piecesOnBoard.add(new Knight(new BoardCell(2,8), BLACK));
        piecesOnBoard.add(new Knight(new BoardCell(7,8), BLACK));
        piecesOnBoard.add(new Bishop(new BoardCell(3,8), BLACK));
        piecesOnBoard.add(new Bishop(new BoardCell(6,8), BLACK));
        piecesOnBoard.add(new King(new BoardCell(5,8), BLACK));
        piecesOnBoard.add(new Queen(new BoardCell(4,8), BLACK));
    }

    public ChessPiece getPieceByLocation(BoardCell boardCell){
        for(ChessPiece chesspiece : piecesOnBoard){
            if (boardCell.equals(chesspiece.getBoardCell())){
                return chesspiece;
            };
        }
        return null;
    }

    public boolean movePiece(BoardCell startPosition, BoardCell finishPosition, Colour colour){
        ChessPiece chessPiece = getPieceByLocation(startPosition);
        //Check moving corrrect colour piece
        if(!chessPiece.getColour().equals(colour)){
            System.out.println("This move is invalid - you can only move a piece of your own colour!");
            return false;
        }
        if(startPosition.equals(finishPosition)){
            System.out.println("This move is invalid - you must move a piece!");
            return false;
        }
        ChessPiece removedPiece = getPieceByLocation(finishPosition);
        if (removedPiece!= null ? removedPiece.getColour().equals(colour) : false) return false;
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
        if(chessPiece.getType().equals(PAWN)){
            Pawn pawn = (Pawn) chessPiece;
            if((finishPosition.getYaxis()==8&&pawn.getDirection().equals(UP))||(finishPosition.getYaxis()==1&&pawn.getDirection().equals(DOWN))){
                int choice = new UserInterface(this, new Scanner(System.in)).upgradePawn();
                switch (choice){
                    case 1:
                        chessPiece = new Queen(pawn.getBoardCell(), pawn.getColour());
                        break;
                    case 2:
                        chessPiece = new Rook(pawn.getBoardCell(), pawn.getColour());
                        break;
                    case 3:
                        chessPiece = new Bishop(pawn.getBoardCell(), pawn.getColour());
                        break;
                    case 4:
                        chessPiece = new Knight(pawn.getBoardCell(), pawn.getColour());
                        break;
                    default:
                        chessPiece = new Queen(pawn.getBoardCell(), pawn.getColour());
                        break;
                }
                removePiece(pawn);
                addPiece(chessPiece);
            }
        }
        return true;
    }

    public ArrayList<ChessPiece> findPiecesOneMoveAway(BoardCell boardcell, Colour colour){

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
                    if(!chessPiece.getColour().equals(colour)){
                        if(chessPiece.getType().equals(QUEEN)||chessPiece.getType().equals(ROOK)){
                            piecesOneMoveAway.add(chessPiece);
                        }
                        if(j==1){
                            if (chessPiece.getType().equals(KING)){
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
            Pawn.Direction direction = null;
            switch (i){
                case 0: //Up&Right
                    xModifier = 1;
                    yModifier= 1;
                    direction = DOWN;
                    break;
                case 1: //Up&Left
                    xModifier = -1;
                    yModifier= 1;
                    direction = DOWN;
                    break;
                case 2: //Down&Right
                    xModifier = 1;
                    yModifier= -1;
                    direction = UP;
                    break;
                case 3: //Down&Left
                    xModifier = -1;
                    yModifier= -1;
                    direction = UP;
                    break;
            }
            roamingBoardCell.increment(xModifier,yModifier);
            int j = 1;
            while(roamingBoardCell.isValid()&&chessPiece==null){
                chessPiece = this.getPieceByLocation(roamingBoardCell);
                if (chessPiece != null){
                    if(!chessPiece.getColour().equals(colour)){
                        if(chessPiece.getType().equals(QUEEN)||chessPiece.getType().equals(BISHOP)){
                            piecesOneMoveAway.add(chessPiece);
                        }
                        if(j==1){
                            if(chessPiece.getType().equals(PAWN)) {
                                Pawn pawn = (Pawn) chessPiece;
                                if (pawn.getDirection().equals(direction)){
                                    piecesOneMoveAway.add(chessPiece);
                                }
                            } else if (chessPiece.getType().equals(KING)){
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
                if(!chessPiece.getColour().equals(colour)) {
                    if (chessPiece.getType().equals(KNIGHT)) {
                        piecesOneMoveAway.add(chessPiece);
                    }
                }
            }
        }
        return piecesOneMoveAway;
    }

    public King getKing(Colour colour) {
        for(ChessPiece chesspiece : piecesOnBoard){
            if (chesspiece.getType().equals(KING)){
                if(chesspiece.getColour().equals(colour)){
                    return (King) chesspiece;
                }

            }
        }
        return null;
    }

    public boolean isInCheckMate(Colour colour) {
        //is King in check
        King king = getKing(colour);
        BoardCell kingcell = king.getBoardCell();
        ArrayList<ChessPiece> piecesThreateningKing = findPiecesOneMoveAway(king.getBoardCell(), king.getColour());
        Colour opposingColour = switchColour(king.getColour());
        if (piecesThreateningKing.isEmpty()){
            return false;
        }
        //can King move out of check
        ArrayList<BoardCell> kingsMoves = new ArrayList<BoardCell>();
        kingsMoves.add(new BoardCell(kingcell.getXaxis()+1,kingcell.getYaxis()+1));
        kingsMoves.add(new BoardCell(kingcell.getXaxis()+1,kingcell.getYaxis()));
        kingsMoves.add(new BoardCell(kingcell.getXaxis()+1,kingcell.getYaxis()-1));
        kingsMoves.add(new BoardCell(kingcell.getXaxis(),kingcell.getYaxis()+1));
        kingsMoves.add(new BoardCell(kingcell.getXaxis(),kingcell.getYaxis()-1));
        kingsMoves.add(new BoardCell(kingcell.getXaxis()-1,kingcell.getYaxis()+1));
        kingsMoves.add(new BoardCell(kingcell.getXaxis()-1,kingcell.getYaxis()));
        kingsMoves.add(new BoardCell(kingcell.getXaxis()-1,kingcell.getYaxis()-1));
        removePiece(king);
        for (BoardCell kingsMoveCell : kingsMoves){
            if(kingsMoveCell.isValid()) {
                ChessPiece chessPiece = getPieceByLocation(kingsMoveCell);
                if(chessPiece!= null ? chessPiece.getColour().equals(king.getColour()) : true) {
                    if (findPiecesOneMoveAway(kingsMoveCell, king.getColour()).isEmpty()) {
                        addPiece(king);
                        return false;
                    }
                }
            }
        }
        addPiece(king);
        //can A Piece block check
        if (piecesThreateningKing.size()==1){
            ChessPiece chessPieceForcingCheck = piecesThreateningKing.get(0);
            if(!chessPieceForcingCheck.getType().equals(KNIGHT)){
                int xAxisDiff = chessPieceForcingCheck.getBoardCell().getXaxis()-king.getBoardCell().getXaxis();
                int yAxisDiff = chessPieceForcingCheck.getBoardCell().getYaxis()-king.getBoardCell().getYaxis();
                int xModifier = 0;
                if(xAxisDiff!=0) {
                    xModifier = xAxisDiff/modulo(xAxisDiff);
                }
                int yModifier = 0;
                if (yAxisDiff!=0) {
                    yModifier = yAxisDiff/modulo(yAxisDiff);
                }
                BoardCell roamingBoardCell = new BoardCell(kingcell.getXaxis(), kingcell.getYaxis());
                while (!roamingBoardCell.equals(chessPieceForcingCheck.getBoardCell())){
                    roamingBoardCell.increment(xModifier, yModifier);
                    ArrayList<ChessPiece> piecesToSaveKing = findPiecesOneMoveAway(roamingBoardCell, opposingColour);
                    if(piecesToSaveKing.size()>0){
                        for (ChessPiece savingPiece : piecesToSaveKing){
                            BoardCell initialPosition = savingPiece.getBoardCell();
                            try {
                                savingPiece.move(this, roamingBoardCell);
                                boolean inCheck = king.isInCheck(this);
                                savingPiece.move(this, initialPosition);
                                if (roamingBoardCell.equals(chessPieceForcingCheck.getBoardCell())){
                                    addPiece(chessPieceForcingCheck);
                                }
                                if (!inCheck){
                                    return false;
                                }
                            } catch (IllegalMoveException e) {
                                e.printStackTrace();
                                break;
                            }

                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean removePiece(ChessPiece chesspiece) {
        return piecesOnBoard.remove(chesspiece);
    }

    public boolean addPiece(ChessPiece chesspiece) {
        if(getPieceByLocation(chesspiece.getBoardCell())==null) {
            return piecesOnBoard.add(chesspiece);
        }
        return false;
    }

    public boolean contains(ChessPiece chessPiece){
        return piecesOnBoard.contains(chessPiece);
    }

}
