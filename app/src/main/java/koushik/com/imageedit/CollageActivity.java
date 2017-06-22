package koushik.com.imageedit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by koushikdeb on 07/06/17.
 */

public class CollageActivity  extends Activity {

    DemoView demoview;

    Bitmap bitmap = null,bi2;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        demoview = new DemoView(this);


            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.baby);
        bi2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        setContentView(demoview);
    }


    class DemoView extends View {
        Context context;
        float x,y;
        public DemoView(Context context) {

            super(context);

        }


        public boolean onTouchEvent(MotionEvent event)
        {

            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN: {

                }
                break;

                case MotionEvent.ACTION_MOVE:
                {
                    x=(int)event.getX();
                    y=(int)event.getY();

                    invalidate();
                }

                break;
                case MotionEvent.ACTION_UP:

                    x=(int)event.getX();
                    y=(int)event.getY();
                    System.out.println(".................."+x+"......"+y); //x= 345 y=530
                    invalidate();
                    break;
            }
            return true;
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

           Paint p=new Paint();
            p.setStyle(Paint.Style.FILL);
            ImageView book = new ImageView(context);
            book.setImageBitmap(bitmap);
            book.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {

                    return false;
                }
            });


            BitmapDrawable drawable = (BitmapDrawable) book.getDrawable();
            Bitmap bitmap2 = drawable.getBitmap();

                canvas.drawBitmap(bitmap2,0,0,p );

            canvas.drawBitmap(bi2,x,y,p );


        }
    }


}