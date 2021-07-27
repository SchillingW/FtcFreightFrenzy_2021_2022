package org.firstinspires.ftc.teamcode.hardwaresystems;

import android.util.Pair;

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

    // run drive train at constant power
    public void run(double x, double y, double rot) {
        Pair<Double, Double> linear = orientVector(x, y);
        driveTrain.run(linear.first, linear.second, rot);
    }

    // run drive train using encoders
    public void moveEncoders(double x, double y, double rot, double speed) {
        Pair<Double, Double> linear = orientVector(x, y);
        driveTrain.moveEncoders(linear.first, linear.second, rot, speed);
    }

    // rotates the input vector by the opposite of the robot's current angle
    // orients vector to driver rather than bot
    public Pair<Double, Double> orientVector(double x, double y) {
        double hype = GyroWrap.vect2hype(x, y);
        double oldRad = GyroWrap.vect2rad(x, y, hype);
        double newRad = oldRad - gyro.getAngle();
        double newX = GyroWrap.rad2vectX(newRad, hype);
        double newY = GyroWrap.rad2vectY(newRad, hype);
        return new Pair(newX, newY);
    }
}
