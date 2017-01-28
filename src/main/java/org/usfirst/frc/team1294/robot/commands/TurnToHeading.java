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

    private static final double kP = 5.;
    private static final double kI = 0.00;
    private static final double kD = 1.;

    private final double kToleranceDegrees = 5.f;

    public TurnToHeading(double heading) {
        super("turn to " + heading, kP, kI, kD);
        requires(Robot.driveSubsystem);
        this.heading = heading;
        this.getPIDController().setInputRange(0.f, 360.f);
        this.getPIDController().setOutputRange(-1.0, 1.0);
        this.getPIDController().setAbsoluteTolerance(kToleranceDegrees);
        this.getPIDController().setContinuous(true);
        setSetpoint(heading);
//        SmartDashboard.putData("TurnToHeadingPid", getPIDController());
      System.out.println("TurnToHeading" + heading + " constructor");
    }

    @Override
    protected void initialize() {
      System.out.println("TurnToHeading" + heading + " init");
    }

    @Override
    protected void execute() {

      System.out.println("TurnToHeading" + heading + " execute");
    }

    @Override
    protected boolean isFinished() {

//      System.out.println("TurnToHeading" + heading + " isFinished " + getPIDController().onTarget() + " " + getPIDController().getAvgError());
//        return this.getPIDController().onTarget();
      return false;
    }


    @Override
    protected double returnPIDInput() {

      System.out.println("TurnToHeading" + heading + " pidin");
        return Robot.driveSubsystem.getAngle();
    }

    @Override
    protected void usePIDOutput(double output) {

      System.out.println("TurnToHeading" + heading + " pidout " + output);
        Robot.driveSubsystem.mecanumDrive(0, 0, output, Robot.driveSubsystem.getAngle());
    }

    @Override
    protected void end() {
//        getPIDController().disable();

      System.out.println("TurnToHeading" + heading + " end");
      getPIDController().reset();
      System.out.println("TurnToHeading" + heading + " reset");
    }

    @Override
    protected void interrupted() {

      System.out.println("TurnToHeading" + heading + " interrupt");
        end();
    }
}
