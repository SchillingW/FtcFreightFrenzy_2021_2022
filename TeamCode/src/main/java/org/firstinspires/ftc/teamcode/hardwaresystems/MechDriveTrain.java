package org.firstinspires.ftc.teamcode.hardwaresystems;

import org.firstinspires.ftc.teamcode.hardwarewrap.DcMotorWrap;

// quad mecanum drive train device
public class MechDriveTrain {

    // list of motors, {rf, rb, lf, lb}
    public DcMotorWrap[] motors;

    // base drive speed
    public double linearSpeed;
    public double turnSpeed;

    // init, get motor references and speed
    public MechDriveTrain(DcMotorWrap[] motors, double linearSpeed, double turnSpeed) {
        this.motors = motors;
        this.linearSpeed = linearSpeed;
        this.turnSpeed = turnSpeed;
    }

    // run at constant power
    public void run(double x, double y, double rot) {
        // rf
        motors[0].run((- x + y) * linearSpeed + rot * turnSpeed);
        // rb
        motors[1].run((+ x + y) * linearSpeed + rot * turnSpeed);
        // lf
        motors[2].run((- x - y) * linearSpeed + rot * turnSpeed);
        // lb
        motors[3].run((+ x - y) * linearSpeed + rot * turnSpeed);
    }
}
