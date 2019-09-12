//Package statement
package org.firstinspires.ftc.teamcode.OpModes;

//import statements
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Hardware.DriveTrain;

//TeleOp class identifier
@TeleOp(name="Arcade Drive", group="TeleOp")
public class TeleOpMecanum extends OpMode {

    //Instantiate Variables
    DriveTrain drive = new DriveTrain();

    int motorPos = 0;
    int speed = 1;
    double leftStickY;
    double leftStickX;
    double direction;
    double velocity;


    //Initializes Method
    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        drive.resetEncoders();
        drive.runtime.reset();
    }

    //Main Loop
    @Override
    public void loop() {

            telemetry.addData("Status", "Run Time: " + drive.runtime.toString());
            telemetry.addData("Motor Encoder Position", "Average Ticks:" + motorPos);
            telemetry.addData("Motor Position", "Motor Rotation", + speed);
            telemetry.update();

            //Gets Motor Position By Taking Average From All Motors
            motorPos = drive.fr.getCurrentPosition() * drive.fl.getCurrentPosition() * drive.bl.getCurrentPosition()
                    * drive.br.getCurrentPosition() / 4;

            //Arcade Controls
            //Latitudinal Direction
            if (Math.abs(gamepad1.left_stick_y) > .05) {
                leftStickY = gamepad1.left_stick_y;
            }
            else {
                leftStickY = 0;
            }
            //Longitudinal Direction
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

            //Gets Magnitude of Left Stick
            velocity = Math.hypot(leftStickX, leftStickY);
            //Gets Direction of Left Stick
            direction = Math.atan2(leftStickY, -leftStickX) - Math.PI / 4;

            //Sets Power to Wheel
            drive.fl.setPower(velocity * Math.cos(direction) + speed);
            drive.fr.setPower(velocity * Math.sin(direction) - speed);
            drive.bl.setPower(velocity * Math.sin(direction) + speed);
            drive.br.setPower(velocity * Math.cos(direction) - speed);
    }
}
