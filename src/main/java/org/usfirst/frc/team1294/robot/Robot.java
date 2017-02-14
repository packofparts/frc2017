
package org.usfirst.frc.team1294.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1294.robot.commands.*;
import org.usfirst.frc.team1294.robot.subsystems.ClimbingSubsystem;
import org.usfirst.frc.team1294.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1294.robot.subsystems.FuelSubsystem;
import org.usfirst.frc.team1294.robot.subsystems.SpatialAwarenessSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	public static DriveSubsystem driveSubsystem;
	public static SpatialAwarenessSubsystem spatialAwarenessSubsystem;
    public static ClimbingSubsystem climbingSubsystem;
    public static FuelSubsystem fuelSubsystem;


	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// THESE MUST BE INITIALIZED FIRST
		driveSubsystem = new DriveSubsystem();
		spatialAwarenessSubsystem = new SpatialAwarenessSubsystem();
        climbingSubsystem = new ClimbingSubsystem();
        fuelSubsystem = new FuelSubsystem();
		oi = new OI();

		chooser.addDefault("Auto Gear Center", new AutoGearCenter());
		chooser.addObject("Auto Gear Left", new AutoGearLeft());
		chooser.addObject("Auto Gear Right", new AutoGearRight());
		SmartDashboard.putData("Auto mode", chooser);

		SmartDashboard.putData(new TestMotor(TestMotor.Motor.DRIVEBASE_LEFT_FRONT));
		SmartDashboard.putData(new TestMotor(TestMotor.Motor.DRIVEBASE_LEFT_REAR));
		SmartDashboard.putData(new TestMotor(TestMotor.Motor.DRIVEBASE_RIGHT_FRONT));
		SmartDashboard.putData(new TestMotor(TestMotor.Motor.DRIVEBASE_RIGHT_REAR));

		SmartDashboard.putData(new ResetGyroCommand());

		SmartDashboard.putData(new TestDriveBaseBreakInCommand());

		SmartDashboard.putData(new DeliverGearCommand());


		SmartDashboard.putData(Scheduler.getInstance());
		SmartDashboard.putData(driveSubsystem);
		SmartDashboard.putData(spatialAwarenessSubsystem);
		SmartDashboard.putData(climbingSubsystem);
		SmartDashboard.putData(fuelSubsystem);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		SmartDashboard.putNumber("Heading", spatialAwarenessSubsystem.getHeading());
		SmartDashboard.putNumber("VelocityZ", spatialAwarenessSubsystem.getRate());
		SmartDashboard.putNumber("getEncoderX", driveSubsystem.getEncoderX());
		SmartDashboard.putNumber("getEncoderY", driveSubsystem.getEncoderY());
		SmartDashboard.putNumber("UltrasonicLeft", spatialAwarenessSubsystem.getLeftUltrasonicDistance());
    SmartDashboard.putNumber("UltrasonicRight", spatialAwarenessSubsystem.getRightUltrasonicDistance());
    SmartDashboard.putNumber("UltrasonicAverage", spatialAwarenessSubsystem.getAverageUltrasonicDistance());

		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
