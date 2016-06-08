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

import static com.ciaran.upskill.chessgame.UtilClass.modulo;
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
        ChessPiece pawn = new Pawn(new BoardCell(3,4),"Black","up");
        assertThat(chessBoard.addPiece(pawn), is(true));
        assertThat(chessBoard.contains(pawn), is(true));
    }

    @Test
    public void test_add_piece_returns_false_and_doesnt_add_piece_to_board_if_space_is_occupied(){
        chessBoard.setUpBoard();
        ChessPiece pawn = new Pawn(new BoardCell(4,2),"Black","up");
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
        assertThat(chessBoard.removePiece(new Pawn(new BoardCell(3,4),"Black", "up")), is(false));
    }

    //Test getKing
    @Test
    public void test_getKing_returns_king_by_colour(){
        chessBoard.setUpBoard();
        King king = chessBoard.getKing("Black");
        assertThat(king.getColour(), is(equalTo("Black")));
        assertThat(king.getBoardCell(), is(equalTo(new BoardCell(5,8))));
        king = chessBoard.getKing("White");
        assertThat(king.getColour(), is(equalTo("White")));
        assertThat(king.getBoardCell(), is(equalTo(new BoardCell(5,1))));
    }

    //Test move
    @Test
    public void test_move_piece_returns_true_on_valid_move(){
        chessBoard.setUpBoard();
        assertThat(chessBoard.movePiece(new BoardCell(4,2), new BoardCell(4,4),"White"), is(true));
    }

    @Test
    public void test_move_piece_returns_false_if_start_position_has_piece_of_different_colour(){
        chessBoard.setUpBoard();
        assertThat(chessBoard.movePiece(new BoardCell(4,2), new BoardCell(4,4),"Black"), is(false));
    }

    @Test
    public void test_move_piece_returns_false_if_finish_position_has_piece_of_same_colour(){
        chessBoard.setUpBoard();
        assertThat(chessBoard.movePiece(new BoardCell(1,1), new BoardCell(1,2),"White"), is(false));
    }

    @Test
    public void test_move_piece_returns_false_if_no_move_taken(){
        chessBoard.setUpBoard();
        assertThat(chessBoard.movePiece(new BoardCell(2,1), new BoardCell(2,1),"White"), is(false));
    }

    @Test
    public void test_move_piece_returns_false_if_move_throws_exception(){
        chessBoard.setUpBoard();
        assertThat(chessBoard.movePiece(new BoardCell(4,2), new BoardCell(3,3),"White"), is(false));
    }

    @Test
    public void test_move_piece_returns_false_if_king_left_in_check(){
        chessBoard.setUpBoard();
        chessBoard.addPiece(new Rook(new BoardCell(5,5),"Black"));
        chessBoard.addPiece(new Rook(new BoardCell(4,3), "Black"));
        chessBoard.movePiece(new BoardCell(5,2),new BoardCell(4,3), "White");
    }

    @Test
    public void test_findPiecesOneMoveAway_finds_rooks_in_all_relevant_directions(){
        BoardCell boardcell = new BoardCell(4,4);
        chessBoard.addPiece(new Rook(new BoardCell(1,4), "Black"));
        chessBoard.addPiece(new Rook(new BoardCell(6,4), "Black"));
        chessBoard.addPiece(new Rook(new BoardCell(4,2), "Black"));
        chessBoard.addPiece(new Rook(new BoardCell(4,8), "Black"));
        chessBoard.addPiece(new Rook(new BoardCell(7,7), "Black")); //Diagonal
        chessBoard.addPiece(new Rook(new BoardCell(5,6), "Black")); //Knight
        ArrayList<ChessPiece> piecesOneMoveAway = chessBoard.findPiecesOneMoveAway(boardcell, "White");
        assertThat(piecesOneMoveAway.size(), is(equalTo(4)));
        for (ChessPiece chessPiece : piecesOneMoveAway){
            assertThat(chessPiece.getType(), is(equalTo("Rook")));
            assertThat(chessPiece.getBoardCell().getXaxis()==4||chessPiece.getBoardCell().getYaxis()==4, is(true));
        }
    }

    @Test
    public void test_findPiecesOneMoveAway_finds_queen_in_all_relevant_directions(){
        BoardCell boardcell = new BoardCell(4,4);
        chessBoard.addPiece(new Queen(new BoardCell(1,4), "Black"));
        chessBoard.addPiece(new Queen(new BoardCell(6,4), "Black"));
        chessBoard.addPiece(new Queen(new BoardCell(4,2), "Black"));
        chessBoard.addPiece(new Queen(new BoardCell(4,8), "Black"));
        chessBoard.addPiece(new Queen(new BoardCell(7,7), "Black")); //Diagonal
        chessBoard.addPiece(new Queen(new BoardCell(2,2), "Black")); //Diagonal
        chessBoard.addPiece(new Queen(new BoardCell(2,6), "Black")); //Diagonal
        chessBoard.addPiece(new Queen(new BoardCell(5,3), "Black")); //Diagonal
        chessBoard.addPiece(new Queen(new BoardCell(5,6), "Black")); //Knight
        ArrayList<ChessPiece> piecesOneMoveAway = chessBoard.findPiecesOneMoveAway(boardcell, "White");
        assertThat(piecesOneMoveAway.size(), is(equalTo(8)));
        for (ChessPiece chessPiece : piecesOneMoveAway){
            assertThat(chessPiece.getType(), is(equalTo("Queen")));
            int xAxisDiff = modulo(chessPiece.getBoardCell().getXaxis() - 4);
            int yAxisDiff = modulo(chessPiece.getBoardCell().getYaxis() - 4);
            assertThat(xAxisDiff==yAxisDiff||(yAxisDiff==0&&xAxisDiff!=0)||(xAxisDiff==0&&yAxisDiff!=0), is(true));
        }
    }

    @Test
    public void test_findPiecesOneMoveAway_finds_bishop_in_all_relevant_directions(){
        BoardCell boardcell = new BoardCell(4,4);
        chessBoard.addPiece(new Bishop(new BoardCell(4,8), "Black")); //Horizontal&Vertical
        chessBoard.addPiece(new Bishop(new BoardCell(7,7), "Black"));
        chessBoard.addPiece(new Bishop(new BoardCell(2,2), "Black"));
        chessBoard.addPiece(new Bishop(new BoardCell(2,6), "Black"));
        chessBoard.addPiece(new Bishop(new BoardCell(5,3), "Black"));
        chessBoard.addPiece(new Bishop(new BoardCell(5,6), "Black")); //Knight
        ArrayList<ChessPiece> piecesOneMoveAway = chessBoard.findPiecesOneMoveAway(boardcell, "White");
        assertThat(piecesOneMoveAway.size(), is(equalTo(4)));
        for (ChessPiece chessPiece : piecesOneMoveAway){
            assertThat(chessPiece.getType(), is(equalTo("Bishop")));
            int xAxisDiff = modulo(chessPiece.getBoardCell().getXaxis() - 4);
            int yAxisDiff = modulo(chessPiece.getBoardCell().getYaxis() - 4);
            assertThat(xAxisDiff, is(equalTo(yAxisDiff)));
        }
    }

    @Test
    public void test_findPiecesOneMoveAway_finds_knight_in_all_relevant_directions(){
        BoardCell boardcell = new BoardCell(4,4);
        chessBoard.addPiece(new Knight(new BoardCell(4,8), "Black")); //Horizontal&Vertical
        chessBoard.addPiece(new Knight(new BoardCell(7,7), "Black")); //Diagonal
        chessBoard.addPiece(new Knight(new BoardCell(5,6), "Black")); //Knight
        chessBoard.addPiece(new Knight(new BoardCell(6,5), "Black")); //Knight
        chessBoard.addPiece(new Knight(new BoardCell(3,6), "Black")); //Knight
        chessBoard.addPiece(new Knight(new BoardCell(6,3), "Black")); //Knight
        chessBoard.addPiece(new Knight(new BoardCell(5,2), "Black")); //Knight
        chessBoard.addPiece(new Knight(new BoardCell(2,5), "Black")); //Knight
        chessBoard.addPiece(new Knight(new BoardCell(3,2), "Black")); //Knight
        chessBoard.addPiece(new Knight(new BoardCell(2,3), "Black")); //Knight
        ArrayList<ChessPiece> piecesOneMoveAway = chessBoard.findPiecesOneMoveAway(boardcell, "White");
        assertThat(piecesOneMoveAway.size(), is(equalTo(8)));
        for (ChessPiece chessPiece : piecesOneMoveAway){
            assertThat(chessPiece.getType(), is(equalTo("Knight")));
            int xAxisDiff = modulo(chessPiece.getBoardCell().getXaxis() - 4);
            int yAxisDiff = modulo(chessPiece.getBoardCell().getYaxis() - 4);
            assertThat((xAxisDiff==1&&yAxisDiff==2)||(yAxisDiff==1&&xAxisDiff==2), is(true));
        }
    }

    @Test
    public void test_findPiecesOneMoveAway_finds_pawns_in_all_relevant_directions(){
        BoardCell boardcell = new BoardCell(4,4);
        chessBoard.addPiece(new Pawn(new BoardCell(4,8), "Black", "up")); //Horizontal&Vertical
        chessBoard.addPiece(new Pawn(new BoardCell(7,7), "Black", "up")); //Diagonal
        chessBoard.addPiece(new Pawn(new BoardCell(5,6), "Black", "up")); //Knight
        chessBoard.addPiece(new Pawn(new BoardCell(5,5), "Black", "down"));
        chessBoard.addPiece(new Pawn(new BoardCell(5,3), "Black", "up"));
        chessBoard.addPiece(new Pawn(new BoardCell(3,5), "Black", "down"));
        chessBoard.addPiece(new Pawn(new BoardCell(3,3), "Black", "up"));
        ArrayList<ChessPiece> piecesOneMoveAway = chessBoard.findPiecesOneMoveAway(boardcell, "White");
        assertThat(piecesOneMoveAway.size(), is(equalTo(4)));
        for (ChessPiece chessPiece : piecesOneMoveAway){
            assertThat(chessPiece.getType(), is(equalTo("Pawn")));
            int xAxisDiff = modulo(chessPiece.getBoardCell().getXaxis() - 4);
            int yAxisDiff = modulo(chessPiece.getBoardCell().getYaxis() - 4);
            assertThat(xAxisDiff, is(1));
            assertThat(yAxisDiff, is(1));
        }
    }

    @Test
    public void test_findPiecesOneMoveAway_finds_king_in_all_relevant_directions(){
        BoardCell boardcell = new BoardCell(4,4);
        chessBoard.addPiece(new King(new BoardCell(4,8), "Black")); //Horizontal&Vertical
        chessBoard.addPiece(new King(new BoardCell(7,7), "Black")); //Diagonal
        chessBoard.addPiece(new King(new BoardCell(5,6), "Black")); //Knight
        chessBoard.addPiece(new King(new BoardCell(5,3), "Black")); //Knight
        chessBoard.addPiece(new King(new BoardCell(5,4), "Black")); //Knight
        chessBoard.addPiece(new King(new BoardCell(5,5), "Black")); //Knight
        chessBoard.addPiece(new King(new BoardCell(4,3), "Black")); //Knight
        chessBoard.addPiece(new King(new BoardCell(4,5), "Black")); //Knight
        chessBoard.addPiece(new King(new BoardCell(3,3), "Black")); //Knight
        chessBoard.addPiece(new King(new BoardCell(3,4), "Black")); //Knight
        chessBoard.addPiece(new King(new BoardCell(3,5), "Black")); //Knight
        ArrayList<ChessPiece> piecesOneMoveAway = chessBoard.findPiecesOneMoveAway(boardcell, "White");
        assertThat(piecesOneMoveAway.size(), is(equalTo(8)));
        for (ChessPiece chessPiece : piecesOneMoveAway){
            assertThat(chessPiece.getType(), is(equalTo("King")));
            int xAxisDiff = modulo(chessPiece.getBoardCell().getXaxis() - 4);
            int yAxisDiff = modulo(chessPiece.getBoardCell().getYaxis() - 4);
            assertThat(xAxisDiff<2, is(true));
            assertThat(yAxisDiff<2, is(true));
        }
    }

    @Test
    public void test_Checkmate_returns_true_when_is_checkMate_and_returns_pieces_to_original_places(){
        chessBoard.addPiece(new King(new BoardCell(1,1), "Black"));
        chessBoard.addPiece(new Rook(new BoardCell(1,8), "White"));
        chessBoard.addPiece(new King(new BoardCell(3,2), "White"));
        //assertTrue(chessBoard.isInCheckMate("Black"));
        assertThat(chessBoard.isInCheckMate("Black"), is(true));
        assertThat(chessBoard.getKing("Black").getBoardCell(), is(equalTo(new BoardCell(1,1))));
    }

    @Test
    public void test_Checkmate_returns_false_when_king_can_move_out_of_check_and_returns_pieces_to_original_places(){
        chessBoard.addPiece(new King(new BoardCell(1,1), "Black"));
        chessBoard.addPiece(new Rook(new BoardCell(1,8), "White"));
        assertThat(chessBoard.isInCheckMate("Black"), is(false));
        assertThat(chessBoard.getKing("Black").getBoardCell(), is(equalTo(new BoardCell(1,1))));
    }

    @Test
    public void test_Checkmate_returns_false_when_king_not_in_check_and_returns_pieces_to_original_places(){
        chessBoard.addPiece(new King(new BoardCell(1,1), "Black"));
        chessBoard.addPiece(new Rook(new BoardCell(2,7), "White"));
        chessBoard.addPiece(new King(new BoardCell(3,2), "White"));
        assertThat(chessBoard.isInCheckMate("Black"), is(false));
        assertThat(chessBoard.getKing("Black").getBoardCell(), is(equalTo(new BoardCell(1,1))));

    }

    @Test
    public void test_Checkmate_returns_false_when_piece_can_block_and_returns_pieces_to_original_places(){
        chessBoard.addPiece(new King(new BoardCell(1,1), "Black"));
        chessBoard.addPiece(new Rook(new BoardCell(1,8), "White"));
        chessBoard.addPiece(new Rook(new BoardCell(2,7), "White"));
        chessBoard.addPiece(new Rook(new BoardCell(4,4), "Black"));
        assertThat(chessBoard.isInCheckMate("Black"), is(false));
        assertThat(chessBoard.getKing("Black").getBoardCell(), is(equalTo(new BoardCell(1,1))));
        ChessPiece chessPiece = chessBoard.getPieceByLocation(new BoardCell(4,4));
        assertThat(chessPiece.getType(), is(equalTo("Rook")));
        assertThat(chessPiece.getColour(), is(equalTo("Black")));
    }

    @Test
    public void test_Checkmate_returns_false_when_piece_can_take_threatening_piece_and_returns_pieces_to_original_places(){
        chessBoard.addPiece(new King(new BoardCell(1,1), "Black"));
        chessBoard.addPiece(new Rook(new BoardCell(1,8), "White"));
        chessBoard.addPiece(new Rook(new BoardCell(2,7), "White"));
        chessBoard.addPiece(new Rook(new BoardCell(8,8), "Black"));
        assertThat(chessBoard.isInCheckMate("Black"), is(false));
        assertThat(chessBoard.getKing("Black").getBoardCell(), is(equalTo(new BoardCell(1,1))));
        ChessPiece chessPiece = chessBoard.getPieceByLocation(new BoardCell(8,8));
        assertThat(chessPiece.getType(), is(equalTo("Rook")));
        assertThat(chessPiece.getColour(), is(equalTo("Black")));
        ChessPiece chessPieceForcingCheck = chessBoard.getPieceByLocation(new BoardCell(1,8));
        assertThat(chessPieceForcingCheck.getType(), is(equalTo("Rook")));
        assertThat(chessPieceForcingCheck.getColour(), is(equalTo("White")));
    }







}
