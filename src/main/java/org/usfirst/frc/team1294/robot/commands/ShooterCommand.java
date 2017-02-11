package org.usfirst.frc.team1294.robot.commands;

import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Created by root on 2/11/17.
 */
public class ShooterCommand extends Command{
	@Override
    protected void execute() {
		super.execute();
		Robot.fuelSubsystem.setShooterMotorBVoltageSpeed(10);
		Robot.fuelSubsystem.setShooterMotorAVoltageSpeed(8);
    }
	protected void end() {
		super.execute();
		Robot.fuelSubsystem.setShooterMotorBVoltageSpeed(0);
		Robot.fuelSubsystem.setShooterMotorAVoltageSpeed(0);
    }
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
