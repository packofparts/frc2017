package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * a command to break in the motors of the drive base.
 */
public class DriveBaseBreakInCommand extends Command {
    private Timer timer;

    public DriveBaseBreakInCommand() {
        super("DriveBaseBreakInCommand");
        requires(Robot.driveSubsystem);
    }

    @Override
    protected void initialize() {
        timer = new Timer();
        timer.start();
    }

    @Override
    protected void execute() {
        // tell the robot to drive all motors at various speeds and direction
        Robot.driveSubsystem.mecanumDrive(0, Math.sin(0.31415 * timer.get()), 0, 0);
    }

    @Override
    protected boolean isFinished() {
        return timer.hasPeriodPassed(5 * 60); // 5 minutes
    }
}
