package de.martin.frank.games.yatzee;

import de.frank.martin.games.boardgamelib.BoardGameSetup;

import java.util.ArrayList;
import java.util.List;

public class YatzeeGameSetup implements BoardGameSetup<YatzeePlayer> {

    @Override
    public List<YatzeePlayer> getPlayers() {
        ArrayList<YatzeePlayer> player = new ArrayList<>();
        player.add(new YatzeePlayer("YOU", 0xFFFF00, true));
        player.add(new YatzeePlayer("CPU", 0x0000FF, false));
        return player;
    }

    @Override
    public int getMaximumRounds() {
        return 15;
    }
}
