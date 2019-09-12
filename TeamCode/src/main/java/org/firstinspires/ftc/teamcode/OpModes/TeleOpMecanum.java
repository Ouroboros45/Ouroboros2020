//Package statement
package org.firstinspires.ftc.teamcode.OpModes;

//import statements
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Hardware.DriveTrain;

//TeleOp class identifier
@TeleOp(name="Arcade Drive", group="TeleOp")
public class TeleOpMecanum extends OpMode {

    //Define object instances and variables
    DriveTrain drive = new DriveTrain();

    int speed = 1;
    double leftStickY;
    double leftStickX;

    int motorPos = 0;
    double velocity;
    double direction;

    //init statement
    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        drive.resetEncoders();
        drive.runtime.reset();
    }

    //loops when played
    @Override
    public void loop() {

            telemetry.addData("Status", "Run Time: " + drive.runtime.toString());
            telemetry.addData("Motor Encoder Position", "Average Ticks:" + motorPos);
            telemetry.addData("Motor Position", "Motor Rotation", + speed);
            telemetry.update();

            //gets current motor position
            motorPos = drive.fr.getCurrentPosition() * drive.fl.getCurrentPosition() * drive.bl.getCurrentPosition()
                    * drive.br.getCurrentPosition() / 4;

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

            //Speed Reducer
            if (gamepad1.right_bumper) {
                leftStickX = leftStickX / 2;
                leftStickY = leftStickY / 2;
            }

            velocity = Math.hypot(leftStickX, leftStickY);
            direction = Math.atan2(leftStickY, -leftStickX) - Math.PI / 4;

            drive.fl.setPower(velocity * Math.cos(direction) + speed);
            drive.fr.setPower(velocity * Math.sin(direction) - speed);
            drive.bl.setPower(velocity * Math.sin(direction) + speed);
            drive.br.setPower(velocity * Math.cos(direction) - speed);
    }
}
