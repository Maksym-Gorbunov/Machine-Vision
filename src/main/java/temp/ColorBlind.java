package temp;

import com.constants.Constants;
import com.pages.page9.Page9;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.javacpp.opencv_imgproc.RETR_TREE;

public class ColorBlind {

  public static String path = Constants.imgPath + "colorblind\\";

  public static void main(String[] args) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    ColorBlind c = new ColorBlind();
//    Mat src = Imgcodecs.imread(path + "color.jpg");
//
    Mat src = Imgcodecs.imread(path + "2.jpg");
//    Mat gray = new Mat();
//    Imgproc.cvtColor(src1, gray, Imgproc.COLOR_RGB2GRAY);
//    Imgcodecs.imwrite(path + "gray.jpg", gray);

    c.findColor(src);
  }

  private void findColor(Mat img) {
    Mat matrix = new Mat();
    img.copyTo(matrix);

    List<Point> neighboursList = new ArrayList<>();
    Mat whiteImage = new Mat(matrix.rows(), matrix.cols(), CvType.CV_8UC1, new Scalar(255));

    for (int row = 0; row < matrix.rows(); row++) {
      for (int col = 0; col < matrix.cols(); col++) {
        double[] pointGBR = matrix.get(row, col);
        String pointColorName = getColorName(pointGBR);


        if (pointColorName.equals("Red")) {
          Point point = new Point(col, row);
          List<Point> neighborhoods = getNeighborhoods(matrix, point);
          for (Point neighborPoint : neighborhoods) {
            double[] neighborBGR = matrix.get((int) neighborPoint.y, (int) neighborPoint.x);
            String neighborColorName = getColorName(neighborBGR);

            //match red/green conflict
            if (neighborColorName.equals("Green")) {

//              //add all neighbours in one list without repeats
//              if (!neighboursList.contains(neighborPoint)) {
//                neighboursList.add(neighborPoint);
//
//              }
              whiteImage.put((int) neighborPoint.y, (int) neighborPoint.x, new double[]{0, 0, 0});
              matrix.put((int) neighborPoint.y, (int) neighborPoint.x, new double[]{0, 0, 0});
            }
          }

          // create neighbours families


        }
      }
    }

    Imgcodecs.imwrite(Constants.imgPath + "colorblind\\result1.jpg", matrix);
    Imgcodecs.imwrite(Constants.imgPath + "colorblind\\white.jpg", whiteImage);


    List<Rect> rectList = new ArrayList<>();
    rectList = getRectangles(whiteImage);
    if(rectList.size()>0){
      //draw rects
      for(Rect rect : rectList){
        Imgproc.rectangle(matrix, rect.tl(), rect.br(), new Scalar(0,0,0), 1);

      }
    }

    Imgcodecs.imwrite(Constants.imgPath + "colorblind\\result2.jpg", matrix);

  }

  private List<Rect> getRectangles(Mat whiteImage) {
    List<Rect> rectList = new ArrayList<>();
    List<MatOfPoint> contours = new ArrayList<>();
    Imgproc.findContours(whiteImage, contours, new Mat(), RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
    for (MatOfPoint c : contours) {
      MatOfPoint2f pointsArea = new MatOfPoint2f(c.toArray());
      RotatedRect rotatedRectangle = Imgproc.minAreaRect(pointsArea);
      Point rotatedRectPoints[] = new Point[4];
      rotatedRectangle.points(rotatedRectPoints);
      Rect rect = Imgproc.boundingRect(new MatOfPoint(rotatedRectPoints));
      rect = cutRectIfOutOfImageArea(whiteImage, rect);
      rectList.add(rect);
    }
    return rectList;
  }

  // Cut off contours rectangle if out off image area
  private Rect cutRectIfOutOfImageArea(Mat image, Rect rect) {
    double startX = rect.tl().x;
    double startY = rect.tl().y;
    double endX = rect.br().x;
    double endY = rect.br().y;
    if (startX < 0) {
      startX = 0;
    }
    if (startY < 0) {
      startY = 0;
    }
    if (endX > image.width()) {
      endX = image.width();
    }
    if (endY > image.height()) {
      endY = image.height();
    }
    Rect cuttedRect = new Rect(new Point(startX, startY), new Point(endX, endY));
    return cuttedRect;
  }

  private List<Point> getNeighborhoods(Mat src, Point point) {
    List<Point> points = new ArrayList<>();
    if (point.y != 0) {
      Point top = new Point(point.x, point.y - 1);
      points.add(top);
    }
    if (point.y != src.height() - 1) {
      Point bottom = new Point(point.x, point.y + 1);
      points.add(bottom);
    }
    if (point.x != 0) {
      Point left = new Point(point.x - 1, point.y);
      points.add(left);
    }
    if (point.x != src.width() - 1) {
      Point right = new Point(point.x + 1, point.y);
      points.add(right);
    }
    return points;
  }


  private void temp(Mat image) {
//    Mat imageCopy = new Mat();
//    image.copyTo(imageCopy);
//    Mat gray = new Mat();
//    Imgproc.cvtColor(image, gray, Imgproc.COLOR_RGB2GRAY);
//    Imgcodecs.imwrite(path + "gray.jpg", gray);

    //Imgproc.rectangle(frame, Page9.rect.tl(), Page9.rect.br(), blue, 3);
    //Imgproc.putText(cuttedRotatedPlate, String.valueOf((int)c.getAngle()), new Point(10, 30), 1, 1, red, 1);
//    double[] point1 = image.get(30,30);
//    for(double field: point1){
//      System.out.println(field);
//    }
    double[] blue = {255, 0, 0};
    double[] green = {0, 255, 0};
    double[] red = {0, 0, 255};

    Point pointA = new Point(10, 10);
    Point pointB = new Point(100, 10);
    Point pointC = new Point(240, 10);
    Point pointD = new Point(340, 10);

//    image.put((int) pointA.x, (int) pointA.y, blue);
//    image.put((int) pointB.x, (int) pointB.y, blue);
    Imgproc.circle(image, pointA, 10, new Scalar(0, 0, 0), 2);
    Imgproc.circle(image, pointB, 10, new Scalar(0, 0, 0), 2);
    Imgproc.circle(image, pointC, 10, new Scalar(0, 0, 0), 2);
    Imgproc.circle(image, pointD, 10, new Scalar(0, 0, 0), 2);

    double[] a = image.get((int) pointA.y, (int) pointA.x);
    double[] b = image.get((int) pointB.y, (int) pointB.x);
    double[] c = image.get((int) pointC.y, (int) pointC.x);
    double[] d = image.get((int) pointD.y, (int) pointD.x);

    String aColorName = getColorName(a);
    String bColorName = getColorName(b);
    String cColorName = getColorName(c);
    String dColorName = getColorName(d);

    System.out.println("A [" + a[0] + ", " + a[1] + ", " + a[2] + "] " + aColorName);
    System.out.println("B [" + b[0] + ", " + b[1] + ", " + b[2] + "] " + bColorName);
    System.out.println("C [" + c[0] + ", " + c[1] + ", " + c[2] + "] " + cColorName);
    System.out.println("D [" + d[0] + ", " + d[1] + ", " + d[2] + "] " + dColorName);
    System.out.println("_________________________________");


    Imgproc.putText(image, aColorName, new Point(pointA.x - 10, pointA.y + 20), 1, 0.8, new Scalar(0, 0, 0), 1);
    Imgproc.putText(image, bColorName, new Point(pointB.x - 10, pointB.y + 20), 1, 0.8, new Scalar(0, 0, 0), 1);
    Imgproc.putText(image, cColorName, new Point(pointC.x - 10, pointC.y + 20), 1, 0.8, new Scalar(0, 0, 0), 1);
    Imgproc.putText(image, dColorName, new Point(pointD.x - 10, pointD.y + 20), 1, 0.8, new Scalar(0, 0, 0), 1);

    Imgcodecs.imwrite(path + "colorCopy.jpg", image);
  }

  // Get color name from BGR values
  private String getColorName(double[] bgr) {
    double blue = bgr[0];
    double green = bgr[1];
    double red = bgr[2];

    String name = "";

    if ((blue > green) && (blue > red)) {
      name = "Blue";
    }
    if ((green > blue) && (green > red)) {
      name = "Green";
    }
    if ((red > blue) && (red > green)) {
      name = "Red";
    }
//    if((red == green) && (red > blue)){
//      name = "Yellow";
//    }
    return name;
  }


}


