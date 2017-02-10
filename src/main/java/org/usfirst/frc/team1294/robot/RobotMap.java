package org.usfirst.frc.team1294.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static final int XBOX_CONTROLLER = 2;

    public static final int DRIVEBASE_LEFT_FRONT_TALON = 1;
    public static final int DRIVEBASE_LEFT_REAR_TALON = 2;
    public static final int DRIVEBASE_RIGHT_FRONT_TALON = 4;
    public static final int DRIVEBASE_RIGHT_REAR_TALON = 3;
    public static final double RAMP_RATE = 48.;
}
