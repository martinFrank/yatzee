package de.martin.frank.games.yatzee;

import de.frank.martin.games.boardgamelib.BaseBoardGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static de.martin.frank.games.yatzee.YatzeeUtil.getBottomRows;
import static de.martin.frank.games.yatzee.YatzeeUtil.getRow;
import static de.martin.frank.games.yatzee.YatzeeUtil.getTopRows;

public class YatzeeGame extends BaseBoardGame<YatzeePlayer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(YatzeeGame.class);
    private final Map<YatzeePlayer, List<YatzeeRow>> board = new HashMap<>();
    private int currentThrowIndex = 0;
    private boolean hasBeenWritten = false;
    private YatzeeRoll[] currentRolls = new YatzeeRoll[3];
    private Boolean[][] currentKeeps = new Boolean[3][5];


    public Set<YatzeeCommand> getOptions() {
        Set<YatzeeCommand> options = new HashSet<>();
        if (currentThrowIndex >= 1 && currentThrowIndex <= 3 && !hasBeenWritten) {
            options.add(YatzeeCommand.WRITE);
            if (currentThrowIndex <= 2) {
                options.add(YatzeeCommand.KEEP);
                options.add(YatzeeCommand.ROLL);
            }
        }
        if (currentThrowIndex == 0 && !hasBeenWritten) {
            options.add(YatzeeCommand.ROLL);
        }
        if (currentThrowIndex >= 1 && currentThrowIndex == 2 && !hasBeenWritten) {
            options.add(YatzeeCommand.WRITE);
        }
        if (hasBeenWritten) {
            options.add(YatzeeCommand.DONE);
        }
//        options.add(YatzeeCommand.INSPECT);
        options.add(YatzeeCommand.SHOW);
        options.add(YatzeeCommand.EXIT);
        return options;
    }

    @Override
    public void startPlayersTurn() {
        super.startPlayersTurn();
        currentRolls = new YatzeeRoll[3];
        currentKeeps = new Boolean[3][5];
        hasBeenWritten = false;
        currentThrowIndex = 0;
    }

    @Override
    public void initGame() {
        super.initGame();
        LOGGER.debug("init Game");
        for (YatzeePlayer player : getPlayers()) {
            board.put(player, createRows());
        }
    }

    public void doRoll() {
        currentRolls[currentThrowIndex] = new YatzeeRoll();
        int prev = currentThrowIndex - 1;
        if (prev >= 0) {
            currentRolls[currentThrowIndex].keep(currentRolls[prev], currentKeeps[prev]);
            currentKeeps[currentThrowIndex] = Arrays.copyOf(currentKeeps[prev], 5);
        }
        currentThrowIndex = currentThrowIndex + 1;
    }

    private List<YatzeeRow> createRows() {
        return Arrays.asList(
                new YatzeeRow(YatzeeRowType.ONE),
                new YatzeeRow(YatzeeRowType.TWO),
                new YatzeeRow(YatzeeRowType.THREE),
                new YatzeeRow(YatzeeRowType.FOUR),
                new YatzeeRow(YatzeeRowType.FIVE),
                new YatzeeRow(YatzeeRowType.SIX),
                new YatzeeRow(YatzeeRowType.ONE_PAIR),
                new YatzeeRow(YatzeeRowType.TWO_PAIR),
                new YatzeeRow(YatzeeRowType.THREE_OF_A_KIND),
                new YatzeeRow(YatzeeRowType.FOUR_OF_A_KIND),
                new YatzeeRow(YatzeeRowType.FULL_HOUSE),
                new YatzeeRow(YatzeeRowType.MINOR_STRAIGHT),
                new YatzeeRow(YatzeeRowType.MAJOR_STRAIGHT),
                new YatzeeRow(YatzeeRowType.YATZEE),
                new YatzeeRow(YatzeeRowType.CHANCE)
        );
    }

    public Map<YatzeePlayer, List<YatzeeRow>> getBoard() {
        return board;
    }

    public int getCurrentThrowIndex() {
        return currentThrowIndex;
    }

    public YatzeeRoll[] getCurrentRolls() {
        return currentRolls;
    }

    public void keepThrow(Set<Integer> keeps) {
        for (int i = 0; i < 5; i ++){
            if(keeps.contains(i+1)){
                this.currentKeeps[currentThrowIndex - 1][i] = true;
            }else{
                this.currentKeeps[currentThrowIndex - 1][i] = false;
            }
        }
    }

    public Boolean[][] getCurrentKeeps() {
        return currentKeeps;
    }

    public void writeRoll(YatzeeRowType type) {
        YatzeeRoll roll = currentRolls[currentThrowIndex-1];
        Optional<YatzeeRow> row = getRow(board.get(getCurrent()), type);
        if(row.isPresent()){
            if(row.get().getRoll()==null){
                row.get().setRoll(roll);
                hasBeenWritten = true;
            }else{
                throw new IllegalArgumentException("this row is already set");
            }
        }else{
            throw new IllegalArgumentException("this row type is not supported");
        }
    }

    public int getTopScore(YatzeePlayer player) {
        return getTopRows(board.get(player)).stream().mapToInt(r -> r.getRoll()==null?0:r.getRoll().getValue(r.getType())).sum();
    }
    public int getBottomScore(YatzeePlayer player) {
        return getBottomRows(board.get(player)).stream().mapToInt(r -> r.getRoll()==null?0:r.getRoll().getValue(r.getType())).sum();
    }

    public int getBonusScore(YatzeePlayer player) {
        return getTopScore(player) >= 63 ? 35:0;
    }
}
