package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * Created by jxlewis on 1/21/17.
 */
public class DeliverGearDriveCommand extends Command {

  @Override
  protected void execute() {
    Robot.driveSubsystem.mechanumDriveFromCommandedRates();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
