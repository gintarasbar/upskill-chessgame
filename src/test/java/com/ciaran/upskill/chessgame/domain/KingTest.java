package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.ChessBoard;
import com.ciaran.upskill.chessgame.IllegalMoveException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class KingTest {

    private ChessBoard chessBoard;
    private ChessPiece chessPiece;

    @Before
    public void setup(){
        chessBoard = new ChessBoard();
        chessPiece = new King(new Coordinate(4, 1),"black");
        chessBoard.addPiece(chessPiece);
    }


    @Test
    public void test_validate_move_detects_a_legal_move(){
        assertTrue(chessPiece.validateMove(chessBoard, new Coordinate(3, 2)));
    }

    @Test
    public void test_validate_move_detects_an_illegal_move(){
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate(5, 3)));
    }

    @Test
    public void test_validate_move_detects_a_valid_king_rook_switch(){
        chessBoard.addPiece(new Rook(new Coordinate(1, 1), "black"));
        assertTrue(chessPiece.validateMove(chessBoard, new Coordinate(2, 1)));
    }

    @Test
    public void test_validate_move_detects_a_invalid_king_rook_switch() {
        chessBoard.addPiece(new Rook(new Coordinate(8, 1), "black"));
        chessBoard.addPiece(new Pawn(new Coordinate(7, 1), "black", "up"));
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate(6, 1)));
    }

    @Test
    public void test_is_in_check_returns_true_if_king_in_check_from_rook(){
        chessBoard.addPiece(new Rook(new Coordinate(4, 5), "white"));
        King king = (King) chessPiece;
        assertTrue(king.isInCheck(chessBoard));
    }

    @Test
    public void test_is_in_check_returns_true_if_king_in_check_from_queen(){
        chessBoard.addPiece(new Queen(new Coordinate(7, 1), "white"));
        King king = (King) chessPiece;
        assertTrue(king.isInCheck(chessBoard));
        chessBoard.removePiece(chessBoard.getPieceByLocation(new Coordinate(8,5)));
        chessBoard.addPiece(new Queen(new Coordinate(4, 5), "white"));
        assertTrue(king.isInCheck(chessBoard));
    }

    @Test
    public void test_is_in_check_returns_true_if_king_in_check_from_bishop(){
        chessBoard.addPiece(new Bishop(new Coordinate(8, 5), "white"));
        King king = (King) chessPiece;
        assertTrue(king.isInCheck(chessBoard));
    }

    @Test
    public void test_is_in_check_returns_true_if_king_in_check_from_knight(){
        chessBoard.addPiece(new Knight(new Coordinate(5, 3), "white"));
        King king = (King) chessPiece;
        assertTrue(king.isInCheck(chessBoard));
    }

    @Test
    public void test_is_in_check_returns_false_if_king_is_not_in_check(){
        chessBoard.addPiece(new Knight(new Coordinate(5, 2), "white"));
        King king = (King) chessPiece;
        assertFalse(king.isInCheck(chessBoard));
    }

    @Test
    public void test_move_piece_updates_piece_and_board(){
        Coordinate finish = new Coordinate(4,2);
        try {
            chessPiece.movePiece(chessBoard, finish);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
        assertTrue(chessBoard.getPieceByLocation(finish).equals(chessPiece));
        assertNull(chessBoard.getPieceByLocation(new Coordinate(1,4)));
    }

    @Test
    public void test_move_piece_takes_other_piece_from_board(){
        Coordinate finish = new Coordinate(4,2);
        ChessPiece victim = new Rook(finish, "white");
        chessBoard.addPiece(victim);
        ChessPiece removedPiece = null;
        try {
            removedPiece = chessPiece.movePiece(chessBoard, finish);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
        assertTrue(chessBoard.getPieceByLocation(finish).equals(chessPiece));
        assertNull(chessBoard.getPieceByLocation(new Coordinate(1,4)));
        assertFalse(chessBoard.contains(victim));
        assertTrue(removedPiece.equals(victim));
    }

    @Test
    public void test_move_piece_updates_piece_and_board_whilst_castling(){
        Coordinate finish = new Coordinate(2,1);
        ChessPiece rook = new Rook(new Coordinate(1,1),"black");
        chessBoard.addPiece(rook);
        try {
            chessPiece.movePiece(chessBoard, finish);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
        assertTrue(chessBoard.getPieceByLocation(finish).equals(chessPiece));
        assertNull(chessBoard.getPieceByLocation(new Coordinate(1,4)));
        assertTrue(chessBoard.getPieceByLocation(new Coordinate(3,1)).equals(rook));
        assertNull(chessBoard.getPieceByLocation(new Coordinate(1,1)));
    }
}
