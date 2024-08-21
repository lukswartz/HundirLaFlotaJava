package battle;
import java.util.ArrayList;

public class Ship {

    private final String desc;
    private final int len;
    private final Cell beginPos;
    private final Cell endPos;
    private final Cell[] parts;
    private boolean hundido = false;

    /**
     * Factory static method that returns an object of type Ship.
     * @param desc: description String of the ship
     * @param len: length of the Ship
     * @param beginPos: Cell type coordinate. It will be in valid range.
     * @param endPos: Cell type coordinate in valid range.
     * @return Ship object. If length between cells does not match length of the ship return null.
     * If cells are not in line return null.
     */
    public static Ship createShip(String desc, int len, Cell beginPos, Cell endPos) {
        Cell start = beginPos;
        Cell end = endPos;

        if (start.lengthBeetween(end) < 0) {
            System.out.println("Error: wrong start and end position");
            return null;
        }else if (start.lengthBeetween(end) != len) {
            System.out.println("Error: wrong length between coordinates");
            return null;
        }else if (start != null && end != null) {
             return new Ship(desc, len, start, end);
        }

        return null;


    }

    private Ship(String desc, int len, Cell beginPos, Cell endPos) {
        this.desc = desc;
        this.len = len;
        this.beginPos = beginPos;
        this.endPos = endPos;
        ArrayList<Cell> partes = getParts(beginPos, endPos);

        this.parts = new Cell[len];

        for (int i = 0; i < len ; i++) {
            parts[i] = partes.get(i);
            parts[i].setEstado(CELL_STATE.SHIP);
        }

    }
    public String getDesc() {
        return desc;
    }

    public int getLen() {
        return len;
    }
    public Cell getBeginPos() {
        return beginPos;
    }

    public Cell getEndPos() {
        return endPos;

    }

    public Cell[] getArrayPartes() {
        return parts;
    }

    public boolean estaHundido() {
        for (Cell c : parts) {
            if(c.getEstado() != CELL_STATE.HIT) {
                hundido = false;
                return false;
            }
        }

        hundido = true;
        return hundido;
    }


    private ArrayList<Cell> getParts(Cell begin, Cell end) {

        ArrayList<Cell> partes = new ArrayList<>();

        if (begin.getRow() == end.getRow()) {
            for (int i = Math.min(begin.getColumn(), end.getColumn()); i <= Math.max(begin.getColumn(), end.getColumn()); i++) {
                partes.add(Cell.createCell(begin.getRow(), i));
            }

        } else {

            for (int i = Math.min(begin.getRow(), end.getRow()); i <= Math.max(begin.getRow(), end.getRow()); i++) {
                partes.add(Cell.createCell((char) i, begin.getColumn()));

            }
        }

        if (begin.compareTo(end) > 0) {
            partes.sort(new OrdenInverso());

        }

        return partes;

    }
}