//Package statement
package org.firstinspires.ftc.teamcode.OpModes;

//import statements

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.Hardware.Intake;

//TeleOp class identifier
@TeleOp(name="Arcade Drive", group="TeleOp")
public class TeleOpMecanum extends OpMode {

    //Instantiate Variables
    DriveTrain drive = new DriveTrain();
    Intake intake = new Intake();

    //Variables for Arcade Drive
    int motorPos = 0;
    double leftStickY;
    double leftStickX;
    double direction;
    double velocity;
    double speed;
    double speedHalver;
    boolean halfTrue = false;

    //Variables for Cruise Foundation Moving (CFM)
    private static final double  massFoundation = 0.0;
    private static final double massStone = 0.0;
    double foundationForce = 0.0;
    double frictionForce = 0.0;
    double stoneForce = 0.0;
    double distance = 0.0;
    double maxCFM_Velocity = 0.0;

    int numberStackedBlocks = 0;


    //Initializes Method
    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        drive.resetEncoders();
        drive.runtime.reset();

        intake.initIntake(this);

        distance = 0.0;
        numberStackedBlocks = 0;

    }

    //Main Loop
    @Override
    public void loop() {

            telemetry.addData("Status", "Run Time: " + drive.runtime.toString());
            telemetry.addData("Motor Encoder Position", "Average Ticks:" + motorPos);
            telemetry.addData("Motor Position", "Motor Rotation", + speed);
            telemetry.update();

            //Gets Motor Position By Taking Average From All Motors
            motorPos = (drive.fr.getCurrentPosition() * drive.fl.getCurrentPosition() * drive.bl.getCurrentPosition()
                    * drive.br.getCurrentPosition()) / 4;

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
            if (gamepad1.right_bumper && !halfTrue) {
                speedHalver = .5;
            } else if (gamepad1.right_bumper && halfTrue){
                speedHalver = 1;
        }

            //Foundation Moving Toggle
            //Toggle sets speed such that the robot can move the fastest
            //while moving the foundation and not dropping any blocks
            //Takes into account the mass of the foundation and block stack
            //and the friction of the floor

            if(gamepad2.dpad_up)
            {
                numberStackedBlocks++;
            }

            if(gamepad2.dpad_down)
            {
                numberStackedBlocks--;
            }

            maxCFM_Velocity = Math.sqrt(2 * distance * ((foundationForce + frictionForce + stoneForce * numberStackedBlocks)
                    / (massFoundation + massStone * numberStackedBlocks)));



            //Gets Magnitude of Left Stick
            velocity = Math.hypot(leftStickX, leftStickY);
            //Gets Direction of Left Stick
            direction = Math.atan2(leftStickY, -leftStickX) - Math.PI / 4;
            speed = gamepad1.right_stick_x;

            //Sets Power to Wheel
            drive.fl.setPower((velocity * Math.cos(direction) + speed) * speedHalver);
            drive.fr.setPower((velocity * Math.sin(direction) - speed) * speedHalver);
            drive.bl.setPower((velocity * Math.sin(direction) + speed) * speedHalver);
            drive.br.setPower((velocity * Math.cos(direction) - speed) * speedHalver);

            //Intake
            intake.compliantIntake_TeleOp();
    }
}
