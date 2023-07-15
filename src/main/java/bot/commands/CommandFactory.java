package bot.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {


    enum COMMANDS {
        START("matching-start"),
        TEST("matching-test");


        private String commandPrefix;

        public String getCommandPrefix() {
            return this.commandPrefix;
        }

        COMMANDS(String commandPrefix) {

            this.commandPrefix = commandPrefix;
        }
    }



    private Map<String, ICommand> commands = new HashMap<>();

    public CommandFactory() {



        registerCommand(COMMANDS.START.getCommandPrefix(), new FoodMatchCommand());
        registerCommand(COMMANDS.TEST.getCommandPrefix(), new TestCommand());
    }

    private void registerCommand(String commandPrefix, ICommand command) {

        commands.put(commandPrefix, command);
    }


    public ICommand fetchCommand(String commandPrefix) {

        String commandExecuted = commandPrefix;


        if(commandPrefix.startsWith("/")) // is a command
            commandExecuted = COMMANDS.valueOf(commandPrefix.replace("/", "").toUpperCase()).getCommandPrefix();



//        if(commandPrefix.equals("/start"))
//            commandExecuted = COMMANDS.START.getCommandPrefix();
//        else if(commandPrefix.equals("/test"))
//            commandExecuted = COMMANDS.TEST.getCommandPrefix();



        ICommand command = commandExecuted != null
                ? commands.get(commandExecuted) : null;

        System.out.println(command);




        return command;
    }
}
