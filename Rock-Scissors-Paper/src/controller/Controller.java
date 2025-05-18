package controller;

import entity.Computer;
import entity.Player;
import entity.User;

import java.util.*;

import static view.ConsoleViewHelper.*;
import static view.GameView.*;

public class Controller {
    private final List<String> rules = List.of(
            "vanilla",
            "mukchippa"
    );
    private final Scanner scanner;
    private GameFlow game;
    private final List<Player> players = new ArrayList<>(List.of(
            new User("User1"),
            new Computer("Computer1")
    ));

    public Controller(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * 게임세션을 생성합니다.
     */
    public void gameStart() {
        GameFlow game = selectRule();
        game.run();
    }




    /**
     * 게임에 진입하기 전 적용할 규칙을 설정합니다.
     * @return 규칙
     */
    private GameFlow selectRule() {
        showRuleList(rules);
        while (true) {
            String input = playerInput("host");
            try {
                if (input.isBlank()) throw new IllegalArgumentException("입력값이 빌 수 없습니다.");
                if (!rules.contains(input)) throw new NoSuchElementException("해당하는 룰이 없습니다: " + input);
                return switch (input) {
                    case "vanilla"      -> new VanillaEngine(players, scanner);
                    case "mukchippa"    -> new MukchippaEngine(players, scanner);
                    default -> throw new NoSuchElementException("알 수 없는 상황이 발생했습니다.");
                };
            } catch (RuntimeException e) {
                showError(e.getMessage());
            }
        }
    }


    /**
     * 플레이어를 추가합니다. 컴퓨터와 일반 유저 중 고를 수 있습니다.
     * exit 명령어를 통해 플레이어를 추가하지 않고 나갈 수 있습니다
     */
    public void addPlayer() {
        System.out.println("[ADD] 플레이어 타입과 이름을 적으십시오.");
        System.out.println("사용가능한 플레이어 타입: Computer, User");
        System.out.println("예시: computer 챗지피티");
        System.out.println("나가기: exit");
        while (true) {
            try {
                String input = playerInput("host");
                if (input.equals("exit")) return;

                String[] parts = input.split(" ", 2);
                if (parts.length < 2) throw new IllegalStateException("형식이 올바르지 않습니다. (예: computer 챗지피티)");

                String type = parts[0];
                String name = parts[1];
                if (name.isBlank()) throw new IllegalArgumentException("플레이어 이름은 빈 문자열일 수 없습니다.");
                for (Player player : players) {
                    if (name.equals(player.getName())) throw new IllegalArgumentException("중복되는 플레이어 이름이 있습니다.");
                }
                if (name.length() > 9) throw new IllegalArgumentException("플레이어 이름은 10글자 이상이 될 수 없습니다.");

                Player newPlayer;
                switch (type) {
                    case "computer" -> newPlayer = new Computer(name);
                    case "user" -> newPlayer = new User(name);
                    default -> throw new IllegalStateException("알 수 없는 플레이어 타입입니다: " + parts[0]);

                }
                players.add(newPlayer);
                System.out.printf("[ADD] 플레이어가 추가되었습니다: %s (%s)", name, type);
                System.out.println();
                return;
            } catch (IllegalStateException | IllegalArgumentException e) {
                showError(e.getMessage());
            }
        }
    }

    /**
     * 플레이어를 삭제합니다.
     * exit 명령어를 통해 삭제하지 않고 나갈 수 있습니다.
     */
    public void deletePlayer() {
        System.out.println("[DELETE] 플레이어 이름을 적으십시오.");
        printPlayerList();
        System.out.println("나가기: exit");
        while (true) {
            try {
                String input = playerInput("host");
                if (input.equals("exit")) return;

                if (input.isBlank()) throw new IllegalArgumentException("입력 값은 빈 문자열일 수 없습니다.");
                Player target = players.stream()
                        .filter(p -> p.getName().equals(input))
                        .findFirst()
                        .orElseThrow(() -> new NoSuchElementException("해당 플레이어를 찾을 수 없습니다: " + input));

                players.remove(target);
                System.out.println("[DELETE] 플레이어가 삭제되었습니다: " + target.getName());
                return;
            } catch (IllegalArgumentException | NoSuchElementException e) {
                showError(e.getMessage());
            }
        }
    }

    /**
     * 현재 존재하는 플레이어 목록을 보여줍니다
     */
    public void printPlayerList() {
        for (Player player : players) {
            System.out.print(" - ");
            System.out.println(player.getName());
        }
    }

    /**
     * 플레이어의 입출력을 묶은 편의성 메서드입니다.
     * @param name 플레이어의 이름
     * @return 플레이어의 입력
     */
    private String playerInput(String name) {
        userPrompt(name);
        return scanner.nextLine().trim().toLowerCase();
    }


}
