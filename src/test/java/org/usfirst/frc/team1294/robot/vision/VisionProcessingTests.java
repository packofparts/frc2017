package org.usfirst.frc.team1294.robot.vision;

import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.*;


public class VisionProcessingTests {

  @BeforeClass(groups = "vision")
  public static void loadNativeOpenCvLibraries() throws Exception {
    try {
      String osname = System.getProperty("os.name");
      String resname = "/opencv/";
      if (osname.startsWith("Windows"))
        resname += "Windows/" + System.getProperty("os.arch") + "/";
      else
        resname += osname + "/" + System.getProperty("os.arch") + "/";

      if (System.getProperty("os.name").startsWith("Linux") && new File("/usr/lib/arm-linux-gnueabihf").exists()) {
        resname += "hf/";
      }
      System.out.println(resname);
      if (osname.startsWith("Windows"))
        resname += "opencv_java310.dll";
      else if (osname.startsWith("Mac"))
        resname += "libopencv_java310.jnilib";
      else
        resname += "libopencv_java310.so";

      InputStream is = VisionProcessingTests.class.getResourceAsStream(resname);
      if (is == null) {
        if (new File("./" + resname).exists()) {
          is = new FileInputStream("./" + resname);
        } else if (new File("./src/main/resources/" + resname).exists()) {
          is = new FileInputStream("./src/main/resources/" + resname);
        }
      }

      File jniLibrary;
      if (is != null) {
        // create temporary file
        if (System.getProperty("os.name").startsWith("Windows"))
          jniLibrary = File.createTempFile("opencv_java310", ".dll");
        else if (System.getProperty("os.name").startsWith("Mac"))
          jniLibrary = File.createTempFile("opencv_java310", ".dylib");
        else
          jniLibrary = File.createTempFile("opencv_java310", ".so");
        // flag for delete on exit
        jniLibrary.deleteOnExit();
        OutputStream os = new FileOutputStream(jniLibrary);

        byte[] buffer = new byte[1024];
        int readBytes;
        try {
          while ((readBytes = is.read(buffer)) != -1) {
            os.write(buffer, 0, readBytes);
          }
        } finally {
          os.close();
          is.close();
        }

        System.load(jniLibrary.getAbsolutePath());
      } else {
        System.loadLibrary("opencv_java310");
      }
      System.out.println("Successfully loaded opencv native libraries.");
    } catch (IOException ex) {
      ex.printStackTrace();
      System.exit(1);
    }
  }

//  @Test
//  public void shouldBeAVisionProcessingTestsbleToFindTargetOnImage() {
//    VisionProcessing visionProcessing = new VisionProcessing();
//    File folder = new File("src/test/resources/TestImages/positive/");
//    for (File file : folder.listFiles()) {
//      String filename = file.getAbsolutePath();
//      Mat image = Imgcodecs.imread(filename);
//
//      visionProcessing.processGearFrame(image);
//      assertThat(file.getAbsolutePath(), visionProcessing.isGearTargetAcquired(), is(true));
//    }
//  }

//  @Test
//  public void testgearFrame_1485027378566() {
//    VisionProcessing visionProcessing = new VisionProcessing();
//    Mat image = Imgcodecs.imread("src/test/resources/TestImages/positive/gearFrame_1485027378566.jpg");
//    visionProcessing.processGearFrame(image);
//    assertThat(visionProcessing.isGearTargetAcquired(), is(true));
//    assertThat(visionProcessing.getGearTargetPixelsFromCenter(), is(-76));
//  }

  //gearFrame_1485026290472
  @Test(groups = "vision")
  public void testgearFrame_1485026290472() {
    VisionProcessing visionProcessing = new VisionProcessing();
    Mat image = Imgcodecs.imread("src/test/resources/TestImages/positive/gearFrame_1485026290472.jpg");
    VisionProcessing.VisionProcessingResult result = visionProcessing.processGearFrame(image);

    //assertThat(visionProcessing.getContours().size(), is(2));

    //Imgproc.drawContours(image, visionProcessing.getContours(), -1, new Scalar(0,0,255));
    //Imgproc.rectangle(image, visionProcessing.getBestPair().get().topLeft(), visionProcessing.getBestPair().get().bottomRight(), new Scalar(255,0,0));
    //Point p = new Point(image.h)
    //Imgproc.circle(image, visionProcessing.getBestPair().get().centerX(), 3, new Scalar(255,0,0));
    //Point topLeft = new Point(visionProcessing.getBestPair().get().a.x, visionProcessing.getBestPair().get().a.y);
    //Point bottomRight;
    //Imgproc.rectangle(image, topLeft, bottomRight, new Scalar(255,0,0));


    MatOfInt parameters = new MatOfInt(Imgcodecs.IMWRITE_JPEG_QUALITY, 100);
    Imgcodecs.imwrite("src/test/resources/TestImages/positive/gearFrame_1485026290472_out.jpg", image, parameters);

    assertThat(result.targetAcquired).isTrue();
    assertThat(result.pixelsOffCenter).isEqualTo(21);
  }
}
