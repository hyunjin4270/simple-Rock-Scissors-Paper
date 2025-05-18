package entity;

public class Computer implements Player {
    private final String name;
    private Move move;

    public Computer(String name) {
        this.name = name;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public Move getMove() {
        return move;
    }

    @Override
    public void setMove(Move move) {
        this.move = move;
    }
}
