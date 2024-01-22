import java.util.ArrayList;
import java.util.List;

public abstract class ConcretePiece implements Piece {
    private final ConcretePlayer owner;
     private int steps;
    private int kills;
    protected ArrayList<Position> stepsArr = new ArrayList<Position>();
    protected int tag;

    int squares;




    public ConcretePiece(ConcretePlayer owner, int tag) {
        this.owner = owner;
        this.tag=tag;
    }

    public void setSteps() {
        this.steps++;
    }

    public int getSteps() {
        return steps;
    }

    public List<Position> getStepsList() {
        return stepsArr;
    }

    /**
     * Get the player who owns the piece.
     *
     * @return The player who is the owner of this game piece.
     */
    @Override
    public Player getOwner() {
        return owner;
    }

    public void setKills() {
        this.kills++;
    }

    public int getKills() {
        return kills;

    }

    public void setSquares(int i) {
        this.squares=i;
    }

    public int getSquares() {
        return squares;
    }
}
