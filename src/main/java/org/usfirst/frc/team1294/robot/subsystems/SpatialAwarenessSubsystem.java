package org.usfirst.frc.team1294.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.usfirst.frc.team1294.robot.RobotMap;
import org.usfirst.frc.team1294.robot.vision.GearGripPipeline;
import org.usfirst.frc.team1294.robot.vision.VisionProcessing;

import java.util.Date;

public class SpatialAwarenessSubsystem extends Subsystem {

  private static final int IMG_WIDTH = 320;
  private static final int IMG_HEIGHT = 240;

  // http://maxbotix.com/documents/LV-MaxSonar-EZ_Datasheet.pdf
  // Outputs analog voltage with a scaling factor of (Vcc/512) per inch. A supply of 5V yields ~9.8mV/in. and
  // 3.3V yields ~6.4mV/in. The output is buffered and corresponds to the most recent range data.
  // RoboRio analog port is 5v per http://www.ni.com/pdf/manuals/374474a.pdf
  // 1000 mv/ 1 v
  // 1 in / 9.8mv
  // 1 m / 39.3701 in
  // Y meters = X volts * 2.5918353351028 volts/meters
  private static final double ULTRASONIC_VOLTS_TO_METERS = 2.5918353351028; // TODO: validate this calibration

  private final CvSink gearVideo;
  private final UsbCamera gearCamera;
  private final CameraServer cameraServer;
  private final VisionProcessing visionProcessing;

  private final AnalogInput leftUltrasonic;
  private final AnalogInput rightUltrasonic;

  private final AHRS navX;

  private Mat gearFrame = new Mat();

  public SpatialAwarenessSubsystem() {
    super("SpatialAwarenessSubsystem");

    cameraServer = CameraServer.getInstance();
    gearCamera = cameraServer.startAutomaticCapture(0);
    gearCamera.setResolution(IMG_WIDTH, IMG_HEIGHT);
    gearCamera.setFPS(30);
    gearVideo = cameraServer.getVideo(gearCamera);

    visionProcessing = new VisionProcessing();

    leftUltrasonic = new AnalogInput(RobotMap.ANALOG_ULTRASONIC_LEFT);
    rightUltrasonic = new AnalogInput(RobotMap.ANALOG_ULTRASONIC_RIGHT);

    navX = new AHRS(SPI.Port.kMXP);

    System.out.println("SpatialAwarenessSubsystem constructor finished");
  }

  @Override
  protected void initDefaultCommand() {
  }

  public VisionProcessing.VisionProcessingResult doVisionProcessingOnGearCamera() {
    try {
      setGearFrameFromCamera();
      VisionProcessing.VisionProcessingResult result = visionProcessing.processGearFrame(gearFrame);

      SmartDashboard.putBoolean("SpatialAwarenessSubsystem.GearTargetAcquired", result.targetAcquired);
      SmartDashboard.putNumber("SpatialAwarenessSubsystem.GearTargerPixelsFromCenter", result.pixelsOffCenter);

      System.out.println("Successfully ran vision processing.");

      return result;
    } catch (Exception ex) {
      System.out.println("Failed to do vision processing.");
      ex.printStackTrace();
    }
    return null;
  }

  public void setGearFrameFromCamera() {
    // grab a gearFrame
    gearVideo.grabFrame(gearFrame);
  }

  public void saveLastImage() {
    String filename = "/u/gearFrame_" + new Date().getTime() + ".jpg";
    System.out.println("saving Last Image " + filename);
    try {
      MatOfInt parameters = new MatOfInt(Imgcodecs.IMWRITE_JPEG_QUALITY, 100);
      Imgcodecs.imwrite(filename, gearFrame, parameters);
    } catch (Exception ex) {
      System.out.println("Failed to save image.");
      ex.printStackTrace();
    }
    System.out.println("last image saved");
  }

  public double getDistanceToWall() {
    return (leftUltrasonic.getVoltage() + rightUltrasonic.getVoltage()) / 2;
  }

  public double getLeftUltrasonicDistance() {
    return leftUltrasonic.getVoltage() * ULTRASONIC_VOLTS_TO_METERS;
  }

  public double getRightUltrasonicDistance() {
    return rightUltrasonic.getVoltage() * ULTRASONIC_VOLTS_TO_METERS;
  }


  public double getHeading() {
    double angle = navX.getAngle() % 360;
    return angle;
  }


  public double getRate() {
    return navX.getRate();
  }

  public void resetGyro() {
    navX.reset();
  }

}
