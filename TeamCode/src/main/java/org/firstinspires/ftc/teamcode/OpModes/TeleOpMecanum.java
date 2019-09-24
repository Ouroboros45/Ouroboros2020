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
    double speedProp;
    boolean halfTrue = false;
    boolean cfmToggle = false;

    //  Variables for Cruise Foundation Moving (CFM)
    //  mu = Approximated Static Coefficient of Friction
    //  fix = A fixing constant
    // distance = distance traveled by robot while moving foundation
    //  mass = mass of the foundation + mass of the blocks
    // numberStackedBlocks = number of blocks stacked on top of one another
    //  maxCFM_Velocity = Max velocity foundation with blocks can move before dropping blocks

    private static final double  massFoundation = 1.905; // Mass in kg
    private static final double massStone = .1882;
    static final double muBlocks = .78;
    static final double muMat = .535;
    double fix = 1.0;
    double distance = .1;
    double mass = 0.0;
    double foundationFriction = 0.0;
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
                speedProp = .5;
            } else if (gamepad1.right_bumper && halfTrue){
                speedProp = 1;
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
            maxCFM_Velocity = fix * Math.sqrt(2 * distance * ((massStone * (numberStackedBlocks + 1) * 9.81 * muBlocks)) / mass);

            telemetry.addData("Number of Blocks : ", numberStackedBlocks);
            telemetry.update();

            //set up power conversion
            //set up toggle

            if(gamepad1.b && !cfmToggle)
            {
                //set speedProm to cfm
                cfmToggle = true;
            }
            else if(gamepad1.b && cfmToggle)
            {
                //set power to normal
                cfmToggle = false;
            }



            //Gets Magnitude of Left Stick
            velocity = Math.hypot(leftStickX, leftStickY);
            //Gets Direction of Left Stick
            direction = Math.atan2(leftStickY, -leftStickX) - Math.PI / 4;
            speed = gamepad1.right_stick_x;

            //Sets Power to Wheel
            if(!cfmToggle)
            {
                drive.fl.setPower((velocity * Math.cos(direction) + speed) * speedProp);
                drive.fr.setPower((velocity * Math.sin(direction) - speed) * speedProp);
                drive.bl.setPower((velocity * Math.sin(direction) + speed) * speedProp);
                drive.br.setPower((velocity * Math.cos(direction) - speed) * speedProp);
            }
            else if(cfmToggle && Math.abs(gamepad1.left_stick_x) > .05)
            {
                // setPower(cfm_power)
            }


            //Intake
            intake.compliantIntake_TeleOp();

            //Outtake
            outtake.outTake_TeleOp();
            if (gamepad2.a && outtake.pushBlock.getPosition() != .5) {
                outtake.pushBlock.setPosition(.5);
            } else if (gamepad2.a && outtake.pushBlock.getPosition() == .5) {
                outtake.pushBlock.setPosition(1);
            }
    }
}
