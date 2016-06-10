package com.ciaran.upskill.chessgame.domain;

import com.ciaran.upskill.chessgame.ChessGame;
import com.ciaran.upskill.chessgame.domain.chesspiece.Bishop;
import com.ciaran.upskill.chessgame.domain.chesspiece.ChessPiece;
import com.ciaran.upskill.chessgame.domain.chesspiece.King;
import com.ciaran.upskill.chessgame.domain.chesspiece.Knight;
import com.ciaran.upskill.chessgame.domain.chesspiece.Pawn;
import com.ciaran.upskill.chessgame.domain.chesspiece.Queen;
import com.ciaran.upskill.chessgame.domain.chesspiece.Rook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.ciaran.upskill.chessgame.Colour.BLACK;
import static com.ciaran.upskill.chessgame.Colour.WHITE;
import static com.ciaran.upskill.chessgame.UtilClass.modulo;
import static com.ciaran.upskill.chessgame.domain.chesspiece.ChessPieceType.BISHOP;
import static com.ciaran.upskill.chessgame.domain.chesspiece.ChessPieceType.KING;
import static com.ciaran.upskill.chessgame.domain.chesspiece.ChessPieceType.KNIGHT;
import static com.ciaran.upskill.chessgame.domain.chesspiece.ChessPieceType.PAWN;
import static com.ciaran.upskill.chessgame.domain.chesspiece.ChessPieceType.QUEEN;
import static com.ciaran.upskill.chessgame.domain.chesspiece.ChessPieceType.ROOK;
import static com.ciaran.upskill.chessgame.domain.chesspiece.Pawn.Direction.DOWN;
import static com.ciaran.upskill.chessgame.domain.chesspiece.Pawn.Direction.UP;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ChessBoardTest {

    private ChessBoard chessBoard;

    @Before
    public void setUp(){
        chessBoard = new ChessBoard();
    }

    //Test getPieceByLocation
    @Test
    public void test_getPieceByLocation_returns_chesspiece_if_it_has_a_matching_boardcell(){
        chessBoard.setUpBoard();
        assertThat(chessBoard.getPieceByLocation(new BoardCell(4,2)), is(instanceOf(ChessPiece.class)));
    }

    @Test
    public void test_getPieceByLocation_returns_null_if_there_is_no_matching_boardcell(){
        chessBoard.setUpBoard();
        assertThat(chessBoard.getPieceByLocation(new BoardCell(3,4)), is(equalTo(null)));
    }

    //Test addpiece
    @Test
    public void test_add_piece_returns_true_and_adds_piece_to_board_if_space_is_empty(){
        chessBoard.setUpBoard();
        ChessPiece pawn = new Pawn(new BoardCell(3,4),BLACK,UP);
        assertThat(chessBoard.addPiece(pawn), is(true));
        assertThat(chessBoard.contains(pawn), is(true));
    }

    @Test
    public void test_add_piece_returns_false_and_doesnt_add_piece_to_board_if_space_is_occupied(){
        chessBoard.setUpBoard();
        ChessPiece pawn = new Pawn(new BoardCell(4,2),BLACK,UP);
        assertThat(chessBoard.addPiece(pawn), is(false));
        assertThat(chessBoard.contains(pawn), is(false));
    }

    //Test removePiece
    public void test_remove_piece_takes_piece_off_board_if_present(){
        chessBoard.setUpBoard();
        ChessPiece chessPiece = chessBoard.getPieceByLocation(new BoardCell(1,1));
        assertThat(chessBoard.removePiece(chessPiece), is(true));
        assertThat(chessBoard.contains(chessPiece), is(false));
    }

    public void test_remove_piece_returns_false_if_piece_not_present(){
        chessBoard.setUpBoard();
        assertThat(chessBoard.removePiece(new Pawn(new BoardCell(3,4),BLACK, UP)), is(false));
    }

    //Test getKing
    @Test
    public void test_getKing_returns_king_by_colour(){
        chessBoard.setUpBoard();
        King king = chessBoard.getKing(BLACK);
        assertThat(king.getColour(), is(equalTo(BLACK)));
        assertThat(king.getBoardCell(), is(equalTo(new BoardCell(5,8))));
        king = chessBoard.getKing(WHITE);
        assertThat(king.getColour(), is(equalTo(WHITE)));
        assertThat(king.getBoardCell(), is(equalTo(new BoardCell(5,1))));
    }

    //Test move
    @Test
    public void test_move_piece_returns_true_on_valid_move(){
        chessBoard.setUpBoard();
        assertThat(chessBoard.movePiece(new BoardCell(4,2), new BoardCell(4,4),WHITE), is(true));
    }

    @Test
    public void test_move_piece_returns_false_if_start_position_has_piece_of_different_colour(){
        chessBoard.setUpBoard();
        assertThat(chessBoard.movePiece(new BoardCell(4,2), new BoardCell(4,4),BLACK), is(false));
    }

    @Test
    public void test_move_piece_returns_false_if_finish_position_has_piece_of_same_colour(){
        chessBoard.setUpBoard();
        assertThat(chessBoard.movePiece(new BoardCell(1,1), new BoardCell(1,2),WHITE), is(false));
    }

    @Test
    public void test_move_piece_returns_false_if_no_move_taken(){
        chessBoard.setUpBoard();
        assertThat(chessBoard.movePiece(new BoardCell(2,1), new BoardCell(2,1),WHITE), is(false));
    }

    @Test
    public void test_move_piece_returns_false_if_move_throws_exception(){
        chessBoard.setUpBoard();
        assertThat(chessBoard.movePiece(new BoardCell(4,2), new BoardCell(3,3),WHITE), is(false));
    }

    @Test
    public void test_move_piece_returns_false_if_king_left_in_check(){
        chessBoard.setUpBoard();
        chessBoard.addPiece(new Rook(new BoardCell(5,5),BLACK));
        chessBoard.addPiece(new Rook(new BoardCell(4,3), BLACK));
        chessBoard.movePiece(new BoardCell(5,2),new BoardCell(4,3), WHITE);
    }

    @Test
    public void test_findPiecesOneMoveAway_finds_rooks_in_all_relevant_directions(){
        BoardCell boardcell = new BoardCell(4,4);
        chessBoard.addPiece(new Rook(new BoardCell(1,4), BLACK));
        chessBoard.addPiece(new Rook(new BoardCell(6,4), BLACK));
        chessBoard.addPiece(new Rook(new BoardCell(4,2), BLACK));
        chessBoard.addPiece(new Rook(new BoardCell(4,8), BLACK));
        chessBoard.addPiece(new Rook(new BoardCell(7,7), BLACK)); //Diagonal
        chessBoard.addPiece(new Rook(new BoardCell(5,6), BLACK)); //Knight
        ArrayList<ChessPiece> piecesOneMoveAway = chessBoard.findPiecesOneMoveAway(boardcell, WHITE);
        assertThat(piecesOneMoveAway.size(), is(equalTo(4)));
        for (ChessPiece chessPiece : piecesOneMoveAway){
            assertThat(chessPiece.getType(), is(equalTo(ROOK)));
            assertThat(chessPiece.getBoardCell().getXaxis()==4||chessPiece.getBoardCell().getYaxis()==4, is(true));
        }
    }

    @Test
    public void test_findPiecesOneMoveAway_finds_queen_in_all_relevant_directions(){
        BoardCell boardcell = new BoardCell(4,4);
        chessBoard.addPiece(new Queen(new BoardCell(1,4), BLACK));
        chessBoard.addPiece(new Queen(new BoardCell(6,4), BLACK));
        chessBoard.addPiece(new Queen(new BoardCell(4,2), BLACK));
        chessBoard.addPiece(new Queen(new BoardCell(4,8), BLACK));
        chessBoard.addPiece(new Queen(new BoardCell(7,7), BLACK)); //Diagonal
        chessBoard.addPiece(new Queen(new BoardCell(2,2), BLACK)); //Diagonal
        chessBoard.addPiece(new Queen(new BoardCell(2,6), BLACK)); //Diagonal
        chessBoard.addPiece(new Queen(new BoardCell(5,3), BLACK)); //Diagonal
        chessBoard.addPiece(new Queen(new BoardCell(5,6), BLACK)); //Knight
        ArrayList<ChessPiece> piecesOneMoveAway = chessBoard.findPiecesOneMoveAway(boardcell, WHITE);
        assertThat(piecesOneMoveAway.size(), is(equalTo(8)));
        for (ChessPiece chessPiece : piecesOneMoveAway){
            assertThat(chessPiece.getType(), is(equalTo(QUEEN)));
            int xAxisDiff = modulo(chessPiece.getBoardCell().getXaxis() - 4);
            int yAxisDiff = modulo(chessPiece.getBoardCell().getYaxis() - 4);
            assertThat(xAxisDiff==yAxisDiff||(yAxisDiff==0&&xAxisDiff!=0)||(xAxisDiff==0&&yAxisDiff!=0), is(true));
        }
    }

    @Test
    public void test_findPiecesOneMoveAway_finds_bishop_in_all_relevant_directions(){
        BoardCell boardcell = new BoardCell(4,4);
        chessBoard.addPiece(new Bishop(new BoardCell(4,8), BLACK)); //Horizontal&Vertical
        chessBoard.addPiece(new Bishop(new BoardCell(7,7), BLACK));
        chessBoard.addPiece(new Bishop(new BoardCell(2,2), BLACK));
        chessBoard.addPiece(new Bishop(new BoardCell(2,6), BLACK));
        chessBoard.addPiece(new Bishop(new BoardCell(5,3), BLACK));
        chessBoard.addPiece(new Bishop(new BoardCell(5,6), BLACK)); //Knight
        ArrayList<ChessPiece> piecesOneMoveAway = chessBoard.findPiecesOneMoveAway(boardcell, WHITE);
        assertThat(piecesOneMoveAway.size(), is(equalTo(4)));
        for (ChessPiece chessPiece : piecesOneMoveAway){
            assertThat(chessPiece.getType(), is(equalTo(BISHOP)));
            int xAxisDiff = modulo(chessPiece.getBoardCell().getXaxis() - 4);
            int yAxisDiff = modulo(chessPiece.getBoardCell().getYaxis() - 4);
            assertThat(xAxisDiff, is(equalTo(yAxisDiff)));
        }
    }

    @Test
    public void test_findPiecesOneMoveAway_finds_knight_in_all_relevant_directions(){
        BoardCell boardcell = new BoardCell(4,4);
        chessBoard.addPiece(new Knight(new BoardCell(4,8), BLACK)); //Horizontal&Vertical
        chessBoard.addPiece(new Knight(new BoardCell(7,7), BLACK)); //Diagonal
        chessBoard.addPiece(new Knight(new BoardCell(5,6), BLACK)); //Knight
        chessBoard.addPiece(new Knight(new BoardCell(6,5), BLACK)); //Knight
        chessBoard.addPiece(new Knight(new BoardCell(3,6), BLACK)); //Knight
        chessBoard.addPiece(new Knight(new BoardCell(6,3), BLACK)); //Knight
        chessBoard.addPiece(new Knight(new BoardCell(5,2), BLACK)); //Knight
        chessBoard.addPiece(new Knight(new BoardCell(2,5), BLACK)); //Knight
        chessBoard.addPiece(new Knight(new BoardCell(3,2), BLACK)); //Knight
        chessBoard.addPiece(new Knight(new BoardCell(2,3), BLACK)); //Knight
        ArrayList<ChessPiece> piecesOneMoveAway = chessBoard.findPiecesOneMoveAway(boardcell, WHITE);
        assertThat(piecesOneMoveAway.size(), is(equalTo(8)));
        for (ChessPiece chessPiece : piecesOneMoveAway){
            assertThat(chessPiece.getType(), is(equalTo(KNIGHT)));
            int xAxisDiff = modulo(chessPiece.getBoardCell().getXaxis() - 4);
            int yAxisDiff = modulo(chessPiece.getBoardCell().getYaxis() - 4);
            assertThat((xAxisDiff==1&&yAxisDiff==2)||(yAxisDiff==1&&xAxisDiff==2), is(true));
        }
    }

    @Test
    public void test_findPiecesOneMoveAway_finds_pawns_in_all_relevant_directions(){
        BoardCell boardcell = new BoardCell(4,4);
        chessBoard.addPiece(new Pawn(new BoardCell(4,8), BLACK, UP)); //Horizontal&Vertical
        chessBoard.addPiece(new Pawn(new BoardCell(7,7), BLACK, UP)); //Diagonal
        chessBoard.addPiece(new Pawn(new BoardCell(5,6), BLACK, UP)); //Knight
        chessBoard.addPiece(new Pawn(new BoardCell(5,5), BLACK, DOWN));
        chessBoard.addPiece(new Pawn(new BoardCell(5,3), BLACK, UP));
        chessBoard.addPiece(new Pawn(new BoardCell(3,5), BLACK, DOWN));
        chessBoard.addPiece(new Pawn(new BoardCell(3,3), BLACK, UP));
        ArrayList<ChessPiece> piecesOneMoveAway = chessBoard.findPiecesOneMoveAway(boardcell, WHITE);
        assertThat(piecesOneMoveAway.size(), is(equalTo(4)));
        for (ChessPiece chessPiece : piecesOneMoveAway){
            assertThat(chessPiece.getType(), is(equalTo(PAWN)));
            int xAxisDiff = modulo(chessPiece.getBoardCell().getXaxis() - 4);
            int yAxisDiff = modulo(chessPiece.getBoardCell().getYaxis() - 4);
            assertThat(xAxisDiff, is(1));
            assertThat(yAxisDiff, is(1));
        }
    }

    @Test
    public void test_findPiecesOneMoveAway_finds_king_in_all_relevant_directions(){
        BoardCell boardcell = new BoardCell(4,4);
        chessBoard.addPiece(new King(new BoardCell(4,8), BLACK)); //Horizontal&Vertical
        chessBoard.addPiece(new King(new BoardCell(7,7), BLACK)); //Diagonal
        chessBoard.addPiece(new King(new BoardCell(5,6), BLACK)); //Knight
        chessBoard.addPiece(new King(new BoardCell(5,3), BLACK)); //Knight
        chessBoard.addPiece(new King(new BoardCell(5,4), BLACK)); //Knight
        chessBoard.addPiece(new King(new BoardCell(5,5), BLACK)); //Knight
        chessBoard.addPiece(new King(new BoardCell(4,3), BLACK)); //Knight
        chessBoard.addPiece(new King(new BoardCell(4,5), BLACK)); //Knight
        chessBoard.addPiece(new King(new BoardCell(3,3), BLACK)); //Knight
        chessBoard.addPiece(new King(new BoardCell(3,4), BLACK)); //Knight
        chessBoard.addPiece(new King(new BoardCell(3,5), BLACK)); //Knight
        ArrayList<ChessPiece> piecesOneMoveAway = chessBoard.findPiecesOneMoveAway(boardcell, WHITE);
        assertThat(piecesOneMoveAway.size(), is(equalTo(8)));
        for (ChessPiece chessPiece : piecesOneMoveAway){
            assertThat(chessPiece.getType(), is(equalTo(KING)));
            int xAxisDiff = modulo(chessPiece.getBoardCell().getXaxis() - 4);
            int yAxisDiff = modulo(chessPiece.getBoardCell().getYaxis() - 4);
            assertThat(xAxisDiff<2, is(true));
            assertThat(yAxisDiff<2, is(true));
        }
    }

    @Test
    public void test_Checkmate_returns_true_when_is_checkMate_and_returns_pieces_to_original_places(){
        chessBoard.addPiece(new King(new BoardCell(1,1), BLACK));
        chessBoard.addPiece(new Rook(new BoardCell(1,8), WHITE));
        chessBoard.addPiece(new King(new BoardCell(3,2), WHITE));
        //assertTrue(chessBoard.isInCheckMate(BLACK));
        assertThat(chessBoard.isInCheckMate(BLACK), is(true));
        assertThat(chessBoard.getKing(BLACK).getBoardCell(), is(equalTo(new BoardCell(1,1))));
    }

    @Test
    public void test_Checkmate_returns_false_when_king_can_move_out_of_check_and_returns_pieces_to_original_places(){
        chessBoard.addPiece(new King(new BoardCell(1,1), BLACK));
        chessBoard.addPiece(new Rook(new BoardCell(1,8), WHITE));
        assertThat(chessBoard.isInCheckMate(BLACK), is(false));
        assertThat(chessBoard.getKing(BLACK).getBoardCell(), is(equalTo(new BoardCell(1,1))));
    }

    @Test
    public void test_Checkmate_returns_false_when_king_not_in_check_and_returns_pieces_to_original_places(){
        chessBoard.addPiece(new King(new BoardCell(1,1), BLACK));
        chessBoard.addPiece(new Rook(new BoardCell(2,7), WHITE));
        chessBoard.addPiece(new King(new BoardCell(3,2), WHITE));
        assertThat(chessBoard.isInCheckMate(BLACK), is(false));
        assertThat(chessBoard.getKing(BLACK).getBoardCell(), is(equalTo(new BoardCell(1,1))));

    }

    @Test
    public void test_Checkmate_returns_false_when_piece_can_block_and_returns_pieces_to_original_places(){
        chessBoard.addPiece(new King(new BoardCell(1,1), BLACK));
        chessBoard.addPiece(new Rook(new BoardCell(1,8), WHITE));
        chessBoard.addPiece(new Rook(new BoardCell(2,7), WHITE));
        chessBoard.addPiece(new Rook(new BoardCell(4,4), BLACK));
        assertThat(chessBoard.isInCheckMate(BLACK), is(false));
        assertThat(chessBoard.getKing(BLACK).getBoardCell(), is(equalTo(new BoardCell(1,1))));
        ChessPiece chessPiece = chessBoard.getPieceByLocation(new BoardCell(4,4));
        assertThat(chessPiece.getType(), is(equalTo(ROOK)));
        assertThat(chessPiece.getColour(), is(equalTo(BLACK)));
    }

    @Test
    public void test_Checkmate_returns_false_when_piece_can_take_threatening_piece_and_returns_pieces_to_original_places(){
        chessBoard.addPiece(new King(new BoardCell(1,1), BLACK));
        chessBoard.addPiece(new Rook(new BoardCell(1,8), WHITE));
        chessBoard.addPiece(new Rook(new BoardCell(2,7), WHITE));
        chessBoard.addPiece(new Rook(new BoardCell(8,8), BLACK));
        assertThat(chessBoard.isInCheckMate(BLACK), is(false));
        assertThat(chessBoard.getKing(BLACK).getBoardCell(), is(equalTo(new BoardCell(1,1))));
        ChessPiece chessPiece = chessBoard.getPieceByLocation(new BoardCell(8,8));
        assertThat(chessPiece.getType(), is(equalTo(ROOK)));
        assertThat(chessPiece.getColour(), is(equalTo(BLACK)));
        ChessPiece chessPieceForcingCheck = chessBoard.getPieceByLocation(new BoardCell(1,8));
        assertThat(chessPieceForcingCheck.getType(), is(equalTo(ROOK)));
        assertThat(chessPieceForcingCheck.getColour(), is(equalTo(WHITE)));
    }







}
