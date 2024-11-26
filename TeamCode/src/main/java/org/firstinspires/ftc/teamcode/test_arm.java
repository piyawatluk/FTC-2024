package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Arm", group = "Iterative OpMode")
public class test_arm extends OpMode

{
    //hardware name declaration :)
    private Servo sv_1, sv_3, sv_2;
    private CRServo sv_4;
    private DcMotor extender_L = null;
    private DcMotor extender_R = null;
    private DigitalChannel ls = null;


    //setting intake speed
    private double in_speed = 1.0;
    private double out_speed = -1.0;


    //setting arm servo position in degree
    double pos1 = 45;
    double pos2 = 90;
    double pos3 = 135;

    @Override
    public void init() {

        //hardware pin name declaration
        sv_1 = hardwareMap.get(Servo.class, "sv_1");
        sv_2 = hardwareMap.get(Servo.class, "sv_2");
        sv_3 = hardwareMap.get(Servo.class, "sv_3");
        sv_4 = hardwareMap.get(CRServo.class, "sv_4");
        extender_L = hardwareMap.get(DcMotor.class, "et_1");
        extender_R = hardwareMap.get(DcMotor.class, "et_2");
        ls = hardwareMap.get(DigitalChannel.class, "ls");

        //initially stop the intake motor
        sv_4.setPower(0);

        //hardware behavior declaration
        ls.setMode(DigitalChannel.Mode.INPUT);

        extender_L.setDirection(DcMotor.Direction.FORWARD);
        extender_R.setDirection(DcMotor.Direction.REVERSE);

        extender_L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extender_R.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extender_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extender_R.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized");
    }

    public int extender_func() {                     //lift function
        int pos_L = extender_L.getCurrentPosition(); // this function only reference encoder position from the left side

        if (gamepad1.x) {
            extender_L.setTargetPosition(6200);
            extender_R.setTargetPosition(6200);

            extender_L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            extender_R.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            extender_L.setPower(1);
            extender_R.setPower(1);
        } else if (gamepad1.b) {
            extender_L.setTargetPosition(0);
            extender_R.setTargetPosition(0);

            extender_L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            extender_R.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            extender_L.setPower(-1);
            extender_R.setPower(-1);

        } else if (gamepad1.a) {
            extender_L.setTargetPosition(2400);
            extender_R.setTargetPosition(2400);

            extender_L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            extender_R.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (pos_L < 3100) {
                extender_L.setPower(1);
                extender_R.setPower(1);
            } else {
                extender_L.setPower(-1);
                extender_R.setPower(-1);
            }

        } else if (!ls.getState()) { // If limit switch is pressed
            extender_L.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            extender_R.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            extender_L.setPower(0);
            extender_R.setPower(0);
        }


        return pos_L; // Return the current position of extender_L
    }


    private int dPadCount = 0;
    private boolean wasDpadUpPressed = false;
    private boolean wasDpadDownPressed = false;
    private void controlServo() {
        // Handle D-pad up and down for servo position control
        if (gamepad2.dpad_up && !wasDpadUpPressed) {
            dPadCount = Math.min(dPadCount + 1, 2); // Cap at 2
        } else if (gamepad2.dpad_down && !wasDpadDownPressed) {
            dPadCount = Math.max(dPadCount - 1, 0); // Cap at 0
        }

        // Store button states for next loop
        wasDpadUpPressed = gamepad2.dpad_up;
        wasDpadDownPressed = gamepad2.dpad_down;

        // Set servo position based on dPadCount
        if (dPadCount == 0) {
            sv_1.setPosition(pos1);
            sv_3.setPosition(180 - pos1);
        } else if (dPadCount == 1) {
            sv_1.setPosition(pos2);
            sv_3.setPosition(180 - pos2);
        } else if (dPadCount == 2) {
            sv_1.setPosition(pos3);
            sv_3.setPosition(180 - pos3);
        }

        telemetry.addData("dPadCount", dPadCount);
        telemetry.update();
    }

    private void controlCRServo() {
        if (gamepad2.left_bumper) {
            sv_4.setPower(in_speed);
        } else if (gamepad2.left_trigger > 0.1) {
            sv_4.setPower(out_speed);
        } else {
            sv_4.setPower(0); // Stop servo if no input
        }
    }

    private void gripper(){
        if (gamepad2.left_bumper == true){
            sv_2.setPosition(0.5);
        } else if (gamepad2.right_trigger > 0.5) {
            sv_2.setPosition(0.8);
        }
    }

    @Override
    public void loop() {

        extender_func();
        controlServo();
        controlCRServo();
        gripper();

        telemetry.addData("Pos", extender_func());
        telemetry.update();

    }
}