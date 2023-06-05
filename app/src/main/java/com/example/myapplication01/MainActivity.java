package com.example.myapplication01;

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

public class MainActivity extends AppCompatActivity {

    final static int LINE = 1, CIRCLE = 2, RECTANGLE = 3;
    static int curShape = LINE;
    static int curColor = Color.DKGRAY;
    static boolean isfinished = false;
    static List<MyShape> myshape = new ArrayList<MyShape>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyGraphicView(this));
        setTitle("그림판그림판그림판");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, "선 그리기");
        menu.add(0, 2, 0, "원 그리기");
        menu.add(0, 3, 0, "사각형 그리기");
        SubMenu sMenu = menu.addSubMenu("색상 변경 >>");
        sMenu.add(0, 4, 0, "빨강");
        sMenu.add(0, 5, 0, "초록");
        sMenu.add(0, 6, 0, "파랑");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                curShape = LINE; // 선
                return true;
            case 2:
                curShape = CIRCLE; // 원
                return true;
            case 3:
                curShape = RECTANGLE; // 사각형
                return true;
            case 4:
                curColor = Color.RED;
                return true;
            case 5:
                curColor = Color.GREEN;
                return true;
            case 6:
                curColor = Color.BLUE;
                return true;
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
                    isfinished = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    stopX = (int) event.getX();
                    stopY = (int) event.getY();
                    isfinished = false;
                    this.invalidate();
                    break;
                case MotionEvent.ACTION_UP:

// 도형 그리기 관련 정보 저장
                    MyShape shape = new MyShape();

                    shape.shapeType = curShape;
                    shape.startX = startX;
                    shape.startY = startY;
                    shape.stopX = stopX;
                    shape.stopY = stopY;
                    shape.color = curColor;

                    myshape.add(shape);
                    isfinished = true;
                    this.invalidate();
                    break;
            }

            return true;

        }

        // 도형 그리기 위해 paint 준비
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);


// 도형 그리기 저장 정보 리스트로 불러와서 그려놓기

            for (int i=0 ; i< myshape.size() ; i++) {

                MyShape shape = myshape.get(i);
                paint.setColor(shape.color);

                switch (shape.shapeType) {
                    case LINE:
                        canvas.drawLine(shape.startX, shape.startY, shape.stopX, shape.stopY, paint);
                        break;
                    case CIRCLE:
                        int radius = (int) Math.sqrt(Math.pow(shape.stopX - shape.startX, 2)
                                + Math.pow(shape.stopY - shape.startY, 2));
                        canvas.drawCircle(shape.startX, shape.startY, radius, paint);
                        break;
                    case RECTANGLE:
                        Rect rect = new Rect(shape.startX, shape.startY, shape.stopX, shape.stopY);
                        canvas.drawRect(rect, paint);
                        break;

                }
            }

// 도형 그리기 진행중

            if (isfinished == false) {

                paint.setColor(curColor);

                switch (curShape) {
                    case LINE:
                        canvas.drawLine(startX, startY, stopX, stopY, paint);
                        break;
                    case CIRCLE:
                        int radius = (int) Math.sqrt(Math.pow(stopX - startX, 2)
                                + Math.pow(stopY - startY, 2));
                        canvas.drawCircle(startX, startY, radius, paint);
                        break;
                    case RECTANGLE:
                        Rect rect = new Rect(startX, startY, stopX, stopY);
                        canvas.drawRect(rect, paint);
                        break;

                }
            }
        }

    }

    // 도형클래스
    private static class MyShape {
        int shapeType; // 도형 형태
        int startX, startY, stopX, stopY; // 도형 양끝점
        int color; // 도형 색

    }
}