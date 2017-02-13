package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A command for the autonomous period that delivers a gear from the right position.
 */
public class AutoGearRight extends CommandGroup {
    public AutoGearRight() {
        addSequential(new MoveFromStartToLiftRight());
        addSequential(new DeliverGearCommand());
    }
}
