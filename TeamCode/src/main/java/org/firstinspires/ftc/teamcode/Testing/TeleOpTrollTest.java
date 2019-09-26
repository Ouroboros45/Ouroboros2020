package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware.DriveTrain;


@TeleOp(name="TrollMac", group= "Troll")
public class TeleOpTrollTest extends OpMode {

    DriveTrain drive = new DriveTrain();

    //Instantiate Variables

    //Variables for Arcade Drive
    int motorPos = 0;
    double leftStickY;
    double leftStickX;
    double direction;
    double velocity;
    double speed;
    double speedProp = 1;
    boolean halfTrue = false;
    boolean cfmToggle = false;

    //  Variables for Cruise Foundation Moving (CFM)

    private static final double  massFoundation = 1.905; // Mass in kg
    private static final double massStone = .1882;
    static final double muBlocks = .78;
    static final double muMat = .535;
    double fix = 1.0;
    double tolerance = .05;
    double mass = 0.0;
    double foundationFriction = 0.0;
    double maxCFM_Velocity = 0.0;

    int numberStackedBlocks = 0;


    //Initializes Method
    @Override
    public void init() {



        //Sets Hardware Map
        drive.fl = hardwareMap.dcMotor.get("fl");
        drive.fr = hardwareMap.dcMotor.get("fr");
        drive.bl = hardwareMap.dcMotor.get("bl");
        drive.br = hardwareMap.dcMotor.get("br");

        //Sets Motor Directions
        drive.fl.setDirection(DcMotor.Direction.REVERSE);
        drive.fr.setDirection(DcMotor.Direction.FORWARD);
        drive.bl.setDirection(DcMotor.Direction.REVERSE);
        drive.br.setDirection(DcMotor.Direction.FORWARD);

        //Set Power For Static Motors - When Robot Not Moving
        drive.fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        drive.fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        drive.bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        drive.br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        drive.runtime.reset();

        numberStackedBlocks = 0;

    }

    //Main Loop
    @Override
    public void loop() {


       // motorPos = (drive.fr.getCurrentPosition() * drive.fl.getCurrentPosition() * drive.bl.getCurrentPosition()
               // * drive.br.getCurrentPosition()) / 4;
        speed = gamepad1.right_stick_x;

        telemetry.addData("Status", "Run Time: " + drive.runtime.toString());
      //  telemetry.addData("Motor Encoder Position", "Average Ticks:" + motorPos);
        telemetry.addData("Motor Position", "Motor Rotation", +speed);
        telemetry.update();

        //Gets Motor Position By Taking Average From All Motors


        //Arcade Controls
        //Latitudinal Direction
        if (Math.abs(gamepad1.left_stick_y) > .05) {
            leftStickY = gamepad1.left_stick_y;
        } else {
            leftStickY = 0;
        }
        //Longitudinal Direction
        if (Math.abs(gamepad1.left_stick_x) > .05) {
            leftStickX = gamepad1.left_stick_x;
        } else {
            leftStickX = 0;
        }

        //Speed Reducer
        if (gamepad1.right_stick_button && !halfTrue) {
            speedProp = .5;
        } else if (gamepad1.right_stick_button && halfTrue) {
            speedProp = 1;
        }

        //Foundation Moving Toggle
        //Toggle sets speed such that the robot can move the fastest
        //while moving the foundation and not dropping any blocks
        //Takes into account the mass of the foundation and block stack
        //and the friction of the floor

        if (gamepad2.dpad_up) {
            numberStackedBlocks++;
        }

        if (gamepad2.dpad_down) {
            numberStackedBlocks--;
        }

        mass = massFoundation + numberStackedBlocks * massStone;
        maxCFM_Velocity = fix * Math.sqrt((2 * tolerance * 9.81 * massStone * numberStackedBlocks * muBlocks)
                / mass);

        telemetry.addData("Number of Blocks : ", numberStackedBlocks);
        telemetry.update();

        //set up power conversion
        //set up toggle



        //Gets Magnitude of Left Stick
        velocity = Math.hypot(leftStickX, leftStickY);
        //Gets Direction of Left Stick
        direction = Math.atan2(leftStickY, -leftStickX) - Math.PI / 4;
        speed = gamepad1.right_stick_x;

        //Sets Power to Wheel
            drive.fl.setPower((velocity * Math.cos(direction) + speed) * speedProp);
            drive.fr.setPower((velocity * Math.sin(direction) - speed) * speedProp);
            drive.bl.setPower((velocity * Math.sin(direction) + speed) * speedProp);
            drive.br.setPower((velocity * Math.cos(direction) - speed) * speedProp);


    }
}
