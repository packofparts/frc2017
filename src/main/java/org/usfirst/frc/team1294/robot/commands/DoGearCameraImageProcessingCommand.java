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
    requires(Robot.spatialAwarenessSubsystem);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.spatialAwarenessSubsystem.doVisionProcessingOnGearCamera();
    Robot.spatialAwarenessSubsystem.saveLastImage();
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
