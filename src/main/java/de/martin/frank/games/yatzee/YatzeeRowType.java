package de.martin.frank.games.yatzee;

public enum YatzeeRowType {

//    PLAYER("player"),
    ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"),
//    TOP_SUM("sum"),
//    TOP_BONUS("bonus"),
    ONE_PAIR("1pair"), TWO_PAIR("2pair"), THREE_OF_A_KIND("3oak"), FOUR_OF_A_KIND("4oak"),
    FULL_HOUSE("fh"), MINOR_STRAIGHT("mistr"), MAJOR_STRAIGHT("mastr"),YATZEE("yatzee"), CHANCE("chance");

//    BOTTOM_SUM("sum"), TOTAL("total");

    private final String name;
    YatzeeRowType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
