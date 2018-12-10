package de.martin.frank.games.yatzee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class YatzeeUtil {

    private YatzeeUtil(){

    }

    static String formatLong(String str) {
        return format(str, 6);
    }
    static String formatShort(String str) {
        return format(str, 4);
    }
    private static String format(String str, int length) {

        if(str.length() == length){
            return str;
        }
        StringBuffer sb = new StringBuffer(str);
        if(sb.length() > length){
            return sb.substring(0,length);
        }
        int i = 0;
        while(sb.length()<length){
            if (i%2==0){
                sb.append(" ");
            }else{
                sb.insert(0, " ");
            }
            i = i + 1;
        }
        return sb.toString();
    }

    public static Optional<YatzeeRow> getRow(List<YatzeeRow> yatzeeRows, YatzeeRowType rowType){
        return yatzeeRows.stream().filter(r -> rowType == r.getType()).findFirst();
    }

    public static List<YatzeeRow> getTopRows(List<YatzeeRow> yatzeeRows){
        List<YatzeeRow> topRows = new ArrayList<>();
        topRows.add(getRow(yatzeeRows, YatzeeRowType.ONE).get());
        topRows.add(getRow(yatzeeRows, YatzeeRowType.TWO).get());
        topRows.add(getRow(yatzeeRows, YatzeeRowType.THREE).get());
        topRows.add(getRow(yatzeeRows, YatzeeRowType.FOUR).get());
        topRows.add(getRow(yatzeeRows, YatzeeRowType.FIVE).get());
        topRows.add(getRow(yatzeeRows, YatzeeRowType.SIX).get());
        return topRows;
    }

    public static List<YatzeeRow> getBottomRows(List<YatzeeRow> yatzeeRows){
        List<YatzeeRow> topRows = new ArrayList<>();
        topRows.add(getRow(yatzeeRows, YatzeeRowType.ONE_PAIR).get());
        topRows.add(getRow(yatzeeRows, YatzeeRowType.TWO_PAIR).get());
        topRows.add(getRow(yatzeeRows, YatzeeRowType.THREE_OF_A_KIND).get());
        topRows.add(getRow(yatzeeRows, YatzeeRowType.FOUR_OF_A_KIND).get());
        topRows.add(getRow(yatzeeRows, YatzeeRowType.FULL_HOUSE).get());
        topRows.add(getRow(yatzeeRows, YatzeeRowType.MINOR_STRAIGHT).get());
        topRows.add(getRow(yatzeeRows, YatzeeRowType.MAJOR_STRAIGHT).get());
        topRows.add(getRow(yatzeeRows, YatzeeRowType.YATZEE).get());
        topRows.add(getRow(yatzeeRows, YatzeeRowType.CHANCE).get());
        return topRows;
    }

}
