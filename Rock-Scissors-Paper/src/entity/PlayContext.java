package entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayContext {
    private final Map<Player, Move> moves = new HashMap<>();
    private final Player attacker;
    private final int roundNumber;

    public PlayContext(Player attacker, int roundNumber) {
        this.attacker = attacker;
        this.roundNumber = roundNumber;
    }


    public PlayContext() {
        this(null, 0);
    }

    public Map<Player, Move> getMoves() {
        return moves;
    }

    public void put(Player player, Move move) {
        moves.put(player, move);
    }

    public Player getAttacker() {
        return attacker;
    }

    public int getRoundNumber() {
        return roundNumber;
    }
}
