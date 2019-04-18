package de.elite.games.yatzee;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class HelpPrinter {

    private HelpPrinter() {

    }

    public static void print(PrintStream out) {
        List<String> lines = getHelpText();
        for (String line : lines) {
            out.print(line);
        }
    }

    private static List<String> getHelpText() {
        List<String> lines = new ArrayList<>();
        lines.add("keep dice:<tbd>");
        lines.add("write into:<tbd>");
        lines.add("roll:<tbd>");
        return lines;
    }
}
