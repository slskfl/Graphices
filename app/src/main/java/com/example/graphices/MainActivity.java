package com.example.graphices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyGraphicView(this));

    }

    private static class MyGraphicView extends View{
        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint=new Paint();
            paint.setAntiAlias(true); //붓 형태
            paint.setColor(Color.GREEN); // 색상
            canvas.drawLine(10, 10, 300, 10, paint);
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(5); // 굵기
            canvas.drawLine(10,30,300,30,paint);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(0); // 굵기
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(10,50,110,150,paint);
            paint.setStyle(Paint.Style.STROKE);
            Rect rect= new Rect(130,50,230,150);
            canvas.drawRect(rect,paint);
            RectF rect1=new RectF(250, 50, 350,150);
            canvas.drawRoundRect(rect1, 20, 20,paint);
            canvas.drawCircle(60, 220,50,paint);
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(5);
            Path path=new Path();
            path.moveTo(10, 290);
            path.lineTo(60, 340);
            path.lineTo(110, 290);
            path.lineTo(160, 340);
            path.lineTo(210, 290);
            paint.setStrokeWidth(0);
            paint.setTextSize(30);

        }
    }
}