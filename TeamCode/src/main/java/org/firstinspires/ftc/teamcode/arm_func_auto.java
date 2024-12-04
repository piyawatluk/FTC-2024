package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

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
    public boolean rightmotor = true;


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

    public int extender_func(int targetPosition) { // Takes targetPosition as input
        DcMotor currentmotor;


        if(rightmotor){
            currentmotor = extender_R;
            telemetry.addData("Active Motor", "Right Motor");
        }
        else{
            currentmotor = extender_L;
            telemetry.addData("Active Motor", "Left Motor");
        }

        int pos = currentmotor.getCurrentPosition(); // References encoder position from the currently using side

        // Set target positions for both motors
        currentmotor.setTargetPosition(targetPosition);

        currentmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Adjust power based on current position relative to the target
        if (pos < targetPosition) {
            currentmotor.setPower(1);
        } else if (pos > targetPosition) {
            currentmotor.setPower(-1);
        } else { // Target reached
            currentmotor.setPower(0);
        }

        // Handle limit switch: stop the motors if the limit switch is pressed
        if (!ls.getState()) {
            currentmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            currentmotor.setPower(0);
        }

        return pos; // Return the current position
    }

}
