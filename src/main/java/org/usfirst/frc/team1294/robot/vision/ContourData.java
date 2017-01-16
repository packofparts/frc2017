package org.usfirst.frc.team1294.robot.vision;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

/**
 * Created by jxlewis on 1/16/17.
 */
public class ContourData {
    private final int centerX;
    private final int centerY;
    private final int width;
    private final int height;
    private final double area;

    public ContourData(MatOfPoint contour) {
        Rect rect = Imgproc.boundingRect(contour);
        centerX = rect.x + rect.width / 2;
        centerY = rect.y + rect.height / 2;
        width = rect.width;
        height = rect.height;
        area = rect.area();
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getArea() {
        return area;
    }
}
