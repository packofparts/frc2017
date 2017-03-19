package org.usfirst.frc.team1294.robot;

import edu.wpi.first.wpilibj.Compressor;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static final int XBOX_CONTROLLER = 2;
    public static final int XBOX_CONTROLLER2 = 1;

    public static final int TALON_DRIVEBASE_LEFT_FRONT = 1;
    public static final int TALON_DRIVEBASE_LEFT_REAR = 2;
    public static final int TALON_DRIVEBASE_RIGHT_FRONT = 4;
    public static final int TALON_DRIVEBASE_RIGHT_REAR = 3;
    public static final int FUEL_SHOOTER_MOTOR_A = 7;
    public static final int FUEL_SHOOTER_MOTOR_B = 5;
    public static final int FUEL_FEEDER_MOTOR = 6;
    public static final int CLIMBING_TALON = 8;
    public static final int ANALOG_ULTRASONIC_LEFT = 0;
    public static final int ANALOG_ULTRASONIC_RIGHT = 1;
    public static final int GEAR_SOLENOID = 0;
    public static final int COMPRESSOR = 0;
}
