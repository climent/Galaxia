package org.templegalaxia.model;

import heronarts.lx.model.LXAbstractFixture;
import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;
import heronarts.lx.transform.LXTransform;

public class Petal extends LXModel {

  public Petal(LXTransform transform) {
    super(new Fixture(transform));
  }

  //  private static List<float[]> xyzs = new ArrayList<>();
  private static PointLoader xyzs = new PointLoader("petalPoints.csv");
  public static int numPixels = xyzs.getNumPixels();

  private static class Fixture extends LXAbstractFixture {
    Fixture(LXTransform transform) {
      for (float[] xyz : xyzs.getPoints()) {
        transform.push();

        transform.translate(xyz[0], xyz[1], xyz[2]);
        addPoint(new LXPoint(transform));

        transform.pop();
      }
    }
  }
}
