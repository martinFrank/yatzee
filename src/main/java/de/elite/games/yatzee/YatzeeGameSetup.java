package de.elite.games.yatzee;

import de.frank.martin.games.boardgamelib.BoardGameSetup;

import java.util.ArrayList;
import java.util.List;

public class YatzeeGameSetup implements BoardGameSetup<YatzeePlayer> {

    @Override
    public List<YatzeePlayer> getPlayers() {
        ArrayList<YatzeePlayer> player = new ArrayList<>();
        player.add(new YatzeePlayer("CP1", 0x0000FF, false));
        player.add(new YatzeePlayer("CP2", 0xFF00FF, false));
        player.add(new YatzeePlayer("YOU", 0xFFFF00, true));
        return player;
    }

    @Override
    public int getMaximumRounds() {
        return 15;
    }
}
