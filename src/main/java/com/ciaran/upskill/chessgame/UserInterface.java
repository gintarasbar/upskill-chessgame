package com.ciaran.upskill.chessgame;

import com.ciaran.upskill.chessgame.domain.BoardCell;
import com.ciaran.upskill.chessgame.domain.ChessBoard;
import com.ciaran.upskill.chessgame.domain.chesspiece.ChessPiece;

import java.util.Scanner;

import static com.ciaran.upskill.chessgame.UtilClass.switchColour;
import static com.ciaran.upskill.chessgame.domain.BoardCell.createValidBoardCell;

public class UserInterface {

    private ChessBoard chessBoard;
    private Scanner scanner;

    public UserInterface(ChessBoard chessboard, Scanner scanner){
        this.scanner = scanner;
        this.chessBoard = chessboard;
    }

    public boolean inputMove(Colour colour) {
        BoardCell startPosition = null;
        while(startPosition==null){
            System.out.print("Please input your piece's starting co-ordinate :");
            startPosition = createValidBoardCell(scanner.next().toUpperCase());
        }
        BoardCell endPosition = null;
        while(endPosition==null){
            System.out.print("Please input your piece's ending co-ordinate :");
            endPosition = createValidBoardCell(scanner.next().toUpperCase());
        }
        return chessBoard.movePiece(startPosition, endPosition, colour);
    }

    public boolean isConcession() {
        System.out.print("Confirm game concession (Y/N) :");
        return scanner.next().toUpperCase().matches("Y");
    }

    public void displayBoard() {
        for (int i=1; i<9; i++){
            for (int j=1; j<9; j++){
                ChessPiece chessPiece = chessBoard.getPieceByLocation(new BoardCell(j, i));
                if (chessPiece!=null){
                    System.out.println(chessPiece.getBoardCell().print()+" - "+chessPiece.getColour()+" - "+chessPiece.getType());
                }
            }
        }
    }

    public int turnMenu() {
        System.out.println("Please select from the following options");
        System.out.println("1 - Move a piece");
        System.out.println("2 - Display the board");
        System.out.println("3 - Concede game");
        System.out.println("4 - Declare stalemate");
        System.out.print("Option :");
        return scanner.nextInt();

    }

    public boolean checkStaleMate(Colour colour) {
        System.out.println(colour+" player has declared a stalemate!");
        System.out.println("Does "+switchColour(colour)+" player agree? (Y/N) :");
        if (scanner.next().toUpperCase().matches("Y")){
            return true;
        } else {
            System.out.println("No stalemate agreed, play continues on");
            return false;
        }
    }
}
