//อิติปิโส ภะคะวา อะระหัง สัมมาสัมพุทโธ, วิชชาจะระณะสัมปันโน, สุขโต โลกะวิทู, อนุตตะโร ปุริสสะทัมมะสาระถิ, สัตถา เทวะมะนุสสานัง, พุทโธ ภะคะวาติ

//@piyawat luknatin, worawan simabowonsut LSP robotics 2024-2025

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="TELEOP!!!", group="Iterative OpMode")
public class teleop extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FRONT_LEFT_MOTOR = null;
    private DcMotor BACK_LEFT_MOTOR = null;
    private DcMotor FRONT_RIGHT_MOTOR = null;
    private DcMotor BACK_RIGHT_MOTOR = null;

    private Servo LEFT_ARM_SERVO, RIGHT_ARM_SERVO, GRIPPER_SERVO;
    private CRServo INTAKE_SERVO;
    private DcMotor extender_L = null;
    private DcMotor extender_R = null;
    private DigitalChannel LIMIT_SWITCH = null;
    private DcMotor CURRENT_MOTOR;

    //setting arm servo position in degree
    double pos3 = (double) 100 / 180;
    double pos2 = (double) 15 / 180;
    double pos1 = (double) 10 / 180;

    // Control variables
    boolean invertX = false;
    boolean invertY = true;
    double deadZone = 0;
    double saturation = 1;
    double sensitivity = 0.1;
    double range = 1;
    public boolean manual = false;
    public boolean RIGHT_MOTOR = false;

    @Override
    public void init() {

        extender_L = hardwareMap.get(DcMotor.class, "et_1");
        extender_R = hardwareMap.get(DcMotor.class,  "et_2");

        telemetry.addData("Status", "Initialized");

        // Initialize motors
        FRONT_LEFT_MOTOR = hardwareMap.get(DcMotor.class, "FLM");
        BACK_LEFT_MOTOR = hardwareMap.get(DcMotor.class, "BLM");
        FRONT_RIGHT_MOTOR = hardwareMap.get(DcMotor.class, "FRM");
        BACK_RIGHT_MOTOR = hardwareMap.get(DcMotor.class, "BRM");

        //arm section
        LEFT_ARM_SERVO = hardwareMap.get(Servo.class, "sv_1");
        GRIPPER_SERVO = hardwareMap.get(Servo.class, "sv_2");
        RIGHT_ARM_SERVO = hardwareMap.get(Servo.class, "sv_3");
        INTAKE_SERVO = hardwareMap.get(CRServo.class, "sv_4");
        LIMIT_SWITCH = hardwareMap.get(DigitalChannel.class, "ls");

        //hardware behavior declaration for lift system
        LIMIT_SWITCH.setMode(DigitalChannel.Mode.INPUT);

        extender_L.setDirection(DcMotor.Direction.FORWARD);
        extender_R.setDirection(DcMotor.Direction.REVERSE);

        extender_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extender_R.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set motor directions
        FRONT_LEFT_MOTOR.setDirection(DcMotor.Direction.REVERSE);
        BACK_LEFT_MOTOR.setDirection(DcMotor.Direction.REVERSE);
        FRONT_RIGHT_MOTOR.setDirection(DcMotor.Direction.FORWARD);
        BACK_RIGHT_MOTOR.setDirection(DcMotor.Direction.FORWARD);

        //initially stop the intake motor
        INTAKE_SERVO.setPower(0);

        ACTIVE_MOTOR_CHECKER();

    }

    @Override
    public void init_loop() {
        ENCODER_RESET();
    }

    @Override
    public void start() {
        runtime.reset();

    }

    public void ACTIVE_MOTOR_CHECKER(){
        if(RIGHT_MOTOR){
            CURRENT_MOTOR = extender_R;
            telemetry.addData("Active Motor", "Right Motor");
        } else{
            CURRENT_MOTOR = extender_L;
            telemetry.addData("Active Motor", "Left Motor");
        }
    }

    public void ENCODER_RESET(){
        LEFT_ARM_SERVO.setPosition(0);
        RIGHT_ARM_SERVO.setPosition(1);
        if (!LIMIT_SWITCH.getState()){
            CURRENT_MOTOR.setPower(0);
            CURRENT_MOTOR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        } else {
            CURRENT_MOTOR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            CURRENT_MOTOR.setPower(-1);
        }
    }

    public void EXTENDER_FUNC(int targetPosition,double power) { // Takes targetPosition as input
        if (!manual){
            int pos = CURRENT_MOTOR.getCurrentPosition(); // References encoder position from the currently using side

            // Set target positions for both motors
            CURRENT_MOTOR.setTargetPosition(targetPosition);
            CURRENT_MOTOR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            CURRENT_MOTOR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Adjust power based on current position relative to the target
            if (pos < targetPosition) {
                CURRENT_MOTOR.setPower(power);
            } else if (pos > targetPosition) {
                CURRENT_MOTOR.setPower(-power);
            } else { // Target reached
                CURRENT_MOTOR.setPower(0);
            }
        }
    }
    public void MANUAL_EXTENDER(){
        if (manual){
            CURRENT_MOTOR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            CURRENT_MOTOR.setPower(-gamepad2.right_stick_y);
        }
    }

    private void movement_presets() {

        if(gamepad1.y){
            EXTENDER_FUNC(0,1);
            LEFT_ARM_SERVO.setPosition(pos3);
            RIGHT_ARM_SERVO.setPosition(1.0 - pos3); //sample collect
        }
        else if(gamepad1.b){
            EXTENDER_FUNC(660,1);
            LEFT_ARM_SERVO.setPosition(pos1);
            RIGHT_ARM_SERVO.setPosition(1.0 - pos1); //specimen collect
        }
        else if(gamepad1.a){
            EXTENDER_FUNC(6200,1);
            LEFT_ARM_SERVO.setPosition(pos2);
            RIGHT_ARM_SERVO.setPosition(1.0 - pos2); //high basket
        }
        else if(gamepad1.dpad_down){
            EXTENDER_FUNC(1900,1);
            LEFT_ARM_SERVO.setPosition(pos2);
            RIGHT_ARM_SERVO.setPosition(1.0 - pos2); //low basket
        }

        else if (gamepad1.dpad_left){
            EXTENDER_FUNC(4600,1); // high chamber
        }

    }

    private void controlCRServo() {
        if (gamepad1.left_bumper) {
            INTAKE_SERVO.setPower(1);
        } else if (gamepad1.left_trigger > 0.1) {
            INTAKE_SERVO.setPower(-1);
        } else {
            INTAKE_SERVO.setPower(0); // Stop servo if no input
        }
    }

    private void gripper(){
        GRIPPER_SERVO.setPosition(Math.min(gamepad1.right_trigger, 0.4));
    }

    public void move_func(){
        // Update joystick inputs in each loop cycle
        double computedX_l = cordinate_converter.computeX(gamepad1.left_stick_x, gamepad1.left_stick_y, deadZone, saturation, sensitivity, range, invertX, invertY);
        double computedY_l = cordinate_converter.computeY(gamepad1.left_stick_x, gamepad1.left_stick_y, deadZone, saturation, sensitivity, range, invertX, invertY);
        double computedX_r = cordinate_converter.computeX(gamepad1.right_stick_x, gamepad1.right_stick_y, deadZone, saturation, sensitivity, range, invertX, invertY);

        // apply mecanum drive equations to calculate motor powers
        double frontLeft = computedY_l + computedX_l + computedX_r;
        double frontRight = computedY_l - computedX_l - computedX_r;
        double backLeft = computedY_l - computedX_l + computedX_r;
        double backRight = computedY_l + computedX_l - computedX_r;

        // Apply motor power values
        FRONT_LEFT_MOTOR.setPower(frontLeft);
        FRONT_RIGHT_MOTOR.setPower(frontRight);
        BACK_LEFT_MOTOR.setPower(backLeft);
        BACK_RIGHT_MOTOR.setPower(backRight);
    }
    public void auto_spec(){
        if (gamepad1.x){
            EXTENDER_FUNC(2900,1);
        }
    }

    public void OVERRIDE_SYSTEM(){

        if (gamepad2.left_trigger >= 0.7 && gamepad2.right_trigger >= 0.7){
            manual = true;
        }

    }

    @Override
    public void loop() {

        movement_presets();
        controlCRServo();
        move_func();
        gripper();
        auto_spec();
        OVERRIDE_SYSTEM();
        MANUAL_EXTENDER();

        if (gamepad1.right_bumper){
            ENCODER_RESET();
        }

        telemetry.addData("Pos_L", extender_L.getCurrentPosition());
        telemetry.addData("Pos_R", extender_R.getCurrentPosition());
        telemetry.addData("manual", manual);
        telemetry.update();
    }

    @Override
    public void stop() {}
}
