package rule;

import entity.Move;
import entity.Outcome;
import dto.RoundResult;
import entity.Player;

import java.util.*;

/**
 * 묵찌빠의 게임로직을 담은 클래스입니다
 */
public class Mukchippa extends RockScissorPaper {

    @Override
    public String getName() {
        return "Mukchippa";
    }


    /**
     * 공격자와 같지 않은 행동을 취한 플레이어만 반환합니다.
     * @param context 게임 정보
     * @return 행동을 다르게 취한 수비자들
     */
    @Override
    public Optional<List<Player>> play(RoundResult context) {
        Player attacker = context.getAttacker();
        if (attacker == null) throw new IllegalArgumentException("공격자가 반드시 필요합니다.");

        Map<Player, Move> moves = context.getMoves();
        Move attMove = moves.get(attacker);

        List<Player> defenders = new LinkedList<>();
        for (Map.Entry<Player, Move> entry : moves.entrySet()) {
            Player player = entry.getKey();
            if (player.equals(attacker)) continue;
            if (entry.getValue() != attMove) defenders.add(player);
        }

        return Optional.of(defenders);
    }

    /**
     * 공격자의 행동을 이긴 수비자 목록을 반환한다.
     *
     * @param roundResult 해당 라운드 정보
     * @return 공격자를 이긴 수비자 리스트
     */
    public List<Player> findDefendersWhoBeatAttacker(RoundResult roundResult) {
        Map<Player, Move> moves = roundResult.getMoves();
        Move attMove = moves.get(roundResult.getAttacker());

        List<Player> attackerCandidate = new ArrayList<>();
        for (Player p : moves.keySet()) {
            if (p.equals(roundResult.getAttacker())) continue;
            Move defMove = moves.get(p);
            if (decide(attMove, defMove) == Outcome.LOSE) {
                attackerCandidate.add(p);
            }
        }
        return attackerCandidate;
    }

    /**
     * 플레이어들의 주사위 눈금 중 가장 큰 눈금을 가진 플레이어를 반환합니다.
     * 만약 가장 큰 눈금을 가진 플레이어가 다수일 시, 그 모두를 반환합니다.
     * @param diceRolls 플레이어의 주사위 눈금
     * @return 가장 큰 눈금을 가진 플레이어
     */
    public List<Player> defineAttacker(Map<Player, Integer> diceRolls) {
        if (diceRolls == null || diceRolls.isEmpty()) throw new IllegalArgumentException("플레이어 포인트가 없습니다.");

        Integer maximumPoint = Collections.max(diceRolls.values());
        List<Player> attacker = new ArrayList<>();
        for (Player player : diceRolls.keySet()) {
            if (diceRolls.get(player).equals(maximumPoint)) attacker.add(player);
        }

        return attacker;
    }
}
