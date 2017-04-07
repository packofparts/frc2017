package org.usfirst.frc.team1294.robot.commands;

import org.usfirst.frc.team1294.robot.Robot;
import org.usfirst.frc.team1294.robot.vision.VisionProcessing.VisionProcessingResult;

import edu.wpi.first.wpilibj.command.Command;

public class DriveForwardUntilVisionTargetCommand extends Command {
	
	public DriveForwardUntilVisionTargetCommand() {
		requires(Robot.driveSubsystem);
	}
	
	@Override
	protected void execute() {
		Robot.driveSubsystem.mecanumDrive(0, -0.25, 0, 0);
	}

	@Override
	protected void initialize() {
		this.setTimeout(0.5);
	}

	@Override
	protected boolean isFinished() {
		VisionProcessingResult result = Robot.spatialAwarenessSubsystem.doVisionProcessingOnGearCamera();
		return this.isTimedOut() || result.targetAcquired;
	}

}
