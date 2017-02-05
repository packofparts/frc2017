package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * Created by root on 2/4/17.
 */
public class DriveGyroCorrect extends PIDCommand{

    private static final double P = 0.1;
    private static final double I = 0f;
    private static  final double D = 0f;
    private static final double TOLERANCE = 2.;
    private static final double DEADZONE = 0.05f;
    private static final double constantMultiplier = 1.5; //adjust this and DEADZONE as the need arises
    private static double left;
    private static double right;


    public DriveGyroCorrect(){
        super("DriveGyroCorrect", P, I, D);
        requires(Robot.driveSubsystem);
        getPIDController().setAbsoluteTolerance(TOLERANCE);
        getPIDController().setInputRange(-5, 5);
        getPIDController().setOutputRange(-1, 1);
        SmartDashboard.putData("DriveGyroCorrectPID", getPIDController());
        SmartDashboard.putNumber("deadzone", DEADZONE);
    }

    @Override
    protected void execute() {
        left = Robot.oi.getJoystick().getTriggerAxis(GenericHID.Hand.kLeft);
        right = Robot.oi.getJoystick().getTriggerAxis(GenericHID.Hand.kRight);
        if(Math.abs(right - left) < SmartDashboard.getNumber("deadzone", DEADZONE)) {
            getPIDController().setSetpoint(0.);
        } else {
            getPIDController().setSetpoint((right - left) * constantMultiplier);
            System.out.println("jbl: " + ((right - left) * constantMultiplier));
        }
        System.out.printf("left: %.2f // right: %.2f // right - left: %.2f%n", left, right,
                right - left);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected double returnPIDInput() {
        return Robot.driveSubsystem.getRate();
    }

    @Override
    protected void usePIDOutput(double output) {
        XboxController joystick = Robot.oi.getJoystick();
        double absXL = Math.abs(joystick.getX(GenericHID.Hand.kLeft));
        absXL = absXL < DEADZONE ? 0 : absXL;
        double absXR = Math.abs(joystick.getX(GenericHID.Hand.kRight));
        absXR = absXR < DEADZONE ? 0 : absXR;
        double abxYL = Math.abs(joystick.getY(GenericHID.Hand.kLeft));
        abxYL = abxYL < DEADZONE ? 0 : abxYL;
        double absYR = Math.abs(joystick.getY(GenericHID.Hand.kRight));
        absYR = absYR < DEADZONE ? 0 : absYR;
        if (absXL > absXR
                || abxYL > absYR) {
            Robot.driveSubsystem.mecanumDrive(joystick.getX(GenericHID.Hand.kLeft),
                    joystick.getY(GenericHID.Hand.kLeft),
                    output,
                    Robot.driveSubsystem.getAngle());
//            System.out.println("FIELD ORIENTED");
        } else {
            // otherwise use the right analog stick for robot oriented
            Robot.driveSubsystem.mecanumDrive(joystick.getX(GenericHID.Hand.kRight),
                    joystick.getY(GenericHID.Hand.kRight),
                    output,
                    0);
//            System.out.println("ROBOT ORIENTED");
        }
        System.out.println(output);
    }
}
