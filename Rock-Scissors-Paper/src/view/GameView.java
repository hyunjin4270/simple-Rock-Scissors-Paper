package view;

import entity.Move;
import entity.Player;

import java.util.List;
import java.util.Map;

public class GameView {

    /** 짧은 인터랙션 딜레이: 400ms */
    private static void shortDelay() {
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /** 일반 딜레이: 700ms */
    private static void normalDelay() {
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /** 컴퓨터 사고 딜레이: 1000ms */
    private static void thinkingDelay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /** 긴장감 딜레이: 1800ms */
    private static void suspenseDelay() {
        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void showWelcome() {
        System.out.println();
        System.out.println("=== 가위-바위-보 게임을 시작합니다 ===");
        normalDelay();
    }


    public static void showRuleList(List<String> list) {
        System.out.println();
        System.out.println("플레이할 룰을 선택하세요:");
        for (String ruleName : list) {
            System.out.println("  - " + ruleName);
        }
        normalDelay();
    }


    public static void showAppliedConfig(String ruleName, List<Player> playerList) {
        System.out.println();
        System.out.println("=== 게임 설정 ===");
        System.out.println("적용된 룰  : " + ruleName);

        System.out.print("플레이어   : ");
        for (int i = 0; i < playerList.size(); i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(playerList.get(i).getName());
        }
        System.out.println();
        System.out.println("================");
        normalDelay();
    }


    public static void showAttacker(Player player) {
        System.out.println();
        System.out.printf("[ATTACKER] 현재 공격자: %s님\n", player.getName());
        shortDelay();
    }


    public static void showRoundCount(int roundNumber) {
        System.out.println();
        System.out.printf("=== Round %d ===\n", roundNumber);
        suspenseDelay();
    }


    public static void showRoll(Map<Player, Integer> diceRolls) {
        System.out.println();
        System.out.println("=== 주사위를 굴립니다! ===");
        normalDelay();

        for (Map.Entry<Player, Integer> e : diceRolls.entrySet()) {
            System.out.printf("  %s - %d\n", e.getKey().getName(), e.getValue());
        }
        suspenseDelay();
    }


    public static void showTieBreaker(List<Player> tiedPlayers) {
        System.out.println();
        System.out.print("[TIE] 동점 플레이어: ");
        for (int i = 0; i < tiedPlayers.size(); i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(tiedPlayers.get(i).getName());
        }
        System.out.println();
        System.out.println("다시 주사위를 굴려 승자를 결정합니다...");
        suspenseDelay();
    }


    public static void showPlayerTurn(String playerName) {
        System.out.println();
        System.out.printf("[TURN] %s님의 차례입니다.\n", playerName);
        shortDelay();
    }


    public static void showAvailableMoves(List<String> moves) {
        System.out.print("[INFO] 사용 가능한 무브: ");
        for (int i = 0; i < moves.size(); i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(moves.get(i));
        }
        System.out.println();
        shortDelay();
    }


    public static void showMoveAccepted(String playerName, String moveName) {
        System.out.println("[OK] " + playerName + " 님의 선택: " + moveName);
        shortDelay();
    }


    public static void promptComputerMove(String playerName) {
        System.out.printf("[COMPUTER] %s님이 무브를 선택합니다...\n", playerName);
        thinkingDelay();
    }


    public static void showBehavior(Map<Player, Move> moves, int round) {
        System.out.println();
        System.out.printf("=== %d 라운드 결과 ===\n", round);
        suspenseDelay();

        for (Map.Entry<Player, Move> entry : moves.entrySet()) {
            System.out.printf("  %s - %s\n",
                    entry.getKey().getName(),
                    entry.getValue().name()
            );
        }
        normalDelay();
    }


    public static void showEndMessage() {
        System.out.println();
        System.out.println("[END] 게임 종료!");
        suspenseDelay();
    }


    public static void showWinner(List<Player> winners) {
        System.out.println();
        System.out.print("[RESULT] 승자: ");
        for (int i = 0; i < winners.size(); i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(winners.get(i).getName());
        }
        System.out.println();
        suspenseDelay();
    }


    public static void showStatus(Map<Player, String> status) {
        System.out.println();
        System.out.println("=== 최종 상태 ===");
        for (Map.Entry<Player, String> e : status.entrySet()) {
            System.out.printf("  %s : %s\n", e.getKey().getName(), e.getValue());
        }
        normalDelay();
    }


    public static void showDrawMessage() {
        System.out.println();
        System.out.println("[RESULT] 무승부!");
        suspenseDelay();
    }
}
