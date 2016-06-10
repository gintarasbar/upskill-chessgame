package com.ciaran.upskill.chessgame.domain.chesspiece;

import com.ciaran.upskill.chessgame.domain.ChessBoard;
import com.ciaran.upskill.chessgame.exceptions.IllegalMoveException;
import com.ciaran.upskill.chessgame.domain.BoardCell;
import org.junit.Before;
import org.junit.Test;

import static com.ciaran.upskill.chessgame.Colour.BLACK;
import static com.ciaran.upskill.chessgame.Colour.WHITE;
import static com.ciaran.upskill.chessgame.domain.chesspiece.Pawn.Direction.UP;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class KingTest {

    private ChessBoard chessBoard;
    private ChessPiece chessPiece;

    @Before
    public void setup(){
        chessBoard = new ChessBoard();
        chessPiece = new King(new BoardCell(4, 1), BLACK);
        chessBoard.addPiece(chessPiece);
    }


    @Test
    public void test_validate_move_detects_a_legal_move(){
        assertThat(chessPiece.validateMove(chessBoard, new BoardCell(3, 2)), is(true));
    }

    @Test
    public void test_validate_move_detects_an_illegal_move(){
        assertThat(chessPiece.validateMove(chessBoard, new BoardCell(5, 3)), is(false));
    }

    @Test
    public void test_validate_move_detects_a_valid_king_rook_switch(){
        chessBoard.addPiece(new Rook(new BoardCell(1, 1), BLACK));
        assertThat(chessPiece.validateMove(chessBoard, new BoardCell(2, 1)), is(true));
    }

    @Test
    public void test_validate_move_detects_a_invalid_king_rook_switch() {
        chessBoard.addPiece(new Rook(new BoardCell(8, 1), BLACK));
        chessBoard.addPiece(new Pawn(new BoardCell(7, 1), BLACK, UP));
        assertThat(chessPiece.validateMove(chessBoard, new BoardCell(6, 1)), is(false));
    }

    @Test
    public void test_is_in_check_returns_true_if_king_in_check_from_rook(){
        chessBoard.addPiece(new Rook(new BoardCell(4, 5), WHITE));
        King king = (King) chessPiece;
        assertThat(king.isInCheck(chessBoard), is(true));
    }

    @Test
    public void test_is_in_check_returns_true_if_king_in_check_from_queen(){
        chessBoard.addPiece(new Queen(new BoardCell(7, 1), WHITE));
        King king = (King) chessPiece;
        assertThat(king.isInCheck(chessBoard), is(true));
        chessBoard.removePiece(chessBoard.getPieceByLocation(new BoardCell(8,5)));
        chessBoard.addPiece(new Queen(new BoardCell(4, 5), WHITE));
        assertThat(king.isInCheck(chessBoard), is(true));
    }

    @Test
    public void test_is_in_check_returns_true_if_king_in_check_from_bishop(){
        chessBoard.addPiece(new Bishop(new BoardCell(8, 5), WHITE));
        King king = (King) chessPiece;
        assertThat(king.isInCheck(chessBoard), is(true));
    }

    @Test
    public void test_is_in_check_returns_true_if_king_in_check_from_knight(){
        chessBoard.addPiece(new Knight(new BoardCell(5, 3), WHITE));
        King king = (King) chessPiece;
        assertThat(king.isInCheck(chessBoard), is(true));
    }

    @Test
    public void test_is_in_check_returns_false_if_king_is_not_in_check(){
        chessBoard.addPiece(new Knight(new BoardCell(5, 2), WHITE));
        King king = (King) chessPiece;
        assertThat(king.isInCheck(chessBoard), is(false));
    }

    @Test
    public void test_move_piece_updates_piece_and_board(){
        BoardCell finish = new BoardCell(4,2);
        try {
            chessPiece.move(chessBoard, finish);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
        assertThat(chessBoard.getPieceByLocation(finish), is(equalTo(chessPiece)));
        assertThat(chessBoard.getPieceByLocation(new BoardCell(1,4)), is(equalTo(null)));
    }

    @Test
    public void test_move_piece_takes_other_piece_from_board(){
        BoardCell finish = new BoardCell(4,2);
        ChessPiece victim = new Rook(finish, WHITE);
        chessBoard.addPiece(victim);
        ChessPiece removedPiece = null;
        try {
            removedPiece = chessPiece.move(chessBoard, finish);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
        assertThat(chessBoard.getPieceByLocation(finish), is(equalTo(chessPiece)));
        assertThat(chessBoard.getPieceByLocation(new BoardCell(1,4)), is(equalTo(null)));
        assertThat(chessBoard.contains(victim), is(false));
        assertThat(removedPiece, is(equalTo(victim)));
    }

    @Test
    public void test_move_piece_updates_piece_and_board_whilst_castling(){
        BoardCell finish = new BoardCell(2,1);
        ChessPiece rook = new Rook(new BoardCell(1,1),BLACK);
        chessBoard.addPiece(rook);
        try {
            chessPiece.move(chessBoard, finish);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
        assertThat(chessBoard.getPieceByLocation(finish), is(equalTo(chessPiece)));
        assertThat(chessBoard.getPieceByLocation(new BoardCell(1,4)), is(equalTo(null)));
        assertThat(chessBoard.getPieceByLocation(new BoardCell(3,1)), is(equalTo(rook)));
        assertThat(chessBoard.getPieceByLocation(new BoardCell(1,1)), is(equalTo(null)));
    }
}
