package org.usfirst.frc.team1294.robot.subsystems;

import org.usfirst.frc.team1294.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PneumaticGearSubsystem extends Subsystem{
	public static final Solenoid gearSolenoid = new Solenoid(RobotMap.GEAR_SOLENOID);
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

}
