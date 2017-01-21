package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * a command that will drive the robot on the specified heading until the
 * specified distance had been traveled. Uses the encoder output from the
 * distance measuring wheels not the drive wheels.
 */
public class DriveHeadingAndDistance extends PIDCommand {

    private final double heading;
    private final double distanceInMeters;
    private final double MAX_SPEED = 1.0;

    public DriveHeadingAndDistance(double heading, double distanceInMeters, double kP, double kI, double kD) {
        super(kP, kI, kD);
        requires(Robot.driveSubsystem);
        this.heading = heading;
        this.distanceInMeters = distanceInMeters;
    }

    @Override
    protected void execute() {
        super.execute();
        Robot.driveSubsystem.mecanumDrive(Math.cos(heading) * distanceInMeters * MAX_SPEED, Math.sin(heading) * distanceInMeters * MAX_SPEED, heading, Robot.driveSubsystem.getAngle());
    }

    @Override
    protected boolean isFinished() {
        return this.getPIDController().onTarget();
    }

    @Override
    protected double returnPIDInput() {
        return Robot.driveSubsystem.getAngle();
    }


    @Override
    protected void usePIDOutput(double output) {
        Robot.driveSubsystem.mecanumDrive(0, 0, output, Robot.driveSubsystem.getAngle());
    }
}
