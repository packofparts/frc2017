package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * A command that turns the robot to a particular heading relative to the field.
 */
public class TurnToHeading extends Command {

    private final double heading;

    public TurnToHeading(double heading) {
        requires(Robot.driveSubsystem);
        this.heading = heading;
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
