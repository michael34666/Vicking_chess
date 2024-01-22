import java.util.Comparator;

public class Comperator1 implements Comparator<ConcretePiece> {



    public Comperator1(){
    }


    @Override
    public int compare(ConcretePiece o1, ConcretePiece o2) {
        if (o1.stepsArr.size() > o2.stepsArr.size()) {
            return 1;
        }
        if(o1.stepsArr.size() == o2.stepsArr.size()){
            if(o1.tag > o2.tag){
                return 1;
            }
            return -1;
        }
        return -1;
    }
}

   