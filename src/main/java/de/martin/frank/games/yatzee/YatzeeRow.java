package de.martin.frank.games.yatzee;

public class YatzeeRow {

    private final YatzeeRowType type;
    private YatzeeRoll roll;
    public YatzeeRow(YatzeeRowType type){
        this.type = type;
    }

    public YatzeeRoll getRoll(){
        return roll;
    }

    public String getValue(){
        if (roll == null){
            return "";
        }
        int value = roll.getValue(type);
        if (value == 0){
            return "XX";
        }else{
            return Integer.toString(value);
        }
    }

    public YatzeeRowType getType() {
        return type;
    }

    public void setRoll(YatzeeRoll aThrow) {
        this.roll = aThrow;
    }
}
