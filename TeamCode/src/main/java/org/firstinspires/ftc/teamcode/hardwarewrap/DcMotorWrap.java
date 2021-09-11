package org.firstinspires.ftc.teamcode.hardwarewrap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

// wrapper class for a DcMotor device
public class DcMotorWrap {

    // telemetry device for debugging
    public Telemetry tele;

    // motor reference
    public DcMotor motor;

    // name in HardwareMap
    public String name;

    // base motor speed
    public double speed;

    // ticks per motor rotation
    public double ticksPerInch;

    // is motor currently moving using encoders
    public boolean isBusy;

    // init, get motor from HardwareMap
    public DcMotorWrap(Telemetry tele, HardwareMap map, String name, double wheelDiameter, double gearRatio, double speed, double tpr) {

        this.tele = tele;

        motor = map.get(DcMotor.class, name);
        this.name = name;
        this.speed = speed / gearRatio;
        this.ticksPerInch = (tpr / gearRatio) / (wheelDiameter * Math.PI);

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // run at constant power
    public void run(double speed) {
        tele.addData("run motor " + name, speed * this.speed);
        motor.setPower(speed * this.speed);
    }

    // begin running motors with encoders
    public void startMoveEncoders(double distance, double speed) {
        double thisSpeed = this.speed * speed;
        motor.setTargetPosition(motor.getCurrentPosition() + (int)(distance * ticksPerInch) * (int)Math.signum(thisSpeed));
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(Math.abs(thisSpeed));
        isBusy = true;
    }

    // check if encoder movement is complete
    public void loopMoveEncoders() {
        if (!motor.isBusy()) {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            isBusy = false;
        }
    }
}
