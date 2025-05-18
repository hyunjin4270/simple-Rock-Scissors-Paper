package dto;

import entity.Move;
import entity.Player;

import java.util.Map;

/**
 * 한 라운드의 결과를 담기 위한 객체입니다.
 * 팩토리 메서드만을 제공함으로써 클래스의 단순화를 유도했습니다.
 */
public class RoundResult {
    private final Map<Player, Move> moves;
    private final Player attacker;
    private final int roundNumber;

    /** 팩토리 메서드로만 생성을 유도합니다 */
    private RoundResult(Map<Player, Move> moves,
                        Player attacker,
                        int roundNumber) {
        this.moves = moves;
        this.attacker = attacker;
        this.roundNumber = roundNumber;
    }


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
