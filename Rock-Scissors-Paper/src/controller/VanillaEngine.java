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

    /**
     * 가위바위보의 기본적인 룰을 진행합니다.
     * @return
     */
    @Override
    protected GameResult doRound() {
        //0) 사전준비
        Map<Player, Move> moves = new HashMap<>();

        //1) 플레이어들의 행동을 결정
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
        // 1-1) 라운드 결과 생성 및 각 행동의 상태 저장
        RoundResult result = RoundResult.of(moves, null, 1);
        Map<Player, String> status = makeStatus(result.getMoves());

        // 2) 무승부 상황일 시 무승부 결과, 아닐시 아닌 결과 생성
        return rule.play(result)
                .map(winners -> GameResult.winners(winners, status))
                .orElseGet(() -> GameResult.draw(status));
    }

    /**
     * 상태를 생성합니다.
     * @param behavior 플레이어들의 행동
     * @return 플레이어들의 상태
     */
    private Map<Player, String> makeStatus(Map<Player, Move> behavior) {
        Map<Player, String> status = new HashMap<>();
        for (Map.Entry<Player, Move> entry : behavior.entrySet()) {
            status.put(entry.getKey(), entry.getValue().name());
        }
        return status;
    }
}
