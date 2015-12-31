package frc.team5333.recyclerush;

/**
 * The DriveController is a class that handles inputs from the controller and manipulates them for usage
 * in the Drive Base.
 *
 * This class uses a Throttle Scale to limit the throttle going to the main drive motors. This can be changed
 * using the Left and Right bumpers.
 *
 * Additionally, pressing the 'A' button on the controller will adjust the Throttle Lock. The Throttle Lock
 * is like cruise control for your Robot.
 *
 * @author Jaci
 */
public class DriveController {

    private static double THROTTLE_SCALE = 0.5;
    private static boolean DRIVE_LOCKED = false;

    private static double last_left, last_right;

    /**
     * Increase the Throttle Scale by 10%
     */
    public static void increaseThrottleScale() {
        THROTTLE_SCALE = Math.min(1, THROTTLE_SCALE + 0.1);

        // This is called a format string. Format Strings have placeholder values that are replaced later.
        // In this example, %.0f is the placeholder. %f will print a double or float, and the .0 in the middle
        // simply means that it shouldn't print any decimal places.
        RobotModule.logger.info(String.format("Throttle Scale: %.0f%%", THROTTLE_SCALE * 100));
    }

    /**
     * Decrease the Throttle Scale by 10%
     */
    public static void decreaseThrottleScale() {
        THROTTLE_SCALE = Math.max(0, THROTTLE_SCALE - 0.1);
        RobotModule.logger.info(String.format("Throttle Scale: %.0f%%", THROTTLE_SCALE * 100));
    }

    /**
     * Get the current throttle scale on a range of 0 - 1
     */
    public static double getThrottleScale() {
        return THROTTLE_SCALE;
    }

    /**
     * Calculate an adjusted throttle value influenced by the current throttle scale.
     */
    public static double getAdjustedThrottle(double original_throttle) {
        return original_throttle * THROTTLE_SCALE;
    }

    /**
     * Returns true if the Drive System is in Drive Lock (Cruise Control)
     */
    public static boolean driveLocked() {
        return DRIVE_LOCKED;
    }

    /**
     * Toggle the Drive Lock between on and off
     */
    public static void toggleDriveLock() {
        DRIVE_LOCKED = !DRIVE_LOCKED;
    }

    /**
     * Calculate and Drive the drive base with the provided (raw, non-adjusted) left and right motor values.
     */
    public static void drive(double left, double right) {
        if (driveLocked()) {
            // We're locked, use the last known values.
            Mappings.MOTOR_DRIVER.tankDrive(last_left, last_right);
        } else {
            last_left = getAdjustedThrottle(left);
            last_right = getAdjustedThrottle(right);
            Mappings.MOTOR_DRIVER.tankDrive(last_left, last_right);
        }
    }

}
