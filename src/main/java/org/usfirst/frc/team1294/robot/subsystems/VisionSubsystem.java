package org.usfirst.frc.team1294.robot.subsystems;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1294.robot.vision.GripPipeline;

import java.util.ArrayList;

/**
 * Created by jxlewis on 1/14/17.
 */
public class VisionSubsystem extends Subsystem {

    private static final int IMG_WIDTH = 320;
    private static final int IMG_HEIGHT = 240;

    private VisionThread visionThread;

    public VisionSubsystem() {
        super("VisionSubsystem");

        CameraServer cameraServer = CameraServer.getInstance();
        UsbCamera camera = cameraServer.startAutomaticCapture();

        CvSource outputStream = cameraServer.putVideo("ProcessedVideo", IMG_WIDTH, IMG_HEIGHT);

        Scalar color_red = new Scalar(0,0,255);

        GripPipeline gripPipeline = new GripPipeline();

        visionThread = new VisionThread(camera, gripPipeline, p-> {
            ArrayList<MatOfPoint> contours = p.filterContoursOutput();

            Mat frame = p.resizeImageOutput();
            Imgproc.drawContours(frame, contours, 0, color_red);
            outputStream.putFrame(frame);
//            for (int i = 0; i< contours.size(); i++) {
////                MatOfPoint mat = contours.get(i);
////                Rect rect = Imgproc.boundingRect(mat);
////
////                SmartDashboard.putNumber("rect" + i + ".x", rect.x);
////                SmartDashboard.putNumber("rect" + i + ".y", rect.y);
////                SmartDashboard.putNumber("rect" + i + ".width", rect.width);
////                SmartDashboard.putNumber("rect" + i + ".height", rect.height);
//
//            }
        });

        visionThread.run();

    }

    @Override
    protected void initDefaultCommand() {

    }
}
