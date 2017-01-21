package org.usfirst.frc.team1294.robot.subsystems;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1294.robot.vision.GearGripPipeline;
import org.usfirst.frc.team1294.robot.vision.VisionProcessing;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

public class CameraSubsystem extends Subsystem {

  private static final int IMG_WIDTH = 320;
  private static final int IMG_HEIGHT = 240;

  private final CvSink gearVideo;
  private final UsbCamera gearCamera;
  private final CameraServer cameraServer;
  private final GearGripPipeline gearGripPipeline = new GearGripPipeline();
  private final VisionProcessing visionProcessing;

  private Mat gearFrame = new Mat();

  public CameraSubsystem() {
    super("CameraSubsystem");

    cameraServer = CameraServer.getInstance();
    gearCamera = cameraServer.startAutomaticCapture(0);
    gearCamera.setResolution(IMG_WIDTH, IMG_HEIGHT);
    gearCamera.setFPS(30);
    gearVideo = cameraServer.getVideo(gearCamera);

    visionProcessing = new VisionProcessing();

    System.out.println("CameraSubsystem constructor finished");
  }

  @Override
  protected void initDefaultCommand() {
  }

  public void doVisionProcessingOnGearCamera() {
    try {
      setGearFrameFromCamera();
      visionProcessing.processGearFrame(gearFrame);

      SmartDashboard.putBoolean("CameraSubsystem.GearTargetAcquired", isGearTargetAcquired());
      SmartDashboard.putNumber("CameraSubsystem.GearTargerPixelsFromCenter", getGearTargetPixelsFromCenter());

      System.out.println("Successfully ran vision processing.");
    } catch (Exception ex) {
      System.out.println("Failed to do vision processing.");
      ex.printStackTrace();
    }
  }

  public void setGearFrameFromCamera() {
    // grab a gearFrame
    gearVideo.grabFrame(gearFrame);
  }

  public void saveLastImage() {
    try {
      String filename = "/u/gearFrame_" + new Date().getTime() + ".jpg";
      MatOfInt parameters = new MatOfInt(Imgcodecs.IMWRITE_JPEG_QUALITY, 100);
      Imgcodecs.imwrite(filename, gearFrame, parameters);
    } catch (Exception ex) {
      System.out.println("Failed to save image.");
      ex.printStackTrace();
    }
  }

  public boolean isGearTargetAcquired() {
    return visionProcessing.isGearTargetAcquired();
  }

  public int getGearTargetPixelsFromCenter() {
    return visionProcessing.getGearTargetPixelsFromCenter();
  }


}
