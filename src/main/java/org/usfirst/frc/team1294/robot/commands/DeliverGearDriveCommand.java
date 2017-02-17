package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * Not intended for standalone use. Must be used as part of the DeliverGearCommand CommandGroup.
 * Drives the robot based on the rates currently set in the parent ComandGroup.
 */
public class DeliverGearDriveCommand extends Command {

  private final DeliverGearCommand parent;

  public DeliverGearDriveCommand(DeliverGearCommand parent) {
    requires(Robot.driveSubsystem);
    this.parent = parent;
  }

  @Override
  protected void execute() {
    double xRate = parent.getxRate();
    double yRate = parent.getyRate();
    double zRate = parent.getzRate();

    Robot.driveSubsystem.mecanumDrive(xRate, yRate, zRate, 0);
  }

  @Override
  protected boolean isFinished() {
    // never finishes by itself, depends on the parent CommandGroup to do that
    return parent.isVisionApproachFinished();
  }
}
