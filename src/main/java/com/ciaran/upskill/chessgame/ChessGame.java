package com.ciaran.upskill.chessgame;

import com.ciaran.upskill.chessgame.domain.ChessBoard;
import com.ciaran.upskill.chessgame.domain.BoardCell;
import com.ciaran.upskill.chessgame.domain.chesspiece.ChessPiece;
import com.ciaran.upskill.chessgame.domain.chesspiece.King;
import com.ciaran.upskill.chessgame.exceptions.GameConcessionException;
import com.ciaran.upskill.chessgame.exceptions.GameStaleMateException;
import com.ciaran.upskill.chessgame.exceptions.IllegalMoveException;

import java.util.ArrayList;
import java.util.Scanner;

import static com.ciaran.upskill.chessgame.UtilClass.modulo;
import static com.ciaran.upskill.chessgame.domain.BoardCell.createValidBoardCell;


public class ChessGame {

    private static ChessBoard chessBoard = new ChessBoard();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        newGame();
    }

    private static void newGame() {
        chessBoard.setUpBoard();
        boolean checkMate = false;
        String colour = "white";
        while (!checkMate) {
            try {
                takeTurn(colour);
            } catch (GameConcessionException e) {
                checkMate = true;
                break;
            } catch (GameStaleMateException gameStaleMateException) {
                checkStaleMate();

            }
            colour = switchColour(colour);
            checkMate = isInCheckMate(colour);
        }
        colour = switchColour(colour);
        System.out.println("Checkmate, "+colour+" wins the game!");
    }

    private static boolean isInCheckMate(String colour) {
        //is King in check
        King king = chessBoard.getKing(colour);
        BoardCell kingcell = king.getBoardCell();
        ArrayList<ChessPiece> piecesThreateningKing = chessBoard.findPiecesOneMoveAway(king.getBoardCell(), king.getColour());
        String opposingColour = switchColour(king.getColour());
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
        chessBoard.removePiece(king);
        for (BoardCell kingsMoveCell : kingsMoves){
            if(kingsMoveCell.isValid()) {
                ChessPiece chessPiece = chessBoard.getPieceByLocation(kingsMoveCell);
                if(chessPiece!= null ? chessPiece.getColour().matches(king.getColour()) : true) {
                    if (chessBoard.findPiecesOneMoveAway(kingsMoveCell, king.getColour()).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        //can A Piece block check
        if (piecesThreateningKing.size()==1){
            ChessPiece chessPieceForcingCheck = piecesThreateningKing.get(0);
            if(!chessPieceForcingCheck.getType().matches("knight")){
                int xAxisDiff = king.getBoardCell().getXaxis()-chessPieceForcingCheck.getBoardCell().getXaxis();
                int yAxisDiff = king.getBoardCell().getYaxis()-chessPieceForcingCheck.getBoardCell().getYaxis();
                int xModifier = modulo(xAxisDiff)/xAxisDiff;
                int yModifier = modulo(yAxisDiff)/yAxisDiff;
                BoardCell roamingBoardCell = new BoardCell(kingcell.getXaxis(), kingcell.getYaxis());
                while (!roamingBoardCell.equals(chessPieceForcingCheck.getBoardCell())){
                    roamingBoardCell.increment(xModifier, yModifier);
                    ArrayList<ChessPiece> piecesToSaveKing = chessBoard.findPiecesOneMoveAway(roamingBoardCell, opposingColour);
                    if(piecesToSaveKing.size()>0){
                        for (ChessPiece savingPiece : piecesToSaveKing){
                            BoardCell initialPosition = savingPiece.getBoardCell();
                            try {
                                savingPiece.move(chessBoard, roamingBoardCell);
                                boolean inCheck = king.isInCheck(chessBoard);
                                savingPiece.move(chessBoard, initialPosition);
                                if (roamingBoardCell.equals(chessPieceForcingCheck.getBoardCell())){
                                    chessBoard.addPiece(chessPieceForcingCheck);
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

    public static String switchColour(String colour) {
        if (colour.matches("black")) {
            return "white";
        } else {
            return "black";
        }

    }

    private static void takeTurn(String colour) throws GameConcessionException, GameStaleMateException {
        System.out.println("It is "+colour+"'s turn.");
        boolean valid = false;
        while(!valid){
            switch (turnMenuOption()){
                case 1: BoardCell startPosition = null;
                    while(startPosition==null){
                        System.out.print("Please input your piece's starting co-ordinate: ");
                        startPosition = createValidBoardCell(scanner.next().toUpperCase());
                    }
                    BoardCell endPosition = null;
                    while(endPosition==null){
                        System.out.print("Please input your piece's ending co-ordinate: ");
                        endPosition = createValidBoardCell(scanner.next().toUpperCase());
                    }
                    valid=chessBoard.movePiece(startPosition, endPosition, colour);
                    break;
                case 2:
                    displayBoard();
                    break;
                case 3:
                    throw new GameConcessionException();
                case 4:
                    throw new GameStaleMateException();
            }

        }
    }

    private static void displayBoard() {
    }

    private static int turnMenuOption() {
        return 0;
    }

    private static void checkStaleMate() {
    }
}
