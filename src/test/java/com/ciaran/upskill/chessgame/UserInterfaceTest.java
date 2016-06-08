package com.ciaran.upskill.chessgame;

import com.ciaran.upskill.chessgame.domain.BoardCell;
import com.ciaran.upskill.chessgame.domain.ChessBoard;
import com.ciaran.upskill.chessgame.domain.chesspiece.ChessPiece;
import com.ciaran.upskill.chessgame.domain.chesspiece.Rook;
import com.ciaran.upskill.chessgame.exceptions.GameConcessionException;
import com.ciaran.upskill.chessgame.exceptions.GameStaleMateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.zip.CheckedOutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserInterfaceTest {

    private Scanner scanner;
    private ChessBoard chessBoard;
    private UserInterface userInterface;

    @Before
    public void setUp(){

    }

    //InputMove
    @Test
    public void test_input_move_returns_true_when_movePiece_returns_true(){
        String simulatedUserInput = "A1"+System.getProperty("line.separator")+"A1";
        InputStream input = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(input);
        scanner = new Scanner(System.in);
        chessBoard = spy(new ChessBoard());
        userInterface = new UserInterface(chessBoard, scanner);
        doReturn(true).when(chessBoard).movePiece(any(BoardCell.class), any(BoardCell.class), anyString());

        assertThat(userInterface.inputMove("White"), is(true));
    }

    @Test
    public void test_input_move_returns_false_when_movePiece_returns_false(){
        String simulatedUserInput = "A1"+System.getProperty("line.separator")+"A1";
        InputStream input = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(input);
        scanner = new Scanner(System.in);
        chessBoard = spy(new ChessBoard());
        userInterface = new UserInterface(chessBoard, scanner);
        doReturn(false).when(chessBoard).movePiece(any(BoardCell.class), any(BoardCell.class), anyString());

        assertThat(userInterface.inputMove("White"), is(false));
    }

    //isConcession
    @Test
    public void test_isConcession_returns_true_when_user_input_is_y(){
        String simulatedUserInput = "Y";
        InputStream input = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(input);
        scanner = new Scanner(System.in);
        chessBoard = spy(new ChessBoard());
        userInterface = new UserInterface(chessBoard, scanner);

        assertThat(userInterface.isConcession(), is(true));
    }

    @Test
    public void test_isConcession_returns_false_when_user_input_is_n(){
        String simulatedUserInput = "N";
        InputStream input = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(input);
        scanner = new Scanner(System.in);
        chessBoard = spy(new ChessBoard());
        userInterface = new UserInterface(chessBoard, scanner);

        assertThat(userInterface.isConcession(), is(false));
    }

    //turnMenu
    @Test
    public void test_turnMenu_returns_next_integer_input(){
        String simulatedUserInput = "1";
        InputStream input = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(input);
        scanner = new Scanner(System.in);
        chessBoard = spy(new ChessBoard());
        userInterface = new UserInterface(chessBoard, scanner);

        assertThat(userInterface.turnMenu(), is(1));
    }

    //displayBoard
    @Test
    public void test_displayBoard(){
        scanner = new Scanner(System.in);
        chessBoard = new ChessBoard();
        userInterface = new UserInterface(chessBoard, scanner);
        ChessPiece chessPiece = new Rook(new BoardCell(3,7), "White");
        chessBoard.addPiece(chessPiece);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        userInterface.displayBoard();
        assertThat(outContent.toString(), is("C7 - White - Rook\n"));
    }

    //checkStaleMate
    @Test
    public void test_checkStalemate_returns_true_when_user_input_is_y(){
        String simulatedUserInput = "Y";
        InputStream input = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(input);
        scanner = new Scanner(System.in);
        chessBoard = spy(new ChessBoard());
        userInterface = new UserInterface(chessBoard, scanner);

        assertThat(userInterface.checkStaleMate("Black"), is(true));
    }

    @Test
    public void test_checkStalemate_returns_false_when_user_input_is_n(){
        String simulatedUserInput = "N";
        InputStream input = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(input);
        scanner = new Scanner(System.in);
        chessBoard = spy(new ChessBoard());
        userInterface = new UserInterface(chessBoard, scanner);

        assertThat(userInterface.checkStaleMate("Black"), is(false));
    }

}
