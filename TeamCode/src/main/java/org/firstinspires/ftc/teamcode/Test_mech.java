package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="mechanum_test", group="Iterative OpMode")
public class Test_mech extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FLM = null;
    private DcMotor BLM = null;
    private DcMotor FRM = null;
    private DcMotor BRM = null;
    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");
        FLM = hardwareMap.get(DcMotor.class, "FLM");
        BLM = hardwareMap.get(DcMotor.class, "BLM");
        FRM = hardwareMap.get(DcMotor.class, "FRM");
        BRM = hardwareMap.get(DcMotor.class, "BRM");


        FLM.setDirection(DcMotor.Direction.REVERSE);
        BLM.setDirection(DcMotor.Direction.REVERSE);
        FRM.setDirection(DcMotor.Direction.FORWARD);
        BRM.setDirection(DcMotor.Direction.FORWARD);



        telemetry.addData("Status", "Initialized");

    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }

    public void move_func(){
        double y = -gamepad1.left_stick_y; // Remember, Y stick is reversed!
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        y = Math.signum(y)*y*y;
        x = Math.signum(x)*x*x;
        rx = Math.signum(rx)*rx*rx;


        FLM.setPower(y - x - rx);
        BLM.setPower(y + x - rx);
        FRM.setPower(y + x + rx);
        BRM.setPower(y - x + rx);

    }

    @Override
    public void loop() {
        move_func();
    }
    @Override
    public void stop() {
    }

}