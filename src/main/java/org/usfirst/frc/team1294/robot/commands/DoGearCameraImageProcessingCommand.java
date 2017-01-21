package org.usfirst.frc.team1294.robot.commands;

import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the gear camera image processing once
 */
public class DoGearCameraImageProcessingCommand extends Command {

  private boolean hasRunOnce = false;

  public DoGearCameraImageProcessingCommand() {
    super("DoGearCameraImageProcessingCommand");
    requires(Robot.cameraSubsystem);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.cameraSubsystem.doVisionProcessingOnGearCamera();
    Robot.cameraSubsystem.saveLastImage();
    hasRunOnce = true;
  }

  @Override
  protected void end() {

  }

  @Override
  protected void interrupted() {
    end();
  }

  @Override
  protected boolean isFinished() {
    return hasRunOnce;
  }
}
