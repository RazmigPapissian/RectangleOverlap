package com.papissian.razzle.rectangleoverlap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button r1, r2;
    private TextView tv;
    private int r1Width, r1Height, r2Width, r2Height;
    private int r1xMin, r1xMax, r1yMin, r1yMax;
    private int r2xMin, r2xMax, r2yMin, r2yMax;
    private int r1CenterX, r1CenterY;
    private int r2CenterX, r2CenterY;
    private boolean overlap = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        r1 = (Button) findViewById(R.id.r1);
        r2 = (Button) findViewById(R.id.r2);
        tv = (TextView) findViewById(R.id.tv);

        r1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();

                if (action == MotionEvent.ACTION_MOVE) {
                    changePosR1((double) motionevent.getRawX(), (double) motionevent.getRawY());
                }
                return false;
            } //end onTouch
        });

        r2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();

                if (action == MotionEvent.ACTION_MOVE) {
                    changePosR2((double) motionevent.getRawX(), (double) motionevent.getRawY());
                }
                return false;
            } //end onTouch
        });

    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            measurements();
        }
    }

    public void measurements(){
        r1Width = r1.getWidth();
        r1Height = r1.getHeight();
        r2Width = r2.getWidth();
        r2Height = r2.getHeight();

        r1xMin = (int) r1.getX();
        r1xMax = r1xMin + r1Width;
        r1yMin = (int) r1.getY();
        r1yMax = r1yMin + r1Height;

        r2xMin = (int) r2.getX();
        r2xMax = r2xMin + r2Width;
        r2yMin = (int) r2.getY();
        r2yMax = r2yMin + r2Height;

        r1CenterX = (int) r1.getX() + (r1Width/2);
        r1CenterY = (int) r1.getY() + (r1Height/2);
        r2CenterX = (int) r2.getX() + (r2Width/2);
        r2CenterY = (int) r2.getY() + (r2Height/2);
    }

    public void changePosR1(double x, double y) {
        r1.setX((int) x - (r1Width/2));
        r1.setY((int) y - (r1Height));

        r1xMin = (int) r1.getX();
        r1xMax = r1xMin + r1Width;
        r1yMin = (int) r1.getY();
        r1yMax = r1yMin + r1Height;

        r1CenterX = (int) r1.getX() + (r1Width/2);
        r1CenterY = (int) r1.getY() + (r1Height/2);

        if (checkOverlap()) {
            tv.setText("True");
        } else {
            tv.setText("False");
        }
    }

    public void changePosR2(double x, double y) {
        r2.setX((int) x - (r2Width/2));
        r2.setY((int) y - (r2Height));

        r2xMin = (int) r2.getX();
        r2xMax = r2xMin + r2Width;
        r2yMin = (int) r2.getY();
        r2yMax = r2yMin + r2Height;

        r2CenterX = (int) r2.getX() + (r2Width/2);
        r2CenterY = (int) r2.getY() + (r2Height/2);

        if (checkOverlap()) {
            tv.setText("True");
        } else {
            tv.setText("False");
        }
    }

    public boolean checkOverlap() {

        if ((r2xMax > r1xMax && r1xMax > r2xMin && r2yMax > r1yMin && r1yMin > r2yMin)
                || (r2xMax > r1xMin && r1xMin > r2xMin && r2yMax > r1yMin && r1yMin > r2yMin)
                || (r2xMax > r1xMin && r1xMin > r2xMin && r2yMax > r1yMax && r1yMax > r2yMin)
                || (r2xMax > r1xMax && r1xMax > r2xMin && r2yMax > r1yMax && r1yMax > r2yMin)) { //case of any corner of r1 inside r2
            overlap = true;
        } else if ((r1xMax > r2xMax && r2xMax > r1xMin && r1yMax > r2yMin && r2yMin > r1yMin)
                || (r1xMax > r2xMin && r2xMin > r1xMin && r1yMax > r2yMin && r2yMin > r1yMin)
                || (r1xMax > r2xMin && r2xMin > r1xMin && r1yMax > r2yMax && r2yMax > r1yMin)
                || (r1xMax > r2xMax && r2xMax > r1xMin && r1yMax > r2yMax && r2yMax > r1yMin)) { //case of any corner of r2 inside r1
            overlap = true;
        } else if (r1CenterX > r2xMin && r1CenterX < r2xMax && r1CenterY > r2yMin && r1CenterY < r2yMax) { //case if r1 inside r2, no corners touching
            overlap = true;
        } else if (r2CenterX > r1xMin && r2CenterX < r1xMax && r2CenterY > r1yMin && r2CenterY < r1yMax) { //case if r2 inside r1, no corners touching
            overlap = true;
        } else {
            overlap = false;
        }

        return overlap;
    }


}
