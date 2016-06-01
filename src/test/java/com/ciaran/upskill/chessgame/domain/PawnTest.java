package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.ChessBoard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PawnTest {

    private ChessBoard chessBoard;
    private ChessPiece chessPiece;

    @Before
    public void setup(){
        chessBoard = new ChessBoard();
        chessPiece = new Pawn(new Coordinate('A', '4'),"black", "up");
        chessBoard.addPiece(chessPiece);
    }


    @Test
    public void test_validate_move_detects_a_legal_move(){
        assertTrue(chessPiece.validateMove(chessBoard, new Coordinate('A', '5')));
    }

    @Test
    public void test_validate_move_detects_a_legal_taking_move(){
        chessBoard.addPiece(new Pawn(new Coordinate('B', '5'), "white", "up"));
        assertTrue(chessPiece.validateMove(chessBoard, new Coordinate('B', '5')));
    }

    @Test
    public void test_validate_move_detects_an_illegal_move_because_space_is_occupied(){
        chessBoard.addPiece(new Pawn(new Coordinate('A', '5'), "white", "up"));
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate('A', '5')));
    }

    @Test
    public void test_validate_move_detects_an_illegal_move_going_backwards(){
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate('A', '3')));
    }

    @Test
    public void test_validate_move_detects_an_illegal_taking_move_space_empty(){
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate('B', '5')));
    }

    @Test
    public void test_validate_move_detects_an_illegal_taking_move_piece_is_of_same_colour(){
        chessBoard.addPiece(new Pawn(new Coordinate('A', '5'), "black", "up"));
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate('A', '5')));
    }

    @Test
    public void test_validate_move_detects_a_move_passing_through_another_piece(){
        chessBoard.addPiece(new Pawn(new Coordinate('A','5'), "black", "up"));
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate('A', '6')));
    }
}
