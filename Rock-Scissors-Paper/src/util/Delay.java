package util;

public class Delay {
    /**
     * 짧은 인터랙션 딜레이: 400ms
     */
    public static void shortDelay() {
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 일반 딜레이: 700ms
     */
    public static void normalDelay() {
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 긴 딜레이: 1000ms
     */
    public static void thinkingDelay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 긴장감 딜레이: 1800ms
     */
    public static void suspenseDelay() {
        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
