
package org.usfirst.frc.team1294.robot;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1294.robot.commands.DriveMotorCommand;
import org.usfirst.frc.team1294.robot.commands.ExampleCommand;
import org.usfirst.frc.team1294.robot.commands.MecanumDriveCommand;
import org.usfirst.frc.team1294.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1294.robot.subsystems.ExampleSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	public static DriveSubsystem driveSubsystem;
	public static AHRS navX;  // NOT command based

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);

		// this is NOT valid command based code, but it will do for testing
		CANTalon testTalon = new CANTalon(1);
		navX = new AHRS(SerialPort.Port.kMXP);
		driveSubsystem = new DriveSubsystem();

		SmartDashboard.putData(new MecanumDriveCommand());
		SmartDashboard.putData(new DriveMotorCommand());
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
		Scheduler.getInstance().run();
		
		// Again, NOT good command based code, but just for testing
		// Code from navX data monitoring example
		// http://www.pdocs.kauailabs.com/navx-mxp/examples/data-monitor/
		/* Display 6-axis Processed Angle Data                                      */
		SmartDashboard.putBoolean(  "IMU_Connected",        navX.isConnected());
		SmartDashboard.putBoolean(  "IMU_IsCalibrating",    navX.isCalibrating());
		SmartDashboard.putNumber(   "IMU_Yaw",              navX.getYaw());
		SmartDashboard.putNumber(   "IMU_Pitch",            navX.getPitch());
		SmartDashboard.putNumber(   "IMU_Roll",             navX.getRoll());

          /* Display tilt-corrected, Magnetometer-based heading (requires             */
          /* magnetometer calibration to be useful)                                   */

		SmartDashboard.putNumber(   "IMU_CompassHeading",   navX.getCompassHeading());

          /* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
		SmartDashboard.putNumber(   "IMU_FusedHeading",     navX.getFusedHeading());

          /* These functions are compatible w/the WPI Gyro Class, providing a simple  */
          /* path for upgrading from the Kit-of-Parts gyro to the navx-MXP            */

		SmartDashboard.putNumber(   "IMU_TotalYaw",         navX.getAngle());
		SmartDashboard.putNumber(   "IMU_YawRateDPS",       navX.getRate());

          /* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */

		SmartDashboard.putNumber(   "IMU_Accel_X",          navX.getWorldLinearAccelX());
		SmartDashboard.putNumber(   "IMU_Accel_Y",          navX.getWorldLinearAccelY());
		SmartDashboard.putBoolean(  "IMU_IsMoving",         navX.isMoving());
		SmartDashboard.putBoolean(  "IMU_IsRotating",       navX.isRotating());

          /* Display estimates of velocity/displacement.  Note that these values are  */
          /* not expected to be accurate enough for estimating robot position on a    */
          /* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
          /* of these errors due to single (velocity) integration and especially      */
          /* double (displacement) integration.                                       */

		SmartDashboard.putNumber(   "Velocity_X",           navX.getVelocityX());
		SmartDashboard.putNumber(   "Velocity_Y",           navX.getVelocityY());
		SmartDashboard.putNumber(   "Displacement_X",       navX.getDisplacementX());
		SmartDashboard.putNumber(   "Displacement_Y",       navX.getDisplacementY());

          /* Display Raw Gyro/Accelerometer/Magnetometer Values                       */
          /* NOTE:  These values are not normally necessary, but are made available   */
          /* for advanced users.  Before using this data, please consider whether     */
          /* the processed data (see above) will suit your needs.                     */

		SmartDashboard.putNumber(   "RawGyro_X",            navX.getRawGyroX());
		SmartDashboard.putNumber(   "RawGyro_Y",            navX.getRawGyroY());
		SmartDashboard.putNumber(   "RawGyro_Z",            navX.getRawGyroZ());
		SmartDashboard.putNumber(   "RawAccel_X",           navX.getRawAccelX());
		SmartDashboard.putNumber(   "RawAccel_Y",           navX.getRawAccelY());
		SmartDashboard.putNumber(   "RawAccel_Z",           navX.getRawAccelZ());
		SmartDashboard.putNumber(   "RawMag_X",             navX.getRawMagX());
		SmartDashboard.putNumber(   "RawMag_Y",             navX.getRawMagY());
		SmartDashboard.putNumber(   "RawMag_Z",             navX.getRawMagZ());
		SmartDashboard.putNumber(   "IMU_Temp_C",           navX.getTempC());

          /* Omnimount Yaw Axis Information                                           */
          /* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount  */
		AHRS.BoardYawAxis yaw_axis = navX.getBoardYawAxis();
		SmartDashboard.putString(   "YawAxisDirection",     yaw_axis.up ? "Up" : "Down" );
		SmartDashboard.putNumber(   "YawAxis",              yaw_axis.board_axis.getValue() );
//
//          /* Sensor Board Information                                                 */
		SmartDashboard.putString(   "FirmwareVersion",      navX.getFirmwareVersion());
//
//          /* Quaternion Data                                                          */
//          /* Quaternions are fascinating, and are the most compact representation of  */
//          /* orientation data.  All of the Yaw, Pitch and Roll Values can be derived  */
//          /* from the Quaternions.  If interested in motion processing, knowledge of  */
//          /* Quaternions is highly recommended.                                       */
		SmartDashboard.putNumber(   "QuaternionW",          navX.getQuaternionW());
		SmartDashboard.putNumber(   "QuaternionX",          navX.getQuaternionX());
		SmartDashboard.putNumber(   "QuaternionY",          navX.getQuaternionY());
		SmartDashboard.putNumber(   "QuaternionZ",          navX.getQuaternionZ());
//
//          /* Connectivity Debugging Support                                           */
		SmartDashboard.putNumber(   "IMU_Byte_Count",       navX.getByteCount());
		SmartDashboard.putNumber(   "IMU_Update_Count",     navX.getUpdateCount());
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
