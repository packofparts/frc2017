package org.usfirst.frc.team1294.robot.vision;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jxlewis on 1/21/17.
 */
public class VisionProcessing {
  private final GearGripPipeline gearGripPipeline = new GearGripPipeline();

  public VisionProcessingResult processGearFrame(Mat gearFrame) {
    // run the grip pipeline
    gearGripPipeline.process(gearFrame);

    // get the bounding rect for each contour
    List<Rect> rects = gearGripPipeline
            .filterContoursOutput()
            //.findContoursOutput()
            .stream()
            .map(contour -> Imgproc.boundingRect(contour))
            .collect(Collectors.toList());

    // get every combination of pairs
    Stream<PairOfRect> pairs = rects.stream()
            .flatMap(i -> rects.stream().filter(j -> !i.equals(j)).map(j -> new PairOfRect(i, j)));

    // get the best (lowest) scoring pair
    Optional<PairOfRect> bestPair = pairs.min((a, b) -> scorePair(b).compareTo(scorePair(a)));


    boolean gearTargetAcquired = bestPair.isPresent();
    double degreesOffCenter = 0;
    double pixelsOffCenter = 0;
    if (bestPair.isPresent()) {
      PairOfRect pair = bestPair.get();
      // calculate how many degrees off center
      double halfWidth = gearFrame.width() / 2;
      pixelsOffCenter = pair.centerX() - halfWidth;
      double percentOffCenter = pixelsOffCenter  / halfWidth;
      degreesOffCenter = percentOffCenter * 60;
    }

    VisionProcessingResult result = new VisionProcessingResult();
    result.targetAcquired = gearTargetAcquired;
    result.degreesOffCenter = degreesOffCenter;
    result.pixelsOffCenter = (int)pixelsOffCenter;
    return result;
  }

  public class VisionProcessingResult {
    public boolean targetAcquired;
    public double degreesOffCenter;
    public int headingWhenImageTaken;
    public int pixelsOffCenter;
  }

  public class PairOfRect {
    public Rect a;
    public Rect b;

    public PairOfRect(Rect a, Rect b) {
      this.a = a;
      this.b = b;
    }

    public double centerX() {
      if (a.x < b.x) {
        return a.x + ((double) (b.x + b.width - a.x)) / 2;
      } else {
        return b.x + ((double) (a.x + a.width - b.x)) / 2;
      }
    }

    public Point topLeft() {
      if (a.x < b.x) {
        return new Point(a.x, a.y);
      } else {
        return new Point(b.x, b.y);
      }
    }

    public Point bottomRight() {
      if (a.x < b.x) {
        return new Point(b.x + b.width, b.y + b.height);
      } else {
        return new Point(a.x + a.width, a.y + a.height);
      }
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
    double combinedWidth;
    double combinedHeight;
    if (pair.a.x < pair.b.x) {
      combinedWidth = pair.a.x + pair.b.x + pair.b.width;
    } else {
      combinedWidth = pair.b.x + pair.a.x + pair.a.width;
    }
    if (pair.a.y < pair.b.y) {
      combinedHeight = pair.a.y + pair.b.y + pair.b.height;
    } else {
      combinedHeight = pair.b.y + pair.a.y + pair.a.height;
    }

    double combinedRatio = combinedWidth / combinedHeight;
    score += Math.abs(combinedRatio - 2.05);

    return score;
  }
}
