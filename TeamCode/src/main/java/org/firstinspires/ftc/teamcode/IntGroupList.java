package org.firstinspires.ftc.teamcode;

public class IntGroupList implements IntGroup {

    public IntGroup[] value;

    @Override
    public int[] getIntList() {
        int index = 0;
        int[] list = new int[getIntCount()];
        for (int i = 0; i < value.length; i++) {
            int[] subList = value[i].getIntList();
            for (int j = 0; j < subList.length; i++) {
                list[index] = subList[j];
                index++;
            }
        }
        return list;
    }

    @Override
    public int getIntCount() {
        int count = 0;
        for (int i = 0; i < value.length; i++) {
            count += value[i].getIntCount();
        }
        return count;
    }
}
