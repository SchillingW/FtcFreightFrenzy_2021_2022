package org.firstinspires.ftc.teamcode.hardwarewrap;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

// base wrapper class for Servo device
public class ServoWrap {

    // servo reference
    public Servo servo;

    // name in HardwareMap
    public String name;

    // outer limits for servo movement
    public double minLimit;
    public double maxLimit;

    // init, get servo from HardwareMap
    public ServoWrap(HardwareMap map, String name, double minLimit, double maxLimit) {
        servo = map.get(Servo.class, name);
        this.name = name;
        this.maxLimit = maxLimit;
        this.minLimit = minLimit;
    }

    // run to target position
    public void run(double target) {
        // use linear interpolation to normalize a 0-1 range target to between the servo limits
        servo.setPosition((maxLimit - minLimit) * target + minLimit);
    }
}
