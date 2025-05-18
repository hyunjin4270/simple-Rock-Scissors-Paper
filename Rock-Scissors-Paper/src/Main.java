import controller.Controller;

import java.util.Scanner;

import static view.ConsoleViewHelper.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Controller CONTROLLER = new Controller(scanner);
    public static void main(String[] args) {
        while (true) {
            home();
            String input = playerInput();
            switch (input) {
                case "1" -> CONTROLLER.gameStart();
                case "2" -> CONTROLLER.printPlayerList();
                case "3" -> CONTROLLER.addPlayer();
                case "4" -> CONTROLLER.deletePlayer();
                case "5" -> System.exit(0);
                default -> showError("잘못된 입력입니다.");
            }
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