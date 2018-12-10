package de.martin.frank.games.yatzee;

import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class YatzeeApp
{
    public static void main( String[] args )
    {
        YatzeeGame yatzeeGame = new YatzeeGame();
        yatzeeGame.setup(new YatzeeGameSetup());
        YatzeeGamePrinter printer = new YatzeeGamePrinter(yatzeeGame);
        yatzeeGame.initGame();
        while(yatzeeGame.getRound() < yatzeeGame.getMaximumRounds()){
            YatzeePlayer player = yatzeeGame.getCurrent();
            if (player.isHuman() ){
                handleKeyboardInput(yatzeeGame, printer);
            }else{
                player.performAiTurn();
            }
        }
        printer.printResults(System.out);

    }

    private static void handleKeyboardInput(YatzeeGame yatzeeGame, YatzeeGamePrinter printer) {
        printer.printGame(System.out);
        Scanner scanner = new Scanner(System.in);
        String line;
        while (true){
            Set<YatzeeCommand> options = yatzeeGame.getOptions();
            System.out.println("options: "+options);
            line = scanner.nextLine();
            String[] cmd = line.split(" ");
            if(isCommandOf(cmd[0], options)){
                if(isCommand(YatzeeCommand.ROLL, cmd[0])){
                    yatzeeGame.doRoll();
                    printer.printGame(System.out);
                }
                if(isCommand(YatzeeCommand.KEEP, cmd[0])){
                    Set<Integer> keeps;
                    try {
                        keeps = getKeeps(cmd);
                    }
                    catch (IllegalArgumentException e){
                            printer.printGame(System.out);
                            System.out.println(e.getMessage());
                            continue;
                    }
                    yatzeeGame.keepThrow(keeps);
                    printer.printGame(System.out);

                }
                if ("EXIT".equalsIgnoreCase(cmd[0] )) {
                    System.exit(0);
                }
                if ("SHOW".equalsIgnoreCase(cmd[0] )) {
                    printer.printGame(System.out);
                }
                if ("WRITE".equalsIgnoreCase(cmd[0] )) {
                    YatzeeRowType type;
                    try {
                        type = getWriteRow(cmd);
                        yatzeeGame.writeRoll(type);
                    }
                    catch (IllegalArgumentException e){
                        printer.printGame(System.out);
                        System.out.println(e.getMessage());
                        continue;
                    }
                    printer.printGame(System.out);
                }
                if ("DONE".equalsIgnoreCase(cmd[0] )) {
                    yatzeeGame.endPlayersTurn();
                    return;
                }
            }else{
                System.out.println("'"+cmd[0]+"' is not a valid cmd, not one of "+options);
            }


        }
    }

    private static YatzeeRowType getWriteRow(String[] cmd) {
        if (cmd.length != 2){
            throw new IllegalArgumentException ("only one parameter allowed after WRITE");
        }
        for (YatzeeRowType type: YatzeeRowType.values()){
            if(type.getName().equalsIgnoreCase(cmd[1])){
                return type;
            }
        }
        throw new IllegalArgumentException ("unknown row parameter after WRITE");
    }

    private static Set<Integer> getKeeps(String[] cmd) {
        Set<Integer> keeps = new HashSet<>();
        for(int i = 1; i < cmd.length; i++){
            for (int j = 0; j < cmd[i].length(); j++){
                String str = ""+cmd[i].charAt(j);
                Integer index;
                try{
                    index = Integer.parseInt(str);
                }catch (IllegalArgumentException e){
                    throw new IllegalArgumentException("invalid parameter for KEEP");
                }
                if (index >= 1 && index <= 5 && !keeps.contains(index)){
                        keeps.add(index);
                }
            }
        }
        return keeps;
    }

    private static boolean isCommand(YatzeeCommand option, String command) {
        return option.toString().equalsIgnoreCase(command);
    }

    private static boolean isCommandOf(String s, Set<YatzeeCommand> options) {
        return options.stream().anyMatch(o -> o.toString().equalsIgnoreCase(s));
    }

}
