package org.usfirst.frc.team1294.robot.commands;

import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FeederCommand extends Command {
  private static double waitTime = 0.5; //time to wait in seconds
  private static double shootTime = 0.25; //time to run the motor for
  private static final double feederMotorVoltage = -9.0; //speed to run the motor at
  private final Timer timer;

  private boolean ableToShoot;
  private boolean shooting;

  public FeederCommand() {
    timer = new Timer();
    SmartDashboard.putNumber("shooter wait time", waitTime);
    SmartDashboard.putNumber("shooter shoot time", shootTime);
  }

  @Override
  protected void initialize() {
    shooting = true;
    ableToShoot = Robot.fuelSubsystem.isMotorARunning() && Robot.fuelSubsystem.isMotorBRunning();
    timer.start();
    waitTime = SmartDashboard.getNumber("shooter wait time", waitTime);
    shootTime = SmartDashboard.getNumber("shooter shoot time", shootTime);
  }

  @Override
  protected boolean isFinished() {
    return !ableToShoot || (!shooting && !Robot.oi.getJoystick2().getAButton());
  }

  @Override
  protected void execute() {
    if (ableToShoot) {
      if (shooting) {
        if (timer.hasPeriodPassed(shootTime)) {
          // stop shooting
          shooting = false;
          timer.reset();
          Robot.fuelSubsystem.setFeederMotorVoltageSpeed(0.0);
        } else {
          // keep shooting
          shooting = true;
          Robot.fuelSubsystem.setFeederMotorVoltageSpeed(feederMotorVoltage);
        }
      } else {
        if (timer.hasPeriodPassed(waitTime)) {
          // start shooting
          shooting = true;
          timer.reset();
          Robot.fuelSubsystem.setFeederMotorVoltageSpeed(feederMotorVoltage);
        } else {
          // stay stopped
          shooting = false;
          Robot.fuelSubsystem.setFeederMotorVoltageSpeed(0.0);
        }
      }
    }
  }

  @Override
  protected void end() {
    timer.stop();
    Robot.fuelSubsystem.setFeederMotorVoltageSpeed(0.0);
  }
}

