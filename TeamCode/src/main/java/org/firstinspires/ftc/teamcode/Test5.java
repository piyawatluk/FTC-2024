package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="stress test", group="Iterative OpMode")
public class Test5 extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor extender_L = null;
    private DcMotor extender_R = null;

    private DigitalChannel ls = null;

    private int cycleCount = 0;

    private boolean isMovingUp = true;

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

    public void extender_func() {
        int targetPosition = 0;

        if (isMovingUp) {
            targetPosition = 6200; // Target position for moving up
            extender_L.setPower(1);
            extender_R.setPower(1);

        } else {
            targetPosition = 0; // Target position for moving down
            extender_L.setPower(-1);
            extender_R.setPower(-1);
        }

        extender_L.setTargetPosition(targetPosition);
        extender_R.setTargetPosition(targetPosition);

        extender_L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extender_R.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (extender_L.isBusy() && extender_R.isBusy()) {

        } else {

            cycleCount++;

            if (cycleCount >= 50) {
                extender_L.setPower(0);
                extender_R.setPower(0);
                telemetry.addData("Status", "Completed 5 up/down cycles.");
            } else {

                isMovingUp = !isMovingUp;
            }
        }

        telemetry.addData("Cycle", cycleCount);
        telemetry.addData("Target Position", targetPosition);
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