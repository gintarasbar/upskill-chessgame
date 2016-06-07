package com.ciaran.upskill.chessgame.domain;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BoardCellTest {
    @Test
    public void test_validate_accepts_valid_coordinates(){
        assertTrue(BoardCell.createValidBoardCell("A1") instanceof BoardCell);
        assertTrue(BoardCell.createValidBoardCell("H8") instanceof BoardCell);
    }

    @Test
    public void test_validate_Coordinate_returns_null_if_invalid(){
        assertTrue(BoardCell.createValidBoardCell("A9")==null);
        assertTrue(BoardCell.createValidBoardCell("I8")==null);
    }

    @Test
    public void test_isValid_returns_true_for_valid_boardcells(){
        assertTrue(new BoardCell(1,8).isValid());
        assertTrue(new BoardCell(8,1).isValid());
    }

    @Test
    public void test_isValid_returns_false_for_invalid_boardcells(){
        assertFalse(new BoardCell(0,1).isValid());
        assertFalse(new BoardCell(1,0).isValid());
        assertFalse(new BoardCell(8,9).isValid());
        assertFalse(new BoardCell(9,8).isValid());
    }
}
