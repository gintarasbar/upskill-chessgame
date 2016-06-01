package com.ciaran.upskill.chessgame;

import com.ciaran.upskill.chessgame.domain.Coordinate;
import javafx.embed.swt.CustomTransferBuilder;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ChessGameTest {

    @Test
    public void test_validate_accepts_valid_coordinates(){
        assertTrue(ChessGame.validateCoordinateInput("A1") instanceof Coordinate);
        assertTrue(ChessGame.validateCoordinateInput("H8") instanceof Coordinate);
    }

    @Test
    public void test_validate_Coordinate_returns_null_if_invalid(){
        assertTrue(ChessGame.validateCoordinateInput("A9")==null);
        assertTrue(ChessGame.validateCoordinateInput("I8")==null);
    }
}
