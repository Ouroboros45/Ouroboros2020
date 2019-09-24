package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Outtake {

    Servo openBasket;
    CRServo rightSideY;
    CRServo leftSideY;
    DcMotor liftRight;
    DcMotor liftLeft;

    LinearOpMode opMode;
    ElapsedTime time = new ElapsedTime();

    boolean blockInLift;

    public boolean initOuttake(OpMode opMode)
    {
        this.opMode = (LinearOpMode) opMode;
        time.reset();

        blockInLift = false;

        try
        {
            openBasket = opMode.hardwareMap.servo.get("Open Basket");
            rightSideY = opMode.hardwareMap.crservo.get("Right Outtake");
            leftSideY = opMode.hardwareMap.crservo.get("Left Outtake");
            liftLeft = opMode.hardwareMap.dcMotor.get("Left Lift");
            liftRight = opMode.hardwareMap.dcMotor.get("Right Lift");

            liftLeft.setDirection(DcMotorSimple.Direction.REVERSE);

            opMode.telemetry.addData("Success", "Outtake Initialized");
            opMode.telemetry.update();

        } catch (Exception e)
        {
            opMode.telemetry.addData("Failed", "Failed to Map");
            opMode.telemetry.update();

            return false;
        }
        return true;
    }


    //  x_coord - robot moves side to side along x axis
    // y_coord - robot moves output system forward and backwards using CRservos
    // z_coord - robot lift moves up and down using Lift DcMotors
    // once coordinates placed into method robot auto aligns the whole output system to
    // drop the block off on at the right spot

    // Process : Robot Aligns, Lift Extends Up, CRServos move forward while hook connected to servo
    // is in contact with block, when block needs to be released hook from servo extended out


    public void outTake_Auto (double x_coord, double y_coord, double z_coord)
    {

    }

    // contains button to move the lift along the y and z plane
    // gamepad two
    public void outTake_TeleOp()
    {

    }

    //  opens up the output basket using the Servos
    public void openBasket()
    {

    }

    //  resets all variables and sets lift back to initial position
    public void resetOuttake()
    {


    }

}
