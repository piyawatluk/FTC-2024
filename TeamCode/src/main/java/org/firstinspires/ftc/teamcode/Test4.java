package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Driver_lift_button", group="Iterative OpMode")
public class Test4 extends OpMode

{

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor extender_L = null;
    private DcMotor extender_R = null;

    private DigitalChannel ls = null;
    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");


        extender_L = hardwareMap.get(DcMotor.class, "et_1");
        extender_R = hardwareMap.get(DcMotor.class, "et_2");


        ls = hardwareMap.get(DigitalChannel.class, "ls");
        ls.setMode(DigitalChannel.Mode.INPUT);

        extender_L.setDirection(DcMotor.Direction.FORWARD);
        extender_R.setDirection(DcMotor.Direction.REVERSE);

        extender_L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extender_R.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extender_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extender_R.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized");

    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }

    public void extender_func(){
        double power;
        int pos_L = extender_L.getCurrentPosition(); // only ref from left side

        if(gamepad1.x == true){
            extender_L.setTargetPosition(6200);
            extender_R.setTargetPosition(6200);

            extender_L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            extender_R.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            extender_L.setPower(1);
            extender_R.setPower(1);
        }

        else if (gamepad1.b == true){
            extender_L.setTargetPosition(0);
            extender_R.setTargetPosition(0);

            extender_L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            extender_R.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            extender_L.setPower(-1);
            extender_R.setPower(-1);

        }

        else if (gamepad1.a == true){
            extender_L.setTargetPosition(3100);
            extender_R.setTargetPosition(3100);

            extender_L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            extender_R.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (pos_L < 3100){
                extender_L.setPower(1);
                extender_R.setPower(1);
            }

            else{

                extender_L.setPower(-1);
                extender_R.setPower(-1);

            }

            extender_L.setPower(-1);
            extender_R.setPower(-1);
        }

        else if (ls.getState() == false){

            extender_L.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            extender_R.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            extender_L.setPower(0);
            extender_R.setPower(0);

        }

        telemetry.addData("Pos", pos_L);
        telemetry.update();
    }

    @Override
    public void loop() {
        extender_func();
    }
    @Override
    public void stop() {
    }

}