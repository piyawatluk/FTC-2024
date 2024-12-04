package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public class auto_red_right extends LinearOpMode {
    private Servo sv_1, sv_3, sv_2;
    private CRServo sv_4;
    private DcMotor extender_L = null;
    private DcMotor extender_R = null;
    public boolean rightmotor = true;
    @Override
    public void runOpMode() throws InterruptedException {
        sv_1 = hardwareMap.get(Servo.class, "sv_1");
        sv_2 = hardwareMap.get(Servo.class, "sv_2");
        sv_3 = hardwareMap.get(Servo.class, "sv_3");
        extender_L = hardwareMap.get(DcMotor.class, "et_1");
        extender_R = hardwareMap.get(DcMotor.class,  "et_2");
        extender_L.setDirection(DcMotor.Direction.FORWARD);
        extender_R.setDirection(DcMotor.Direction.REVERSE);
        extender_L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extender_R.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extender_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extender_R.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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


        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d startPose = new Pose2d(25, -60, 90);

        drive.setPoseEstimate(startPose);

        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
                .strafeTo(new Vector2d(10,-33))
                .addDisplacementMarker(5, () -> {
                    int targetPosition = 4000;
                    currentmotor.setTargetPosition(targetPosition);
                    currentmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    currentmotor.setPower(1);
                    sv_1.setPosition(1);
                    sv_3.setPosition(0);
                })

                .back(10)
                .strafeRight(15)
                .splineToConstantHeading(new Vector2d(41, -6),Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(50,-55),Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(50,-6),Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(55,-55),Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(55,-6),Math.toRadians(90))
                .strafeRight(7)
                .back(49)
                .build();

        waitForStart();

        if (!isStopRequested())
            drive.followTrajectorySequence(trajSeq);
    }
}