package dto;

import entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * 게임의 결과를 담기 위한 객체입니다.
 * 팩토리 메서드만을 제공함으로써 클래스의 단순화를 유도했습니다.
 */
public class GameResult {
    private final boolean draw;
    private final List<Player> winners;
    private final Map<Player, String> status;

    /**
     * 클래스의 단순화를 유도하기 위해서 생성자를 통한 객체 생성을 막았습니다.
     * @param draw 무승부 여부
     * @param winners 우승자 리스트
     * @param status 플레이어 상태
     */
    private GameResult(boolean draw, List<Player> winners, Map<Player, String> status) {
        this.draw = draw;
        this.winners = winners;
        this.status = status;
    }

    /**
     * 해당 게임이 무승부 상황으로 판명났을 때 사용하는 메서드입니다.
     * 승자가 없기 때문에 승자를 비웁니다.
     * @param status 플레이어 상태
     * @return 무승부 상태의 GameResult 인스턴스
     */
    public static GameResult draw(Map<Player, String> status) {
        return new GameResult(true, Collections.emptyList(), status);
    }

    /**
     * 해당 게임에 승패가 판명났을 때 사용하는 메서드입니다.
     * @param winners 우승자 리스트
     * @param status 플레이어 상태
     * @return 무승부 상태가 아닌 GameResult 인스턴스
     */
    public static GameResult winners(List<Player> winners, Map<Player, String> status) {
        return new GameResult(false, winners, status);
    }

    /**
     * 게임 결과가 무승부인지 확인하는 메서드입니다.
     * @return 무승부 여부
     */
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

