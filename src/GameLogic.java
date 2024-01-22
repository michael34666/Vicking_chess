import java.util.*;

public class GameLogic implements PlayableLogic {

    private ConcretePiece[][] board;
    private final ConcretePlayer firstPlayer;
    private final ConcretePlayer secondPlayer;
    private boolean isFirstPlayerTurn;
    Position kingPos;
    List<ConcretePiece> PieceList = new ArrayList<>();
    int[][][] q4 = new int[11][11][37];
    public GameLogic() {
        this.board = new ConcretePiece[11][11];
        firstPlayer = new ConcretePlayer(true);
        secondPlayer = new ConcretePlayer(false);
        kingPos = new Position(5, 5);
        initialize();
    }

    public void initialize() {
        isFirstPlayerTurn = false;
        board[3][5] = new Pawn(firstPlayer, 1);
        board[4][5] = new Pawn(firstPlayer, 3);
        board[6][5] = new Pawn(firstPlayer, 11);
        board[7][5] = new Pawn(firstPlayer, 13);
        board[4][4] = new Pawn(firstPlayer, 2);
        board[4][6] = new Pawn(firstPlayer, 4);
        board[5][7] = new Pawn(firstPlayer, 9);
        board[5][6] = new Pawn(firstPlayer, 8);
        board[5][4] = new Pawn(firstPlayer, 6);
        board[5][3] = new Pawn(firstPlayer, 5);
        board[6][6] = new Pawn(firstPlayer, 12);
        board[6][4] = new Pawn(firstPlayer, 10);
        board[5][5] = new King(firstPlayer);
        board[0][3] = new Pawn(secondPlayer, 1);
        board[0][4] = new Pawn(secondPlayer, 2);
        board[0][5] = new Pawn(secondPlayer, 3);
        board[0][6] = new Pawn(secondPlayer, 4);
        board[0][7] = new Pawn(secondPlayer, 5);
        board[1][5] = new Pawn(secondPlayer, 6);
        board[3][10] = new Pawn(secondPlayer, 8);
        board[4][10] = new Pawn(secondPlayer, 10);
        board[5][10] = new Pawn(secondPlayer, 14);
        board[6][10] = new Pawn(secondPlayer, 16);
        board[7][10] = new Pawn(secondPlayer, 18);
        board[5][9] = new Pawn(secondPlayer, 13);
        board[10][3] = new Pawn(secondPlayer, 20);
        board[10][4] = new Pawn(secondPlayer, 21);
        board[10][5] = new Pawn(secondPlayer, 22);
        board[10][6] = new Pawn(secondPlayer, 23);
        board[10][7] = new Pawn(secondPlayer, 24);
        board[9][5] = new Pawn(secondPlayer, 19);
        board[3][0] = new Pawn(secondPlayer, 7);
        board[4][0] = new Pawn(secondPlayer, 9);
        board[5][0] = new Pawn(secondPlayer, 11);
        board[6][0] = new Pawn(secondPlayer, 15);
        board[7][0] = new Pawn(secondPlayer, 17);
        board[5][1] = new Pawn(secondPlayer, 12);

        board[3][5].stepsArr.add(new Position(5, 3));
        board[4][5].stepsArr.add(new Position(5, 4));
        board[6][5].stepsArr.add(new Position(5, 6));
        board[7][5].stepsArr.add(new Position(5, 7));
        board[4][4].stepsArr.add(new Position(4, 4));
        board[4][6].stepsArr.add(new Position(6, 4));
        board[5][7].stepsArr.add(new Position(7, 5));
        board[5][6].stepsArr.add(new Position(6, 5));
        board[5][4].stepsArr.add(new Position(4, 5));
        board[5][3].stepsArr.add(new Position(3, 5));
        board[6][6].stepsArr.add(new Position(6, 6));
        board[6][4].stepsArr.add(new Position(4, 6));
        board[5][5].stepsArr.add(new Position(5, 5));
        board[0][3].stepsArr.add(new Position(3, 0));
        board[0][4].stepsArr.add(new Position(4, 0));
        board[0][5].stepsArr.add(new Position(5, 0));
        board[0][6].stepsArr.add(new Position(6, 0));
        board[0][7].stepsArr.add(new Position(7, 0));
        board[1][5].stepsArr.add(new Position(5, 1));
        board[3][10].stepsArr.add(new Position(10, 3));
        board[4][10].stepsArr.add(new Position(10, 4));
        board[5][10].stepsArr.add(new Position(10, 5));
        board[6][10].stepsArr.add(new Position(10, 6));
        board[7][10].stepsArr.add(new Position(10, 7));
        board[5][9].stepsArr.add(new Position(9, 5));
        board[10][3].stepsArr.add(new Position(3, 10));
        board[10][4].stepsArr.add(new Position(4, 10));
        board[10][5].stepsArr.add(new Position(5, 10));
        board[10][6].stepsArr.add(new Position(6, 10));
        board[10][7].stepsArr.add(new Position(7, 10));
        board[9][5].stepsArr.add(new Position(5, 9));
        board[3][0].stepsArr.add(new Position(0, 3));
        board[4][0].stepsArr.add(new Position(0, 4));
        board[5][0].stepsArr.add(new Position(0, 5));
        board[6][0].stepsArr.add(new Position(0, 6));
        board[7][0].stepsArr.add(new Position(0, 7));
        board[5][1].stepsArr.add(new Position(1, 5));


        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (board[i][j] != null) {
                    Position p = new Position(i, j);
                    PieceList.add(getPieceAtPosition(p));
                }
            }
        }
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                for (int m = 0; m < 37; m++) {
                    if (board[j][i] != null) {
                        if (board[j][i].getOwner().isPlayerOne())
                            q4[j][i][board[j][i].tag - 1] = 1;
                        else {
                            q4[j][i][board[j][i].tag + 12] = 1;
                        }
                    }
                }
            }
        }

    }

    @Override
    public boolean move(Position a, Position b) {
        ConcretePiece currentPiece = board[a.getY()][a.getX()];
        ConcretePlayer currentPlayer;
        if (isFirstPlayerTurn) {
            currentPlayer = firstPlayer;
        } else {
            currentPlayer = secondPlayer;
        }

        // if the user try to move from empty cell
        if (currentPiece == null) {
            return false;
        }

        // if the cell is not empty
        if (getPieceAtPosition(b) != null) {
            return false;
        }
        // check if he can move his piece
        if (currentPiece.getOwner() != currentPlayer) {
            return false;
        }

        // if he tries to move to the same place return false
        if (a.getX() == b.getX() && a.getY() == b.getY()) {
            return false;
        }
        // if he tries in to move in diagonal
        if (a.getX() != b.getX() && a.getY() != b.getY()) {
            return false;
        }

        // check if pawn try to move to the corner
        if (currentPiece instanceof Pawn) { //check if the piece is  a pawn
            if (b.equals(new Position(0, 0)) ||
                    b.equals(new Position(10, 10)) ||
                    b.equals(new Position(0, 10)) ||
                    b.equals(new Position(10, 0))) {
                return false;
            }
        }

        // check if the vertical move is valid
        for (int i = a.getY() + 1; i < b.getY(); i++) {
            if (board[i][a.getX()] != null)
                return false;
        }
        for (int i = a.getY() - 1; i > b.getY(); i--) {
            if (board[i][a.getX()] != null)
                return false;
        }

        // check if the horizontal move is valid
        for (int i = a.getX() + 1; i < b.getX(); i++) {
            if (board[a.getY()][i] != null)
                return false;
        }
        for (int i = a.getX() - 1; i > b.getX(); i--) {
            if (board[a.getY()][i] != null)
                return false;
        }

        board[a.getY()][a.getX()] = null;
        board[b.getY()][b.getX()] = currentPiece;

        if (currentPiece.getOwner().isPlayerOne())
            q4[b.getY()][b.getX()][currentPiece.tag - 1]++;
        else
            q4[b.getY()][b.getX()][currentPiece.tag + 12]++;

        // update the king position
        if (currentPiece instanceof King) {
            kingPos = b;
        }

        // eat other pieces if can
        eating(b);


        isFirstPlayerTurn = !isFirstPlayerTurn;
        currentPiece.setSteps();
        getPieceAtPosition(b).stepsArr.add(b);
        currentPiece.setSquares(currentPiece.getSquares() + Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()));

        isGameFinished();
    return true;
    }

    public void eating(Position b) {
        Piece currentPiece = getPieceAtPosition(b);

        Position up = new Position(b.getX(), b.getY() - 1);
        Position two_up = new Position(b.getX(), b.getY() - 2);

        Position down = new Position(b.getX(), b.getY() + 1);
        Position two_down = new Position(b.getX(), b.getY() + 2);

        Position right = new Position(b.getX() + 1, b.getY());
        Position two_right = new Position(b.getX() + 2, b.getY());

        Position left = new Position(b.getX() - 1, b.getY());
        Position two_left = new Position(b.getX() - 2, b.getY());

        Position p1 = new Position(0, 10);
        Position p2 = new Position(10, 10);
        Position p3 = new Position(0, 0);
        Position p4 = new Position(10, 0);

        if (currentPiece instanceof Pawn) {
            if (getPieceAtPosition(up) != null && getPieceAtPosition(up) instanceof Pawn && getPieceAtPosition(up).getOwner() != currentPiece.getOwner()) {
                if (two_up.equals(p3) || two_up.equals(p4)) {
                    // ((Pawn) getPieceAtPosition(up)).setKills();
                    ((Pawn) currentPiece).setKills();
                    board[up.getY()][up.getX()] = null;
                }
                if (up.getY() == 0) {
                    //  ((Pawn) getPieceAtPosition(up)).setKills();
                    ((Pawn) currentPiece).setKills();
                    board[up.getY()][up.getX()] = null;
                } else if (getPieceAtPosition(two_up) != null && getPieceAtPosition(two_up).getOwner() == currentPiece.getOwner() && getPieceAtPosition(two_up) instanceof Pawn ) {
                    // ((Pawn) getPieceAtPosition(up)).setKills();
                    ((Pawn) currentPiece).setKills();
                    board[up.getY()][up.getX()] = null;
                }
            }

            if (getPieceAtPosition(right) != null && getPieceAtPosition(right) instanceof Pawn && getPieceAtPosition(right).getOwner() != currentPiece.getOwner()) {
                if (two_right.equals(p2) || two_right.equals(p4)) {
                    // ((Pawn) getPieceAtPosition(up)).setKills();
                    ((Pawn) currentPiece).setKills();
                    board[right.getY()][right.getX()] = null;
                }
                if (right.getX() == 10) {
                    // ((Pawn) getPieceAtPosition(up)).setKills();
                    ((Pawn) currentPiece).setKills();
                    board[right.getY()][right.getX()] = null;
                } else if (getPieceAtPosition(two_right) != null && getPieceAtPosition(two_right).getOwner() == currentPiece.getOwner() && getPieceAtPosition(two_right) instanceof Pawn)  {

                    //((Pawn) getPieceAtPosition(up)).setKills();
                    ((Pawn) currentPiece).setKills();
                    board[right.getY()][right.getX()] = null;
                }
            }

            if (getPieceAtPosition(down) != null && getPieceAtPosition(down) instanceof Pawn && getPieceAtPosition(down).getOwner() != currentPiece.getOwner()) {

                if (two_down.equals(p1) || two_down.equals(p2)) {

                    // ((Pawn) getPieceAtPosition(up)).setKills();
                    ((Pawn) currentPiece).setKills();
                    board[down.getY()][down.getX()] = null;
                }
                if (down.getY() == 10) {

                    //  ((Pawn) getPieceAtPosition(up)).setKills();
                    ((Pawn) currentPiece).setKills();
                    board[down.getY()][down.getX()] = null;
                } else if (getPieceAtPosition(two_down) != null && getPieceAtPosition(two_down).getOwner() == currentPiece.getOwner() && getPieceAtPosition(two_down) instanceof Pawn ) {
                    //((Pawn) getPieceAtPosition(up)).setKills();
                    ((Pawn) currentPiece).setKills();
                    board[down.getY()][down.getX()] = null;
                }
            }

            if (getPieceAtPosition(left) != null && getPieceAtPosition(left) instanceof Pawn && getPieceAtPosition(left).getOwner() != currentPiece.getOwner()) {


                if (two_left.equals(p3) || two_left.equals(p1)) {
                    //((Pawn) getPieceAtPosition(up)).setKills();
                    ((Pawn) currentPiece).setKills();
                    board[left.getY()][left.getX()] = null;
                }
                if (left.getX() == 0) {
                    // ((Pawn) getPieceAtPosition(up)).setKills();
                    ((Pawn) currentPiece).setKills();
                    board[left.getY()][left.getX()] = null;
                } else if (getPieceAtPosition(two_left) != null && getPieceAtPosition(two_left).getOwner() == currentPiece.getOwner() && getPieceAtPosition(two_left) instanceof Pawn ) {
                    //((Pawn) getPieceAtPosition(up)).setKills();
                    ((Pawn) currentPiece).setKills();
                    board[left.getY()][left.getX()] = null;
                }
            }
        }

    }

    public boolean cornerKing() {
        Position p1 = new Position(0, 10);
        Position p2 = new Position(10, 10);
        Position p3 = new Position(0, 0);
        Position p4 = new Position(10, 0);
        if (getPieceAtPosition(p1) instanceof King ||
                getPieceAtPosition(p2) instanceof King ||
                getPieceAtPosition(p3) instanceof King ||
                getPieceAtPosition(p4) instanceof King) {

            p1=null;
            p2=null;
            p3=null;
            p4=null;
            return true;
        }
        return false;
    }

    public boolean blueWin() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                Position p = new Position(i, j);
                if (getPieceAtPosition(p) instanceof Pawn && getPieceAtPosition(p).getOwner() == secondPlayer) {
                    return false;
                }
            }
        }
        firstPlayer.setWins(firstPlayer.getWins() + 1);
        return true;
    }

    public boolean redWin() {
        if (kingPos == null) {

            return true;
        }
        return false;
    }


    @Override
    public ConcretePiece getPieceAtPosition(Position position) {
        if (position.getY() < 0 || position.getY() >= 11 || position.getX() < 0 || position.getX() >= 11)
            return null;
        return board[position.getY()][position.getX()];
    }

    @Override
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    @Override
    public Player getSecondPlayer() {
        return secondPlayer;
    }

    @Override
    public boolean isGameFinished() {
        if (cornerKing() || blueWin()) {
            firstPlayer.setWins(firstPlayer.getWins() + 1);
            print1();
            print2();
            print3();
            print4();
            return true;
        }
        if (isKingSurrounded() || redWin()) {
            secondPlayer.setWins(secondPlayer.getWins() + 1);
            print1();
            print2();
            print3();
            print4();
            return true;
        }
        return false;
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return !isFirstPlayerTurn;
    }

    @Override
    public void reset() {
        this.board = new ConcretePiece[11][11];
        initialize();
    }


    @Override
    public void undoLastMove() {

    }

    @Override
    public int getBoardSize() {
        return 11;
    }


    private boolean isKingSurrounded() {
        Position up = new Position(kingPos.getX(), kingPos.getY() - 1);
        Position down = new Position(kingPos.getX(), kingPos.getY() + 1);
        Position right = new Position(kingPos.getX() + 1, kingPos.getY());
        Position left = new Position(kingPos.getX() - 1, kingPos.getY());

        if (kingPos.getX() == 0) { // the king is in the first column
            // check up, right, down
            if (getPieceAtPosition(up) != null && getPieceAtPosition(up).getOwner() == secondPlayer &&
                    getPieceAtPosition(down) != null && getPieceAtPosition(down).getOwner() == secondPlayer &&
                    getPieceAtPosition(right) != null && getPieceAtPosition(right).getOwner() == secondPlayer) {
                //secondPlayer.setWins(secondPlayer.getWins() + 1);
                return true;
            }
        }

        if (kingPos.getX() == 10) { // the king is in the last column
            // check up, left, down

            if (getPieceAtPosition(up) != null && getPieceAtPosition(down) != null &&
                    getPieceAtPosition(left) != null) {
                if (getPieceAtPosition(up).getOwner() == secondPlayer && getPieceAtPosition(down).getOwner() == secondPlayer
                        && getPieceAtPosition(left).getOwner() == secondPlayer) {
                    //  secondPlayer.setWins(secondPlayer.getWins() + 1);
                    return true;
                }
            }
        }

        if (kingPos.getY() == 0) {
            if (getPieceAtPosition(left) != null && getPieceAtPosition(left).getOwner() == secondPlayer &&
                    getPieceAtPosition(down) != null && getPieceAtPosition(down).getOwner() == secondPlayer &&
                    getPieceAtPosition(right) != null && getPieceAtPosition(right).getOwner() == secondPlayer) {
                //  secondPlayer.setWins(secondPlayer.getWins() + 1);
                return true;
            }
        }

        if (kingPos.getY() == 10) {
            if (getPieceAtPosition(up) != null && getPieceAtPosition(up).getOwner() == secondPlayer &&
                    getPieceAtPosition(left) != null && getPieceAtPosition(left).getOwner() == secondPlayer &&
                    getPieceAtPosition(right) != null && getPieceAtPosition(right).getOwner() == secondPlayer) {
                //secondPlayer.setWins(secondPlayer.getWins() + 1);
                return true;
            }
        }
        // check if he surrounded in all directions

        if (getPieceAtPosition(up) != null && !getPieceAtPosition(up).getOwner().isPlayerOne() &&
                getPieceAtPosition(left) != null && !getPieceAtPosition(left).getOwner().isPlayerOne() &&
                getPieceAtPosition(right) != null && !getPieceAtPosition(right).getOwner().isPlayerOne() &&
                getPieceAtPosition(down) != null && !getPieceAtPosition(down).getOwner().isPlayerOne()) {
            // secondPlayer.setWins(secondPlayer.getWins() + 1);
            return true;
        }

        return false;
    }

    public void ptintOnsection() {
        for (int i = 1; i <= 75; i++) {
            System.out.print('*');
        }
        System.out.println();
    }

    public void print1() {

        List<ConcretePiece> arr = new ArrayList<>();
        arr = PieceList;
        Collections.sort(arr, new Comperator1());

        if (cornerKing() || blueWin()) {

            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).getOwner() == firstPlayer) {
                    if (arr.get(i).getSteps() >= 1) {
                        System.out.println(arr.get(i) + arr.get(i).stepsArr.toString());

                    }
                }
            }
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).getOwner() == secondPlayer) {
                    if (arr.get(i).getSteps() >= 1) {
                        System.out.println(arr.get(i) + arr.get(i).stepsArr.toString());
                    }

                }
            }
        } else {

            for (int j = 0; j < arr.size(); j++) {
                if (arr.get(j).getOwner() == secondPlayer) {
                    if (arr.get(j).getSteps() >= 1) {
                        System.out.println(arr.get(j) + arr.get(j).stepsArr.toString());
                    }
                }
            }

            for (int j = 0; j < arr.size(); j++) {


                if (arr.get(j).getOwner() == firstPlayer) {
                    if (arr.get(j).getSteps() >= 1) {
                        System.out.println(arr.get(j) + arr.get(j).stepsArr.toString());
                    }
                }


            }
        }
        ptintOnsection();
    }

    //Comparator<ConcretePiece> killesLambda = (o1, o2) -> Integer.compare(o1.getKills(), o2.getKills());

    public void print2() {
        List<ConcretePiece> arr = new ArrayList<>();
        arr = PieceList;
        Collections.sort(arr, new Comperator2(blueWin() || cornerKing()));

        for (int j = arr.size() - 1; j > 0; j--) {
            if (arr.get(j).getKills() >= 1) {
                System.out.print(arr.get(j));
                System.out.println(arr.get(j).getKills() + " kills");
            }
        }

        ptintOnsection();

    }

    // Comparator<ConcretePiece> sqaresLambda = (o1, o2) -> Integer.compare(o1.getSquares(), o2.getSquares());

    public void print3() {
        List<ConcretePiece> arr = new ArrayList<>();
        arr = PieceList;

        Collections.sort(arr, new Comperator3(blueWin() || cornerKing()));

        for (int j = arr.size() - 1; j > 0; j--) {
            if (arr.get(j).getSquares() >= 1) {
                System.out.print(arr.get(j));
                System.out.println(arr.get(j).getSquares() + " squares");
            }
        }

        ptintOnsection();
    }

    public void print4() {
        List<Position> positions = new LinkedList<>();
        for (int i = 0; i < q4.length; i++) {
            for (int j = 0; j < q4.length; j++) {
                int counter = 0;
                for (int k = 0; k < 37; k++) {
                    if (q4[j][i][k] != 0)
                        counter++;
                }
                if (counter > 1) {
                    positions.add(new Position(i, j));
                }
            }
        }

        positions.sort((o1, o2) -> {
            int counter1 = 0, counter2 = 0;
            for (int i = 0; i < 37; i++) {
                if (q4[o1.getY()][o1.getX()][i] != 0) {
                    counter1++;
                }
                if (q4[o2.getY()][o2.getX()][i] != 0) {
                    counter2++;
                }
            }

            if (counter1 != counter2)
                return counter2 - counter1;

            if (o1.getX() != o2.getX())
                return o1.getX() - o2.getX();
            return o1.getY() - o2.getY();
        });

        for (Position pos : positions) {
            int counter = 0;
            for (int i = 0; i < 37; i++) {
                if (q4[pos.getY()][pos.getX()][i] != 0) {
                    counter++;
                }
            }
            System.out.println("" + pos + counter + " pieces");
        }
        ptintOnsection();
    }
}

