package controller;

import entity.Computer;
import entity.PlayContext;
import entity.Player;
import rule.Mukchippa;

import java.util.*;

import static view.GameView.*;

/**
 * 묵찌빠 게임 룰 구현
 */
public class MukchippaEngine extends GameFlow {
    private final Mukchippa rule = new Mukchippa();

    public MukchippaEngine(List<Player> players, Scanner scanner) {
        super(players, scanner);
    }

    @Override
    public String getName() {
        return "mukchippa";
    }

    @Override
    protected RoundResult doRound() {
        List<Player> survivors = new ArrayList<>(getPlayers());
        Map<Player, String> status = new LinkedHashMap<>();
        int round = 1;
        Player attacker = selectAttacker(survivors);

        while (true) {
            showAttacker(attacker);
            PlayContext context = runGame(new PlayContext(attacker, round), survivors);
            showBehavior(context.getMoves(), context.getRoundNumber());
            List<Player> defenders = rule.play(context).orElse(List.of());

            List<Player> nextSurvivors = new ArrayList<>();
            nextSurvivors.add(attacker);
            nextSurvivors.addAll(defenders);
            processEliminations(survivors, nextSurvivors, status, round);

            survivors = nextSurvivors;
            if (survivors.size() == 1) {
                return RoundResult.winners(survivors, status);
            }
            attacker = determineNextAttacker(context, attacker);
            round++;
        }
    }

    private Player determineNextAttacker(PlayContext context, Player attacker) {
        List<Player> beaters = rule.findDefendersWhoBeatAttacker(context);
        if (beaters.isEmpty()) {
        } else if (beaters.size() == 1) {
            attacker = beaters.getFirst();
        } else {
            attacker = selectAttacker(beaters);
        }
        return attacker;
    }

    private static void processEliminations(List<Player> survivors, List<Player> nextSurvivors, Map<Player, String> status, int round) {
        List<Player> eliminated = new ArrayList<>();
        for (Player player : survivors) {
            if (!nextSurvivors.contains(player)) {
                eliminated.add(player);
                status.put(player, round + "라운드 탈락");
            }
        }
        if (!eliminated.isEmpty()) {
            System.out.print("[ELIMINATED] " + round + "라운드 탈락: ");
            for (int i = 0; i < eliminated.size(); i++) {
                if (i > 0) System.out.print(", ");
                System.out.print(eliminated.get(i).getName());
            }
            System.out.println();
        }
    }

    private PlayContext runGame(PlayContext context,List<Player> survivors) {
        showRoundCount(context.getRoundNumber());
        for (Player player : survivors) {
            showPlayerTurn(player.getName());
            if (player instanceof Computer) {
                context.put(player, playComputerTurn());
                promptComputerMove(player.getName());
                continue;
            }
            context.put(player, playPlayerTurn(getScanner(), player));
        }
        return context;
    }


    /**
     * 주사위를 굴려 가장 높은 눈금을 뽑고, 동점자 있으면 재추첨합니다.
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
