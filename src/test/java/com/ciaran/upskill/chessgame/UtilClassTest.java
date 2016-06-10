package com.ciaran.upskill.chessgame;

import org.junit.Test;

import static com.ciaran.upskill.chessgame.Colour.BLACK;
import static com.ciaran.upskill.chessgame.Colour.WHITE;
import static com.ciaran.upskill.chessgame.UtilClass.modulo;
import static com.ciaran.upskill.chessgame.UtilClass.switchColour;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UtilClassTest {

    @Test
    public void test_modulo_returns_positive_values_regardless_of_input(){
        assertThat(modulo(1), is(1));
        assertThat(modulo(-1), is(1));
    }

    @Test
    public void test_switch_colour_returns_opposing_colour(){
        assertThat(switchColour(BLACK), is(WHITE));
        assertThat(switchColour(WHITE), is(BLACK));
    }

    @Test
    public void test_switch_colour_handles_nulls(){
        assertThat(switchColour(null), is(equalTo(null)));
    }
}
