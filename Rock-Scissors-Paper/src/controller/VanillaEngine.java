package controller;

import dto.GameResult;
import dto.RoundResult;
import entity.*;
import rule.Vanilla;

import java.util.*;

import static view.GameView.*;

/**
 * 기본적인 가위바위보 게임을 진행하는 클래스입니다.
 */
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
    protected GameResult doRound() {
        Map<Player, Move> moves = new HashMap<>();
        for (Player player : getPlayers()) {
            showPlayerTurn(player.getName());
            Move move;
            if (player instanceof Computer) {
                move = playComputerTurn();
                promptComputerMove(player.getName());
            }
            else if (player instanceof User) {
                move = playUserTurn(getScanner(), player);
                showMoveAccepted(player.getName(), move.name());
            }
            else {
                throw new NoSuchElementException("찾을 수 없는 플레이어 타입입니다.");
            }
            moves.put(player, move);
        }

        RoundResult result = RoundResult.of(moves, null, 1);
        Map<Player, String> status = makeStatus(result.getMoves());
        return rule.play(result)
                .map(winners -> GameResult.winners(winners, status))
                .orElseGet(() -> GameResult.draw(status));
    }

    private Map<Player, String> makeStatus(Map<Player, Move> behavior) {
        Map<Player, String> status = new HashMap<>();
        for (Map.Entry<Player, Move> entry : behavior.entrySet()) {
            status.put(entry.getKey(), entry.getValue().name());
        }
        return status;
    }


}
