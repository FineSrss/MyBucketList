package bibucketlist.com.mybucketlist.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import bibucketlist.com.mybucketlist.R;
import bibucketlist.com.mybucketlist.model.Bucket;
import io.realm.Realm;

/**
 * Created by pineone on 2017-05-11.
 */

public class NewBucketDialog extends AppCompatActivity {
    private static final int PICK_FROM_ALBUM = 0;
    private Context mContext;
    private ImageView bucketImage;
    private Realm realm;

    public NewBucketDialog(Context context)
    {
        this.mContext = context;
        realm = Realm.getDefaultInstance();
    }

    public void buildAndShowInputBucketDialog(){
        Log.d("TAG", "buildAndShowInputBucketDialog called!!");
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Add A Bucket");

        LayoutInflater li = LayoutInflater.from(mContext);
        View dialogView = li.inflate(R.layout.bucket_add_dialog_view, null);
        final Button imgSelectBtn = (Button) dialogView.findViewById(R.id.dialog_select_imagebtn);
        final Spinner bucketCategory = (Spinner) dialogView.findViewById(R.id.dialog_select_category);
        final EditText bucketTitle = (EditText) dialogView.findViewById(R.id.dialog_edit_bucket_title);
        final EditText bucketPrice = (EditText) dialogView.findViewById(R.id.dialog_edit_bucket_price);
        bucketImage = (ImageView) dialogView.findViewById(R.id.dialog_image_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext
                , android.R.layout.simple_spinner_item
                , mContext.getResources().getStringArray(R.array.categories));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bucketCategory.setAdapter(adapter);
        bucketCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bucketPrice.setText("");
                if(position == 2)
                    bucketPrice.setVisibility(View.VISIBLE);
                else if(position == 1 || position == 3)
                    bucketPrice.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgSelectBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                Log.d("TAG", "imgSelectBtn Onclick");
                ((Activity) mContext).startActivityForResult(intent, PICK_FROM_ALBUM);

            }
        });

        builder.setView(dialogView);
        builder.setPositiveButton("추가", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addBucket(bucketCategory.getSelectedItemPosition()
                        , bucketTitle.getText().toString()
                        , bucketPrice.getText().toString()
                        , ((BitmapDrawable) bucketImage.getDrawable()).getBitmap());
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = builder.show();

    }


    public void storeCropImage(Bitmap bitmap, String filePath){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bucketImage";
        File directory_bucketImage = new File(dirPath);

        if(!directory_bucketImage.exists())
            directory_bucketImage.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try{
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addBucket(int category, String bucketTitle, String bucketPrice, Bitmap bucketImage){
        SimpleDateFormat sdfNow = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sdfNow = new SimpleDateFormat("yyyy-MM-dd");
        }
        String regDate = sdfNow.format(System.currentTimeMillis());
        realm.beginTransaction();
        Bucket bucket = realm.createObject(Bucket.class, System.currentTimeMillis());
        bucket.setBucketTitle(bucketTitle);
        bucket.setCategory(category);
        bucket.setRegDate(regDate);

        if(bucketPrice.equals(""))
            bucket.setBucketPrice(0);
        else
            bucket.setBucketPrice(Integer.parseInt(bucketPrice));

        ByteArrayOutputStream stream = new ByteArrayOutputStream(0);
        bucketImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bucket.setBucketImage(byteArray);
        realm.commitTransaction();
    }


    public ImageView getBucketImage() {
        return bucketImage;
    }
}
