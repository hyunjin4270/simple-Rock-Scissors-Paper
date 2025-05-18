package controller;

import dto.GameResult;
import entity.Move;
import entity.Player;

import java.util.*;

import static view.ConsoleViewHelper.showError;
import static view.ConsoleViewHelper.userPrompt;
import static view.GameView.*;

/**
 * 게임의 진행 흐름을 통일시키기 위한 추상클래스입니다.
 */
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
    protected Scanner getScanner() {
        return scanner;
    }

    public List<Player> getPlayers() {
        return players;
    }

    protected Random getRandom() {
        return random;
    }

    /**
     * 템플릿 메서드로서 전체 게임의 라이프사이클을 관리합니다.
     * 1) beforeRound(): 게임 시작 전 환영 메시지 출력
     * 2) doRound()   : 실제 게임 로직 수행 (하위 클래스에서 구현)
     * 3) afterRound(): 게임 종료 후 결과 정리 및 출력
     * 하위 클래스는 doRound()만 구현하면 전체 흐름에 따라 자동으로 실행됩니다.
     */
    public final void run() {
        beforeRound();
        GameResult gameResult = doRound();
        afterRound(gameResult);
    }

    /**
     * 게임 진행 전 환영메세지를 담당하는 메서드입니다.
     */
    protected void beforeRound() {
        showWelcome();
        showAppliedConfig(getName(), players);
    }

    /**
     * 본격적인 게임 진행을 위한 메서드입니다. 이를 상속받아 이 메서드를 구현하는 것이 핵심적인 설계입니다.
     * @return 게임결과
     */
    protected abstract GameResult doRound();

    /**
     * 본격적인 게임이 끝난 뒤, 게임결과를 받아 이를 기반으로 결과를 정리하는 메서드입니다
     * @param result 게임결과
     */
    protected void afterRound(GameResult result) {
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
    protected Move playUserTurn(Scanner scanner, Player player) {
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
