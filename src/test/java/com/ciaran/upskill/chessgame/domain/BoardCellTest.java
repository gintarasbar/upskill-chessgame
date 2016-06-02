package com.ciaran.upskill.chessgame.domain;


import org.junit.Test;

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
}
