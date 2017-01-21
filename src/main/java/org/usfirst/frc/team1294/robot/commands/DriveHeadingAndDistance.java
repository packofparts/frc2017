package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.opencv.core.Mat;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * a command that will drive the robot on the specified heading until the
 * specified distance had been traveled. Uses the encoder output from the
 * distance measuring wheels not the drive wheels.
 */
public class DriveHeadingAndDistance extends PIDCommand {

    private final double heading;
    private final double distanceInMeters;
//    private final double MAX_SPEED = 1.0;
    private static double distanceX;
    private static double distanceY;

    public DriveHeadingAndDistance(double heading, double distanceInMeters, double kP, double kI, double kD) {
        super(kP, kI, kD);
        Robot.driveSubsystem.resetEncoder();
        requires(Robot.driveSubsystem);
        this.heading = heading;
        this.distanceInMeters = distanceInMeters;
        distanceX = Math.cos(heading);
        distanceY = Math.sin(heading);
    }

    @Override
    protected void execute() {
        super.execute();
        Robot.driveSubsystem.mecanumDrive(distanceX, distanceY, heading, Robot.driveSubsystem.getAngle());
    }

    @Override
    protected boolean isFinished() {
        return this.getPIDController().onTarget();
    }

    @Override
    protected double returnPIDInput() {
        return distanceInMeters - Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
    }


    @Override
    protected void usePIDOutput(double output) {
        distanceY *= output;
        distanceX *= output;
    }
}
