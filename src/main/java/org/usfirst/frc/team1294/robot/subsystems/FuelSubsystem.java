package org.usfirst.frc.team1294.robot.subsystems;

import org.usfirst.frc.team1294.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A subsystem that encompases every motor and sensor related to the fuel shooting and/or collecting game mech.
 */
public class FuelSubsystem extends Subsystem {
	public final CANTalon shooterMotorA;
	public final CANTalon shooterMotorB;
	public final CANTalon feederMotor;
    public FuelSubsystem() {
        super("FuelSubsystem");
        shooterMotorA = new CANTalon(RobotMap.FUEL_SHOOTER_MOTOR_A);
        shooterMotorB = new CANTalon(RobotMap.FUEL_SHOOTER_MOTOR_B);
        feederMotor = new CANTalon(RobotMap.FUEL_FEEDER_MOTOR);
        feederMotor.changeControlMode(TalonControlMode.Voltage);
        shooterMotorA.changeControlMode(TalonControlMode.Voltage);
        shooterMotorB.changeControlMode(TalonControlMode.Voltage);
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void setFeederMotorVoltageSpeed(double voltageSpeed){
    	feederMotor.set(voltageSpeed);
    }

    public void setShooterMotorAVoltageSpeed(double voltageSpeed){
    	shooterMotorA.set(voltageSpeed);
    }
    public void setShooterMotorBVoltageSpeed(double voltageSpeed){
    	shooterMotorB.set(voltageSpeed);
    }

    public boolean isMotorARunning(){ return shooterMotorA.getOutputVoltage() != 0; }

    public boolean isMotorBRunning(){ return shooterMotorB.getOutputVoltage() != 0; }

}
