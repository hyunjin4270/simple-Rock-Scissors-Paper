package view;

import entity.Player;

import java.util.List;

public class HomeView {
    public static void printAddSuccessMessage(String name, String type) {
        System.out.printf("[ADD] 플레이어가 추가되었습니다: %s (%s)", name, type);
        System.out.println();
    }

    public static void printAddInstructions() {
        System.out.println("[ADD] 플레이어 타입과 이름을 적으십시오.");
        System.out.println("사용가능한 플레이어 타입: Computer, User");
        System.out.println("예시: computer 챗지피티");
        System.out.println("나가기: exit");
    }
    public static void printDeleteSuccessMessage(Player target) {
        System.out.println("[DELETE] 플레이어가 삭제되었습니다: " + target.getName());
        System.out.println();
    }

    public static void printDeleteInstructions(List<Player> players) {
        System.out.println("[DELETE] 플레이어 이름을 적으십시오.");
        for (Player player : players) {
            System.out.print(" - ");
            System.out.println(player.getName());
        }
        System.out.println("나가기: exit");
    }
}
