package rule;

import entity.Move;
import entity.PlayContext;
import entity.Player;

import java.util.*;

public class Mukchippa extends RockScissorPaper {
    private static final Random rand = new Random();

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
    public Optional<List<Player>> play(PlayContext context) {
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
     * 만약 공격자를 이기는 행동을 취한 수비자가 다수일 때 사용하는 포인트입니다.
     * @return 1부터 20까지 랜덤한 숫자
     */
    public int getAttackerPoint() {
        return rand.nextInt(20) + 1;
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
