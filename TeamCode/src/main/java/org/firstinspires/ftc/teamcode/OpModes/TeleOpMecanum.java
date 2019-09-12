package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.DriveTrain;

@TeleOp(name="Arcade Drive", group="TeleOp")
public class TeleOpMecanum extends LinearOpMode {

    DriveTrain drive = new DriveTrain();

    int speed = 1;
    double leftStickY;
    double leftStickX;
    double rightStickX;
    double rightStickY;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        drive.resetEncoders();

        waitForStart();
        drive.runtime.reset();
        int motorPos = 0;
        double speedLock = 0;
        boolean changeable = true;
        double velocity;
        double direction;

        while (opModeIsActive()) {

            telemetry.addData("Status", "Run Time: " + drive.runtime.toString());
            telemetry.addData("Motor Encoder Position", "Average Ticks:" + motorPos);
            telemetry.addData("Motor Position", "Motor Rotation", + speed);
            telemetry.update();

            motorPos = drive.fr.getCurrentPosition() * drive.fl.getCurrentPosition() * drive.bl.getCurrentPosition()
                    * drive.br.getCurrentPosition() / 4;
            velocity = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            direction = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;

            if (Math.abs(gamepad1.left_stick_y) > .05) {
                leftStickY = gamepad1.left_stick_y;
            }
            else {
                leftStickY = 0;
            }

            if (Math.abs(gamepad1.left_stick_x) > .05) {
                leftStickX = gamepad1.left_stick_x;
            }
            else {
                leftStickX = 0;
            }

            if (gamepad1.right_bumper) {
                leftStickX = leftStickX / 5;
                leftStickY = leftStickY / 5;
            }
            drive.fl.setPower(velocity * Math.cos(direction) + speed);
            drive.fr.setPower(velocity * Math.sin(direction) - speed);
            drive.bl.setPower(velocity * Math.sin(direction) + speed);
            drive.br.setPower(velocity * Math.cos(direction) - speed);
        }
    }



}
