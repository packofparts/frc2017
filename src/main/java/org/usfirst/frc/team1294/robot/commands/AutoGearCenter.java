package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A command for the autonomous period that delivers a gear from the center position.
 */
public class AutoGearCenter extends CommandGroup {
    public AutoGearCenter() {
        addSequential(new MoveFromStartToLiftCenter());
        addSequential(new DeliverGear());
    }
}
