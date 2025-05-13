package rule;

import entity.Move;
import entity.Outcome;
import entity.Player;

import java.util.*;

import static entity.Move.*;
import static entity.Outcome.*;

public abstract class RockScissorPaper {
    public abstract String getName();

    /**
     * 플레이어 리스트를 받고, 승자를 반환합니다. 만약 무승부 상황이 나면 아무도 반환하지 않습니다.
     * @param playerList 플레이어 리스트
     * @return 승자 or 무승부 상황(null)
     */
    public abstract Optional<List<Player>> decideWinner(List<Player> playerList);



    /**
     * 2개의 무브를 비교해서 비교군 무브를 기준으로 승패를 가립니다.
     * @param ctrlMove 비교군 무브
     * @param expMove 대조군 무브
     * @return 비교군 무브의 승패유무
     */
    protected Outcome decide(Move ctrlMove, Move expMove) {
        if (
                (ctrlMove == ROCK   && expMove == SCISSOR) ||
                (ctrlMove == SCISSOR&& expMove == PAPER)   ||
                (ctrlMove == PAPER  && expMove == ROCK)
        ) {
            return WIN;
        } else if (
                (ctrlMove == ROCK   && expMove == PAPER) ||
                (ctrlMove == SCISSOR&& expMove == ROCK)   ||
                (ctrlMove == PAPER  && expMove == SCISSOR)
        ){
            return LOSE;
        } else {
            return DRAW;
        }
    }

    /**
     * 플레이어의 무브를 보고 무승부 상황인지 판별합니다.
     * @param playerList 플레이어 리스트
     * @return 무승부 상황 판별 값
     */
    protected boolean isDraw(List<Player> playerList) {
        Set<Move> moves = new HashSet<>();
        for (Player player : playerList) {
            moves.add(player.getMove());
        }
        if (moves.size() != 2) return true;
        return false;
    }
}
