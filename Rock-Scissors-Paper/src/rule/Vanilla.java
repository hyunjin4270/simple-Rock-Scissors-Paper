package rule;

import entity.Move;
import dto.RoundResult;
import entity.Player;

import java.util.*;

import static entity.Outcome.*;
import static entity.Outcome.DRAW;

/**
 * 기본적인 가위바위보 로직을 담은 클래스입니다.
 */
public class Vanilla extends RockScissorPaper {
    @Override
    public String getName() {
        return "Vanilla";
    }


    @Override
    public Optional<List<Player>> play(RoundResult context) {
        Map<Player, Move> players = context.getMoves();
        if (isDraw(players)) {
            return Optional.empty();
        }

        LinkedList<Player> groupA = new LinkedList<>();
        LinkedList<Player> groupB = new LinkedList<>();

        List<Player> playerList = new ArrayList<>(players.keySet());
        Player ctrlPlayer = playerList.getFirst();
        groupA.add(ctrlPlayer);

        for (Player expPlayer : playerList) {
            if (expPlayer.equals(ctrlPlayer)) continue;
            Move ctrlMove = players.get(ctrlPlayer);
            Move expMove  = players.get(expPlayer);

            if (decide(ctrlMove, expMove) == DRAW) {
                groupA.add(expPlayer);
            } else {
                groupB.add(expPlayer);
            }
        }

        Move firstA = players.get(groupA.getFirst());
        Move firstB = players.get(groupB.getFirst());

        if (decide(firstA, firstB) == WIN) {
            return Optional.of(groupA);
        } else {
            return Optional.of(groupB);
        }
    }
}
