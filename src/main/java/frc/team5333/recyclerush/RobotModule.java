/**
 * It's generally good practise to keep your code under the "frc.teamXXXX.yeargame" package.
 * For example, the 2016 season would use package: "frc.team5333.stronghold"
 */
package frc.team5333.recyclerush;

import frc.team5333.recyclerush.data.DataRecorder;
import frc.team5333.recyclerush.data.MatchDataCommand;
import jaci.openrio.module.routines.Routines;
import jaci.openrio.toast.core.command.CommandBus;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.module.ModuleConfig;

/**
 * The "RobotModule" class is the entrypoint to your Robot Program, and is used to 'bootstrap' your Module.
 *
 * This is where all your setup code is called from, and is kind of like the figurehead for your Robot Module.
 *
 * In this example we're extending from "IterativeModule", which is a preset defined by Toast that automatically
 * registers you to something called the "StateTracker". The "StateTracker" is a class inside of Toast that calls
 * code in your module when the Robot's different modes are activated (Disabled, Autonomous, Teleop and Test), and
 * when they are periodically called for control loops.
 *
 * Also, don't forget to credit yourself. You can do that by reading the line beneath me ^-^
 * @author Jaci
 */
public class RobotModule extends IterativeModule {

    /**
     * This prints out stuff that we can read in the console. Don't worry, we declare a value to this
     * variable in the init() method.
     */
    public static Logger logger;

    /**
     * This allows us to store data in a file instead of in code. In our case, we're using it to get the ports
     * that motors and controllers are connected to. This means if we need to change a port number, we don't
     * need to rewrite code and redeploy our code. Instead, we just change a line in the file and our code will
     * read that value at startup.
     */
    public static ModuleConfig configuration;

    /**
     * Your module name is like an identifier. This is used to tell modules apart.
     */
    @Override
    public String getModuleName() {
        return "5333-Recycle-Rush-Rewrite";
    }

    /**
     * Your module version is changed with every public release you do of your code. This increments differently.
     * Although this isn't in the scope of the lesson, if you have any questions feel free to ask or
     * search "Semantic Versioning" in your browser.
     */
    @Override
    public String getModuleVersion() {
        return "0.0.1";
    }

    /**
     * The robotInit() method is called when your module is initially loaded. This is where all of your setup goes.
     */
    @Override
    public void robotInit() {
        logger = new Logger("5333-RecycleRush", Logger.ATTR_DEFAULT);
        configuration = new ModuleConfig("5333-Recycle-Rush-Rewrite");
        Mappings.init();

        // The CommandBus allows us to give the robot program instructions as it's running. See MatchDataCommand.java
        // for more information
        CommandBus.registerCommand(new MatchDataCommand());
    }

    /**
     * Called when we enter autonomous mode (first 15 seconds of the match)
     */
    public void autonomousInit() {
        DataRecorder.init();
    }

    /**
     * Called when we enter teleoperated mode (remaining 2:15 minutes of the match)
     */
    public void teleopInit() {
        DataRecorder.init();

        // Routines is another Toast Module that is responsible for tracking our autonomous program.
        // Routines will record and playback whatever you put in the joystick, but still needs to be stopped
        // in case we enter another mode early
        Routines.stopAll();
    }

    /**
     * Called when our robot is disabled. This is called before the match starts and when it ends,
     * but may also be called if your robot is shutdown by the Field Management System, or if the match
     * is called early.
     */
    public void disabledInit() {
        Routines.stopAll();
    }

    /**
     * Called about 50 times per second while we're in autonomous mode
     */
    public void autonomousPeriodic() {
        DataRecorder.tick();
    }

    /**
     * Called about 50 times per second while we're in teleoperated mode
     */
    public void teleopPeriodic() {
        DataRecorder.tick();
        TeleopController.teleopTicker();
    }
}
