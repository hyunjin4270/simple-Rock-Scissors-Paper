package controller;

import entity.Computer;
import entity.Move;
import entity.PlayContext;
import entity.Player;
import rule.Vanilla;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static view.GameView.*;

public class VanillaEngine extends GameFlow {
    private final Vanilla rule = new Vanilla();

    public VanillaEngine(List<Player> players, Scanner scanner) {
        super(players, scanner);
    }

    @Override
    public String getName() {
        return rule.getName();
    }

    @Override
    protected RoundResult doRound() {
        Map<Player, Move> behavior = new HashMap<>();
        for (Player player : players) {
            if (player instanceof Computer) {
                behavior.put(player, playComputerTurn());
                promptComputerMove(player.getName());
                continue;
            }
            behavior.put(player, playPlayerTurn(getScanner(), player));
        }

        Map<Player, String> status = makeStatus(behavior);
        return rule.play(new PlayContext(behavior))
                .map(winners -> RoundResult.winners(winners, status))
                .orElseGet(() -> RoundResult.draw(status));
    }

    private Map<Player, String> makeStatus(Map<Player, Move> behavior) {
        Map<Player, String> status = new HashMap<>();
        for (Map.Entry<Player, Move> entry : behavior.entrySet()) {
            status.put(entry.getKey(), Move.asString(entry.getValue()));
        }
        return status;
    }


}
