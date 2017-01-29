package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * A command that moves the robot from a position near the lift to where the pilot can
 * use the lift to retrieve the gear.
 */
public class DeliverGearCommand extends CommandGroup {

  private final DeliverGearStrafeCommand deliverGearStrafeCommand;
  private final DeliverGearTurnCommand deliverGearTurnCommand;
  private final DeliverGearApproachCommand deliverGearApproachCommand;
  private final DeliverGearDriveCommand deliverGearDriveCommand;

  private double xRate;
  private double yRate;
  private double zRate;

  public DeliverGearCommand() {
    deliverGearStrafeCommand = new DeliverGearStrafeCommand();
    deliverGearTurnCommand = new DeliverGearTurnCommand();
    deliverGearApproachCommand = new DeliverGearApproachCommand();
    deliverGearDriveCommand = new DeliverGearDriveCommand();

    addParallel(deliverGearStrafeCommand);
    addParallel(deliverGearTurnCommand);
    addParallel(deliverGearApproachCommand);
    addParallel(deliverGearDriveCommand);

    setTimeout(15);
  }

  @Override
  protected boolean isFinished() {
    return deliverGearStrafeCommand.onTarget() && deliverGearTurnCommand.onTarget() && deliverGearApproachCommand.onTarget();
  }

  public double getxRate() {
    return xRate;
  }

  public void setxRate(double xRate) {
    this.xRate = xRate;
  }

  public double getyRate() {
    return yRate;
  }

  public void setyRate(double yRate) {
    this.yRate = yRate;
  }

  public double getzRate() {
    return zRate;
  }

  public void setzRate(double zRate) {
    this.zRate = zRate;
  }

}
