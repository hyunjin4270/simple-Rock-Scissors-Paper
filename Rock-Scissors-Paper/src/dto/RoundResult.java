package dto;

import entity.Move;
import entity.Player;

import java.util.Map;

/**
 * 한 라운드의 결과를 전달하기 위한 DTO.
 * 순수 데이터만 담고, 변경 메서드는 없음.
 */
public class RoundResult {
    private final Map<Player, Move> moves;  // Player.getName() 혹은 ID로 치환
    private final Player attacker;            // player 이름 또는 ID
    private final int roundNumber;

    private RoundResult(Map<Player, Move> moves,
                        Player attacker,
                        int roundNumber) {
        // 방어적 복사 + 불변화
        this.moves = moves;
        this.attacker = attacker;
        this.roundNumber = roundNumber;
    }

    /** 팩토리 메서드로만 생성을 유도 */
    public static RoundResult of(Map<Player, Move> moves,
                                    Player attacker,
                                    int roundNumber) {
        return new RoundResult(moves, attacker, roundNumber);
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
