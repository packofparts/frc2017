package org.usfirst.frc.team1294.robot.subsystems;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.VisionThread;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1294.robot.vision.ContourData;
import org.usfirst.frc.team1294.robot.vision.GripPipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VisionSubsystem extends Subsystem {

    private static final int IMG_WIDTH = 320;
    private static final int IMG_HEIGHT = 240;
    private List<ContourData> contourReport = new ArrayList<>();

    private VisionThread visionThread;

    public VisionSubsystem() {
        super("VisionSubsystem");

        CameraServer cameraServer = CameraServer.getInstance();
        UsbCamera camera = cameraServer.startAutomaticCapture();
        camera.setFPS(5);
        camera.setResolution(320, 240);

        CvSource outputStream = cameraServer.putVideo("ProcessedVideo", IMG_WIDTH, IMG_HEIGHT);

        GripPipeline gripPipeline = new GripPipeline();

        visionThread = new VisionThread(camera, gripPipeline, p -> {
            // get the filtered list of contours
            ArrayList<MatOfPoint> contours = p.filterContoursOutput();

            // save contour data
            contourReport = contours.stream().map(c -> new ContourData(c)).collect(Collectors.toList());

            // draw the contours onto the frame
            Mat frame = p.blurOutput();
            Imgproc.drawContours(frame, contours, 0, new Scalar(0,0,255));

            // output the frame
            outputStream.putFrame(frame);
        });

        visionThread.start();

    }

    @Override
    protected void initDefaultCommand() {

    }

    public List<ContourData> getContourReport() {
        return contourReport;
    }
}
