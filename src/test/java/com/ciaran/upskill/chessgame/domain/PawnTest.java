package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.ChessBoard;
import com.ciaran.upskill.chessgame.IllegalMoveException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PawnTest {

    private ChessBoard chessBoard;
    private ChessPiece chessPiece;

    @Before
    public void setup(){
        chessBoard = new ChessBoard();
        chessPiece = new Pawn(new Coordinate(2, 2),"black", "up");
        chessBoard.addPiece(chessPiece);
    }


    @Test
    public void test_validate_move_detects_a_legal_move(){
        assertTrue(chessPiece.validateMove(chessBoard, new Coordinate(2, 3)));
    }

    @Test
    public void test_validate_move_detects_a_legal_taking_move(){
        chessBoard.addPiece(new Pawn(new Coordinate(3, 3), "white", "up"));
        assertTrue(chessPiece.validateMove(chessBoard, new Coordinate(3, 3)));
    }

    @Test
    public void test_validate_move_detects_an_illegal_move_because_space_is_occupied(){
        chessBoard.addPiece(new Pawn(new Coordinate(2, 3), "white", "up"));
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate(2, 3)));
    }

    @Test
    public void test_validate_move_detects_an_illegal_move_going_backwards(){
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate(2, 1)));
    }

    @Test
    public void test_validate_move_detects_an_illegal_taking_move_space_empty(){
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate(3, 3)));
    }

    @Test
    public void test_validate_move_detects_an_illegal_move_because_it_hasnt_moved(){
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate(2, 2)));
    }

    @Test
    public void test_validate_move_detects_an_illegal_taking_move_piece_is_of_same_colour(){
        chessBoard.addPiece(new Pawn(new Coordinate(2, 3), "black", "up"));
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate(2, 3)));
    }

    @Test
    public void test_validate_move_detects_a_move_passing_through_another_piece(){
        chessBoard.addPiece(new Pawn(new Coordinate(2,3), "black", "up"));
        assertFalse(chessPiece.validateMove(chessBoard, new Coordinate(2, 4)));
    }

    @Test
    public void test_move_piece_updates_piece_and_board(){
        Coordinate finish = new Coordinate(2,3);
        try {
            chessPiece.movePiece(chessBoard, finish);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
        assertTrue(chessBoard.getPieceByLocation(finish).equals(chessPiece));
        assertNull(chessBoard.getPieceByLocation(new Coordinate(1,4)));
    }

    @Test
    public void test_move_piece_updates_piece_and_board_when_moving_two_spaces(){
        Coordinate finish = new Coordinate(2,4);
        try {
            chessPiece.movePiece(chessBoard, finish);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
        assertTrue(chessBoard.getPieceByLocation(finish).equals(chessPiece));
        assertNull(chessBoard.getPieceByLocation(new Coordinate(2,2)));
    }

    @Test
    public void test_move_piece_takes_other_piece_from_board(){
        Coordinate finish = new Coordinate(1,3);
        ChessPiece victim = new Rook(finish, "white");
        chessBoard.addPiece(victim);
        ChessPiece removedPiece = null;
        try {
            removedPiece = chessPiece.movePiece(chessBoard, finish);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
        assertTrue(chessBoard.getPieceByLocation(finish).equals(chessPiece));
        assertNull(chessBoard.getPieceByLocation(new Coordinate(2,2)));
        assertFalse(chessBoard.contains(victim));
        assertTrue(removedPiece.equals(victim));
    }

    @Test
    public void test_move_piece_takes_other_pawn_from_board_in_en_passant(){
        Coordinate finish = new Coordinate(3,6);
        ChessPiece victim = new Pawn(finish, "white", "down");
        chessBoard.addPiece(victim);
        ChessPiece removedPiece = null;
        try {
            chessPiece.movePiece(chessBoard, new Coordinate(2,4));
            chessPiece.movePiece(chessBoard, new Coordinate(2,5));
            removedPiece = chessPiece.movePiece(chessBoard, finish);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
        assertTrue(chessBoard.getPieceByLocation(finish).equals(chessPiece));
        assertNull(chessBoard.getPieceByLocation(new Coordinate(2,2)));
        assertFalse(chessBoard.contains(victim));
        assertTrue(removedPiece.equals(victim));
    }
}
