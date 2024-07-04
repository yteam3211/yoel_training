package frc.util;

import frc.util.OutputSystem;
import frc.util.PID.Gains;

public abstract class DriveSystem extends OutputSystem{


    public DriveSystem(String nameSystem) {
        super("DriveSystem");
    }


    abstract public Gains getAutoGains() ;


    abstract public void tank(double outputLeft, double outputRight);


    abstract public void resetSensors(int i);


    abstract public double getRightPosition();


    abstract public double getLeftPosition();


    abstract public double getRightEncoderDistance();


    abstract public double getLeftEncoderDistance();


    abstract public void stopOutput();


    abstract public Gains getTurnGains();



    
}
