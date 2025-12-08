package com.BotTicTakToe.model;

public class Board {

    public final int WIDTH = 3;
    public final int HEIGHT = 3;

    private final Cell[][] board;

    public Board() {
        this.board = new Cell[WIDTH][HEIGHT];
        fillByCells();
    }

    public Cell getCell(int x, int y) {
        if (isValidCoordinates(x, y)) {
            return board[x][y];
        } else {
            return null;
        }
    }

    private boolean isValidCoordinates(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    private void fillByCells() {
        for (int y = 0; y < WIDTH; y++) {
            for (int x = 0; x < HEIGHT; x++) {
                board[y][x] = new Cell(x, y);
            }
        }
    }


}
