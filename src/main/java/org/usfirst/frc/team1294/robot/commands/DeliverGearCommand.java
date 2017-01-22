package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1294.robot.Robot;
import org.usfirst.frc.team1294.robot.util.SimplePIDSource;

/**
 * A command that moves the robot from a position near the lift to where the pilot can
 * use the lift to retrieve the gear. Uses ultrasonic distance transducers to keep the
 * lift squared up with the gear side of the robot. Uses vision system to move the robot
 * side to side to keep the lift centered. Will time out after 10 seconds.
 */
public class DeliverGearCommand extends CommandGroup {

  private final DeliverGearApproachCommand deliverGearApproachCommand;
  private DeliverGearStrafeCommand deliverGearStrafeCommand;
  private DeliverGearDriveCommand deliverGearDriveCommand;
  private DeliverGearTurnCommand deliverGearTurnCommand;

  public DeliverGearCommand() {
    requires(Robot.driveSubsystem);
    requires(Robot.cameraSubsystem);

    deliverGearStrafeCommand = new DeliverGearStrafeCommand();
    deliverGearApproachCommand = new DeliverGearApproachCommand();
    deliverGearTurnCommand = new DeliverGearTurnCommand();
    deliverGearDriveCommand = new DeliverGearDriveCommand();

    addParallel(deliverGearStrafeCommand);
    addParallel(deliverGearApproachCommand);
    addParallel(deliverGearTurnCommand);
    addParallel(deliverGearDriveCommand);
  }

  @Override
  protected void initialize() {

  }

  @Override
  protected boolean isFinished() {
    return deliverGearStrafeCommand.onTarget() && deliverGearTurnCommand.onTarget() && deliverGearApproachCommand.onTarget();
  }

}
