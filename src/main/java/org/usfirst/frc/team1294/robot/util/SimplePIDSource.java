package org.usfirst.frc.team1294.robot.util;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

import java.util.function.DoubleSupplier;

/**
 * Created by jxlewis on 1/19/17.
 */
public class SimplePIDSource implements PIDSource {

  private DoubleSupplier input;

  public SimplePIDSource(DoubleSupplier input) {

    this.input = input;
  }

  @Override
  public void setPIDSourceType(PIDSourceType pidSource) {

  }

  @Override
  public PIDSourceType getPIDSourceType() {
    return PIDSourceType.valueOf("Simple");
  }

  @Override
  public double pidGet() {
    return input.getAsDouble();
  }


}
