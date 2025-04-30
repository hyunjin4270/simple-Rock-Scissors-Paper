package view;

import entity.Player;

import java.util.List;

public class GameView {
    public static void showWelcome() {
        System.out.println("=== 가위-바위-보 게임을 시작합니다 ===");
    }

    public static void showRuleList(List<String> list) {
        System.out.println("플레이할 룰을 선택하세요: ");
        for (String ruleName : list) {
            System.out.println("- " + ruleName);
        }
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
        System.out.println();
    }

    public static void showPlayerTurn(String playerName) {
        System.out.printf("[TURN] %s님의 차례입니다.%n", playerName);
    }


    public static void showAvailableMoves(List<String> moves) {
        System.out.print("[INFO] 사용 가능한 무브: ");
        for (int i = 0; i < moves.size(); i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(moves.get(i));
        }
        System.out.println();
    }

    public static void showMoveAccepted(String playerName, String moveName) {
        System.out.println("[OK] " + playerName + " 님의 선택: " + moveName);
    }

    public static void promptComputerMove(String playerName) {
        System.out.printf("[COMPUTER] %s님이 무브를 선택했습니다.", playerName);
        System.out.println();
    }

    public static void showEndMessage() {
        System.out.println("[END] 게임 종료! 결과를 발표하겠습니다.");
    }

    public static void showWinner(List<Player> winner) {
        System.out.println("[RESULT] 승패가 갈렸습니다!");
        System.out.println("[RESULT] 승자:");
        for (int i = 0; i < winner.size(); i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(winner.get(i).getName());
        }
        System.out.println();
    }

    public static void showDrawMessage() {
        System.out.println("[RESULT] 무승부!");
    }

    /**
     * 게임의 생동감을 주기 위한 딜레이입니다.
     */
    private void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
