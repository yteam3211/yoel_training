/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util.vision;

import java.util.HashMap;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.util.vision.commands.LimelightCameraChangeModeCommand;
import frc.util.vision.commands.LimelightLEDChangeModeCommand;

/**
 * This class creat a conection with limelight data.
 * <p>
 * 
 * @author Matan Steinmetz
 */
public class Limelight extends SubsystemBase {
  protected String table;

  protected NetworkTable limelightTable;
  protected NetworkTableEntry tx;
  protected NetworkTableEntry ty;
  protected NetworkTableEntry ta;
  protected NetworkTableEntry tv;
  protected NetworkTableEntry ts;
  protected NetworkTableEntry LEDMode;
  protected NetworkTableEntry cameraMode;
  protected NetworkTableEntry streamMode;

  protected double tx_ = 0, ty_ = 0, ta_ = 0, ts_ = 0;
  protected double lastTx_ = 0, lastTy_ = 0, lastTa_ = 0, lastTs = 0;
  protected boolean isValueChange = true;

  protected double yawAngle = 0, pitchAngle = 90, high = 0, cameraDistanceFromCenterRobot = 0;
  protected double angle = 0, distance = 0;

  private ShuffleboardTab tab;
  private HashMap<String, GenericEntry> keys = new HashMap<>();

  private Limelight(Builder builder) {
    this.limelightTable = NetworkTableInstance.getDefault().getTable(builder.table);
    table = builder.table;
    tx = this.limelightTable.getEntry("tx");
    ty = this.limelightTable.getEntry("ty");
    ta = this.limelightTable.getEntry("ta");
    tv = this.limelightTable.getEntry("tv");
    ts = this.limelightTable.getEntry("ts");
    

    this.LEDMode = this.limelightTable.getEntry("ledMode");
    this.cameraMode = this.limelightTable.getEntry("camMode");
    this.streamMode = this.limelightTable.getEntry("stream");

    this.yawAngle = builder.yawAngle;
    this.pitchAngle = builder.pitchAngle;
    this.cameraDistanceFromCenterRobot = builder.cameraDistanceFromCenterRobot;
    this.high = builder.high;
    tab = Shuffleboard.getTab(builder.table);
    Command ledOn = new LimelightLEDChangeModeCommand(this, limelightLEDMode.kOn);
    Command ledOff = new LimelightLEDChangeModeCommand(this, limelightLEDMode.kOff);

    Command view = new LimelightCameraChangeModeCommand(this, limelightCameraMode.kView);
    Command vision = new LimelightCameraChangeModeCommand(this, limelightCameraMode.kVision);
    // Command USB = new LimelightCameraChangeModeCommand(this,
    // limelightCameraMode.kUSB);
    setStreamMode(limelightStreamMode.kPiPSecondary);


    ledOff.setName("Set");
    ledOn.setName("Set");

    view.setName("Set");
    vision.setName("Set");
    // USB.setName("Set");

    tab.add("LED On", ledOn).withPosition(2, 0);
    tab.add("LED Off", ledOff).withPosition(2, 1);

    tab.add("Vision Mode", vision).withPosition(3, 0);
    tab.add("View Mode", view).withPosition(3, 1);
    // tab.add("USB Mode", USB).withPosition(3, 2);

    super.setDefaultCommand(new LimelightUpdateValue(this));
  }

  /**
   * set LED to be on / off / blinking mode.
   * <p>
   * 
   * @param value - what you whant LED to do.
   */
  public void setLEDMode(limelightLEDMode value) {
    if (value == limelightLEDMode.kOn) {
      LEDMode.setNumber(3);
    } else if (value == limelightLEDMode.kOff) {
      System.out.println(LEDMode.getNumber(33.5));
      LEDMode.setNumber(1);
    } else {
      LEDMode.setNumber(2);
    }
  }

  /**
   * set pipeline number.
   * <p>
   * 
   * @param pipeline pipeline number to use.
   */
  public void setPipeline(int pipeline) {
  }

  /**
   * set camera to be in vision / driver mode.
   * <p>
   * 
   * @param value - what camera mode to use.
   */
  public void setCameraMode(limelightCameraMode value) {
    if (value == limelightCameraMode.kVision) {
      cameraMode.setNumber(0);
    } else if (value == limelightCameraMode.kView) {
      cameraMode.setNumber(1);
    } else {
      cameraMode.setNumber(2);
    }
  }

  /**
   * set stream mode.
   * <p>
   * 
   * @param value - what camera mode to use.
   */
  public void setStreamMode(limelightStreamMode value) {
    if (value == limelightStreamMode.kStandard) {
      limelightTable.getEntry("stream").setNumber(0);
    } else if (value == limelightStreamMode.kPiPMain) {
      limelightTable.getEntry("stream").setNumber(1);
    } else {
      limelightTable.getEntry("stream").setNumber(2);
    }
  }

  /**
   * @param vision true if change to vision mode, false for view (driver) mode.
   */
  public void changeMode(boolean vision) {
    if (vision) {
      setCameraMode(limelightCameraMode.kVision);
      setLEDMode(limelightLEDMode.kOn);
    } else {
      setCameraMode(limelightCameraMode.kView);
      setLEDMode(limelightLEDMode.kOff);
    }
  }

  /**
   * @return limelight table name.
   */
  public String getLimelightTableName() {
    return table;
  }

  /**
   * @return true if value change.
   */
  public boolean isValueChange() {
    return isValueChange;
  }

  /**
   * @return X angle.
   */
  public double getX() {
    return limelightTable.getEntry("tx").getDouble(0);
  }

  /**
   * @return Y angle.
   */
  public double getY() {
    return limelightTable.getEntry("ty").getDouble(0);
  }

  /**
   * @return Target area.
   */
  public double getArea() {
    return ta_;
  }

  public double getS(){
    return ts_;
  }

  /**
   * @return true if valid.
   */
  public boolean isValid() {
    return tv.getDouble(0.0) != 0 ? true : false;
  }

  /**
   * Use this function to get error angle from center robot.
   * <p>
   * 
   * @return the angle of the center robot to target.
   */
  public double getAngleToTarget(double angleRobot) {
    return angleRobot + getX();
  }

  /**
   * Use this fnction to get distance to target.
   * <p>
   * 
   * @return the distance to target.
   */
  public double getDistanceToTarget() {
    distance = high / Math.tan(getY() + pitchAngle);
    return distance;
  }

  /**
   * You Don't Need To Use It!!!
   */
  protected void updateValues() {
    lastTx_ = tx_;
    lastTy_ = ty_;
    lastTa_ = ta_;
    lastTs = ts_;

    tx_ = tx.getDouble(0.0);
    ty_ = ty.getDouble(0.0);
    ta_ = ta.getDouble(0.0);
    ts_ = ts.getDouble(0.0);
    

    isValueChange = (lastTx_ != tx_ || lastTy_ != ty_ || lastTa_ != ta_);
  }

  /**
   * Don't use this function!!!
   */
  protected void calculate() {
    if (pitchAngle > 90) {
      distance = high / Math.tan(Math.toRadians(pitchAngle + getY()));
    } else {
      distance = Math.tan(Math.toRadians(pitchAngle + getY())) * high;
    }

    if (cameraDistanceFromCenterRobot == 0) {
      angle = getX();
    } else {
      double H = distance * Math.sin(Math.toRadians(yawAngle - getX()));
      double B = H / Math.tan(Math.toRadians(yawAngle - getX()));

      if (B > cameraDistanceFromCenterRobot) {
        B -= cameraDistanceFromCenterRobot;
        angle = 90 - Math.toDegrees(Math.atan(H / B));
      } else {
        B = cameraDistanceFromCenterRobot - B;
        angle = -(90 - Math.toDegrees(Math.atan(H / B)));
      }
    }
  }

  /**
   * put limelight data in Dashboard.
   */
  public void putLimlightValuesInDashboard() {
    System.out.println();
    putInDashboard("Is Valid", isValid(), 0, 0);
    putInDashboard("Tx", tx_, 0, 1);
    putInDashboard("Ty", ty_, 0, 2);
    putInDashboard("Ta", ta_, 0, 3);
    putInDashboard("Ts", ts_, 1, 1);

    putInDashboard("Angle", getAngleToTarget(RobotContainer.navx.getAngle()), 1, 3);
    putInDashboard("Distance", getDistanceToTarget(), 1, 2);
  }

  public void putInDashboard(String key, Object value, int columnIndex, int rowIndex) {
    if (keys.containsKey(key)) {
      keys.get(key).setValue(value);
    } else {
      keys.put(key, tab.add(key, value).withPosition(columnIndex, rowIndex).getEntry());
    }
  }

  public void putInDashboard(String key, Object value) {
    if (keys.containsKey(key)) {
      keys.get(key).setValue(value);
    } else {
      keys.put(key, tab.add(key, value).getEntry());
    }
  }

  /**
   * Limelight enum for camera modes.
   */
  public enum limelightCameraMode {
    kVision, kView, kUSB
  }

  /**
   * Limelight enum for LED modes.
   */
  public enum limelightLEDMode {
    kOn, kOff, kBlink
  }

  /**
   * Limelight enum for stream modes.
   */
  public enum limelightStreamMode {
    kStandard, kPiPMain, kPiPSecondary
  }

  private class LimelightUpdateValue extends Command {
    protected Limelight limelight;

    public LimelightUpdateValue(Limelight limelight) {
      addRequirements(limelight);

      this.limelight = limelight;
    }

    @Override
    public boolean runsWhenDisabled() {
      return true;
    }

    @Override
    public void execute() {
      limelight.putLimlightValuesInDashboard();
    }

    @Override
    public boolean isFinished() {
      return false;
    }
  }

  public static class Builder {
    protected double yawAngle = 0, pitchAngle = 90, high = 0, cameraDistanceFromCenterRobot = 0;
    protected String table;

    /**
     * @param table - the table were limelight put all the data.
     */
    public Builder(String table) {
      this.table = table;
    }

    /**
     * Use defult table as "limelight".
     */
    public Builder() {
      this.table = "limelight";
    }

    /**
     * @param angle the yaw angle from the center camara (e.g. team 1690 2019
     *              limelight angle).
     *              <p>
     *              0 - no angle to the side.
     *              <p>
     *              Defult angle is 0.
     */
    public Builder setYawAngle(double angle) {
      this.yawAngle = angle;
      return this;
    }

    /**
     * @param angle the pich angle from the center of the camera to the groand.
     *              <p>
     *              angle = 90 - limelight stright.
     *              <p>
     *              angle higher then 90 - limelight with angle up.
     *              <p>
     *              angle smoller 90 - limelight with angle down.
     *              <p>
     *              Defult angle is 90 from the graond.
     */
    public Builder setPitchAngle(double angle) {
      this.pitchAngle = angle;
      return this;
    }

    /**
     * @param high the high between the center camera to center target.
     */
    public Builder setHigh(double high) {
      this.high = high;
      return this;
    }

    /**
     * @param distance the distance between center camera to center robot.
     *                 <p>
     *                 0 - use the limelight angle.
     *                 <P>
     *                 Defult distance is 0.
     */
    public Builder setCameraFromRobotCenter(double distance) {
      this.cameraDistanceFromCenterRobot = distance;
      return this;
    }

    public Limelight build() {
      return new Limelight(this);
    }
  }
}