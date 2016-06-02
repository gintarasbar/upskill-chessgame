package com.ciaran.upskill.chessgame.domain;

public class BoardCell {

    private int xaxis;
    private int yaxis;

    public static BoardCell createValidBoardCell(String coordinate) {
        if(coordinate.matches("[A-H][1-8]")){
            return new BoardCell(convertToInteger(coordinate.charAt(0)),Integer.parseInt(String.valueOf(coordinate.charAt(1))));
        }
        System.out.println("The co-ordinate you have input is invalid, please try again. Remember a co-ordinate is comprised of a letter from A-H followed by a number from 1-8.");
        return null;
    };

    public BoardCell(int xaxis, int yaxis) {
        this.xaxis=xaxis;
        this.yaxis=yaxis;
    }

    private static int convertToInteger(char xaxis) throws IllegalArgumentException{
        switch (xaxis){
            case 'A': return 1;
            case 'B': return 2;
            case 'C': return 3;
            case 'D': return 4;
            case 'E': return 5;
            case 'F': return 6;
            case 'G': return 7;
            case 'H': return 8;
            default: throw new IllegalArgumentException("Character must be in range A-H");
        }
    }

    public int getXaxis() {
        return xaxis;
    }

    private char getXaxisChar() {
        switch (xaxis){
            case 1: return 'A';
            case 2: return 'B';
            case 3: return 'C';
            case 4: return 'D';
            case 5: return 'E';
            case 6: return 'F';
            case 7: return 'G';
            case 8: return 'H';
            default: return Character.MIN_VALUE;
        }
    }

    public void setXaxis(int xaxis) {
        if (xaxis<1||xaxis>8)throw new IllegalArgumentException("Xaxis ranges from 1 to 8");
        this.xaxis = xaxis;
    }

    public int getYaxis() {
        return yaxis;
    }

    public void setYaxis(int yaxis) {
        if (yaxis<1||yaxis>8)throw new IllegalArgumentException("Yaxis ranges from 1 to 8");
        this.yaxis = yaxis;
    }

    public void moveXAxisUp() {
        xaxis++;
    }

    public void moveXAxisDown() {
        xaxis--;
    }

    public void moveYAxisUp() {
        yaxis++;
    }

    public void moveYAxisDown() {
        yaxis--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardCell that = (BoardCell) o;

        if (xaxis != that.xaxis) return false;
        return yaxis == that.yaxis;

    }
}
