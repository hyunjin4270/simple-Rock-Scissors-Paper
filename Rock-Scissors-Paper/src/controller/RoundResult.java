package controller;

import entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RoundResult {
    private final boolean draw;
    private final List<Player> winners;
    private final Map<Player, String> status;

    private RoundResult(boolean draw, List<Player> winners, Map<Player, String> status) {
        this.draw = draw;
        this.winners = winners;
        this.status = status;
    }

    public static RoundResult draw(Map<Player, String> status) {
        return new RoundResult(true, Collections.emptyList(), status);
    }

    public static RoundResult winners(List<Player> winners, Map<Player, String> status) {
        return new RoundResult(false, winners, status);
    }

    public boolean isDraw() {
        return draw;
    }

    public List<Player> getWinners() {
        return winners;
    }

    public Map<Player, String> getStatus() {
        return status;
    }
}

