package org.firstinspires.ftc.teamcode.function_test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="DC_exteder_test", group="Iterative OpMode")
public class TEST2 extends OpMode

{

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor extender = null;
    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");
        extender = hardwareMap.get(DcMotor.class, "et");
        extender.setDirection(DcMotor.Direction.REVERSE);
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
        int max_pos = 6200;
        int min_pos = 700;
        double power;
        double drive = -gamepad1.left_stick_y;

        if (drive > 0){
            power = 1;
        }
        else if (drive < 0){
            power = -1;
        }

        else {
            power = 0;
        }

        int pos = extender.getCurrentPosition();


        if ((power > 0 && pos < max_pos) || (power < 0 && pos > min_pos)) {

            extender.setPower(power);

        }

        else {

            extender.setPower(0);

        }


        //extender.setPower(power);


        telemetry.addData("input (%.2f)", drive);
        telemetry.addData("Motors", "Power (%.2f)", power);
        telemetry.addData("Pos", pos);
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