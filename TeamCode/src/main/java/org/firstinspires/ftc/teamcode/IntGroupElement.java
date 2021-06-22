package org.firstinspires.ftc.teamcode;

public class IntGroupElement implements IntGroup {

    public int value;

    @Override
    public int[] getIntList() {
        return new int[]{value};
    }

    @Override
    public int getIntCount() {
        return 1;
    }
}
