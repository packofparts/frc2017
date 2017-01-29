package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * Not intended for standalone use. Must be used as part of the DeliverGearCommand CommandGroup.
 * Sets the y rate in the parent CommandGroup.
 */
public class DeliverGearApproachCommand extends PIDCommand {

  private static final double TOLERANCE = 0.05f;
  private static final double KP = 0.3f;
  private static final double KI = 0;
  private static final double KD = 0;
  private static final double MAXIMUM_OUTPUT = 0.25;
  private static final double DISTANCE_TO_WALL_SETPOINT = 0.5; // todo determine correct distance to wall

  public DeliverGearApproachCommand() {
    super("DeliverGearApproachCommand", KP, KI, KD);
    getPIDController().setAbsoluteTolerance(TOLERANCE);
    getPIDController().setOutputRange(-MAXIMUM_OUTPUT, MAXIMUM_OUTPUT);
    getPIDController().setSetpoint(DISTANCE_TO_WALL_SETPOINT);
    SmartDashboard.putData("DeliverGearApproachCommandPID", getPIDController());
  }

  @Override
  protected double returnPIDInput() {
    return Robot.driveSubsystem.getDistanceToWall();
  }

  @Override
  protected void usePIDOutput(double output) {
    CommandGroup group = getGroup();
    if (group instanceof DeliverGearCommand) {
      // get the currently commanded x and z rate
      double xRate = ((DeliverGearCommand) group).getxRate();
      double zRate = ((DeliverGearCommand) group).getzRate();

      // only approach the target if x and z are below thresholds
      if (xRate < 0.1 && zRate < 0.1) {
        ((DeliverGearCommand) group).setyRate(output);
      } else {
        ((DeliverGearCommand) group).setyRate(0);
      }
    }


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
