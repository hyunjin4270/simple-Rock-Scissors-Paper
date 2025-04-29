package entity;

public class Player {
    private final String name;
    private final Boolean isComputer;
    private Move move;

    public Player(String name, Boolean isComputer) {
        this.name = name;
        this.isComputer = isComputer;
    }

    public String getName() {
        return name;
    }

    public Boolean getComputer() {
        return isComputer;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }
}
