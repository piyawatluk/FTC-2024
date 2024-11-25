package org.firstinspires.ftc.teamcode.function_test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="spinneyyyngh", group="Iterative OpMode")
public class Test3 extends OpMode

{

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor extender = null;
    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");
        extender = hardwareMap.get(DcMotor.class, "et");
        extender.setDirection(DcMotor.Direction.FORWARD);
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


        if (gamepad1.a == true){
            power = 1;
        }
        else if (gamepad1.b == true){
            power = -1;
        }

        else {
            power = 0;
        }


        extender.setPower(power);


        telemetry.addData("Motors", "Power (%.2f)", power);
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