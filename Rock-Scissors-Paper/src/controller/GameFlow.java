package controller;

import entity.Move;
import entity.Player;
import view.GameView;

import java.util.*;

import static view.ConsoleViewHelper.showError;
import static view.ConsoleViewHelper.userPrompt;
import static view.GameView.*;

public abstract class GameFlow {
    private final List<Player> players;
    private final List<String> MOVE_NAMES = Move.findAll();
    private final Random random = new Random();
    private final Scanner scanner;
    public abstract String getName();



    public GameFlow(List<Player> players, Scanner scanner) {
        this.players = players;
        this.scanner = scanner;
    }

    public final void run() {
        beforeRound();
        RoundResult roundResult = doRound();
        afterRound(roundResult);
    }

    protected Scanner getScanner() {
        return scanner;
    }

    public List<Player> getPlayers() {
        return players;
    }

    protected Random getRandom() {
        return random;
    }

    protected void beforeRound() {
        showWelcome();
        showAppliedConfig(getName(), players);
    }

    protected abstract RoundResult doRound();

    protected void afterRound(RoundResult result) {
        showEndMessage();
        if(result.isDraw()) {
            showDrawMessage();
        } else {
            showWinner(result.getWinners());
        }
        showStatus(result.getStatus());
    }


    /**
     * 플레이어의 행동을 입력받는 메서드입니다. 만약 해당하지 않는 행동을 고를경우 다시 고르게 합니다.
     * @param scanner 입력장치를 통일시키기 위한 매개변수
     * @param player 입력을 받을 플레이어
     * @return 플레이어가 선택한 행동
     */
    protected Move playPlayerTurn(Scanner scanner, Player player) {
        showAvailableMoves(MOVE_NAMES);
        while (true) {
            String input = playerInput(scanner, player.getName());
            try {
                Move move = Move.from(input);
                showMoveAccepted(player.getName(), move.name());
                return move;
            } catch (RuntimeException e) {
                showError(e.getMessage());
            }
        }
    }

    /**
     * 랜덤한 무브를 선택하는 기능입니다.
     * @return 랜덤 무브
     */
    protected Move playComputerTurn() {
        int idx = random.nextInt(MOVE_NAMES.size());
        String name = MOVE_NAMES.get(idx);
        return Move.from(name);
    }

    /**
     * 플레이어의 입출력을 묶은 편의성 메서드입니다.
     * @param scanner 입력 도구
     * @param name 플레이어의 이름
     * @return 플레이어의 입력
     */
    protected String playerInput(Scanner scanner, String name) {
        userPrompt(name);
        return scanner.nextLine().trim().toLowerCase();
    }
}
