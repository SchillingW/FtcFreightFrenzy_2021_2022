package org.firstinspires.ftc.teamcode.hardwaresystems;

import org.firstinspires.ftc.teamcode.hardwarewrap.DcMotorWrap;

// two wheel linear drive train device
public class LinearDriveTrain {

    // list of motors, {rf, rb, lf, lb}
    public DcMotorWrap[] motors;

    // base drive speed
    public double linearSpeed;
    public double turnSpeed;

    // distance between opposing
    public double inchesPerRotation;

    // init, get motor references and speed
    public LinearDriveTrain(DcMotorWrap[] motors, double linearSpeed, double turnSpeed, double opposingDistance) {
        this.motors = motors;
        this.linearSpeed = linearSpeed;
        this.turnSpeed = turnSpeed;
        this.inchesPerRotation = opposingDistance * Math.PI;
    }

    // run at constant power
    public void run(double l, double r) {
        motors[0].run(l);
        motors[1].run(r);
    }

    // begin moving drive motors with encoders
    public void startMoveEncoders(double dist, double speed, boolean isStraight) {
        for (int i = 0; i < motors.length; i++) {
            motors[i].startMoveEncoders(dist * (isStraight ? 1 : inchesPerRotation * (i == 0 ? -1 : 1)), speed);
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
    public void moveEncoders(double dist, double speed, boolean isStraight) {
        startMoveEncoders(dist, speed, isStraight);
        while (isBusy()) {
            loopMoveEncoders();
        }
    }
}
