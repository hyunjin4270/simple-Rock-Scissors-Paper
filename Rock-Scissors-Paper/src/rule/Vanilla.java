package rule;

import entity.Move;
import entity.Outcome;
import entity.Player;

import java.util.*;

import static entity.Move.*;
import static entity.Outcome.*;
import static entity.Outcome.DRAW;

public class Vanilla extends RockScissorPaper {
    @Override
    public String getName() {
        return "Vanilla";
    }


    @Override
    public Optional<List<Player>> decideWinner(List<Player> playerList) {
        if (isDraw(playerList)) return Optional.empty();

        List<Player> groupA = new LinkedList<>();
        List<Player> groupB = new LinkedList<>();
        Player ctrlPlayer = playerList.getFirst();
        groupA.add(ctrlPlayer);
        for (Player expPlayer : playerList) {
            if (expPlayer.equals(ctrlPlayer)) continue;
            if (decide(ctrlPlayer.getMove(), expPlayer.getMove()) == DRAW) {
                groupA.add(expPlayer);
            } else {
                groupB.add(expPlayer);
            }
        }

        if (decide(groupA.getFirst().getMove(), groupB.getFirst().getMove()) == WIN) {
            return Optional.of(groupA);
        } else {
            return Optional.of(groupB);
        }
    }
}
