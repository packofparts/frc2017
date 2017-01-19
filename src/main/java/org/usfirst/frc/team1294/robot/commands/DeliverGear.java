package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * A command that moves the robot from a position near the lift to where the pilot can
 * use the lift to retrieve the gear. Uses ultrasonic distance transducers to keep the
 * lift squared up with the gear side of the robot. Uses vision system to move the robot
 * side to side to keep the lift centered. Will time out after 10 seconds.
 */
public class DeliverGear extends Command {

    public DeliverGear() {
        requires(Robot.driveSubsystem);
        requires(Robot.cameraSubsystem);
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
