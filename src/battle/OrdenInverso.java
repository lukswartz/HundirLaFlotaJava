package battle;
import java.util.Comparator;

public class OrdenInverso implements Comparator<Cell> {

    @Override
    public int compare(Cell c1, Cell c2) {
        return c2.compareTo(c1);
    }
}