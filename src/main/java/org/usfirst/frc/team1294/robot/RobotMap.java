package org.usfirst.frc.team1294.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static final int XBOX_CONTROLLER = 2;

    public static final int TALON_DRIVEBASE_LEFT_FRONT = 1;
    public static final int TALON_DRIVEBASE_LEFT_REAR = 2;
    public static final int TALON_DRIVEBASE_RIGHT_FRONT = 4;
    public static final int TALON_DRIVEBASE_RIGHT_REAR = 3;

    public static final int ANALOG_ULTRASONIC_LEFT = 0;
    public static final int ANALOG_ULTRASONIC_RIGHT = 1;

}
