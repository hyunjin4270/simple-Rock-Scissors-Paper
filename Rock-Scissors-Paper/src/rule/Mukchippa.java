package rule;

import entity.Move;
import entity.PlayContext;
import entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Mukchippa extends RockScissorPaper{
    @Override
    public String getName() {
        return "Mukchippa";
    }

    @Override
    public Optional<List<Player>> play(PlayContext context) {
        Player attacker = context.getAttacker();
        if (attacker == null) {
            throw new IllegalArgumentException("attacker가 반드시 필요합니다.");
        }
        Map<Player, Move> moves = context.getMoves();
        Move attMove = moves.get(attacker);

        List<Player> defenders = new LinkedList<>();
        for (Map.Entry<Player, Move> entry : moves.entrySet()) {
            Player p = entry.getKey();
            if (p.equals(attacker)) continue;

            if (entry.getValue() != attMove) {
                defenders.add(p);
            }
        }

        return Optional.of(defenders);
    }
}
