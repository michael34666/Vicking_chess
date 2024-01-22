import java.util.Comparator;

import java.util.Comparator;
import java.util.function.Predicate;

public class Comperator2 implements Comparator<ConcretePiece> {


    private boolean isPlayerOneWin;

    public Comperator2(boolean isPlayerOneWin) {
        this.isPlayerOneWin = isPlayerOneWin;
    }


    @Override
    public int compare(ConcretePiece o1, ConcretePiece o2) {
        if (o1.getKills() > o2.getKills()) {
            return 1;
        }
        if (o1.getKills() == o2.getKills()) {
            if (o1.tag < o2.tag) {
                return 1;
            }
            if (o1.tag == o2.tag) {
                if (isPlayerOneWin && (!o1.getOwner().isPlayerOne())) {
                    return -1;
                }
                return 1;
            }
        }
        return -1;
    }
}