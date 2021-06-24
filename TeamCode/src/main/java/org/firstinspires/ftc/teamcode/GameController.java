package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

// game controller class, handles controller input
public class GameController {

    // gamepad references
    public Gamepad[] pad;

    // double input indices
    public int stickLX = 0;
    public int stickLY = 1;
    public int stickRX = 2;
    public int stickRY = 3;

    // boolean input indices
    public int buttonA = 0;
    public int buttonB = 1;
    public int buttonX = 2;
    public int buttonY = 3;

    // double input data
    public double[][] doubleInputs = new double[2][4];

    // boolean input data
    public boolean[][] boolInputs = new boolean[2][4];
    public boolean[][] boolInputsLast = new boolean[2][4];
    public boolean[][] boolInputsThis = new boolean[2][4];

    // init, get controller references
    public GameController(Gamepad[] pad) {
        this.pad = pad;
    }

    // update input data
    public void update() {

        // backup last frame data
        boolInputsLast = boolInputs;

        for (int i = 0; i < pad.length; i++) {

            // stickLX
            doubleInputs[i][0] = pad[i].left_stick_x;
            // stickLY
            doubleInputs[i][1] = -pad[i].left_stick_y;
            // stickRX
            doubleInputs[i][2] = pad[i].right_stick_x;
            // stickRY
            doubleInputs[i][3] = -pad[i].right_stick_y;
            // buttonA
            boolInputs[i][0] = pad[i].a;
            // buttonB
            boolInputs[i][1] = pad[i].b;
            // buttonX
            boolInputs[i][2] = pad[i].x;
            // buttonY
            boolInputs[i][3] = pad[i].y;

            // iterate through date, finding this-frame presses
            for (int j = 0; j < boolInputs[i].length; j++) {boolInputsThis[i][j] = boolInputs[i][j] && !boolInputsLast[i][j];}
        }
    }
}
