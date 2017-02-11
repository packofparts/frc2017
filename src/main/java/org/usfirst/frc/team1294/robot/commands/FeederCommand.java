package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team1294.robot.Robot;

import java.awt.*;

/**
 * Created by root on 2/11/17.
 */
public class FeederCommand extends Command {
    private static final double WaitTime = 1.0; //time to wait in seconds
    private static final double St

    public FeederCommand(){

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute() {
        super.execute();
        while(Robot.oi.getJoystick2().getAButton()) {
            Robot.fuelSubsystem.
        }
    }

    @Override
    protected synchronized void requires(Subsystem subsystem) {
        super.requires(subsystem);
    }
}
