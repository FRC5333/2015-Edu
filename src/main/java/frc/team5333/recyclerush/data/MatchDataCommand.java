package frc.team5333.recyclerush.data;

import frc.team5333.recyclerush.RobotModule;
import jaci.openrio.toast.core.command.AbstractCommand;
import jaci.openrio.toast.core.command.IHelpable;
import jaci.openrio.toast.core.command.UsageException;

/**
 * The MatchData command is used to set the current match during competition. This is accessible through the console.
 *
 * @author Jaci
 */
public class MatchDataCommand extends AbstractCommand implements IHelpable {

    public static String MATCH_TYPE = "q", MATCH_NUM = "1";

    @Override
    public String getCommandName() {
        return "match";
    }

    @Override
    public String getHelp() {
        return "Set the current match. Usage: match <type> <number>";
    }

    @Override
    public void invokeCommand(int argLength, String[] args, String command) {
        if (args.length == 2) {
            MATCH_TYPE = args[0];
            MATCH_NUM = args[1];
        } else if (args.length == 0) {
            RobotModule.logger.info("Current Match: " + MATCH_TYPE + " " + MATCH_NUM);
        } else throw new UsageException(getHelp());
    }
}
