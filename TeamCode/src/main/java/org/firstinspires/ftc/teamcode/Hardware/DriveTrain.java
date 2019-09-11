package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class DriveTrain {

    private LinearOpMode opMode;
    private Sensors sensors;

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
        }
        else {
            fl.setPower(-speed);
            fr.setPower(speed);
            bl.setPower(-speed);
            br.setPower(speed);
        }
    }

    public void turnPID(double headingChange, double kP, double kI, double kD, boolean isRight, double timeoutS) {

    }


}
