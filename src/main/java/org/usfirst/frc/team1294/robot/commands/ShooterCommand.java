package org.usfirst.frc.team1294.robot.commands;

import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterCommand extends Command {
  public ShooterCommand() {
    setTimeout(15);
  }

  @Override
  protected void execute() {
    Robot.fuelSubsystem.setShooterMotorBVoltageSpeed(10);
    Robot.fuelSubsystem.setShooterMotorAVoltageSpeed(8);
  }

  protected void end() {
    Robot.fuelSubsystem.setShooterMotorBVoltageSpeed(0);
    Robot.fuelSubsystem.setShooterMotorAVoltageSpeed(0);
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

}
