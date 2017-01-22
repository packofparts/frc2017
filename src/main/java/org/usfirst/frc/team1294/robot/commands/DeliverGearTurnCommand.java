package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * Created by jxlewis on 1/21/17.
 */
public class DeliverGearTurnCommand extends PIDCommand {

  private static final double TOLERANCE = 2.0f;
  private static final double KP = 0.3f;
  private static final double KI = 0;
  private static final double KD = 0;

  public DeliverGearTurnCommand() {
    super("DeliverGearTurnCommand", KP, KI, KD);
    getPIDController().setAbsoluteTolerance(TOLERANCE);
    getPIDController().setInputRange(-180, 180);
    getPIDController().setOutputRange(-1, 1);
    getPIDController().setSetpoint(0);
    SmartDashboard.putData("turnpid", getPIDController());
  }

  @Override
  protected double returnPIDInput() {
    return Robot.driveSubsystem.getAngleToWall();
  }

  @Override
  protected void usePIDOutput(double output) {
    Robot.driveSubsystem.setCommandedTurnRate(output);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  public boolean onTarget() {
    return getPIDController().onTarget();
  }
}
