package org.usfirst.frc.team1294.robot.commands;

import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Austin Jenchi (timtim17)
 */
public class DriveStraightDriveCommand extends Command {

  public DriveStraightDriveCommand() {
    requires(Robot.driveSubsystem);
  }

  @Override
  protected void execute() {
    double xRate = 0;
    double yRate = 0;
    double zRate = 0;

    CommandGroup group = getGroup();
    if (group instanceof DriveStraightCommand) {
      xRate = ((DriveStraightCommand) group).getxRate();
      yRate = ((DriveStraightCommand) group).getyRate();
      zRate = ((DriveStraightCommand) group).getzRate();
    }

    Robot.driveSubsystem.mecanumDrive(xRate, yRate, zRate, 0);
  }

  @Override
  protected boolean isFinished() {
    // never finishes by itself, depends on the parent CommandGroup to do that
    return false;
  }
}
