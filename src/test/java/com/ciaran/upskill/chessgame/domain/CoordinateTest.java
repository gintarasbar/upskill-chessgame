package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.ChessBoard;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CoordinateTest {

    @Test
    public void test_validate_move_detects_a_legal_move(){
        ChessBoard chessBoard = new ChessBoard();
        ChessPiece chessPiece = new Bishop(new Coordinate('A', '4'),"black");
        chessBoard.addPiece(chessPiece);
        assertTrue(chessPiece.validateMove(chessBoard, new Coordinate('C', '6')));
    }

    @Test
    public void test_validate_move_detects_an_illegal_move(){
        ChessBoard chessBoard = new ChessBoard();
        ChessPiece chessPiece = new Bishop(new Coordinate('A', '4'),"black");
        chessBoard.addPiece(chessPiece);
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate('B', '6')));
    }

    @Test
    public void test_validate_move_detects_a_move_passing_through_another_piece(){
        ChessBoard chessBoard = new ChessBoard();
        ChessPiece chessPiece = new Bishop(new Coordinate('A', '4'),"black");
        chessBoard.addPiece(chessPiece);
        chessBoard.addPiece(new Pawn(new Coordinate('B','5'), "black", "up"));
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate('C', '6')));
    }
}
