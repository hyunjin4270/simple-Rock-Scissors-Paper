package entity;

import java.util.List;
import java.util.NoSuchElementException;

public enum Move {
    ROCK, PAPER, SCISSOR;

    /**
     * 사용자의 입력값에 맞는 무브를 반환하는 기능입니다.
     * 만약 입력값이 이상하거나, 해당하는 무브가 없다면 예외를 터뜨립니다.
     * @param input 사용자 입력값 (null 불가)
     * @return 사용자 입력값에 맞는 무브
     */
    public static Move from(String input) {
        if (input == null || input.isBlank()) throw new IllegalArgumentException("입력 값이 빌 수 없습니다.");
        String name = input.trim().toLowerCase();

        return switch (name) {
            case "rock"     -> ROCK;
            case "scissor"  -> SCISSOR;
            case "paper"    -> PAPER;
            default         -> throw new NoSuchElementException("해당하는 무브를 찾을 수 없습니다: " + name);
        };
    }

    public static List<String> findAll() {
        return List.of("Rock", "Scissor", "Paper");
    }
}
