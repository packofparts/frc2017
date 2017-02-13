package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A command for the autonomous period that delivers a gear from the left position.
 */
public class AutoGearLeft extends CommandGroup {
    public AutoGearLeft() {
        addSequential(new MoveFromStartToLiftLeft());
        addSequential(new DeliverGearCommand());
    }
}
