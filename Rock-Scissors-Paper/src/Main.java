import controller.GameController;
import entity.Computer;
import entity.Player;
import entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static view.ConsoleViewHelper.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Player> players = new ArrayList<>(List.of(
            new User("User1"),
            new Computer("Computer1")
    ));
    private static final GameController gameController = new GameController();
    public static void main(String[] args) {
        while (true) {
            home();
            String input = playerInput();
            switch (input) {
                case "1" -> gameController.gameStart(scanner, players);
                case "2" -> printPlayerList();
                case "3" -> addPlayer();
                case "4" -> deletePlayer();
                case "5" -> System.exit(0);
                default -> showError("잘못된 입력입니다.");
            }
        }
    }

    private static void addPlayer() {
        System.out.println("[ADD] 플레이어 타입과 이름을 적으십시오.");
        System.out.println("사용가능한 플레이어 타입: Computer, User");
        System.out.println("예시: computer 챗지피티");
        System.out.println("나가기: exit");
        while (true) {
            String input = playerInput().trim();
            if (input.equals("exit")) {
                return;
            }

            String[] parts = input.split(" ", 2);
            if (parts.length < 2) {
                showError("형식이 올바르지 않습니다. (예: computer 챗지피티)");
                continue;
            }
            String type = parts[0];
            String name = parts[1];
            if (name.isBlank()) {
                showError("플레이어 이름은 빈 문자열일 수 없습니다.");
                continue;
            }
            Player newPlayer;
            switch (type) {
                case "computer" -> newPlayer = new Computer(name);
                case "user" -> newPlayer = new User(name);
                default -> {
                    showError("알 수 없는 플레이어 타입입니다: " + parts[0]);
                    continue;
                }
            }
            players.add(newPlayer);
            System.out.printf("[ADD] 플레이어가 추가되었습니다: %s (%s)", name, type);
            System.out.println();
            return;
        }
    }


    private static void deletePlayer() {
        System.out.println("[DELETE] 플레이어 이름을 적으십시오.");
        printPlayerList();
        System.out.println("나가기: exit");
        while (true) {
            String input = playerInput();
            if (input.equals("exit")) {
                return;
            }
            if (input.isBlank()) {
                showError("입력 값은 빈 문자열일 수 없습니다.");
                continue;
            }

            Player target = null;
            for (Player player : players) {
                if (!player.getName().equals(input)) continue;
                target = player;
                break;
            }

            if (target == null) {
                showError("해당 플레이어를 찾을 수 없습니다: " + input);
                continue;
            }

            // 삭제
            players.remove(target);
            System.out.println("[DELETE] 플레이어가 삭제되었습니다: " + target.getName());
            return;
        }
    }


    private static void printPlayerList() {
        for (Player player : players) {
            System.out.print(" - ");
            System.out.println(player.getName());
        }
    }

    /**
     * 플레이어의 입출력을 묶은 편의성 메서드입니다.
     * @return 플레이어의 입력
     */
    private static String playerInput() {
        userPrompt("host");
        return scanner.nextLine().trim().toLowerCase();
    }
}