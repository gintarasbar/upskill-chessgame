package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.ChessBoard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class KnightTest {

    private ChessBoard chessBoard;
    private ChessPiece chessPiece;

    @Before
    public void setup(){
        chessBoard = new ChessBoard();
        chessPiece = new Knight(new Coordinate('A', '4'),"black");
        chessBoard.addPiece(chessPiece);
    }


    @Test
    public void test_validate_move_detects_a_legal_move(){
        assertTrue(chessPiece.validateMove(chessBoard, new Coordinate('C', '5')));
    }

    @Test
    public void test_validate_move_detects_an_illegal_move(){
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate('C', '6')));
    }
}
