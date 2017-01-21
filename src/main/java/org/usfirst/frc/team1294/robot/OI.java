package org.usfirst.frc.team1294.robot;

import edu.wpi.first.wpilibj.XboxController;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  private XboxController joystick;
  
  //Alliance Wall is 6.1722 meters(243 in)
  // robot placed middle of Alliance Wall
  // ~3.851 meters from right or left
  public final double HEADING_TO_TRAVEL_C = 0;
  public final double DISTANCE_TO_TRAVEL_C = 1.99;
  public final double HEADING_TO_FACE_C = 0;

  // robot placed ~2.57 meters from right
  // Distance is 5.4 meters all the way to the wall 
  public final double DISTANCE_TO_TRAVEL_R = 4.8;
  public final double HEADING_TO_TRAVEL_R = 30.0;
  public final double HEADING_TO_FACE_R = -90.0;
  
  // Robot placed ~2.57 from left
  //Distance is 5.4 meters all the way to the wall
  public final double DISTANCE_TO_TRAVEL_L = 4.8;
  public final double HEADING_TO_TRAVEL_L = -30.0;
  public final double HEADING_TO_FACE_L = 90.0;

  public OI() {
    this.joystick = new XboxController(RobotMap.XBOX_CONTROLLER);
  }

  public XboxController getJoystick() {
    return joystick;
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
