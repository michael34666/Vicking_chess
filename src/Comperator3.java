import java.util.Comparator;

public class Comperator3 implements Comparator<ConcretePiece> {


    private boolean isPlayerOneWin;

    public Comperator3(boolean isPlayerOneWin){
        this.isPlayerOneWin = isPlayerOneWin;
    }


    @Override
    public int compare(ConcretePiece o1, ConcretePiece o2) {
        if (o1.getSquares() > o2.getSquares()) {
            return 1;
        }
        if(o1.getSquares() == o2.getSquares()){
            if(o1.tag < o2.tag){
                return 1;
            }
            if(o1.tag == o2.tag) {
                if (isPlayerOneWin && (!o1.getOwner().isPlayerOne())) {
                    return -1;
                }
                return 1;
            }
        }
        return -1;
    }
}