package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.ChessBoard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class KingTest {

    private ChessBoard chessBoard;
    private ChessPiece chessPiece;

    @Before
    public void setup(){
        chessBoard = new ChessBoard();
        chessPiece = new King(new Coordinate('A', '4'),"black");
        chessBoard.addPiece(chessPiece);
    }


    @Test
    public void test_validate_move_detects_a_legal_move(){
        assertTrue(chessPiece.validateMove(chessBoard, new Coordinate('B', '5')));
    }

    @Test
    public void test_validate_move_detects_an_illegal_move(){
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate('B', '6')));
    }

    @Test
    public void test_validate_move_detects_a_valid_king_rook_switch(){
        chessBoard.addPiece(new Rook(new Coordinate('A','1'), "black"));
        assertTrue(chessPiece.validateMove(chessBoard, new Coordinate('A', '2')));
    }

    @Test
    public void test_validate_move_detects_a_invalid_king_rook_switch() {
        chessBoard.addPiece(new Rook(new Coordinate('A', '8'), "black"));
        chessBoard.addPiece(new Pawn(new Coordinate('A', '7'), "black", "up"));
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate('A', '6')));
    }

    @Test
    public void test_is_in_check_returns_true_if_king_in_check(){
        chessBoard.addPiece(new Knight(new Coordinate('B', '6'), "white"));
        King king = (King) chessPiece;
        assertTrue(king.isInCheck(chessBoard));
    }

    @Test
    public void test_is_in_check_returns_false_if_king_is_not_in_check(){
        chessBoard.addPiece(new Knight(new Coordinate('B', '6'), "white"));
        King king = (King) chessPiece;
        assertFalse(king.isInCheck(chessBoard));
    }
}
