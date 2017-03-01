package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * A command that turns the robot to a particular heading relative to the field.
 */
public class TurnToHeading extends PIDCommand {

    private final double heading;

    private static final double kP = 0.05;
    private static final double kI = 0.00;
    private static final double kD = 0.00;
    private static final double MAX_RATE = 0.25;

    private final double kToleranceDegrees = 5.f;

    private boolean hasRunReturnPidInputAtLeastOnce;

    public TurnToHeading(double heading) {
        super("turn to " + heading, kP, kI, kD);
        requires(Robot.driveSubsystem);
        this.heading = heading;
        getPIDController().setOutputRange(-MAX_RATE, MAX_RATE);
        getPIDController().setAbsoluteTolerance(kToleranceDegrees);
        getPIDController().setContinuous(true);
        setSetpoint(heading);
        SmartDashboard.putData("TurnToHeadingPID", getPIDController());
    }

    @Override
    protected void initialize() {
      hasRunReturnPidInputAtLeastOnce = false;
    }

    @Override
    protected boolean isFinished() {
        return this.getPIDController().onTarget()
                && hasRunReturnPidInputAtLeastOnce
                && Math.abs(Robot.spatialAwarenessSubsystem.getRate()) <= 0.3;
    }


    @Override
    protected double returnPIDInput() {
      if (!hasRunReturnPidInputAtLeastOnce) hasRunReturnPidInputAtLeastOnce = true;
      return Robot.spatialAwarenessSubsystem.getHeading();
    }

    @Override
    protected void usePIDOutput(double output) {
      Robot.driveSubsystem.mecanumDrive(0, 0, output, Robot.spatialAwarenessSubsystem.getHeading());
    }
}
