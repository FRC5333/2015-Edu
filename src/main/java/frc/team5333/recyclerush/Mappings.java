package frc.team5333.recyclerush;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import jaci.openrio.toast.lib.device.XboxController;
import jaci.openrio.toast.lib.registry.Registrar;

/**
 * The Mappings Class holds all the variables for our Driver and Robot controls. This means if we
 * need to change a value or port number, we don't have to scan through all our code to change
 * every occurrence.
 *
 * @author Jaci
 */
public class Mappings {

    // Left and Right Drive Motors (Talon SR Controllers)
    public static Talon DRIVE_LEFT, DRIVE_RIGHT;

    // Clamp and Lift Motors (Victor 888 Controllers)
    public static Victor CLAMP, LIFT;

    // The RobotDrive instance for the Drive Base
    public static RobotDrive MOTOR_DRIVER;

    // The Xbox 360 Controller attached to the Driver Station
    public static XboxController CONTROLLER;

    /**
     * Start up the Mappings class. This declares the value for each static
     * variable in the class, setting them up for use.
     */
    public static void init() {
        // Get a Motor instance for the Left and Right drive motors
        // getInt() will try and find the value at key "motor.left". If the file is not yet created, it will use '1' as the default value
        DRIVE_LEFT = Registrar.talon(RobotModule.configuration.getInt("motor.left", 1));
        DRIVE_RIGHT = Registrar.talon(RobotModule.configuration.getInt("motor.right", 0));

        // Get a Motor instance for the Clamp and Lift motors
        CLAMP = Registrar.victor(RobotModule.configuration.getInt("motor.clamp", 3));
        LIFT = Registrar.victor(RobotModule.configuration.getInt("motor.lift", 2));

        // Create an instance of an XboxController from the Driver Station
        CONTROLLER = new XboxController(RobotModule.configuration.getInt("joystick.port", 0));

        // Create RobotDrive helper class from the Left and Right drive motors
        MOTOR_DRIVER = new RobotDrive(DRIVE_LEFT, DRIVE_RIGHT);
    }

}
