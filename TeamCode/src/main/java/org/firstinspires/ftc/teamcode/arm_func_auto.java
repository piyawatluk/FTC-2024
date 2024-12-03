package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

public class arm_func_auto {
    private Servo sv_1, sv_3, sv_2;
    private CRServo sv_4;
    private DcMotor extender_L = null;
    private DcMotor extender_R = null;
    private DigitalChannel ls = null;

    public void initializeHardware(DcMotor extender_L,DcMotor extender_R, DigitalChannel ls, Servo sv_1,Servo sv_2,Servo sv_3,CRServo sv_4){
        this.extender_L = extender_L;
        this.extender_R = extender_R;
        this.ls = ls;
        this.sv_1 = sv_1;
        this.sv_2 = sv_2;
        this.sv_3 = sv_3;
        this.sv_4 = sv_4;

        ls.setMode(DigitalChannel.Mode.INPUT);

        extender_L.setDirection(DcMotor.Direction.FORWARD);
        extender_R.setDirection(DcMotor.Direction.REVERSE);

        extender_L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extender_R.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extender_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extender_R.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public int moveToPosition(int target_pos){
        int posL = extender_L.getCurrentPosition();
        int posR = extender_R.getCurrentPosition();
        int currentPos = (posL + posR) / 2;

        extender_L.setTargetPosition(target_pos);
        extender_R.setTargetPosition(target_pos);

        extender_L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extender_R.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (currentPos < target_pos) {
            extender_L.setPower(1);
            extender_R.setPower(1);
        } else if (currentPos > target_pos) {
            extender_L.setPower(-1);
            extender_R.setPower(-1);
        } else {
            extender_L.setPower(0);
            extender_R.setPower(0);
        }

        if (!ls.getState()){
            extender_L.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            extender_R.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            extender_L.setPower(0);
            extender_R.setPower(0);
        }

        return currentPos;
    }

}
