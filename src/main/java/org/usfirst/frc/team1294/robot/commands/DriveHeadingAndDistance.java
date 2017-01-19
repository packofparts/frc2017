package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * a command that will drive the robot on the specified heading until the
 * specified distance had been traveled. Uses the encoder output from the
 * distance measuring wheels not the drive wheels.
 */
public class DriveHeadingAndDistance extends Command {

    private final double heading;
    private final double distanceInMeters;

    public DriveHeadingAndDistance(double heading, double distanceInMeters) {
        requires(Robot.driveSubsystem);

        this.heading = heading;
        this.distanceInMeters = distanceInMeters;
    }

    @Override
    protected void execute() {
        super.execute();
        // TODO: implement this command
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
