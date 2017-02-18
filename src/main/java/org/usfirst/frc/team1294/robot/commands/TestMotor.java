package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * Created by jxlewis on 2/12/17.
 */
public class TestMotor extends Command {

  private final Motor motor;

  public TestMotor(Motor motor) {
    super("TestMotor " + motor.toString());
    this.motor = motor;
    requires(Robot.driveSubsystem);
    setTimeout(1);
  }

  public enum Motor {
    DRIVEBASE_LEFT_FRONT,
    DRIVEBASE_LEFT_REAR,
    DRIVEBASE_RIGHT_FRONT,
    DRIVEBASE_RIGHT_REAR
  }

  @Override
  protected void execute() {
    switch(motor) {
      case DRIVEBASE_LEFT_FRONT:
        Robot.driveSubsystem.setLeftFrontTalon(0.25);
        break;
      case DRIVEBASE_LEFT_REAR:
        Robot.driveSubsystem.setLeftRearTalon(0.25);
        break;
      case DRIVEBASE_RIGHT_FRONT:
        Robot.driveSubsystem.setRightFrontTalon(0.25);
        break;
      case DRIVEBASE_RIGHT_REAR:
        Robot.driveSubsystem.setRightRearTalon(0.25);
        break;
    }
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    Robot.driveSubsystem.stop();
  }
}
