package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * Created by jxlewis on 1/21/17.
 */
public class DeliverGearStrafeCommand extends PIDCommand {
  private static final double STRAFE_TOLERANCE = 0.5f;
  private static final double STRAFE_KP = 1.0f;
  private static final double STRAFE_KI = 0;
  private static final double STRAFE_KD = 0;

  public DeliverGearStrafeCommand() {
    super("DeliverGearStrafeCommand", STRAFE_KP, STRAFE_KI, STRAFE_KD);
    getPIDController().setAbsoluteTolerance(STRAFE_TOLERANCE);
    getPIDController().setInputRange(-160, 160);
    getPIDController().setOutputRange(-0.25, 0.25);
    getPIDController().setSetpoint(0);
    SmartDashboard.putData("strafepid", getPIDController());
  }

  @Override
  protected double returnPIDInput() {
    Robot.cameraSubsystem.doVisionProcessingOnGearCamera();
    if (Robot.cameraSubsystem.isGearTargetAcquired()) {
      return -Robot.cameraSubsystem.getGearTargetPixelsFromCenter();
    } else {
      return 0;
    }
  }

  @Override
  protected void usePIDOutput(double output) {
    Robot.driveSubsystem.setCommandedStrafeRate(output);
  }

  @Override
  protected boolean isFinished() {
    return false;
    //return getPIDController().onTarget();
  }

  public boolean onTarget() {
    return getPIDController().onTarget();
  }
}
