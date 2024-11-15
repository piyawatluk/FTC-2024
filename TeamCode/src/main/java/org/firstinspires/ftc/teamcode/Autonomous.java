package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Autonomous (Blocks to Java)", group = "")
public class Autonomous extends LinearOpMode {

    private DcMotor ML;
    private DcMotor MR;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        ML = hardwareMap.dcMotor.get("ML");
        MR = hardwareMap.dcMotor.get("MR");

        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                ML.setPower(-0.2);
                MR.setPower(0.2);
                sleep(1000);
                MR.setPower(-0.2);
                ML.setPower(0.2);
                sleep(1000);
                ML.setPower(0);
                MR.setPower(0);
            }
        }
    }
}