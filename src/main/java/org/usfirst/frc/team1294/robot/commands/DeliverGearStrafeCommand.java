package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Not intended for standalone use. Must be used as part of the DeliverGearCommand CommandGroup.
 * Sets the x rate in the parent CommandGroup.
 */
public class DeliverGearStrafeCommand extends PIDCommand {
  private static final double STRAFE_TOLERANCE = 0.5f;
  private static final double STRAFE_KP = 1.0f;
  private static final double STRAFE_KI = 0;
  private static final double STRAFE_KD = 0;
  private static final double MAXIMUM_OUTPUT = 1;

  private final DeliverGearCommand parent;

  public DeliverGearStrafeCommand(DeliverGearCommand parent) {
    super("DeliverGearStrafeCommand", STRAFE_KP, STRAFE_KI, STRAFE_KD);
    this.parent = parent;
    getPIDController().setAbsoluteTolerance(STRAFE_TOLERANCE);
    getPIDController().setOutputRange(-MAXIMUM_OUTPUT, MAXIMUM_OUTPUT);
    getPIDController().setSetpoint(0);

    SmartDashboard.putData("DeliverGearStrafeCommandPID", getPIDController());
  }

  @Override
  protected double returnPIDInput() {
    // todo : math goes here
    // calculate the number of meters that the robot should strafe to the left or right
    // such that if the robot were pointing directly at the vision target, that the difference
    // between the two ultrasonic sensors should read zero.
    return 0;
  }

  @Override
  protected void usePIDOutput(double output) {
    parent.setxRate(output);
  }

  @Override
  protected boolean isFinished() {
    // never finishes by itself, depends on the parent CommandGroup to do that
    return false;
  }

  public boolean onTarget() {
    return getPIDController().onTarget();
  }
}
