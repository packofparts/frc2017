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
    Robot.cameraSubsystem.setFPS(10);
  }

  @Override
  protected void execute() {
    Robot.cameraSubsystem.doVisionProcessingOnGearCamera();
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
