package org.usfirst.frc.team1294.robot.filter;

/**
 * {@link SimpleKalman} is an one dimensional implementation of KALMAN filter used to
 * predict a noisy signal containing fluctuations due to measurement errors, noise etc.
 *
 * @author AchuthaRanga.Chenna https://gist.github.com/achutharanga/4e542c39bcbd28071d4f
 *
 */
public class SimpleKalman {
  /** process noise COVARIANCE */
  private final double q;
  /** measurement noise COVARIANCE */
  private final double r;

  /** Measurement value */
  private double x = 0d;
  /** estimation error COVARIANCE */
  private double p = 50d;
  /** KALMAN gain */
  private double k = 0.04d;

  /**
   * Init the filter with initial value of signal.
   * @param initial_measurement
   */
  public SimpleKalman(double q, double r, double initial_measurement) {
    this.q = q;
    this.r = r;
    this.x = initial_measurement;
  }

  /**
   * Method to get the predicted value of the measured signal value.
   *
   * @param measurement
   *            value from real time data.
   * @return predicted measurement.
   */
  public double getPredicted_Value(double measurement) {
    p = p + q;
    k = p / (p + r);
    x = x + k * (measurement - x);
    p = (1 - k) * p;
    return x;
  }
}
