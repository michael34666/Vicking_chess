public class ConcretePlayer implements Player {
     private boolean isPlayerOne;
     private int wins;

        /**
         *
         * @return true if the player is player 1, false otherwise.
         */;
    public ConcretePlayer(boolean isPlayerOne)
    {
        this.isPlayerOne=isPlayerOne;
        this.wins=0;

    }
    @Override
    public boolean isPlayerOne() {
        return isPlayerOne;
    }

    @Override
    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins=wins;
    }
}
