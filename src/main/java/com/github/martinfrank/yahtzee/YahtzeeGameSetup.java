package com.github.martinfrank.yahtzee;

import com.github.martinfrank.boardgamelib.BoardGameSetup;

import java.util.ArrayList;
import java.util.List;

public class YahtzeeGameSetup implements BoardGameSetup<YahtzeePlayer> {

    @Override
    public List<YahtzeePlayer> getPlayers() {
        ArrayList<YahtzeePlayer> player = new ArrayList<>();
        player.add(new YahtzeePlayer("CP1", 0x0000FF, false));
        player.add(new YahtzeePlayer("CP2", 0xFF00FF, false));
        player.add(new YahtzeePlayer("YOU", 0xFFFF00, true));
        return player;
    }

    @Override
    public int getMaximumRounds() {
        return 15;
    }
}
