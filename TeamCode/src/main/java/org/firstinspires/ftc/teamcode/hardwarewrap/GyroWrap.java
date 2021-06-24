package org.firstinspires.ftc.teamcode.hardwarewrap;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

// wrapper class for a Gyro sensor device
public class GyroWrap {

    // sensor reference
    public BNO055IMU sensor;

    // name in HardwareMap
    public String name;

    // data parameters
    public int axis;
    public boolean flip;

    // init, get gyro reference and set parameters
    public GyroWrap(LinearOpMode op, HardwareMap map, String name, int axis, boolean flip) {

        // set parameters, measure in radians
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.mode = BNO055IMU.SensorMode.IMU;
        params.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        params.loggingEnabled = false;

        // get sensor reference
        sensor = map.get(BNO055IMU.class, name);
        this.name = name;
        this.axis = axis;
        this.flip = flip;

        // initialize sensor
        sensor.initialize(params);

        // calibrate sensor
        while (!sensor.isGyroCalibrated() && (op == null || op.opModeIsActive())) {}
    }

    // get current gyro angle around active axis
    public double getAngle() {
        AxesOrder order = new AxesOrder[]{AxesOrder.XYZ, AxesOrder.YZX, AxesOrder.ZXY}[axis];
        Orientation orient = sensor.getAngularOrientation(AxesReference.INTRINSIC, order, AngleUnit.RADIANS);
        return orient.firstAngle * (flip ? -1 : 1);
    }

    // convert radian angle measurement to rotations
    public static double rad2rot(double rad) {
        return rad * 180 / Math.PI;
    }

    // get hypotenuse of vector
    public static double vect2hype(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    // get radians of vector
    public static double vect2rad(double x, double y, double hype) {
        return Math.acos(x / (hype == 0 ? 1 : hype)) * (y < 0 ? -1 : 1) + (y < 0 ? Math.PI * 2 : 0);
    }

    // get x component of vector from radians
    public static double rad2vectX(double rad, double hype) {
        return Math.cos(rad) * hype;
    }

    // get y component of vector from radians
    public static double rad2vectY(double rad, double hype) {
        return Math.sin(rad) * hype;
    }
}
