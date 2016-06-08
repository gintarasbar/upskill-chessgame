package com.ciaran.upskill.chessgame.domain;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BoardCellTest {
    @Test
    public void test_validate_accepts_valid_coordinates(){
        assertThat(BoardCell.createValidBoardCell("A1"), is(instanceOf(BoardCell.class)));
        assertThat(BoardCell.createValidBoardCell("H8"), is(instanceOf(BoardCell.class)));
    }

    @Test
    public void test_validate_Coordinate_returns_null_if_invalid(){
        assertThat(BoardCell.createValidBoardCell("A9"), is(equalTo(null)));
        assertThat(BoardCell.createValidBoardCell("I8"), is(equalTo(null)));
    }

    @Test
    public void test_isValid_returns_true_for_valid_boardcells(){
        assertThat(new BoardCell(1,8).isValid(), is(true));
        assertThat(new BoardCell(8,1).isValid(), is(true));
    }

    @Test
    public void test_isValid_returns_false_for_invalid_boardcells(){
        assertThat(new BoardCell(0,1).isValid(), is(false));
        assertThat(new BoardCell(1,0).isValid(), is(false));
        assertThat(new BoardCell(8,9).isValid(), is(false));
        assertThat(new BoardCell(9,8).isValid(), is(false));
    }
}
