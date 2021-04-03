package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

// base autonomous OpMode
public abstract class AutoNoWobble extends LinearOpMode {

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
    public double[] ringHeights = {19, 22};

    // declare field movements
    public double startToShootX = 1.5 * inchPerFoot;
    public double startToShootY = 5 * inchPerFoot;
    public double startToParkY = 6 * inchPerFoot;

    public abstract Robot getRobot();

    // run on OpMode initialization
    @Override
    public void runOpMode() {

        // initialize robot
        robot = getRobot();

        // wait for driver to press start
        waitForStart();
        telemetry.addData("Start", "Auto");
        telemetry.update();

        // aim for high goal
        encoderDriveVertical(startToShootY);
        encoderDriveHorizontal(startToShootX);

        // shoot 3 rings
        shootAllRings();
        //encoderShoot(2);
        //if (robot.collector != null) {robot.collector.setPower(-1);}
        //encoderShoot(2);
        //if (robot.collector != null) {robot.collector.setPower(0);}

        // park
        encoderDriveVertical(startToParkY - startToShootY);

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
}
