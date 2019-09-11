package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class DriveTrain {

    private static double motorCounts = 1440;
    private static double gearUp = 1;
    private static double wheelDiam = 4;
    private static double inchCounts = (motorCounts / gearUp)
            / (wheelDiam * Math.PI);

    private LinearOpMode opMode;
    private Sensors sensors;

    public ElapsedTime     runtime = new ElapsedTime();

    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;

    public void initDriveTrain(LinearOpMode opMode) {
        this.opMode = opMode;
        sensors = new Sensors();

        fl = this.opMode.hardwareMap.dcMotor.get("fl");
        fr = this.opMode.hardwareMap.dcMotor.get("fr");
        bl = this.opMode.hardwareMap.dcMotor.get("bl");
        br = this.opMode.hardwareMap.dcMotor.get("br");

        fl.setDirection(DcMotor.Direction.REVERSE);
        fr.setDirection(DcMotor.Direction.FORWARD);
        bl.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.FORWARD);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void turn(double speed, boolean isRight) {
        if (isRight) {
            fl.setPower(speed);
            fr.setPower(-speed);
            bl.setPower(speed);
            br.setPower(-speed);
        } else {
            fl.setPower(-speed);
            fr.setPower(speed);
            bl.setPower(-speed);
            br.setPower(speed);
        }
    }

    public void turnPID(double headingChange, double kP, double kI, double kD, boolean isRight, double timeoutS) {

    }

    public void resetEncoders() {

        opMode.telemetry.addData("Status", "Resetting Encoders");
        opMode.telemetry.update();

        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        opMode.telemetry.addData("Path0", "Starting at %7d :%7d",
                bl.getCurrentPosition(),
                br.getCurrentPosition());
        opMode.telemetry.update();

    }

    public void encoderDrive(double speed,
                             double leftBlinches, double leftInches, double rightInches, double rightBlinches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;
        int newRightBlarget;
        int newLeftBlarget;

        if (opMode.opModeIsActive()) {

            newLeftTarget = fl.getCurrentPosition() + (int) (leftInches * inchCounts);
            newRightTarget = fr.getCurrentPosition() + (int) (rightInches * inchCounts);
            newLeftBlarget = bl.getCurrentPosition() + (int) (leftBlinches * inchCounts);
            newRightBlarget = br.getCurrentPosition() + (int) (rightBlinches * inchCounts);
            fl.setTargetPosition(newLeftTarget);
            fr.setTargetPosition(newRightTarget);
            bl.setTargetPosition(newLeftBlarget);
            br.setTargetPosition(newRightBlarget);

            fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            fl.setPower(Math.abs(speed));
            fr.setPower(Math.abs(speed));
            bl.setPower(Math.abs(speed));
            br.setPower(Math.abs(speed));


            while (opMode.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (bl.isBusy() && br.isBusy())) {

                opMode.telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                opMode.telemetry.addData("Path2", "Running at %7d :%7d",
                        fl.getCurrentPosition(),
                        fr.getCurrentPosition());

                opMode.telemetry.update();
            }

            fl.setPower(0);
            fr.setPower(0);
            bl.setPower(0);
            br.setPower(0);

            fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            opMode.sleep(50);
        }

    }

    public void runEncoders() {
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void runToPosition() {
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

}
