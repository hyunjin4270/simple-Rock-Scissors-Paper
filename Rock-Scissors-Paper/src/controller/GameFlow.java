package controller;

import entity.Player;

import java.util.List;
import java.util.Optional;

import static view.GameView.showAppliedConfig;
import static view.GameView.showWelcome;

public abstract class GameFlow {
    protected final List<Player> players;

    public abstract String getName();

    public GameFlow(List<Player> players) {
        this.players = players;
    }

    public final void run() {
        beforeRound();
        doRound();
        afterRound();
    }

    protected void beforeRound() {
        showWelcome();
        showAppliedConfig(getName(), players);
    }

    protected abstract void doRound();

    protected void afterRound() {

    }
}
