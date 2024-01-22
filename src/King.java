import java.awt.*;
public class King extends ConcretePiece{

    private static final String type = "♔";
    int tag;

    public King(ConcretePlayer owner) {
        super(owner,7);
    }



    @Override
    public String getType() {
        return type;
    }

    public String toString() {
        return "K7: ";
    }
}

