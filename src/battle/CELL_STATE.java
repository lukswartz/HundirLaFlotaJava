package battle;

public enum CELL_STATE {
    FOG('~'), SHIP('O'),HIT('X'), MISS('M');

    private char desc;

    private CELL_STATE(char desc) {
        this.desc = desc;
    }

    public char getDesc () {
        return desc;
    }
}
