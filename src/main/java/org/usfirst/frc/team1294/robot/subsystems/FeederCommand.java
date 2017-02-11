package org.usfirst.frc.team1294.robot.subsystems;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Created by root on 2/11/17.
 */
public class FeederCommand extends Command {

    private XboxController joystick2;


    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute() {
        double leftTriggerValue = joystick2
        double rightTriggerValue = joystick2.getTriggerAxis(GenericHID.Hand.kRight);
        super.execute();
    }

    @Override
    protected synchronized void requires(Subsystem subsystem) {
        super.requires(subsystem);
    }
}
