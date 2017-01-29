package org.usfirst.frc.team1294.robot.commands;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1294.robot.Robot;
import org.usfirst.frc.team1294.robot.RobotMap;
import org.usfirst.frc.team1294.robot.subsystems.DriveSubsystem;

/**
 * A command that turns the robot to a particular heading relative to the field.
 */
public class TurnToHeading extends PIDCommand {

    private final double heading;

    private static final double kP = 0.04;
    private static final double kI = 0.00;
    private static final double kD = 0.075;

    private final double kToleranceDegrees = 5.f;

    private boolean hasRunReturnPidInputAtLeastOnce;

    public TurnToHeading(double heading) {
        super("turn to " + heading, kP, kI, kD);
        requires(Robot.driveSubsystem);
        this.heading = heading;
        getPIDController().setInputRange(0.f, 360.f);
        getPIDController().setOutputRange(-1., 1.);
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
