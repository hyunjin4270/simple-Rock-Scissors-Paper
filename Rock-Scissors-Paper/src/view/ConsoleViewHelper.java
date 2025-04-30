package view;

public class ConsoleViewHelper {
    public static void home() {
        System.out.println("===============================================================");
        System.out.println("1. 시작 | 2. 플레이어 목록 | 3. 컴퓨터 추가 | 4. 컴퓨터 삭제 | 5. 종료");
        System.out.println("===============================================================");
    }

    public static void userPrompt(String name) {
        System.out.printf("[%9s] : ", name);
    }

    public static void showError(String message) {
        System.out.println("[ERROR] " + message);
    }
}
