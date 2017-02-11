package org.usfirst.frc.team1294.robot.subsystems;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Created by root on 2/11/17.
 */
public class FeederCommand extends Command {


    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute() {

        super.execute();
    }

    @Override
    protected synchronized void requires(Subsystem subsystem) {
        super.requires(subsystem);
    }
}
