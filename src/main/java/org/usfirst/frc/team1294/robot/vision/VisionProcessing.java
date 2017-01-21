package org.usfirst.frc.team1294.robot.vision;

import edu.wpi.cscore.CvSink;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1294.robot.subsystems.CameraSubsystem;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jxlewis on 1/21/17.
 */
public class VisionProcessing {
  private boolean gearTargetAcquired;
  private int gearTargetPixelsFromCenter;
  private final GearGripPipeline gearGripPipeline = new GearGripPipeline();

  public VisionProcessing() {

  }

  public void processGearFrame(Mat gearFrame) {
    // run the grip pipeline
    gearGripPipeline.process(gearFrame);

    // get the bounding rect for each contour
    List<Rect> rects = gearGripPipeline
            .filterContoursOutput()
            .stream()
            .map(contour -> Imgproc.boundingRect(contour))
            .collect(Collectors.toList());

    // get every combination of pairs
    Stream<PairOfRect> pairs = rects.stream()
            .flatMap(i -> rects.stream().filter(j -> !i.equals(j)).map(j -> new PairOfRect(i, j)));

    // get the best (lowest) scoring pair
    Optional<PairOfRect> bestPair = pairs.min((a, b) -> scorePair(b).compareTo(scorePair(a)));

    this.gearTargetAcquired = bestPair.isPresent();
    if (bestPair.isPresent()) {
      PairOfRect pair = bestPair.get();

      // calculate how many pixels off center
      gearTargetPixelsFromCenter = (int) (pair.centerY() - gearFrame.width() / 2);
    } else {
      gearTargetPixelsFromCenter = 0;
    }
  }

//  public void saveLastImage() {
//    try {
//      String filename = "/u/gearFrame_" + new Date().getTime() + ".jpg";
//      MatOfInt parameters = new MatOfInt(Imgcodecs.IMWRITE_JPEG_QUALITY, 100);
//      Imgcodecs.imwrite(filename, gearFrame, parameters);
//    } catch (Exception ex) {
//      System.out.println("Failed to save image.");
//      ex.printStackTrace();
//    }
//  }

  public boolean isGearTargetAcquired() {
    return gearTargetAcquired;
  }

  public int getGearTargetPixelsFromCenter() {
    if (gearTargetAcquired) {
      return gearTargetPixelsFromCenter;
    } else {
      return 0;
    }
  }

  public class PairOfRect {
    public Rect a;
    public Rect b;

    public PairOfRect(Rect a, Rect b) {
      this.a = a;
      this.b = b;
    }

    public double centerY() {
      return a.y + ((double) (b.y + b.width - a.y)) / 2;
    }

    public Point topLeft() {
      return new Point(a.x, a.y);
    }

    public Point bottomRight() {
      return new Point(b.x + b.width, b.y + b.height);
    }
  }

  public Double scorePair(PairOfRect pair) {
    double score = 0;

    // difference in height
    score += Math.abs(pair.a.height - pair.b.height);

    // difference in width
    score += Math.abs(pair.a.width - pair.b.width);

    // difference in center Y
    double rect1CenterY = pair.a.y + pair.a.height / 2;
    double rect2CenterY = pair.b.y + pair.b.height / 2;
    score += Math.abs(rect1CenterY - rect2CenterY);

    // difference between combined rect width:height ratio and the ideal of 2.05
    double combinedWidth = pair.a.x + pair.b.x + pair.b.width;
    double combinedHeight = pair.a.y + pair.b.y + pair.b.height;
    double combinedRatio = combinedWidth / combinedHeight;
    score += Math.abs(combinedRatio - 2.05);

    return score;
  }
}
