//Package statement
package org.firstinspires.ftc.teamcode.OpModes;

//import statements

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.Hardware.Intake;
import org.firstinspires.ftc.teamcode.Hardware.Outtake;

//TeleOp class identifier
@TeleOp(name="Arcade Drive", group="TeleOp")
public class TeleOpMecanum extends OpMode {

    //Instantiate Variables
    DriveTrain drive = new DriveTrain();
    Intake intake = new Intake();
    Outtake outtake = new Outtake();

    //Variables for Arcade Drive
    int motorPos = 0;
    double leftStickY;
    double leftStickX;
    double direction;
    double velocity;
    double speed;
    double speedHalver;
    boolean halfTrue = false;

    //  Variables for Cruise Foundation Moving (CFM)
    //  mu = Approximated Static Coefficient of Friction
    //  fix = A fixing constant
    // distance = distance traveled by robot while moving foundation
    //  mass = mass of the foundation + mass of the blocks
    // numberStackedBlocks = number of blocks stacked on top of one another
    //  maxCFM_Velocity = Max velocity foundation with blocks can move before dropping blocks

    private static final double  massFoundation = 1.905; // Mass in kg
    private static final double massStone = .1882;
    double mu = 2.00;
    double fix = 1.0;
    double distance = .25;
    double mass = 0.0;
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
        outtake.initOuttake(this);

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

            mass = massFoundation + numberStackedBlocks * massStone;
            maxCFM_Velocity = fix * Math.sqrt((distance * massStone * (numberStackedBlocks + 1) * 9.81 * mu) / mass);

            //set up power conversion
            //set up toggle


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
