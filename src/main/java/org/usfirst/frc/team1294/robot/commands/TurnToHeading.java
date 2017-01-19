package org.usfirst.frc.team1294.robot.commands;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team1294.robot.Robot;
import org.usfirst.frc.team1294.robot.RobotMap;
import org.usfirst.frc.team1294.robot.subsystems.DriveSubsystem;

/**
 * A command that turns the robot to a particular heading relative to the field.
 */
public class TurnToHeading extends PIDCommand {

    private final double heading;

    private static final double kP = 0.03;
    private static final double kI = 0.00;
    private static final double kD = 0.00;
    private static final double kF = 0.00;

    private final double kToleranceDegrees = 2.0f;

    public TurnToHeading(double heading) {
        super(kP, kI, kD);
        requires(Robot.driveSubsystem);
        this.heading = heading;
    }

    @Override
    protected void execute() {
        this.getPIDController().setInputRange(-180.0f,  180.0f);
        this.getPIDController().setOutputRange(-1.0, 1.0);
        this.getPIDController().setAbsoluteTolerance(kToleranceDegrees);
        this.getPIDController().setContinuous(true);
    }

    @Override
    protected boolean isFinished() {
        return this.getPIDController().onTarget();
    }


    @Override
    protected double returnPIDInput() {
        return Robot.driveSubsystem.getAngle();
    }

    @Override
    protected void usePIDOutput(double output) {
        Robot.driveSubsystem.mecanumDrive(0, 0, output, 0);
    }
}
