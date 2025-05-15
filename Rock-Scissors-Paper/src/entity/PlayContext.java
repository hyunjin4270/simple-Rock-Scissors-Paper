package entity;

import java.util.Map;
import java.util.Objects;

public class PlayContext {
    private final Map<Player, Move> moves;
    private final Player attacker;
    private final int roundNumber;

    public PlayContext(Map<Player, Move> moves, Player attacker, int roundNumber) {
        this.moves = Objects.requireNonNull(moves, "moves must not be null");
        this.attacker = attacker;
        this.roundNumber = roundNumber;
    }


    public PlayContext(Map<Player, Move> moves) {
        this(moves, null, 0);
    }

    public Map<Player, Move> getMoves() {
        return moves;
    }

    public Player getAttacker() {
        return attacker;
    }

    public int getRoundNumber() {
        return roundNumber;
    }
}
