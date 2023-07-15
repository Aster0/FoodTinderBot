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



    private Map<String, AbstractCommand> commands = new HashMap<>();

    public CommandFactory() {



        registerCommand(COMMANDS.START.getCommandPrefix(), new FoodMatchCommand());
        registerCommand(COMMANDS.TEST.getCommandPrefix(), new TestCommand());
    }

    private void registerCommand(String commandPrefix, AbstractCommand command) {

        commands.put(commandPrefix, command);
    }


    public AbstractCommand fetchCommand(String commandPrefix, boolean cameFromCallback) {

        String commandExecuted = commandPrefix;

        boolean cameFromPrefix = true;


        if(commandPrefix.startsWith("/")) { // is a command
            commandExecuted = COMMANDS.valueOf(commandPrefix.replace("/", "").toUpperCase()).getCommandPrefix();
            cameFromPrefix = false;
        }




//        if(commandPrefix.equals("/start"))
//            commandExecuted = COMMANDS.START.getCommandPrefix();
//        else if(commandPrefix.equals("/test"))
//            commandExecuted = COMMANDS.TEST.getCommandPrefix();



        AbstractCommand command = commandExecuted != null
                ? commands.get(commandExecuted) : null;


        if(cameFromPrefix && !cameFromCallback) {
            if(!command.allowPrefixReplies) // whether we should let the bot reply to prefix messages from the user.
                return null;
        }

        System.out.println(command);




        return command;
    }
}
