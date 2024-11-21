package org.firstinspires.ftc.teamcode.drive.opmode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;


@TeleOp(name = "motor_calibrator", group = "drive")
public class motor_DIR_ENCO extends LinearOpMode {
    private DcMotor FLM = null;
    private DcMotor BLM = null;
    private DcMotor FRM = null;
    private DcMotor BRM = null;



    @Override
    public void runOpMode() {

        FLM = hardwareMap.get(DcMotor.class, "FLM");
        BLM = hardwareMap.get(DcMotor.class, "BLM");
        FRM = hardwareMap.get(DcMotor.class, "FRM");
        BRM = hardwareMap.get(DcMotor.class, "BRM");


        FLM.setDirection(DcMotor.Direction.REVERSE);
        BLM.setDirection(DcMotor.Direction.REVERSE);
        FRM.setDirection(DcMotor.Direction.FORWARD);
        BRM.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addLine("Press play to begin the debugging motor direction");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        telemetry.clearAll();
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.HTML);

        while (!isStopRequested()) {
            telemetry.addLine("Press each button to turn on its respective motor");
            telemetry.addLine();
            telemetry.addLine("<font face=\"monospace\">Xbox/PS4 Button - Motor</font>");
            telemetry.addLine("<font face=\"monospace\">&nbsp;&nbsp;X / ▢&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Front Left</font>");
            telemetry.addLine("<font face=\"monospace\">&nbsp;&nbsp;Y / Δ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Front Right</font>");
            telemetry.addLine("<font face=\"monospace\">&nbsp;&nbsp;B / O&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Rear&nbsp;&nbsp;Right</font>");
            telemetry.addLine("<font face=\"monospace\">&nbsp;&nbsp;A / X&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Rear&nbsp;&nbsp;Left</font>");
            telemetry.addLine();

            if(gamepad1.x) {
                FLM.setPower(1);
                telemetry.addLine("Running Motor: Front Left");
                telemetry.addData("FLM_MOTOR_ENCODER_READINGS", FLM.getCurrentPosition());
            } else if(gamepad1.y) {
                FRM.setPower(1);
                telemetry.addLine("Running Motor: Front Right");
                telemetry.addData("FRM_MOTOR_ENCODER_READINGS", FRM.getCurrentPosition());
            } else if(gamepad1.b) {
                BRM.setPower(1);
                telemetry.addLine("Running Motor: Rear Right");
                telemetry.addData("BRM_MOTOR_ENCODER_READINGS", BRM.getCurrentPosition());
            } else if(gamepad1.a) {
                BLM.setPower(1);
                telemetry.addLine("Running Motor: Rear Left");
                telemetry.addData("BLM_MOTOR_ENCODER_READINGS", BLM.getCurrentPosition());
            } else {
                FLM.setPower(0);
                FRM.setPower(0);
                BLM.setPower(0);
                BRM.setPower(0);

                telemetry.addLine("Running Motor: None");
            }

            telemetry.update();
        }
    }
}
