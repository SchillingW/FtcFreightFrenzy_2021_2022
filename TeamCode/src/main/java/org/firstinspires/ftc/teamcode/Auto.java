package org.firstinspires.ftc.teamcode;

import java.util.List;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

// base autonomous OpMode
public abstract class Auto extends LinearOpMode {

    // declare robot object
    public Robot robot;

    // declare diameters
    public double wheelDiameter = 3;
    public double trainDiameter = 20;

    // calculate circumference
    public double wheelCircumference = wheelDiameter * Math.PI;
    public double trainCircumference = trainDiameter * Math.PI;

    // declare speeds
    public double driveSpeed = 1;
    public double turnSpeed = 1;

    // declare motor errors
    public double driveError = 0.9;
    public double turnError = 1.3;

    // declare measurements
    public double inchPerFoot = 12;

    // declare field movements
    public double initialMoveX = inchPerFoot;
    public double startToShootX = 1.5 * inchPerFoot;
    public double startToShootY = 5 * inchPerFoot;
    public double[] startToWobbleX = {3 * inchPerFoot, inchPerFoot, 3 * inchPerFoot};
    public double[] startToWobbleY = {9 * inchPerFoot, 7 * inchPerFoot, 5 * inchPerFoot};
    public double startToParkX = 2 * inchPerFoot;
    public double startToParkY = 6 * inchPerFoot;

    public abstract Robot getRobot();

    // run on OpMode initialization
    @Override
    public void runOpMode() {

        // initialize robot
        robot = getRobot();
        robot.initVuforia();
        robot.initObjectDetector(hardwareMap);

        // read ring count while waiting for driver to press start
        robot.activateTensorFlow();
        telemetry.addData("Sense", "Start");
        telemetry.update();
        int ringCount = 0;
        while (!opModeIsActive()) {ringCount = getRingCount();}
        telemetry.addData("Sense", "End");
        telemetry.update();
        robot.stopTensorFlow();
        telemetry.addData("Start", "Auto");
        telemetry.update();

        // aim for high goal
        encoderDriveHorizontal(-initialMoveX);
        encoderDriveVertical(startToShootY);
        encoderDriveHorizontal(startToShootX);

        // shoot 3 rings
        shootAllRings();
        //encoderShoot(2);
        //if (robot.collector != null) {robot.collector.setPower(-1);}
        //encoderShoot(2);
        //if (robot.collector != null) {robot.collector.setPower(0);}

        // drop wobble goal
        encoderDrive(startToWobbleX[ringCount] - startToShootX, startToWobbleY[ringCount] - startToShootY);
        encoderLowerArm();

        // park
        if (startToParkY > startToWobbleY[ringCount]) {encoderDriveHorizontal(startToParkX - startToWobbleX[ringCount]);}
        encoderDriveVertical(startToParkY - startToWobbleY[ringCount]);

        // end OpMode
        //telemetry.addData("Exit", "ExportGyro");
        //telemetry.update();
        //boolean exported = robot.exportGyro();
        telemetry.addData("Exit", "Finished");
        //telemetry.addData("ExportGyro", exported ? "Success" : "Fail");
        telemetry.update();
    }

    // drive horizontally
    public void encoderDriveHorizontal(double distance) {
        double[] driveSpeeds = new double[robot.driveSystem.length];
        for (int i = 0; i < driveSpeeds.length; i++) {driveSpeeds[i] = driveSpeed;}
        robot.encoderMoveLinear(driveSpeeds, robot.driverSpeeds(distance / wheelCircumference * driveError, 0, 0), robot.driveSystem, this);
    }

    // drive vertically
    public void encoderDriveVertical(double distance) {
        double[] driveSpeeds = new double[robot.driveSystem.length];
        for (int i = 0; i < driveSpeeds.length; i++) {driveSpeeds[i] = driveSpeed;}
        robot.encoderMoveLinear(driveSpeeds, robot.driverSpeeds(0, distance / wheelCircumference * driveError, 0), robot.driveSystem, this);
    }

    // drive
    public void encoderDrive(double x, double y) {
        double[] driveSpeeds = new double[robot.driveSystem.length];
        for (int i = 0; i < driveSpeeds.length; i++) {driveSpeeds[i] = driveSpeed;}
        robot.encoderMoveLinear(driveSpeeds, robot.driverSpeeds(x / wheelCircumference * driveError, y / wheelCircumference * driveError, 0), robot.driveSystem, this);
    }

    // turn
    public void encoderTurn(double rotations) {
        double[] driveSpeeds = new double[robot.driveSystem.length];
        for (int i = 0; i < driveSpeeds.length; i++) {driveSpeeds[i] = turnSpeed;}
        robot.encoderMoveLinear(driveSpeeds, robot.driverSpeeds(0, 0, rotations / wheelCircumference * trainCircumference * turnError), robot.driveSystem, this);
    }

    // shoot
    public void encoderShoot(int height) {
        if (robot.belt != null) {robot.belt.setPower(1);}
        if (robot.flicker != null) {robot.flicker.setPosition(1);}
        robot.encoderMoveLinear(robot.ringSpeeds[height], robot.ringRotations[height], robot.ringSystem, this);
        if (robot.belt != null) {robot.belt.setPower(0.5);}
        if (robot.flicker != null) {robot.flicker.setPosition(0);}
        while (opModeIsActive() && robot.flicker != null && robot.flicker.getPosition() > 0) {}
    }

    // abstract shoot all rings
    public abstract void shootAllRings();

    // lower arm
    public void encoderLowerArm() {
        robot.encoderMoveLinear(new double[]{robot.armSpeed}, new double[]{-robot.armRotations}, new int[]{7}, this);
        if (robot.servoArm != null) {robot.servoArm.setPosition(1);}
        robot.encoderMoveLinear(new double[]{robot.armSpeed}, new double[]{robot.armRotations / 2}, new int[]{7}, this);
    }

    public int getRingCount() {
        String label = robot.getObjectDetectionLabel();
        telemetry.addData("RingDetection", label);
        telemetry.update();
        switch (label) {
            case "Null":
                return 2;
            case "None":
                return 2;
            case "Single":
                return 1;
            case "Quad":
                return 0;
            default:
                return 2;
        }
    }

    public String getObjectDetectionLabel() {
        List<Recognition> recognitions = robot.objectDetector.getUpdatedRecognitions();
        if (recognitions == null) {return "Null";
        } else if (recognitions.size() == 0) {return "None";
        } else {return recognitions.get(0).getLabel();}
    }
}
