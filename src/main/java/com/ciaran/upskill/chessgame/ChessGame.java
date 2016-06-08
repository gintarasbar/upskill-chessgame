package com.ciaran.upskill.chessgame;

import com.ciaran.upskill.chessgame.domain.ChessBoard;
import com.ciaran.upskill.chessgame.exceptions.GameConcessionException;
import com.ciaran.upskill.chessgame.exceptions.GameStaleMateException;

import java.util.Scanner;

import static com.ciaran.upskill.chessgame.UtilClass.switchColour;


public class ChessGame {

    private static ChessBoard chessBoard = new ChessBoard();
    private static UserInterface userInterface;

    public static void main(String[] args){
        newGame();
    }

    private static void newGame() {
        chessBoard.setUpBoard();
        userInterface = new UserInterface(chessBoard, new Scanner(System.in));
        boolean checkMate = false;
        String colour = "White";
        while (!checkMate) {
            try {
                playerTurn(colour);
            } catch (GameConcessionException e) {
                checkMate = true;
                break;
            } catch (GameStaleMateException gameStaleMateException) {
                userInterface.checkStaleMate(colour);

            }
            colour = switchColour(colour);
            checkMate = chessBoard.isInCheckMate(colour);
        }
        colour = switchColour(colour);
        System.out.println("Checkmate, "+colour+" wins the game!");
    }

    private static void playerTurn(String colour) throws GameConcessionException, GameStaleMateException {
        System.out.println("It is "+colour+"'s turn.");
        boolean valid = false;
        while(!valid){
            switch (userInterface.turnMenu()){
                case 1:
                    valid = userInterface.inputMove(colour);
                    break;
                case 2:
                    userInterface.displayBoard();
                    break;
                case 3:
                    if(userInterface.isConcession()){
                        throw new GameConcessionException();
                    }
                case 4:
                    throw new GameStaleMateException();
                default:
                    System.out.println("The menu option you have chosen is not invalid, please try again!");
            }

        }
    }


}
