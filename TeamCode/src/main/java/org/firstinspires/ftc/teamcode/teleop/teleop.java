package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="teleop please run this", group="Iterative OpMode")
public class teleop extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FLM = null;
    private DcMotor BLM = null;
    private DcMotor FRM = null;
    private DcMotor BRM = null;
    boolean invertX = false;
    boolean invertY = false;
    double computedX = 0;
    double computedY = 0;
    double deadZone = 0;
    double saturation = 0;
    double sensitivity = 0;
    double range = 0;

    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");
        FLM = hardwareMap.get(DcMotor.class, "FLM");
        BLM = hardwareMap.get(DcMotor.class, "BLM");
        FRM = hardwareMap.get(DcMotor.class, "FRM");
        BRM = hardwareMap.get(DcMotor.class, "BRM");


        FLM.setDirection(DcMotor.Direction.FORWARD);
        BLM.setDirection(DcMotor.Direction.FORWARD);
        FRM.setDirection(DcMotor.Direction.REVERSE);
        BRM.setDirection(DcMotor.Direction.REVERSE);



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