package org.firstinspires.ftc.teamcode.hardwaresystems;

import org.firstinspires.ftc.teamcode.hardwarewrap.DcMotorWrap;

// quad mecanum drive train device
public class LinearDriveTrain {

    // list of motors, {rf, rb, lf, lb}
    public DcMotorWrap[] motors;

    // base drive speed
    public double linearSpeed;
    public double turnSpeed;

    // distance between diagonal wheels
    public double inchesPerRotation;

    // init, get motor references and speed
    public LinearDriveTrain(DcMotorWrap[] motors, double linearSpeed, double turnSpeed, double diagonalDistance) {
        this.motors = motors;
        this.linearSpeed = linearSpeed;
        this.turnSpeed = turnSpeed;
        this.inchesPerRotation = diagonalDistance * Math.PI;
    }

    // run at constant power
    public void run(double x, double y, double rot) {
        double[] speeds = calculateSpeeds(x, y, rot);
        for (int i = 0; i < motors.length; i++) {
            motors[i].run(speeds[i]);
        }
    }

    // begin moving drive motors with encoders
    public void startMoveEncoders(double x, double y, double rot, double speed) {
        double[] distances = calculateSpeeds(x, y, rot * inchesPerRotation);
        for (int i = 0; i < motors.length; i++) {
            motors[i].startMoveEncoders(distances[i], speed);
        }
    }

    // loop encoder movement for drive motors
    public void loopMoveEncoders() {
        for (int i = 0; i < motors.length; i++) {
            motors[i].loopMoveEncoders();
        }
    }

    // are all drive motors busy
    public boolean isBusy() {
        boolean busy = true;
        for (int i = 0; i < motors.length; i++) {
            busy = busy && motors[i].isBusy;
        }
        return busy;
    }

    // drive using encoders
    public void moveEncoders(double x, double y, double rot, double speed) {
        startMoveEncoders(x, y, rot, speed);
        while (isBusy()) {
            loopMoveEncoders();
        }
    }

    // get wheel speeds for dimensional speeds
    public double[] calculateSpeeds(double x, double y, double rot) {
        return new double[] {
                // rf
                (+ x - y) * linearSpeed + rot * turnSpeed,
                // rb
                (- x - y) * linearSpeed + rot * turnSpeed,
                // lf
                (+ x + y) * linearSpeed + rot * turnSpeed,
                // lb
                (- x + y) * linearSpeed + rot * turnSpeed
        };
    }
}
