package org.firstinspires.ftc.teamcode.hardwaresystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardwarewrap.GyroWrap;

// qyro-oriented mecanum drive train device
public class GyroOrientDriveTrain {

    // drive train reference
    public MechDriveTrain driveTrain;

    // gyro sensor reference
    public GyroWrap gyro;

    // init, get drive train and gyro
    public GyroOrientDriveTrain(MechDriveTrain driveTrain, GyroWrap gyro) {
        this.driveTrain = driveTrain;
        this.gyro = gyro;
    }

    // run drive train after rotating the input vector by the opposite of the robot's current angle
    // orients vector to driver rather than bot
    public void run(double x, double y, double rot, Telemetry tele) {

        double angle = gyro.getAngle();

        double hype = GyroWrap.vect2hype(x, y);
        double oldRad = GyroWrap.vect2rad(x, y, hype);
        double newRad = oldRad - angle;
        double newX = GyroWrap.rad2vectX(newRad, hype);
        double newY = GyroWrap.rad2vectY(newRad, hype);
        tele.addData("X", x);
        tele.addData("Y", y);
        tele.addData("Hype", hype);
        tele.addData("OldRad", oldRad);
        tele.addData("Angle", angle);
        tele.addData("NewRad", newRad);
        tele.addData("NewX", newX);
        tele.addData("NewY", newY);
        driveTrain.run(newX, newY, rot);
    }
}
