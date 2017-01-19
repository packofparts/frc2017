package org.usfirst.frc.team1294.robot.commands;

import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Austin Jenchi (timtim17)
 */
public class PutGearFrameCommand extends Command {
  public PutGearFrameCommand() {
    super("Put gear frame on camera");
    requires(Robot.cameraSubsystem);
  }

  @Override
  protected void initialize() {
    Robot.cameraSubsystem.setFPS(10);
  }

  @Override
  protected void execute() {
    Robot.cameraSubsystem.doVisionProcessingOnGearCamera();
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
    return false;
  }
}
