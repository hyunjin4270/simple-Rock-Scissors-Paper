import entity.Move;
import entity.Outcome;
import entity.Player;

import java.util.*;

import static entity.Move.*;
import static entity.Outcome.*;

public class RockScissorPaper {
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


    private Outcome decide(Move preMove, Move sufMove) {
        if (
                (preMove == ROCK   && sufMove == SCISSOR) ||
                (preMove == SCISSOR&& sufMove == PAPER)   ||
                (preMove == PAPER  && sufMove == ROCK)
        ) {
            return WIN;
        } else if (
                (preMove == ROCK   && sufMove == PAPER) ||
                (preMove == SCISSOR&& sufMove == ROCK)   ||
                (preMove == PAPER  && sufMove == SCISSOR)
        ){
            return LOSE;
        } else {
            return DRAW;
        }
    }

    private boolean isDraw(List<Player> playerList) {
        Set<Move> moves = new HashSet<>();
        for (Player player : playerList) {
            moves.add(player.getMove());
        }
        if (moves.size() != 2) return true;
        return false;
    }
}
