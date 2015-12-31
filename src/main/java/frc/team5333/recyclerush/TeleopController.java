package frc.team5333.recyclerush;

/**
 * Static Imports convert MyClass.MY_VARIABLE to just MY_VARIABLE, as if
 * it belonged to this class. Super useful if you call it a lot, but generally
 * harder to read, a most programmers will completely skip over imports while
 * reading code
 */
import static frc.team5333.recyclerush.Mappings.*;

/**
 * The TeleopController is responsible for everything we do in the Teleoperated Period, where the driver has control.
 *
 * In this example, we're piping data from the Xbox Controller to the DriveController class. We're also checking
 * if buttons have been pressed to activate different features of the Drive System.
 *
 * @author Jaci
 */
public class TeleopController {

    static boolean lastA = false;
    static boolean lastLeftB = false;
    static boolean lastRightB = false;

    /**
     * This method is called in {@link RobotModule#teleopPeriodic()}
     */
    public static void teleopTicker() {
        // These are the left and right 'Y' (up and down) values of the Thumbsticks on the Xbox Controller
        double left = CONTROLLER.leftY();
        double right = CONTROLLER.rightY();

        // The throttle of the lifting motor is controlled by the 'triggers' on the Xbox Controller (aim and shoot in
        // most video games). These triggers have a range of 0.0 to 1.0. The below equation means that if the rightTrigger
        // is fully pushed down (1.0) and the left one is not (0.0), the motor will throttle with 1.0 (up). If the leftTrigger
        // is pressed and the right one is not, we get -1.0, meaning to go down. Finally, if neither (or both) are pressed,
        // the net is 0, meaning no movement at all.
        double lift = CONTROLLER.rightTrigger() - CONTROLLER.leftTrigger();

        // The clamp motor is a little different. Because the clamp motor is a slow (but powerful) motor, throttle control
        // isn't really of a concern. Because of this, we can set it to -1 (out), 0, or 1 (in). Since the left and right
        // thumbsticks on the Xbox Controller push down, we're going to use them as it means we don't have to move our
        // thumbs around the controller and can drive simultaneously.
        double clamp = 0;
        if (CONTROLLER.leftStick()) clamp -= 1;
        if (CONTROLLER.rightStick()) clamp += 1;

        // The DriveController handles our drivebase logic from the values we retrieved earlier.
        DriveController.drive(left, right);

        // Clamp and Lift motors have no special functions, so we drive them directly.
        CLAMP.set(clamp);
        LIFT.set(lift);

        // The below functions are an easy way of detecting if a button's state has changed. i.e. on -> off, or off -> on,
        // but not on -> on or off -> off
        boolean a = CONTROLLER.a();                     // Get the current state of the button
        if (a != lastA) {                               // Is the state different to our last recorded state of this button?
            lastA = a;                                  // If yes, the button has been pressed or released. Update the last value.
            if (a) DriveController.toggleDriveLock();   // If the button returns 'true' (i.e. pressed down, but not released), activate a toggle.
        }

        boolean leftB = CONTROLLER.leftBumper();
        if (leftB != lastLeftB) {
            lastLeftB = leftB;
            if (leftB) DriveController.decreaseThrottleScale();
        }

        boolean rightB = CONTROLLER.rightBumper();
        if (rightB != lastRightB) {
            lastRightB = rightB;
            if (rightB) DriveController.increaseThrottleScale();
        }
    }

}
