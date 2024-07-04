package frc.util;

import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;
import frc.util.commands.ResetSensorsCommand;

public class SuperNavX extends SuperSystem implements SuperInterface {
  private AHRS navX = new AHRS(SPI.Port.kMXP);
  private double offset;
  public SuperNavX() {
    super("Navx");
    this.resetNavx();
    offset = 0;
    getTab().addCommandToDashboard("ResetSensor", new ResetSensorsCommand(this, 0));
  }

  @Override
  public void periodic() {
    getTab().putInDashboard("NavX Angle", getSuperAngle(), 2, 0, true);
    getTab().putInDashboard("NavX Pitch", getPitch(), 2, 1, true);
    getTab().putInDashboard("NavX Roll", getRoll(), 2, 2, true);
    getTab().putInDashboard("NavX Yaw", getYaw(), 2, 3, true);

    getTab().putInDashboard("Navx Velocity X", getNavX().getVelocityX(), true);
    getTab().putInDashboard("Navx Velocity y", getNavX().getVelocityY(), true);
    getTab().putInDashboard("Navx Velocity Z", getNavX().getVelocityZ(), true);
  }

  public float getYaw() {
    return navX.getYaw();
  }

  public float getRoll() {
    return navX.getRoll();
  }

  public float getPitch() {
    return navX.getPitch();
  }

  public double getAngle() {
    return navX.getAngle();
  }

  public double getSuperAngle() {
    return getAngle() + offset;
  }

  public double getAngle360() {
    double angle = getSuperAngle() % 360;
    if (angle < 0)
      angle += 360;
    return angle;
  }

  public AHRS getNavX() {
    return navX;
  }

  public void resetNavx() {
    navX.reset();
  }

  public double getRate() {
    return navX.getRate();
  }

  @Override
  public void resetSensors(double pos) {
    offset = pos;
    resetNavx();
  }
}