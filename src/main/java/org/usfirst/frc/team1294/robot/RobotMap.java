package org.usfirst.frc.team1294.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static final int XBOX_CONTROLLER = 2;
    public static final int XBOX_CONTROLLER2 = 1;

    public static final int DRIVEBASE_LEFT_FRONT_TALON = 1;
    public static final int DRIVEBASE_LEFT_REAR_TALON = 2;
    public static final int DRIVEBASE_RIGHT_FRONT_TALON = 4;
    public static final int DRIVEBASE_RIGHT_REAR_TALON = 3;
    public static final int FUEL_SHOOTER_MOTOR_A = 5;
    public static final int FUEL_SHOOTER_MOTOR_B = 6;
    public static final int FUEL_FEEDER_MOTOR = 7;
    public static final double RAMP_RATE = 48.;
    public static final int CLIMBING_TALON = 8;
    public static final int ANALOG_ULTRASONIC_LEFT = 0;
    public static final int ANALOG_ULTRASONIC_RIGHT = 1;
}
