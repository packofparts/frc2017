package org.usfirst.frc.team1294.robot.commands;

import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Created by root on 2/11/17.
 */

public class FeederCommand extends Command {

    private static final double waitTime = 1.0; //time to wait in seconds
    private static final double startTime = 1.0; //time to run the motor for
    private static final double feederMotorVoltage = -9.0; //time to run the motor for
    private boolean done = false;
    private final Timer timer;
    private double shootTime;
    private boolean shooting = true;

    public FeederCommand(){
        timer = new Timer();
        timer.start();
        //requires(Robot.fuelSubsystem);
        shootTime = timer.get();
    }

    @Override
    protected void initialize() {
        done = false;
        shooting = true;
    }

    @Override
    protected boolean isFinished() {
        return (!Robot.fuelSubsystem.isMotorARunning() && !Robot.fuelSubsystem.isMotorBRunning()) || done;
    }

    @Override
    protected void execute() {
            if(shooting){
                if(timer.get() - shootTime >= startTime){
                    shootTime = timer.get();
                    shooting = false;
                }
                Robot.fuelSubsystem.setFeederMotorVoltageSpeed(feederMotorVoltage);
            }
            else if(!shooting) {

                if (timer.get() - shootTime >= waitTime){
                    shootTime= timer.get();
                    shooting = true;
                }
                Robot.fuelSubsystem.setFeederMotorVoltageSpeed(0.0);
                done = !Robot.oi.getJoystick2().getAButton(); //if there is a problem it will be here
            }
    }

    @Override
    protected void end() {
        timer.stop();
    }
}

