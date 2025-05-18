import flow.HomeFlow;

import java.util.Scanner;

import static view.ConsoleViewHelper.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final HomeFlow HOME_CONTROLLER = new HomeFlow(scanner);
    public static void main(String[] args) {
        while (true) {
            home();
            String input = playerInput();
            switch (input) {
                case "1" -> HOME_CONTROLLER.gameStart();
                case "2" -> HOME_CONTROLLER.printPlayerList();
                case "3" -> HOME_CONTROLLER.addPlayer();
                case "4" -> HOME_CONTROLLER.deletePlayer();
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