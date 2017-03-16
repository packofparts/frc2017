package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1294.robot.Robot;

import java.util.ArrayList;
import java.util.List;

/**
 * Not intended for standalone use. Must be used as part of the DeliverGearCommand CommandGroup.
 * Sets the y rate in the parent CommandGroup.
 */
public class DeliverGearApproachCommand extends PIDCommand {

  private static final double TOLERANCE = 0.1f;
  private static final double KP = 0.75f;
  private static final double KI = 0.01;
  private static final double KD = 0;
  private static final double MAXIMUM_OUTPUT = 0.2;

  // http://maxbotix.com/documents/LV-MaxSonar-EZ_Datasheet.pdf
  // https://www.adafruit.com/products/980
  // the minimum distance the sensor will read is 6 inches = 0.1524 meters
  private static final double DISTANCE_TO_WALL_SETPOINT = .18; // todo validate this distance to wall
  // might want to move a short distance closer than the minimum ultrasonic distance will allow

  private final DeliverGearCommand parent;
  private final List<Double> distanceList;

  public DeliverGearApproachCommand(DeliverGearCommand parent) {
    super("DeliverGearApproachCommand", KP, KI, KD);
    this.parent = parent;
    getPIDController().setAbsoluteTolerance(TOLERANCE);
    getPIDController().setOutputRange(-MAXIMUM_OUTPUT, MAXIMUM_OUTPUT);
    getPIDController().setSetpoint(DISTANCE_TO_WALL_SETPOINT);
//    SmartDashboard.putData("DeliverGearApproachCommandPID", getPIDController());

    distanceList = new ArrayList<>();
  }

  @Override
  protected double returnPIDInput() {
    distanceList.add(Robot.spatialAwarenessSubsystem.getAverageUltrasonicDistance());
    if (distanceList.size() > 30) {
      distanceList.remove(0);
    }
    return Robot.spatialAwarenessSubsystem.getAverageUltrasonicDistance();// + 0.3;
  }

  @Override
  protected void usePIDOutput(double output) {
    // get the currently commanded x and z rate
    //double xRate = parent.getxRate();
    //double zRate = parent.getzRate();

    // only approach the target if x and z are below thresholds
    if (parent.isVisionTargetAcquired()) { // && xRate < 0.2 && zRate < 0.2) {
      parent.setyRate(output);
    } else {
      parent.setyRate(0);
    }
  }

  @Override
  protected boolean isFinished() {
    // never finishes by itself, depends on the parent CommandGroup to do that
    return false;
  }

  public boolean onTarget() {
    return  getPIDController().onTarget();
  }
}
