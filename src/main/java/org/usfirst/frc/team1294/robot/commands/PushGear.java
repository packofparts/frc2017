package org.usfirst.frc.team1294.robot.commands;

import org.usfirst.frc.team1294.robot.subsystems.PneumaticGearSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class PushGear extends Command{
	protected void execute(){
	PneumaticGearSubsystem.gearSolenoid.set(true);
	}
	@Override
	protected void end(){
		PneumaticGearSubsystem.gearSolenoid.set(false);
	}
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
