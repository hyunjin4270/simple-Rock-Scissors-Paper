package flow;

import dto.GameResult;
import dto.RoundResult;
import entity.*;
import rule.Mukchippa;

import java.util.*;

import static view.GameView.*;

/**
 * 묵찌빠 게임 룰 구현
 */
public class MukchippaEngine extends GameFlow {
    private final Mukchippa rule = new Mukchippa();

    private int round;
    private final Map<Player, String> status = new LinkedHashMap<>();

    public MukchippaEngine(List<Player> players, Scanner scanner) {
        super(players, scanner);
    }

    @Override
    public String getName() {
        return "mukchippa";
    }

    /**
     * 한 판의 전체 라운드를 진행합니다.
     * @return 게임결과값
     */
    @Override
    protected GameResult doRound() {
        //0) 게임을 진행하기 위한 사전준비
        List<Player> survivors = new ArrayList<>(getPlayers());
        round = 1;

        //1) 생존자 중 공격자를 선정
        Player attacker = selectAttacker(survivors);

        while (true) {
            showAttacker(attacker);

            //2) 플레이어의 행동을 결정
            RoundResult result = runGame(attacker, survivors);
            showBehavior(result.getMoves(), result.getRoundNumber());

            //3) 행동을 기반으로 탈락자 선정
            List<Player> defenders = rule.play(result).orElse(List.of());
            List<Player> nextSurvivors = new ArrayList<>();
            nextSurvivors.add(attacker);
            nextSurvivors.addAll(defenders);
            processEliminations(survivors, nextSurvivors);
            survivors = nextSurvivors;

            //4) 한 명만 남으면 결과 반환, 아니면 라운드 반복
            if (survivors.size() == 1) {
                return GameResult.winners(survivors, status);
            }

            //5) 다음 라운드 공격자 선정
            attacker = determineNextAttacker(result, attacker);
            round++;
        }
    }

    /**
     * 공격자를 이긴 수비자 중에서 다음 라운드 공격자를 결정한다.
     * 1) 이긴 수비자가 없을때   - 현재 라운드의 공격자가 다음 라운드의 공격자가 됨
     * 2) 이긴 수비자가 1명일때  - 해당 수비자가 다음 라운드의 공격자가 됨
     * 3) 이긴 수비자가 다수일때  - 주사위를 통해 다음 라운드의 공격자를 선정
     * @param result 직전 라운드 정보
     * @param attacker 이전 라운드 공격자
     * @return 다음 라운드 공격자
     */
    private Player determineNextAttacker(RoundResult result, Player attacker) {
        List<Player> beaters = rule.findDefendersWhoBeatAttacker(result);
        if (beaters.isEmpty()) {
        } else if (beaters.size() == 1) {
            attacker = beaters.getFirst();
        } else {
            attacker = selectAttacker(beaters);
        }
        return attacker;
    }

    /**
     * 직전 라운드의 탈락자들을 처리하는 메서드입니다.
     * 탈락자들의 상태를 기록하고, 출력합니다.
     * @param survivors 이전 라운드 생존자
     * @param nextSurvivors 직전 라운드 생존자
     */
    private void processEliminations(List<Player> survivors, List<Player> nextSurvivors) {
        List<Player> eliminated = new ArrayList<>();
        for (Player player : survivors) {
            if (!nextSurvivors.contains(player)) {
                eliminated.add(player);
                status.put(player, round + "라운드 탈락");
            }
        }
        if (!eliminated.isEmpty()) showEliminated(round, eliminated);
    }

    /**
     * 플레이어들의 행통을 입력받는 메서드입니다.
     * 기본 골자는 Vanilla와 같습니다.
     * @param attacker 직전 라운드 공격자
     * @param survivors 직전 라운드 생존자
     * @return 행동을 기록한 해당 라운드 정보
     */
    private RoundResult runGame(Player attacker, List<Player> survivors) {
        showRoundCount(round);
        Map<Player, Move> moves = new HashMap<>();
        for (Player player : survivors) {
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
                throw new NoSuchElementException("찾을 수 없는 플레이어 타입 입니다.");
            }
            moves.put(player, move);
        }
        return RoundResult.of(moves, attacker, round);
    }

    /**
     * 주사위를 굴려 가장 높은 수를 가진 플레이어를 선정합니다.
     * 만약 가장 높은 눈금을 가진 플레이어가 다수면, 해당하는 플레이어들끼리 다시 주사위를 굴립니다.
     */
    private Player selectAttacker(List<Player> candidates) {
        while (true) {
            Map<Player, Integer> dice = new LinkedHashMap<>();
            for (Player player : candidates) {
                dice.put(player, rollDice());
            }
            showRoll(dice);

            List<Player> top = rule.defineAttacker(dice);
            if (top.size() == 1) {
                return top.getFirst();
            }
            showTieBreaker(top);
            candidates = top;
        }
    }

    /** 1~20 범위 랜덤 주사위 */
    private int rollDice() {
        return new Random().nextInt(20) + 1;
    }
}
