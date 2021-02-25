package com.example.minipaintshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.drawable.GradientDrawable.LINE;

public class MainActivity extends AppCompatActivity {
    static final int LINE = 1, CIRCLE = 2, RECT = 3;
    static int curShape = LINE;
    static int curColor = Color.BLACK;
    static final int RED = 4, GREEN = 5, BLUE = 6, BLACK = 7;
    static List<MyGraphicView.MyShape> myShapes = new ArrayList<MyGraphicView.MyShape>();
    static boolean isFinish = false; // ACTION_UP 여부 확인 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyGraphicView(this));

        setTitle("간단 그림판");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, LINE, 0, "선 그리기");
        menu.add(0, CIRCLE, 0, "원 그리기");
        menu.add(0, RECT, 0, "사각형 그리기");
        SubMenu subMenu = menu.addSubMenu("색상변경>>");
        subMenu.add(0, RED, 0, "빨간펜");
        subMenu.add(0, GREEN, 0, "초록펜");
        subMenu.add(0, BLUE, 0, "파랑펜");
        subMenu.add(0, BLACK, 0, "기본펜(검정)");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case LINE:
                curShape = LINE;
                break;
            case CIRCLE:
                curShape = CIRCLE;
                break;
            case RECT:
                curShape = RECT;
                break;
            case RED:
                curColor = Color.RED;
                break;
            case GREEN:
                curColor = Color.GREEN;
                break;
            case BLUE:
                curColor = Color.BLUE;
                break;
            case BLACK:
                curColor = Color.BLACK;
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class MyGraphicView extends View {
        int startX = -1, startY = -1, stopX = -1, stopY = -1;

        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = (int) event.getX();
                    startY = (int) event.getY();
                    isFinish = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    stopX = (int) event.getX();
                    stopY = (int) event.getY();
                    this.invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    MyShape shape = new MyShape();
                    shape.shapeType = curShape;
                    shape.startX = startX;
                    shape.startY = startY;
                    shape.stopX = stopX;
                    shape.stopY = stopY;
                    shape.color = curColor;
                    myShapes.add(shape);
                    isFinish = true;
                    this.invalidate();
                    break;
            }
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);

            //동적 배열에 있는 도형을 모두 그린다
            for (int i = 0; i < myShapes.size(); i++) {
                MyShape shape = myShapes.get(i);
                paint.setColor(shape.color);
                switch (shape.shapeType) {
                    case LINE:
                        canvas.drawLine(shape.startX, shape.startY, shape.stopX, shape.stopY, paint);
                        break;
                    case CIRCLE:
                        int radius = (int) Math.sqrt(Math.pow(shape.stopX - shape.startX, 2) + Math.pow(shape.stopY - shape.startY, 2));
                        canvas.drawCircle(shape.startX, shape.startY, radius, paint);
                        break;
                    case RECT:
                        Rect rect = new Rect(shape.startX, shape.startY, shape.stopX, shape.stopY);
                        canvas.drawRect(rect, paint);
                        break;
                }
            }
            if (isFinish == false) {
                paint.setColor(curColor);
                switch (curShape) {
                    case LINE:
                        canvas.drawLine(startX, startY, stopX, stopY, paint);
                        break;
                    case CIRCLE:
                        int radius = (int) Math.sqrt(Math.pow(stopX - startX, 2) + Math.pow(stopY - startY, 2));
                        canvas.drawCircle(startX, startY, radius, paint);
                        break;
                    case RECT:
                        Rect rect = new Rect(startX, startY, stopX, stopY);
                        canvas.drawRect(rect, paint);
                        break;
                }
            }
        }

        //도형 클래스
        private static class MyShape {
            int shapeType; //도형 종류
            int startX, startY, stopX, stopY; // 도형의 좌표
            int color; //도형의 색상

        }

    }
}