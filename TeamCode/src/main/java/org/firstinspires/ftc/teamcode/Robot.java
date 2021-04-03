package org.firstinspires.ftc.teamcode;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.Telemetry;

// base robot object
public class Robot {

    // declare hardware components
    public DcMotor[] drivers = new DcMotor[4];
    public DcMotor collector;
    public DcMotor hook;
    public DcMotor shooter;
    public DcMotor arm;
    public DcMotor elev;
    public DcMotor[] allMotors = new DcMotor[9];
    public CRServo belt;
    public CRServo servoHook;
    public Servo flicker;
    public Servo servoArm;
    public Servo collectPressure;
    public BNO055IMU gyro;

    // declare names
    public String[] driverNames = new String[]{"rf", "rb", "lf", "lb"};
    public String collectorName = "collect";
    public String hookName = "hook";
    public String shooterName = "shooter";
    public String armName = "arm";
    public String beltName = "belt";
    public String elevName = "elevator";
    public String servoHookName = "servoHook";
    public String flickerName = "flicker";
    public String servoArmName = "servoArm";
    public String collectPressureName = "collectPress";
    public String gyroName = "gyro";
    public String fileName = "robotStateSave.txt";

    // declare motor encoder state;
    public boolean[] motorsOn = new boolean[allMotors.length];

    // declare ticks per rotation
    public static double tprHdHex = 28;
    public static double tprCoreHex = 288;
    public static double tprNeveRest = 1120;
    public static double tprHdHex20 = 560;
    public static double tprHdHex60 = 1680;
    public double[] ticksPerRotation;

    // declare motor groups
    public int[] driveSystem = new int[drivers.length];
    public double[] driveSpeeds = new double[drivers.length];
    public boolean sideDrivePolarity;
    public int[] ringSystem = new int[2];
    public boolean collectorPolarity;
    public double hookRotations = 9;
    public double hookSpeed = 0.4;
    public double shooterRotations = 600;
    public double armSpeed = 0.5;
    public double armRotations = 0.7;
    public double elevSpeedUp = 1;
    public double elevSpeedDown = 0.2;
    public double elevSpeedSide = 0.5;
    public double[][] ringSpeeds;
    public double[][] ringRotations;

    // vufori data
    public String vuforiaKey = "AZqx6cn/////AAABmUF3PrieskminEJXAytmwats87gqoTPBI1GON+9j1teIgFm1sUbxkFvKWCbUzY3jzRBoGtNJvd7UT8bCLB/bFzRqFokDIo1+RDtedjRJJ7uqJsIaYe5O74dmlEnmduv9TzVvnCFYO2aUEjLPauypT/dSpP1vHZPBqgIUKiZOYtphAF67DXweX2xWSbs/uxuzZ4K/JpAOvb5y3uxMzdOxTLB2VyvdlF/B5a6dq15F2N5+SPEhoer/WSaIkx0cGcKfmu/Ivr68dfUxiDVQUqAPUUmqZ0tT5ONLaZwJVrFOFhqfzW3iI1cxhGm6aghlMMi+Ry5jBt8aI6ristwe8RmgnGWKshGLuUC6DwVz+ayAEiPu";
    public String odModelAsset = "UltimateGoal.tflite";
    public String labelElement1 = "Quad";
    public String labelElement2 = "Single";
    public double magnification = 2.5;
    public double aspectRatio = 16.0/9.0;
    public float minConfidence = 0.8f;
    public VuforiaLocalizer vuforiaLocalizer;
    public TFObjectDetector objectDetector;

    // declare starting orientation
    public double startRadians = 0;

    // initialize robot
    public Robot(HardwareMap map, Telemetry tele, double[] inputTPR, boolean[] hasServos, double[] shooterSpeeds, boolean drivePolarity, boolean collectPolarity, boolean shootPolarity, boolean armPolarity) {

        // set ticks per rotation as well as data for included motors
        ticksPerRotation = inputTPR;
        sideDrivePolarity = drivePolarity;
        collectorPolarity = collectPolarity;

        // initialize drive motors
        tele.addData("Init", "Drivers");
        tele.update();
        double maxDriveTPR = 0;
        for (int i = 0; i < drivers.length; i++) {maxDriveTPR = Math.max(maxDriveTPR, inputTPR[i]);}
        for (int i = 0; i < drivers.length; i++) {driveSpeeds[i] = inputTPR[i] / maxDriveTPR;}
        for (int i = 0; i < drivers.length; i++) {drivers[i] = inputTPR[i] != 0 ? map.get(DcMotor.class, driverNames[i]) : null;}
        for (int i = 0; i < drivers.length; i++) {allMotors[i] = drivers[i];}
        for (int i = 0; i < drivers.length; i++) {driveSystem[i] = i;}

        // initialize ring system
        tele.addData("Init", "RingSystem");
        tele.update();
        collector = inputTPR[4] != 0 ? map.get(DcMotor.class, collectorName) : null;
        hook = inputTPR[5] != 0 ? map.get(DcMotor.class, hookName) : null;
        shooter = inputTPR[6] != 0 ? map.get(DcMotor.class, shooterName) : null;
        arm = inputTPR[7] != 0 ? map.get(DcMotor.class, armName) : null;
        elev = inputTPR[8] != 0 ? map.get(DcMotor.class, elevName) : null;
        allMotors[4] = collector;
        allMotors[5] = hook;
        allMotors[6] = shooter;
        allMotors[7] = arm;
        allMotors[8] = elev;
        ringSystem[0] = 5;
        ringSystem[1] = 6;
        ringSpeeds = new double[shooterSpeeds.length][ringSystem.length];
        ringRotations = new double[shooterSpeeds.length][ringSystem.length];
        for (int i = 0; i < ringSpeeds.length; i++) {for (int j = 0; j < ringSpeeds[i].length; j++) {
            ringSpeeds[i][j] = j == 0 ? hookSpeed : shooterSpeeds[i] * (shootPolarity ? 1 : -1);
            ringRotations[i][j] = (j == 0 ? hookRotations : -shooterRotations) * ringSpeeds[i][j];
        }} armSpeed *= armPolarity ? 1 : -1;
        armRotations *= armPolarity ? 1 : -1;

        // initialize encoders
        tele.addData("Init", "Encoders");
        tele.update();
        for (int i = 0; i < allMotors.length; i++) {if (allMotors[i] != null) {allMotors[i].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);}}
        for (int i = 0; i < allMotors.length; i++) {if (allMotors[i] != null) {allMotors[i].setMode(DcMotor.RunMode.RUN_USING_ENCODERS);}}

        // initialize servos
        tele.addData("Init", "Servos");
        tele.update();
        belt = hasServos[0] ? map.get(CRServo.class, beltName) : null;
        servoHook = hasServos[1] ? map.get(CRServo.class, servoHookName) : null;
        flicker = hasServos[2] ? map.get(Servo.class, flickerName) : null;
        servoArm = hasServos[3] ? map.get(Servo.class, servoArmName) : null;
        collectPressure = hasServos[4] ? map.get(Servo.class, collectPressureName) : null;

        // initialize gyro sensor
        tele.addData("Init", "Gyro");
        tele.update();
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.mode = BNO055IMU.SensorMode.IMU;
        params.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        params.loggingEnabled = false;
        gyro = map.get(BNO055IMU.class, gyroName);
        gyro.initialize(params);
        while (!gyro.isGyroCalibrated()) {}

        // finish tele
        tele.addData("Init", "Finished");
        tele.update();
    }

    // get driving motor speeds based on linear direction and rotation
    public double[] driverSpeeds(double linearX, double linearY, double rotational) {
        double[] speeds = new double[4];
        speeds[0] = (+ linearX * (sideDrivePolarity ? 1 : -1) - linearY + rotational) * driveSpeeds[0];
        speeds[1] = (- linearX * (sideDrivePolarity ? 1 : -1) - linearY + rotational) * driveSpeeds[1];
        speeds[2] = (+ linearX * (sideDrivePolarity ? 1 : -1) + linearY + rotational) * driveSpeeds[2];
        speeds[3] = (- linearX * (sideDrivePolarity ? 1 : -1) + linearY + rotational) * driveSpeeds[3];
        return speeds;
    }

    /*
    // move motors with encoders
    public void encoderMove(double speed, double[] rotations, boolean[] run) {

        // set motor distances
        int[] targets = new int[4];
        for (int i = 0; i < allMotors.length; i++) {if (run[i]) {targets[i] = allMotors[i].getCurrentPosition() + (int)(rotations[i] * ticksPerRotation[i]);}}
        for (int i = 0; i < allMotors.length; i++) {if (run[i]) {allMotors[i].setTargetPosition(targets[i]);}}
        for (int i = 0; i < allMotors.length; i++) {if (run[i]) {allMotors[i].setMode(DcMotor.RunMode.RUN_TO_POSITION);}}

        // start running motors
        for (int i = 0; i < allMotors.length; i++) {if (run[i]) {allMotors[i].setPower(Math.abs(speed));}}

        // continue running motors while within distance
        boolean areBusy = true;
        while (areBusy) {for (int i = 0; i < allMotors.length; i++) {if (run[i]) {areBusy = areBusy && allMotors[i].isBusy();}}}

        // stop running motors
        for (int i = 0; i < allMotors.length; i++) {if (run[i]) {allMotors[i].setPower(0);}}
        for (int i = 0; i < allMotors.length; i++) {if (run[i]) {allMotors[i].setMode(DcMotor.RunMode.RUN_USING_ENCODERS);}}
    }
    */

    // move motors with encoders
    public void encoderMoveLinear(double[] speed, double[] rotations, int[] index, LinearOpMode op) {
        encoderMoveStart(speed, rotations, index, op);
        while (encoderMoveLoop(op)) {};
    }

    // begin move motors with encoders
    public void encoderMoveStart(double[] speed, double[] rotations, int[] index, LinearOpMode op) {
        if (op == null || op.opModeIsActive()) {
            int[] targets = new int[allMotors.length];
            for (int i = 0; i < index.length; i++) {if (allMotors[index[i]] != null) {targets[index[i]] = allMotors[index[i]].getCurrentPosition() + (int)(rotations[i] * ticksPerRotation[index[i]]);}}
            for (int i = 0; i < index.length; i++) {if (allMotors[index[i]] != null) {allMotors[index[i]].setTargetPosition(targets[index[i]]);}}
            for (int i = 0; i < index.length; i++) {if (allMotors[index[i]] != null) {allMotors[index[i]].setMode(DcMotor.RunMode.RUN_TO_POSITION);}}
            for (int i = 0; i < index.length; i++) {if (allMotors[index[i]] != null) {allMotors[index[i]].setPower(Math.abs(speed[i]));}}
            for (int i = 0; i < index.length; i++) {if (allMotors[index[i]] != null) {motorsOn[index[i]] = true;}}
        }
    }

    // loop move motors with encoders
    public boolean encoderMoveLoop(LinearOpMode op) {
        boolean areBusy = false;
        for (int i = 0; i < allMotors.length; i++) {if (motorsOn[i] && allMotors[i] != null) {
                if (allMotors[i].isBusy() && (op == null || op.opModeIsActive())) {areBusy = true;
                } else {
                    allMotors[i].setPower(0);
                    allMotors[i].setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
                    motorsOn[i] = false;
        }}} return areBusy;
    }

    // get angle of gyro sensor about the upwards axis in radians
    public double gyroAngle() {
        return gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle - startRadians;
    }

    // initialize vuforia localizer
    public void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = vuforiaKey;
        parameters.cameraDirection = CameraDirection.BACK;
        vuforiaLocalizer = ClassFactory.getInstance().createVuforia(parameters);
    }

    public void initObjectDetector(HardwareMap map) {
        int monitorViewId = map.appContext.getResources().getIdentifier("monitorViewId", "id", map.appContext.getPackageName());
        TFObjectDetector.Parameters parameters = new TFObjectDetector.Parameters(monitorViewId);
        parameters.minResultConfidence = minConfidence;
        objectDetector = ClassFactory.getInstance().createTFObjectDetector(parameters, vuforiaLocalizer);
        objectDetector.loadModelFromAsset(odModelAsset, labelElement1, labelElement2);
    }

    public void activateTensorFlow() {
        objectDetector.activate();
        objectDetector.setZoom(magnification, aspectRatio);
    }

    public void stopTensorFlow() {
        objectDetector.shutdown();
    }

    public String getObjectDetectionLabel() {
        List<Recognition> recognitions = objectDetector.getRecognitions();
        if (recognitions == null) {return "Null";
        } else if (recognitions.size() == 0) {return "None";
        } else {return recognitions.get(0).getLabel();}
    }

    // import current gyro orientation to save between OpModes
    public boolean importGyro() {
        try {
            FileInputStream file = new FileInputStream(fileName);
            byte[] bytes = new byte[Double.BYTES];
            file.read(bytes);
            startRadians = ByteBuffer.allocate(Double.BYTES).put(bytes).getDouble();
            file.close();
            return true;
        } catch(IOException e) {return false;}
    }

    // export current gyro orientation to save between OpModes
    public boolean exportGyro() {
        try {
            FileOutputStream file = new FileOutputStream(fileName);
            file.write(ByteBuffer.allocate(Double.BYTES).putDouble(gyroAngle()).array());
            file.close();
            return true;
        } catch(IOException e) {return false;}
    }
}
