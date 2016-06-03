package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.domain.chesspiece.ChessPiece;
import com.ciaran.upskill.chessgame.domain.chesspiece.King;
import com.ciaran.upskill.chessgame.domain.chesspiece.Pawn;
import com.ciaran.upskill.chessgame.domain.chesspiece.Rook;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ChessBoardTest {

    private ChessBoard chessBoard;

    @Before
    public void setUp(){
        chessBoard = new ChessBoard();
        chessBoard.setUpBoard();
    }

    //Test getPieceByLocation
    @Test
    public void test_getPieceByLocation_returns_chesspiece_if_it_has_a_matching_boardcell(){
        assertTrue(chessBoard.getPieceByLocation(new BoardCell(4,2)) instanceof ChessPiece);
    }

    @Test
    public void test_getPieceByLocation_returns_chesspiece_if_there_is_no_matching_boardcell(){
        assertNull(chessBoard.getPieceByLocation(new BoardCell(3,4)));
    }

    //Test addpiece
    @Test
    public void test_add_piece_returns_true_and_adds_piece_to_board_if_space_is_empty(){
        ChessPiece pawn = new Pawn(new BoardCell(3,4),"black","up");
        assertTrue(chessBoard.addPiece(pawn));
        assertTrue(chessBoard.contains(pawn));
    }

    @Test
    public void test_add_piece_returns_false_and_doesnt_add_piece_to_board_if_space_is_occupied(){
        ChessPiece pawn = new Pawn(new BoardCell(4,2),"black","up");
        assertFalse(chessBoard.addPiece(pawn));
        assertFalse(chessBoard.contains(pawn));
    }

    //Test removePiece
    public void test_remove_piece_takes_piece_off_board_if_present(){
        ChessPiece chessPiece = chessBoard.getPieceByLocation(new BoardCell(1,1));
        assertTrue(chessBoard.removePiece(chessPiece));
        assertFalse(chessBoard.contains(chessPiece));
    }

    public void test_remove_piece_returns_false_if_piece_not_present(){
        assertFalse(chessBoard.removePiece(new Pawn(new BoardCell(3,4),"black", "up")));
    }

    //Test getKing
    @Test
    public void test_getKing_returns_king_by_colour(){
        King king = chessBoard.getKing("black");
        assertTrue(king.getColour().matches("black"));
        assertTrue(king.getBoardCell().equals(new BoardCell(5,8)));
        king = chessBoard.getKing("white");
        assertTrue(king.getColour().matches("white"));
        assertTrue(king.getBoardCell().equals(new BoardCell(5,1)));
    }

    //Test move
    @Test
    public void test_move_piece_returns_true_on_valid_move(){
        assertTrue(chessBoard.movePiece(new BoardCell(4,2), new BoardCell(4,4),"white"));
    }

    @Test
    public void test_move_piece_returns_false_if_start_position_has_piece_of_different_colour(){
        assertFalse(chessBoard.movePiece(new BoardCell(4,2), new BoardCell(4,4),"black"));
    }

    @Test
    public void test_move_piece_returns_false_if_finish_position_has_piece_of_same_colour(){
        assertFalse(chessBoard.movePiece(new BoardCell(1,1), new BoardCell(1,2),"white"));
    }

    @Test
    public void test_move_piece_returns_false_if_no_move_taken(){
        assertFalse(chessBoard.movePiece(new BoardCell(2,1), new BoardCell(2,1),"white"));
    }

    @Test
    public void test_move_piece_returns_false_if_move_throws_exception(){
        assertFalse(chessBoard.movePiece(new BoardCell(4,2), new BoardCell(3,3),"white"));
    }

    @Test
    public void test_move_piece_returns_false_if_king_left_in_check(){
        chessBoard.addPiece(new Rook(new BoardCell(5,5),"black"));
        chessBoard.addPiece(new Rook(new BoardCell(4,3), "black"));
        chessBoard.movePiece(new BoardCell(5,2),new BoardCell(4,3), "white");
    }

}
