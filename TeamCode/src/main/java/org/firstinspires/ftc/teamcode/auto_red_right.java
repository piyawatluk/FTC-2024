package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "red_right")
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

        Pose2d startPose = new Pose2d(25, -60, Math.toRadians(90));

        drive.setPoseEstimate(startPose);

        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
                //.strafeTo(new Vector2d(4,-27))
                .addDisplacementMarker(3, () -> {
                    currentmotor.setTargetPosition(4000);
                    currentmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    currentmotor.setPower(1);
                })

                .addSpatialMarker(new Vector2d(4, -27), () -> {
                    currentmotor.setTargetPosition(3300);
                    currentmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    currentmotor.setPower(1);
                    if (currentmotor.getCurrentPosition() < 4000){
                        sv_2.setPosition(0.4);
                    }
                })

                .addSpatialMarker(new Vector2d(46, -60), () -> {

                    sv_2.setPosition(0);
                })

                //.back(10)
                //.strafeRight(25)
                //.splineToLinearHeading(new Pose2d(46, -6,Math.toRadians(180)),Math.toRadians(0))
                //.strafeLeft(50)
                //.strafeRight(50)
                //.back(9)
                //.strafeLeft(50)
                //.strafeRight(50)
                //.back(3)
                //.strafeLeft(50)

                .strafeTo(new Vector2d(4,-27))
                .back(10)
                .strafeRight(25)
                .splineToLinearHeading(new Pose2d(46, -6,Math.toRadians(270)),Math.toRadians(0))
                .forward(55)
                .back(15)
                .splineToLinearHeading(new Pose2d(4,-27,Math.toRadians(90)) , Math.toRadians(90))
                .strafeTo(new Vector2d(25,-60))

                .build();

        waitForStart();

        if (!isStopRequested())
            drive.followTrajectorySequence(trajSeq);
    }
}