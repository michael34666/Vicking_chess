public class Pawn extends ConcretePiece {

    private int Index;
    private String type;
    private static final String PLAYER_ONE_TYPE = "♟";
    private static final String PLAYER_TWO_TYPE = "♙";
    boolean a;// help to thr tag
    int tag;


    public Pawn(ConcretePlayer owner, int tag) {
        super(owner, 7);
        this.tag = tag;
        if (owner.isPlayerOne()) {
            type = PLAYER_ONE_TYPE;
            a = true;
        } else {
            type = PLAYER_TWO_TYPE;
            a = false;
        }

    }


    @Override
    public String toString() {
        if (a) {
            return "D" + tag + ": ";
        }
        return "A" + tag + ": ";
    }

    @Override
    public String getType() {
        return type;
    }

    public String getName() {
        return this.toString();
    }


}


