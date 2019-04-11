package de.elite.games.yatzee;

public class Row {

    private final RowType type;
    private Roll roll;

    Row(RowType type) {
        this.type = type;
    }

    int getValue() {
        if (roll == null){
            return 0;
        }
        return RollCalculator.getValue(type, roll);
    }

    RowType getType() {
        return type;
    }

    void setRoll(Roll roll) {
        this.roll = roll;
    }

    public boolean isEmpty() {
        return roll == null;
    }
}
