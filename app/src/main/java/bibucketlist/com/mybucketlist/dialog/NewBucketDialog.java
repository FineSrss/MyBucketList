package bibucketlist.com.mybucketlist.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
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
    private static final int CROP_FROM_IMAGE = 1;
    private Context mContext;
    private ImageView bucketImage;
    private Uri imgUri;
    private String absolutePath;
    private Realm realm;


    public NewBucketDialog(Context context)
    {
        this.mContext = context;
        Log.d("TAG", "[mk] called!!");
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
                //startActivityForResult(intent, PICK_FROM_ALBUM);

                //Activity activity = (Activity) mContext;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "new bucket, onActivityResult call!1");

        if(resultCode != RESULT_OK) return;

        switch (requestCode){
            case PICK_FROM_ALBUM:
                imgUri = data.getData();
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(imgUri, "image/*");

                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 200);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                ((Activity) mContext).startActivityForResult(intent, CROP_FROM_IMAGE);
                break;
            case CROP_FROM_IMAGE:
                if(resultCode != RESULT_OK) return;

                final Bundle extras = data.getExtras();

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/bucketImage/" + System.currentTimeMillis() + ".jpg";

                if(extras != null){
                    Bitmap photo = extras.getParcelable("data");
                    bucketImage.setImageBitmap(photo);
                    storeCropImage(photo, filePath);
                    absolutePath = filePath;
                    break;
                }

                File f = new File(imgUri.getPath());
                if(f.exists())
                    f.delete();

                break;
        }
    }

    private void storeCropImage(Bitmap bitmap, String filePath){
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

    /*Context context;
    private static final int PICK_PROM_ALBUM = 0;
    AlertDialog.Builder mBuilder;
    LayoutInflater inflater;
    View mView;
    Spinner mSpinner;
    EditText bucketTitle;
    EditText bucketPrice;
    ImageView bucketImg;

    public NewBucketDialog(Context context){
        this.context = context;
    }

    public void createBucketDialog(){
        mBuilder = new AlertDialog.Builder(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.list_item_dialog, null);

        mSpinner = (Spinner) mView.findViewById(R.id.selectCategories);
        bucketTitle = (EditText) mView.findViewById(R.id.titleText);
        bucketPrice = (EditText) mView.findViewById(R.id.priceText);
        bucketImg = (ImageView) mView.findViewById(R.id.bucketImage);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, context.getResources().getStringArray(R.array.categories));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        Button addImageBtn = (Button) mView.findViewById(R.id.addImage);
        Button saveBtn = (Button) mView.findViewById(R.id.saveBucketBtn);
        Button cancelBtn = (Button) mView.findViewById(R.id.cancelBucketBtn);
        ImageView bucketImg = (ImageView) mView.findViewById(R.id.bucketImage);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1)
                    bucketPrice.setVisibility(View.INVISIBLE);
                else if(position == 2)
                    bucketPrice.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addImageBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void showFileChooser(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        //intent.addCategory(Intent.CATEGORY_OPENABLE);

        try{
             startActivityForResult(intent, PICK_PROM_ALBUM);
        } catch(android.content.ActivityNotFoundException ex){
            Toast.makeText(this, "Please install a File Manager", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            return;
        }
        switch (requestCode)
        {
            case PICK_PROM_ALBUM:
                Uri uri = data.getData();
                bucketImg.setImageURI(uri);
                break;
        }

    }*/
}
