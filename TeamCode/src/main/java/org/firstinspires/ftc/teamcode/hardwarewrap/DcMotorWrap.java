package org.firstinspires.ftc.teamcode.hardwarewrap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

// wrapper class for a DcMotor device
public class DcMotorWrap {

    // motor reference
    public DcMotor motor;

    // name in HardwareMap
    public String name;

    // base motor speed
    public double speed;

    // init, get motor from HardwareMap
    public DcMotorWrap(HardwareMap map, String name, double speed) {
        motor = map.get(DcMotor.class, name);
        this.name = name;
        this.speed = speed;
        resetMode();
    }

    // run at constant power
    public void run(double speed) {
        motor.setPower(speed * this.speed);
    }

    // reset motor mode to Run With Encoder
    public void resetMode() {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
