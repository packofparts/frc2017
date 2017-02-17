package org.usfirst.frc.team1294.robot.subsystems;
import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team1294.robot.RobotMap;
import org.usfirst.frc.team1294.robot.commands.ClimbRope;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A subsystem that encompases every motor and sensor related to the rope climbing game mech.
 */
public class ClimbingSubsystem extends Subsystem {
	public final CANTalon climbTalon;

    public ClimbingSubsystem() {
        super("ClimbingSubsystem");
        climbTalon = new CANTalon(RobotMap.CLIMBING_TALON);
    }

    @Override
    protected void initDefaultCommand() {
    }
    public void setMotor(double speed) {
        climbTalon.set(speed);
      }
}