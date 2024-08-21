package battle;
/**
 * Una celda está obliga a crearse con las coordenadas dentro de los límites del tablero.
 * FILAS: de 'A' (65) a 'J' (74). Internamente se almacena de 1 a 10.
 * Pero se representa como char realizando artimética con códigos ASCII '@' == 64.
 * COLUMNAS: de 1 a 10;
 */
public class Cell implements Comparable <Cell> {

    private int row;
    private int column;
    private CELL_STATE estado;
    private boolean withFog;



    public static Cell createCell(int row, int column) {

        if (row > 0 && row < 11 && column > 0 && column < 11) {

            return new Cell(row, column);
        }else {
            return null;
        }


    }

    public static Cell createCell(char rowChar, int column) {

        int row = (int)(rowChar - '@'); //input row like a char from 'A' to 'J'

        return createCell(row, column);


    }

    private Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.estado = CELL_STATE.FOG;
        this.withFog = true;
    }

    public char getRow() {
        return (char) ('@' + row);
    }

    public int getColumn() {
        return column;
    }

    public CELL_STATE getEstado() {
        return estado;
    }

    public void setEstado (CELL_STATE estado) {
        this.estado = estado;
    }

    public boolean hasFog() {
        return withFog;
    }

    public void setFog(boolean withFog) {
        this.withFog = withFog;
    }

    public int lengthBeetween(Cell other) {

        int result = -1;

        if (other != null) {
            if (this.row == other.row) {
                result = Math.abs(this.column - other.column) + 1 ;
            } else if (this.column == other.column) {
                result = Math.abs(this.row - other.row) + 1;
            } else {
                result = -1;
            }
        }

        return result;
    }

    public String toString() {
        return  (char)(row + '@' ) + "" +  column;
    }

    @Override
    public int compareTo(Cell cell) {

        if (this.getRow() > cell.getRow() ||
                this.getRow() == cell.getRow() && this.getColumn() > cell.getColumn()) {
            return 1;
        }else if (this.getRow() < cell.getRow() ||
                    this.getRow() == cell.getRow() && this.getColumn() < cell.getColumn()) {

            return -1;
        }else {

            return 0;

        }

    }


}