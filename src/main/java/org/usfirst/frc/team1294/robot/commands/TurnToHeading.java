package org.usfirst.frc.team1294.robot.commands;

import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * A command that turns the robot to a particular heading relative to the field.
 */
public class TurnToHeading extends PIDCommand {

    private final double heading;

    private static final double kP = 0.01;
    private static final double kI = 0.00;
    private static final double kD = 0.00;
    private static final double MAX_RATE = 0.25;

    private final double kToleranceDegrees = 5.f;

    private boolean hasRunReturnPidInputAtLeastOnce;

    public TurnToHeading(double heading) {
        super("turn to " + heading, kP, kI, kD);
        requires(Robot.driveSubsystem);
        this.heading = heading;
        getPIDController().setInputRange(0.f, 360.f);
        getPIDController().setOutputRange(-MAX_RATE, MAX_RATE);
        getPIDController().setAbsoluteTolerance(kToleranceDegrees);
        getPIDController().setContinuous(true);
        setSetpoint(heading);
    }

    @Override
    protected void initialize() {
      hasRunReturnPidInputAtLeastOnce = false;
    }

    @Override
    protected boolean isFinished() {
        return this.getPIDController().onTarget()
                && hasRunReturnPidInputAtLeastOnce
                && Math.abs(Robot.driveSubsystem.getRate()) <= 0.3;
    }


    @Override
    protected double returnPIDInput() {
      if (!hasRunReturnPidInputAtLeastOnce) hasRunReturnPidInputAtLeastOnce = true;
      return Robot.driveSubsystem.getAngle();
    }

    @Override
    protected void usePIDOutput(double output) {
      Robot.driveSubsystem.mecanumDrive(0, 0, output, Robot.driveSubsystem.getAngle());
    }
}
