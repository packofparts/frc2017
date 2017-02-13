package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * Not intended for standalone use. Must be used as part of the DeliverGearCommand CommandGroup.
 * Drives the robot based on the rates currently set in the parent ComandGroup.
 */
public class DeliverGearDriveCommand extends Command {

  public DeliverGearDriveCommand() {
    requires(Robot.driveSubsystem);
  }

  @Override
  protected void execute() {
    double xRate = 0;
    double yRate = 0;
    double zRate = 0;

    CommandGroup group = getGroup();
    if (group instanceof DeliverGearCommand) {
      xRate = ((DeliverGearCommand) group).getxRate();
      yRate = ((DeliverGearCommand) group).getyRate();
      zRate = ((DeliverGearCommand) group).getzRate();
    }

    Robot.driveSubsystem.mecanumDrive(xRate, yRate, zRate, 0);
  }

  @Override
  protected boolean isFinished() {
    // never finishes by itself, depends on the parent CommandGroup to do that
    return false;
  }
}
