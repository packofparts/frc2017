package org.usfirst.frc.team1294.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import org.usfirst.frc.team1294.robot.commands.DriveHeadingAndDistance;
import org.usfirst.frc.team1294.robot.commands.DriveStraightCommand;
import org.usfirst.frc.team1294.robot.commands.FlipAUTurn;
import org.usfirst.frc.team1294.robot.commands.ShooterCommand;
import org.usfirst.frc.team1294.robot.commands.TurnToHeading;
import org.usfirst.frc.team1294.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  private final XboxController joystick2;
  private final XboxController joystick;
  private final JoystickButton buttonA;
  private final JoystickButton buttonB;
  private final JoystickButton buttonX;
  private final JoystickButton buttonY;
  private final JoystickButton buttonRightBumper;
  private final JoystickButton buttonLeftBumper;
  private final JoystickButton buttonStart;
  private final JoystickButton buttonBack;
  private final JoystickButton buttonLeftThumb;
  private final JoystickButton buttonRightThumb;
  private final Trigger dpadUp;
  private final Trigger dpadUpRight;
  private final Trigger dpadRight;
  private final Trigger dpadDownRight;
  private final Trigger dpadDown;
  private final Trigger dpadDownLeft;
  private final Trigger dpadLeft;
  private final Trigger dpadUpLeft;

  private final JoystickButton buttonA2;
  private final JoystickButton buttonB2;
  private final JoystickButton buttonX2;
  private final JoystickButton buttonY2;
  private final JoystickButton buttonRightBumper2;
  private final JoystickButton buttonLeftBumper2;
  private final JoystickButton buttonStart2;
  private final JoystickButton buttonBack2;
  private final JoystickButton buttonLeftThumb2;
  private final JoystickButton buttonRightThumb2;
  private final Trigger dpadUp2;
  private final Trigger dpadUpRight2;
  private final Trigger dpadRight2;
  private final Trigger dpadDownRight2;
  private final Trigger dpadDown2;
  private final Trigger dpadDownLeft2;
  private final Trigger dpadLeft2;
  private final Trigger dpadUpLeft2;

  public OI() {
    this.joystick = new XboxController(RobotMap.XBOX_CONTROLLER);

    this.joystick2 = new XboxController(RobotMap.XBOX_CONTROLLER2);


    this.buttonA2 = new JoystickButton(this.joystick2, 1);
    this.buttonB2 = new JoystickButton(this.joystick2, 2);
    this.buttonX2 = new JoystickButton(this.joystick2, 3);
    this.buttonY2 = new JoystickButton(this.joystick2, 4);
    this.buttonLeftBumper2 = new JoystickButton(this.joystick2, 5);
    this.buttonRightBumper2 = new JoystickButton(this.joystick2, 6);
    this.buttonBack2 = new JoystickButton(this.joystick2, 7);
    this.buttonStart2 = new JoystickButton(this.joystick2, 8);
    this.buttonLeftThumb2 = new JoystickButton(this.joystick2, 9);
    this.buttonRightThumb2 = new JoystickButton(this.joystick2, 10);
    this.dpadUp2 = new Trigger() {@Override public boolean get() {return joystick2.getPOV(0) == 0;}};
    this.dpadUpRight2 = new Trigger() {@Override public boolean get() {return joystick2.getPOV(0) == 45;}};
    this.dpadRight2 = new Trigger() {@Override public boolean get() {return joystick2.getPOV(0) == 90;}};
    this.dpadDownRight2 = new Trigger() {@Override public boolean get() {return joystick2.getPOV(0) == 135;}};
    this.dpadDown2 = new Trigger() {@Override public boolean get() {return joystick2.getPOV(0) == 180;}};
    this.dpadDownLeft2 = new Trigger() {@Override public boolean get() {return joystick2.getPOV(0) == 225;}};
    this.dpadLeft2 = new Trigger() {@Override public boolean get() {return joystick2.getPOV(0) == 270;}};
    this.dpadUpLeft2 = new Trigger() {@Override public boolean get() {return joystick2.getPOV(0) == 315;}};

    // mappings based on this post from CD...
    // https://www.chiefdelphi.com/forums/attachment.php?attachmentid=20028&d=1455109186
    this.buttonA = new JoystickButton(this.joystick, 1);
    this.buttonB = new JoystickButton(this.joystick, 2);
    this.buttonX = new JoystickButton(this.joystick, 3);
    this.buttonY = new JoystickButton(this.joystick, 4);
    this.buttonLeftBumper = new JoystickButton(this.joystick, 5);
    this.buttonRightBumper = new JoystickButton(this.joystick, 6);
    this.buttonBack = new JoystickButton(this.joystick, 7);
    this.buttonStart = new JoystickButton(this.joystick, 8);
    this.buttonLeftThumb = new JoystickButton(this.joystick, 9);
    this.buttonRightThumb = new JoystickButton(this.joystick, 10);
    this.dpadUp = new Trigger() {@Override public boolean get() {return joystick.getPOV(0) == 0;}};
    this.dpadUpRight = new Trigger() {@Override public boolean get() {return joystick.getPOV(0) == 45;}};
    this.dpadRight = new Trigger() {@Override public boolean get() {return joystick.getPOV(0) == 90;}};
    this.dpadDownRight = new Trigger() {@Override public boolean get() {return joystick.getPOV(0) == 135;}};
    this.dpadDown = new Trigger() {@Override public boolean get() {return joystick.getPOV(0) == 180;}};
    this.dpadDownLeft = new Trigger() {@Override public boolean get() {return joystick.getPOV(0) == 225;}};
    this.dpadLeft = new Trigger() {@Override public boolean get() {return joystick.getPOV(0) == 270;}};
    this.dpadUpLeft = new Trigger() {@Override public boolean get() {return joystick.getPOV(0) == 315;}};

    //this.buttonA.whenPressed(new DriveHeadingAndDistance(0, 1));
    this.buttonA.whenPressed(new DriveStraightCommand(5));
    this.buttonA.whenPressed(new DriveStraightCommand(5));
    this.buttonB.whenPressed(new TurnToHeading(180));
    this.buttonRightBumper.whileHeld(new DeliverGearCommand());

    this.buttonA2.whenPressed(new FeederCommand());
    this.buttonB2.toggleWhenPressed(new ShooterCommand());
    this.buttonX2.whenPressed(new PushGear());
  }

    public XboxController getJoystick() {
        return joystick;
    }

    public XboxController getJoystick2() {
        return joystick2;
    }

    //// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
