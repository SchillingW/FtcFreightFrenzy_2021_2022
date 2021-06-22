package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

// base robot object
public class Robot {

    public DcMotor[] dcMotors;
    public String[] dcMotorNames;
    public Servo[] servos;
    public String[] servoNames;
    public CRServo[] crServos;
    public String[] crServoNames;

    public Robot(HardwareMap map) {
        for (int i = 0; i < dcMotors.length; i++) {dcMotors[i] = map.get(DcMotor.class, dcMotorNames[i]);}
        for (int i = 0; i < servos.length; i++) {servos[i] = map.get(Servo.class, servoNames[i]);}
        for (int i = 0; i < crServos.length; i++) {crServos[i] = map.get(CRServo.class, crServoNames[i]);}
    }
}
