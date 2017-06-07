package bibucketlist.com.mybucketlist.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import bibucketlist.com.mybucketlist.R;
import bibucketlist.com.mybucketlist.model.Bucket;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by pineone on 2017-06-02.
 */

public class BucketRealmAdapter extends RealmBasedRecyclerViewAdapter<Bucket, BucketRealmAdapter.ViewHolder> {
    public class ViewHolder extends RealmViewHolder{
        public TextView bucketTitleText;
        public TextView bucketPriceText;
        public TextView bucketRegDate;
        public ImageView bucketImage;

        public ViewHolder(FrameLayout container){
            super(container);
            this.bucketTitleText = (TextView) container.findViewById(R.id.bucketTitle);
            this.bucketRegDate = (TextView) container.findViewById(R.id.regDate);
            this.bucketImage = (ImageView) container.findViewById(R.id.bucketImage);
        }
    }

    public BucketRealmAdapter(Context context, RealmResults<Bucket> realmResults, boolean automaticUpdate, boolean animateResults){
        super(context, realmResults, automaticUpdate, animateResults);
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.bucket_item_view, viewGroup, false);
        return new ViewHolder((FrameLayout) v);
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
        final Bucket bucket = realmResults.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bucket.getBucketImage(), 0, bucket.getBucketImage().length);
        viewHolder.bucketTitleText.setText(bucket.getBucketTitle());
        viewHolder.bucketRegDate.setText(bucket.getRegDate());
        viewHolder.bucketImage.setImageBitmap(bitmap);
    }
}
