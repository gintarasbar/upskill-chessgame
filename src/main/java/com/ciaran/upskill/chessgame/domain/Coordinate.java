package com.ciaran.upskill.chessgame.domain;

public class Coordinate {

    private int xaxis;
    private int yaxis;

    public Coordinate(char xaxis, char yaxis){
        this.xaxis=convertToInteger(xaxis);
        this.yaxis=yaxis;
    }

    public Coordinate(int xaxis, int yaxis) {
        this.xaxis=xaxis;
        this.yaxis=yaxis;
    }

    private int convertToInteger(char xaxis) {
        switch (xaxis){
            case 'A': return 1;
            case 'B': return 2;
            case 'C': return 3;
            case 'D': return 4;
            case 'E': return 5;
            case 'F': return 6;
            case 'G': return 7;
            case 'H': return 8;
            default: return Integer.parseInt(null);
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

    public void setXaxis(int xaxis) { this.xaxis = xaxis;
    }

    public void setXaxis(char xaxis) {
        this.xaxis = convertToInteger(xaxis);
    }

    public int getYaxis() {
        return yaxis;
    }

    public char getYaxisChar() { return (char) yaxis;}

    public void setYaxis(int yaxis) {
        this.yaxis = yaxis;
    }

    public void setYaxis(char yaxis) {
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
}
