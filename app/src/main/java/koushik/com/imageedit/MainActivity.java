package koushik.com.imageedit;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import koushik.com.imageedit.view.CustomImageView;
import com.adobe.creativesdk.aviary.AdobeImageIntent;
import com.adobe.creativesdk.foundation.AdobeCSDKFoundation;
import com.adobe.creativesdk.foundation.auth.IAdobeAuthClientCredentials;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.R.attr.bitmap;
import static android.media.MediaRecorder.VideoSource.CAMERA;

public class MainActivity extends AppCompatActivity  {




    Uri selectedImage = Uri.parse("http://ugc-01.cafemomstatic.com/gen/constrain/630/600/70/2017/03/10/15/bu/me/pol6fd0rso.jpg");
    Bitmap bitmap3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("CREATE:", "created activity");
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)    {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    1);
                         //   saveToInternalStorage(bmp);
                            //  startService(i);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    1);
                            //saveToInternalStorage(bmp);
                        } catch (Exception e) {

                        }

                    }
                };

                thread.start();

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        }

    }

    public void go(View view) {

        Uri imageUri = Uri.parse("http://ugc-01.cafemomstatic.com/gen/constrain/630/600/70/2017/03/10/15/bu/me/pol6fd0rso.jpg");

    /* 2) Create a new Intent */
        Intent imageEditorIntent = new AdobeImageIntent.Builder(MainActivity.this)
                .setData(imageUri)
                .build();

    /* 3) Start the Image Editor with request code 1 */
        startActivityForResult(imageEditorIntent, 1);



    }
    int PIC_CROP=2;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                /* 4) Make a case for the request code we passed to startActivityForResult() */
                case 1:

                    /* 5) Show the image! */
                    CustomImageView mImageView = (CustomImageView)findViewById(R.id.customImageVIew1);

                    Uri editedImageUri = data.getParcelableExtra(AdobeImageIntent.EXTRA_OUTPUT_URI);

                    try {
                         bitmap3 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), editedImageUri);
                        mImageView.setBitmap(bitmap3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    break;
            }
        }



        if (requestCode == CAMERA && resultCode == RESULT_OK) {

            selectedImage = data.getData();
            performCrop();

        }



        if (requestCode == PIC_CROP && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            //get the cropped bitmap
            Bitmap thePic = extras.getParcelable("data");

            // Do what you want to do with the pic here
        }




    }

    public void collage(View view) {
Intent i = new Intent(this,CollageActivity.class);
        startActivity(i);
    }

    public void createimage(View v) {
        LinearLayout pic = (LinearLayout)findViewById(R.id.compleateimage);


        pic.setDrawingCacheEnabled(true);
        // this is the important code :)
        // Without it the view will have a dimension of 0,0 and the bitmap will be null

        pic.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        pic.layout(0, 0, pic.getMeasuredWidth(), pic.getMeasuredHeight());

        pic.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(pic.getDrawingCache());
        pic.setDrawingCacheEnabled(false); // clear drawing cache
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Intent in1 = new Intent(this, Compleateactivity.class);
        in1.putExtra("image",byteArray);
        startActivity(in1);

    }

    public void crop(View view) {
        performCrop();
    }

    public void performCrop() {
    try {

        //call the standard crop action intent (the user device may not support it)
        Intent cropIntent = new Intent();
        //indicate image type and Uri
        cropIntent.setDataAndType(selectedImage, "image/*");
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT,selectedImage.toString());
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 640);
        cropIntent.putExtra("outputY", 640);
        //retrieve data on return
        cropIntent.putExtra("return-data", true);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, PIC_CROP);
    } catch (ActivityNotFoundException anfe) {
        //display an error message
        String errorMessage = "Whoops - your device doesn't support the crop action!";
        Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
        toast.show();
    }
}


}
