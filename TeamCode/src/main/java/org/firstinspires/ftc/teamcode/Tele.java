package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

// base tele OpMode
public abstract class Tele extends OpMode {

    // declare robot object
    public Robot robot;
    public float startRadians = -90;

    // last frame data
    public boolean lastAButton1 = true;
    public boolean lastBButton1 = true;
    public boolean lastYButton1 = true;
    public boolean lastXButton1 = true;
    public boolean lastAButton2 = true;
    public boolean lastBButton2 = true;
    public boolean lastYButton2 = true;
    public boolean lastXButton2 = true;
    public boolean lastRBumper1 = true;
    public boolean lastLBumper1 = true;
    public boolean lastRBumper2 = true;
    public boolean lastLBumper2 = true;
    public boolean lastRTrigger2 = true;
    public boolean lastLTrigger2 = true;
    public boolean lastDPadUp2 = true;
    public boolean lastDPadDown2 = true;

    // user controlled data
    public boolean gyroCompensate = true;
    public boolean driveOn = true;
    public boolean collectorOn = true;
    public boolean reverseOn = false;
    public boolean elevUp = false;
    public boolean objectDetectionOn = false;

    public abstract Robot getRobot();

    // initialize robot
    @Override
    public void init() {
        robot = getRobot();
        robot.startRadians = startRadians;
        if (objectDetectionOn) {
            telemetry.addData("ObjectDetection", "InitVuforia");
            telemetry.update();
            robot.initVuforia();
            telemetry.addData("ObjectDetection", "InitTensorflow");
            telemetry.update();
            robot.initObjectDetector(hardwareMap);
            telemetry.addData("ObjectDetection", "ActivateTensorflow");
            telemetry.update();
            robot.activateTensorFlow();
            telemetry.addData("ObjectDetection", "Finished");
            telemetry.update();
        }
        //telemetry.addData("Init", "ImportGyro");
        //telemetry.update();
        //boolean imported = robot.importGyro();
        //telemetry.addData("ImportGyro", imported ? "Success" : "Fail");
    }

    // loop while running
    @Override
    public void loop() {

        // event input
        boolean aButton1 = gamepad1.a;
        boolean bButton1 = gamepad1.b;
        boolean yButton1 = gamepad1.y;
        boolean xButton1 = gamepad1.x;
        boolean aButton2 = gamepad2.a;
        boolean bButton2 = gamepad2.b;
        boolean yButton2 = gamepad2.y;
        boolean xButton2 = gamepad2.x;
        boolean rBumper1 = gamepad1.right_bumper;
        boolean lBumper1 = gamepad1.left_bumper;
        boolean rBumper2 = gamepad2.right_bumper;
        boolean lBumper2 = gamepad2.left_bumper;
        boolean rTrigger2 = gamepad2.right_trigger > 0;
        boolean lTrigger2 = gamepad2.left_trigger > 0;
        boolean dPadUp2 = gamepad2.dpad_up;
        boolean dPadDown2 = gamepad2.dpad_down;
        if (aButton1 && !lastAButton1) {gyroCompensate = !gyroCompensate;}
        if (bButton1 && !lastBButton1) {robot.startRadians = robot.gyroAngle() + robot.startRadians + startRadians;}
        if (yButton1 && !lastYButton1) {reverseOn = !reverseOn;}
        if (xButton1 && !lastXButton1) {driveOn = !driveOn;}
        /*
        if (aButton2 && !lastAButton2) {robot.encoderMoveStart(robot.ringSpeeds[0], robot.ringRotations[0], robot.ringSystem, null);}
        if (bButton2 && !lastBButton2) {robot.encoderMoveStart(robot.ringSpeeds[1], robot.ringRotations[1], robot.ringSystem, null);}
        if (yButton2 && !lastYButton2) {robot.encoderMoveStart(robot.ringSpeeds[2], robot.ringRotations[2], robot.ringSystem, null);}
        if (xButton2 && !lastXButton2) {robot.encoderMoveStart(robot.ringSpeeds[3], robot.ringRotations[3], robot.ringSystem, null);}
        if (robot.flicker != null) {robot.flicker.setPosition(aButton2 || bButton2 || yButton2 || xButton2 ? 0 : 1);}
        */
        if (robot.elev != null) {robot.elev.setPower(aButton2 || bButton2 || yButton2 || xButton2 ? robot.elevSpeedUp : (dPadDown2 ? robot.elevSpeedDown : 0));}
        if (robot.shooter != null) {robot.shooter.setPower(aButton2 || bButton2 || yButton2 || xButton2 ? robot.ringSpeeds[xButton2 ? 3 : (yButton2 ? 2 : (aButton2 ? 1 : 0))][1] : 0);}
        if (robot.flicker != null) {robot.flicker.setPosition(lTrigger2 ? 0 : 1);}
        if (rBumper2 && !lastRBumper2) {collectorOn = !collectorOn;}
        if (robot.collectPressure != null) {
            if (rTrigger2) {robot.collectPressure.setPosition(1);
            } else {robot.collectPressure.setPosition(0);}
        }
        /*
        if (robot.elev != null) {
            if (DPadUp2) {
                robot.elev.setPower(robot.elevSpeedUp);
            } else if (DPadDown2) {
                robot.elev.setPower(-robot.elevSpeedDown);
            } else if (robot.motorsOn[6]) {
                robot.elev.setPower(robot.elevSpeedSide);
            } else {
                robot.elev.setPower(0);
            }
        }
        */
        /*
        if (DPadUp2 && !robot.motorsOn[8] && !elevUp) {
            robot.encoderMoveStart(new double[]{robot.elevSpeed}, new double[]{robot.elevRotations}, new int[]{8}, null);
            elevUp = true;
        } if (DPadDown2 && !robot.motorsOn[8] && elevUp) {
            robot.encoderMoveStart(new double[]{robot.elevSpeed}, new double[]{-robot.elevRotations}, new int[]{8}, null);
            elevUp = false;
        }*/ if (robot.hook != null && lBumper2 && !lastLBumper2 && robot.motorsOn[5]) {
            robot.hook.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
            robot.motorsOn[5] = false;
        } if (robot.hook != null && !robot.motorsOn[5]) {robot.hook.setPower(lBumper2 ? -robot.hookSpeed : 0);}

        // drive input
        double padX = (gamepad1.dpad_left ? -1 : 0) + (gamepad1.dpad_right ? 1 : 0);
        double padY = (gamepad1.dpad_down ? -1 : 0) + (gamepad1.dpad_up ? 1 : 0);
        double stickX = gamepad1.left_stick_x;
        double stickY = -gamepad1.left_stick_y;
        double rotational = gamepad1.right_stick_x;
        double inputX = padX + stickX;
        double inputY = padY + stickY;
        double hypotenuse = getHypotenuse(inputX, inputY);
        if (hypotenuse > 1) {
            inputX /= hypotenuse;
            inputY /= hypotenuse;
            hypotenuse = 1;
        } double gyroAngle = robot.gyroAngle();
        double inputRadians = getRadiansCos(inputX, inputY, hypotenuse);
        double outputRadians = inputRadians - gyroAngle;

        // add telemetry
        telemetry.addData("GyroCompensate", gyroCompensate);
        telemetry.addData("DriveOn", driveOn);
        telemetry.addData("CollectorOn", collectorOn);
        telemetry.addData("ReverseOn", reverseOn);
        telemetry.addData("ElevUp", elevUp);
        telemetry.addData("StartRadians", formatDouble(robot.startRadians));
        telemetry.addData("PadX", formatDouble(padX));
        telemetry.addData("PadY", formatDouble(padY));
        telemetry.addData("StickX", formatDouble(stickX));
        telemetry.addData("StickY", formatDouble(stickY));
        telemetry.addData("InputX", formatDouble(inputX));
        telemetry.addData("InputY", formatDouble(inputY));
        telemetry.addData("Hypotenuse", formatDouble(hypotenuse));
        telemetry.addData("Gyro", formatDouble(gyroAngle));
        telemetry.addData("InputRadians", formatDouble(inputRadians));
        telemetry.addData("OutputRadians", formatDouble(outputRadians));
        telemetry.addData("ObjectDetectionLabel", objectDetectionOn ? robot.getObjectDetectionLabel() : "Off");

        // send output
        robot.encoderMoveLoop(null);
        if (robot.collector != null) {robot.collector.setPower(collectorOn ? (robot.collectorPolarity ? 1 : -1) : 0);}
        if (robot.servoHook != null) {robot.servoHook.setPower(collectorOn ? 0 : 0.5);}
        if (robot.arm != null) {robot.arm.setPower(gamepad2.left_stick_y * robot.armSpeed);}
        if (robot.servoArm != null && gamepad2.right_stick_y != 0) {robot.servoArm.setPosition(gamepad2.right_stick_y > 0 ? 1 : 0);}
        int reverseFactor = reverseOn ? -1 : 1;
        if (gyroCompensate) {setDrivePower(getXFromRadians(outputRadians, hypotenuse) * reverseFactor, getYFromRadians(outputRadians, hypotenuse) * reverseFactor, rotational);
        } else {setDrivePower(inputX * reverseFactor, inputY * reverseFactor, rotational);}

        // finish loop
        lastAButton1 = aButton1;
        lastBButton1 = bButton1;
        lastYButton1 = yButton1;
        lastXButton1 = xButton1;
        lastAButton2 = aButton2;
        lastBButton2 = bButton2;
        lastYButton2 = yButton2;
        lastXButton2 = xButton2;
        lastRBumper1 = rBumper1;
        lastLBumper1 = lBumper1;
        lastRBumper2 = rBumper2;
        lastLBumper2 = lBumper2;
        lastRTrigger2 = rTrigger2;
        lastLTrigger2 = lTrigger2;
        lastDPadUp2 = dPadUp2;
        lastDPadDown2 = dPadDown2;
        telemetry.update();
    }

    // end OpMode
    @Override
    public void stop() {
        if (robot.collector != null) {robot.collector.setPower(0);}
        setDrivePower(0, 0, 0);
        if (objectDetectionOn) {robot.stopTensorFlow();}
        telemetry.update();
    }

    // start driving based on linear direction and rotation
    public void setDrivePower(double linearX, double linearY, double rotational) {
        double[] speed = robot.driverSpeeds(linearX, linearY, rotational);
        for (int i = 0; i < 4; i++) {if (robot.drivers[i] != null) {robot.drivers[i].setPower(driveOn ? speed[i] : 0);}}
        telemetry.addData("OutputX", formatDouble(linearX));
        telemetry.addData("OutputY", formatDouble(linearY));
        telemetry.addData("Rotational", formatDouble(rotational));
    }

    public static double getHypotenuse(double x, double y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static double getRadiansCos(double x, double y, double hypotenuse) {
        return Math.acos(x / (hypotenuse == 0 ? 1 : hypotenuse)) * (y < 0 ? -1 : 1) + (y < 0 ? Math.PI * 2 : 0);
    }

    public static double getRadiansSin(double x, double y, double hypotenuse) {
        return Math.asin(y / (hypotenuse == 0 ? 1 : hypotenuse)) * (x < 0 ? -1 : 1) + (x < 0 ? Math.PI : (y < 0 ? Math.PI * 2 : 0));
    }

    public static double getXFromRadians(double radians, double hypotenuse) {
        return Math.cos(radians) * hypotenuse;
    }

    public static double getYFromRadians(double radians, double hypotenuse) {
        return Math.sin(radians) * hypotenuse;
    }

    public static String formatDouble(double value) {
        String valueStr = (value + "");
        boolean positive = valueStr.charAt(0) != '-';
        int stringLen = valueStr.length();
        int substringLen = positive ? 4 : 5;
        String truncateAndSign = (positive ? "+" : "") + valueStr.substring(0, Math.min(stringLen, substringLen));
        String extraZeros = new String(new char[Math.max(0, substringLen - stringLen)]).replace("\0", "0");
        return truncateAndSign + extraZeros;
    }
}
