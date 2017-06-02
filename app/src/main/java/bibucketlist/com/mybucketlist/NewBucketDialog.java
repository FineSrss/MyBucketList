package bibucketlist.com.mybucketlist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by pineone on 2017-05-11.
 */

public class NewBucketDialog extends AppCompatActivity {
    Context context;
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

    }
}
