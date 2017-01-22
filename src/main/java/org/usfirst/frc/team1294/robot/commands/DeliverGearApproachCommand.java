package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * Created by jxlewis on 1/21/17.
 */
public class DeliverGearApproachCommand extends PIDCommand {

  private static final double TOLERANCE = 0.5f;
  private static final double KP = 0.3f;
  private static final double KI = 0;
  private static final double KD = 0;

  public DeliverGearApproachCommand() {
    super("DeliverGearApproachCommand", KP, KI, KD);
    getPIDController().setAbsoluteTolerance(TOLERANCE);
    getPIDController().setInputRange(0, 1.0); // 0 meters to 1 meters
    getPIDController().setOutputRange(-0.25, 0.25);
    getPIDController().setSetpoint(0);
    SmartDashboard.putData("strafepid", getPIDController());
  }

  @Override
  protected double returnPIDInput() {
    return Robot.driveSubsystem.getDistanceToWall();
  }

  @Override
  protected void usePIDOutput(double output) {
    Robot.driveSubsystem.setCommandedApproachRate(output);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  public boolean onTarget() {
    return getPIDController().onTarget();
  }
}
