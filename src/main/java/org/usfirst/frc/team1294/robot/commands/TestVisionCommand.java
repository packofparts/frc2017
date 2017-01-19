package org.usfirst.frc.team1294.robot.commands;

import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Austin Jenchi (timtim17)
 */
public class TestVisionCommand extends Command {
  public TestVisionCommand() {
    super("TestVisionCommand");
    requires(Robot.cameraSubsystem);
  }

  @Override
  protected void initialize() {
    Robot.cameraSubsystem.doVisionProcessingOnGearCamera();
  }

  @Override
  protected void execute() {

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
    return true;
  }
}
