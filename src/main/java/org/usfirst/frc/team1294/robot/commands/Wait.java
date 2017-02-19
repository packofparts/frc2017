package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class Wait extends Command{
	private final Timer timer;
	private double waitTime;
	private double timeElapsed;
	
	public Wait(double time) {
		timer = new Timer();
		timer.start();
		waitTime = time;
	}
	@Override
	protected void execute(){
		timeElapsed = timer.get();
	}
	@Override
	protected boolean isFinished() {
		if(timeElapsed >= waitTime){
			return true;
		}
		return false;
	}
	@Override
	protected void end(){
		timer.stop();
	}
}
