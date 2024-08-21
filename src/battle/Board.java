package battle;

import java.util.*;


public class Board {
    private static final int ROWS = 10;
    private static final int COLS = 10;

    private Cell[][] board = new Cell[10][10];
    private ArrayList<Ship> barcos = new ArrayList<>();
    private int barcosHundidos = 0;


    public Board() {
        initBoard();
    }

    /*
    Create a empty board of Cells.
     */
    private void initBoard() {

        for (char i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLS; j++) {
                board[i][j] = Cell.createCell((char)(i + 65), j + 1);
            }

        }
    }
    public int getBarcosHundidos() {
        return barcosHundidos;
    }

    public ArrayList<Ship> getBarcos() {
        return barcos;
    }

    public boolean addShip(Ship s) {
        if (validShipPos(s)) {
            barcos.add(s);
            updateBoard();
            return true;

        } else {
            return false;
        }

    }

    /*
    Check around all parts of the new ship and check if there are other ship.

     */
    private boolean validShipPos(Ship s) {
        Cell[] newShipParts = s.getArrayPartes();

        for (Cell c : newShipParts) {

            for (int i = c.getRow() - 'A' - 1; i <= c.getRow() - 'A' + 1; i++) {
                for(int j = c.getColumn() -2; j <= c.getColumn(); j++){
                    if(i >= 0 && i < ROWS && j >=0 && j <COLS ) {

                        if (board[i][j].getEstado() == CELL_STATE.SHIP) {
                            return false;
                        }

                    }
                }
            }


        }

        return true;
    }

    private void updateBoard() {

        for (Ship s: barcos) {
            Cell[] partes = s.getArrayPartes();

            for(Cell c: partes) {
                setCellState(c.getRow(), c.getColumn(), c.getEstado());
            }

        }
    }

    public void setCellState(char row,  int col, CELL_STATE estado) {

        int i = (int) (row - 'A');
        int j = col - 1;

        board[i][j].setEstado(estado);

    }

    public void makeShot(char row, int col) {


        int r = (int) (row - 'A');
        int c = col -1;
        board[r][c].setFog(false);

        if (board[r][c].getEstado() == CELL_STATE.SHIP) {

            setCellState(row, col, CELL_STATE.HIT);
            updateShipsAfterShot(row, col);
        } else if ( board[r][c].getEstado() == CELL_STATE.HIT) {
            System.out.println("You hit a ship!");

        } else{

            setCellState(row, col, CELL_STATE.MISS);
            System.out.println("You missed!");

        }
    }

    private void updateShipsAfterShot(char row, int col) {

        for (Ship s: barcos) {
            Cell[] partes = s.getArrayPartes();

            for(Cell c: partes) {
                if (c.getRow() == row && c.getColumn() == col) {
                    c.setEstado(CELL_STATE.HIT);
                    if(s.estaHundido()) {
                        barcosHundidos++;
                        if(barcosHundidos < 5 ) {
                            System.out.println("You sank a ship!");
                            return;
                        }else {
                            return;
                        }

                    }else {
                        System.out.println("You hit a ship!");
                        return;
                    }
                }
            }

        }
    }

    public void  printBoardWithFog() {
        System.out.println();
        for (int i = -1; i < COLS; i++) {
            for(int j = -1; j < ROWS ; j++) {

                if (i == -1) {
                    if (j == -1) {
                        System.out.print("  ");
                    } else {
                        System.out.print((j + 1) + " ");
                    }
                } else {
                    if(j == -1) {
                        System.out.print((char)(65 + i) + " ");
                    } else {
                        if(board[i][j].hasFog()){
                            System.out.print("~ ");
                        }else {
                            System.out.print(board[i][j].getEstado().getDesc() + " ");
                        }
                    }
                }
            }

            System.out.println();
        }

    }
    public void  printBoard() {
        for (int i = -1; i < COLS; i++) {
            for(int j = -1; j < ROWS ; j++) {

                if (i == -1) {
                    if (j == -1) {
                        System.out.print("  ");
                    } else {
                        System.out.print((j + 1) + " ");
                    }
                } else {
                    if(j == -1) {
                        System.out.print((char)(65 + i) + " ");
                    } else {
                        System.out.print(board[i][j].getEstado().getDesc() + " ");
                    }
                }
            }

            System.out.println();
        }
        System.out.println();

    }

}