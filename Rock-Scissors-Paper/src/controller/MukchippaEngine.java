package controller;

import entity.Player;

import java.util.List;
import java.util.Scanner;

public class MukchippaEngine extends GameFlow{
    public MukchippaEngine(List<Player> players, Scanner scanner) {
        super(players, scanner);
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    protected RoundResult doRound() {
        return null;
    }
}
