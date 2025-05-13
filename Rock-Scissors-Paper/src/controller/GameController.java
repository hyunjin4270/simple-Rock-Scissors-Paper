package controller;

import entity.Computer;
import entity.Move;
import entity.Player;
import rule.RockScissorPaper;
import rule.Vanilla;

import java.util.*;

import static view.ConsoleViewHelper.*;
import static view.GameView.*;

public class GameController {
    private final List<String> rules = List.of(
            "vanilla"
    );
    private RockScissorPaper game;
    private final List<String> MOVE_NAMES = Move.findAll();
    private final Random random = new Random();

    public void gameStart(Scanner scanner, List<Player> players) {
        //준비작업
        game = selectRule(scanner);

        //게임 시작
        showWelcome();
        showAppliedConfig(game.getName(), players);

        //게임 진행
        advanceTurn(scanner, players);

        //게임 종료
        endGame(players);
    }

    private void endGame(List<Player> players) {
        showEndMessage();
        Optional<List<Player>> playerGroup = game.decideWinner(players);
        if (playerGroup.isEmpty()) {
            showDrawMessage();
        } else {
            showWinner(playerGroup.get());
        }
    }

    private void advanceTurn(Scanner scanner, List<Player> players) {
        for (Player player : players) {
            showPlayerTurn(player.getName());

            if (player instanceof Computer) {
                player.setMove(playComputerTurn());
                promptComputerMove(player.getName());
                continue;
            }

            Move move = playPlayerTurn(scanner, player);
            player.setMove(move);
            showMoveAccepted(player.getName(), move.name());
        }
    }

    private Move playPlayerTurn(Scanner scanner, Player player) {
        showAvailableMoves(MOVE_NAMES);
        while (true) {
            String input = playerInput(scanner, player.getName());
            try {
                return Move.from(input);
            } catch (RuntimeException e) {
                showError(e.getMessage());
            }
        }
    }

    /**
     * 랜덤한 무브를 선택하는 기능입니다.
     * @return 랜덤 무브
     */
    private Move playComputerTurn() {
        int idx = random.nextInt(MOVE_NAMES.size());
        String name = MOVE_NAMES.get(idx);
        return Move.from(name);
    }


    /**
     * 게임에 진입하기 전 적용할 규칙을 설정합니다.
     * @param scanner 입력 도구
     * @return 규칙
     */
    private RockScissorPaper selectRule(Scanner scanner) {
        showRuleList(rules);
        while (true) {
            String input = playerInput(scanner, "host");
            try {
                if (input.isBlank()) throw new IllegalArgumentException("입력값이 빌 수 없습니다.");
                if (!rules.contains(input)) throw new NoSuchElementException("해당하는 룰이 없습니다: " + input);
                return switch (input) {
                    case "vanilla" -> new Vanilla();
                    default -> throw new NoSuchElementException("알 수 없는 상황이 발생했습니다.");
                };
            } catch (RuntimeException e) {
                showError(e.getMessage());
            }
        }
    }

    /**
     * 플레이어의 입출력을 묶은 편의성 메서드입니다.
     * @param scanner 입력 도구
     * @param name 플레이어의 이름
     * @return 플레이어의 입력
     */
    private String playerInput(Scanner scanner, String name) {
        userPrompt(name);
        return scanner.nextLine().trim().toLowerCase();
    }
}
