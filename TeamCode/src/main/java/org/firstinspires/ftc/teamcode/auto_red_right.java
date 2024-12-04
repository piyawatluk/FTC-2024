package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.arm_func_auto;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(name = "auto test")
public class auto_red_right extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private Servo sv_1, sv_3, sv_2;
    private CRServo sv_4;
    private DcMotor extender_L = null;
    private DcMotor extender_R = null;
    private DigitalChannel ls = null;
    public boolean rightmotor = true;
    double pos4 = (double) 180 / 180;
    double pos3 = (double) 110 / 180;
    double pos2 = (double) 25 / 180;
    double pos1 = (double) 0 / 180;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        //arm section
        sv_1 = hardwareMap.get(Servo.class, "sv_1");
        sv_2 = hardwareMap.get(Servo.class, "sv_2");
        sv_3 = hardwareMap.get(Servo.class, "sv_3");
        sv_4 = hardwareMap.get(CRServo.class, "sv_4");

        extender_L = hardwareMap.get(DcMotor.class, "et_1");
        extender_R = hardwareMap.get(DcMotor.class,  "et_2");
        ls = hardwareMap.get(DigitalChannel.class, "ls");

        //initially stop the intake motor
        sv_4.setPower(0);

        //hardware behavior declaration for lift system
        ls.setMode(DigitalChannel.Mode.INPUT);

        extender_L.setDirection(DcMotor.Direction.FORWARD);
        extender_R.setDirection(DcMotor.Direction.REVERSE);

        extender_L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extender_R.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extender_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extender_R.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public int extender_func(int targetPosition) { // Takes targetPosition as input
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

        // Set target positions for both motors
        currentmotor.setTargetPosition(targetPosition);

        currentmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Adjust power based on current position relative to the target
        if (pos < targetPosition) {
            currentmotor.setPower(1);
        } else if (pos > targetPosition) {
            currentmotor.setPower(-1);
        } else { // Target reached
            currentmotor.setPower(0);
        }

        // Handle limit switch: stop the motors if the limit switch is pressed
        if (!ls.getState()) {
            currentmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            currentmotor.setPower(0);
        }

        return pos; // Return the current position
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        // We want to start the bot at x:25, y: -60, heading: 90 degrees
        Pose2d startPose = new Pose2d(25, -60, Math.toRadians(90));

        drive.setPoseEstimate(startPose);

        Trajectory traj1 = drive.trajectoryBuilder(startPose)
                .strafeTo(new Vector2d(10,-33))
                .addTemporalMarker(30, () -> {
                    // Aprox.from pythagorus theorem
                    // Run your action in here!
                    extender_func(4000);
                    sv_1.setPosition(pos4);
                    sv_3.setPosition(1.0 - pos4); // high chamber
                })
                .addSpatialMarker(new Vector2d(10,-33), () -> {
                    extender_func(3700);
                    sv_2.setPosition(0.3);
                })
                .build();

        Trajectory traj1_5 = drive.trajectoryBuilder(traj1.end())
                .addDisplacementMarker(5, () -> {
                    extender_func(2200);
                    sv_1.setPosition(pos4);
                    sv_3.setPosition(1 - pos4);
                })
                .back(10, SampleMecanumDrive.getVelocityConstraint(10, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH), SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();

        Trajectory traj2 = drive.trajectoryBuilder(traj1_5.end())
                .strafeRight(15)
                .splineToConstantHeading(new Vector2d(38, -6),Math.toRadians(90),SampleMecanumDrive.getVelocityConstraint(10, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH), SampleMecanumDrive.getAccelerationConstraint(DriveConstants.SAFE_ACCEL))
                .splineToConstantHeading(new Vector2d(48,-55),Math.toRadians(270),SampleMecanumDrive.getVelocityConstraint(10, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH), SampleMecanumDrive.getAccelerationConstraint(DriveConstants.SAFE_ACCEL))
                .splineToConstantHeading(new Vector2d(48,-6),Math.toRadians(90),SampleMecanumDrive.getVelocityConstraint(10, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH), SampleMecanumDrive.getAccelerationConstraint(DriveConstants.SAFE_ACCEL))
                .splineToConstantHeading(new Vector2d(55,-55),Math.toRadians(270),SampleMecanumDrive.getVelocityConstraint(10, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH), SampleMecanumDrive.getAccelerationConstraint(DriveConstants.SAFE_ACCEL))
                .splineToConstantHeading(new Vector2d(55,-6),Math.toRadians(90),SampleMecanumDrive.getVelocityConstraint(10, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH), SampleMecanumDrive.getAccelerationConstraint(DriveConstants.SAFE_ACCEL))
                .build();

        Trajectory traj3 = drive.trajectoryBuilder(traj2.end())
                .strafeRight(7)
                .build();
        Trajectory traj4 = drive.trajectoryBuilder(traj3.end())
                .back(49)
                .build();

        drive.followTrajectory(traj1);
        drive.followTrajectory(traj1_5);
        drive.followTrajectory(traj2);
        drive.followTrajectory(traj3);
        drive.followTrajectory(traj4);
    }

    @Override
    public void stop() {
    }
}