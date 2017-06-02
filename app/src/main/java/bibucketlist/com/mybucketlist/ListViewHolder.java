package bibucketlist.com.mybucketlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pineone on 2017-05-02.
 */

public class ListViewHolder extends RecyclerView.ViewHolder{

    public ImageView bucketImage;
    public TextView titleText, regDateText;//, priorityText;

    public ListViewHolder(View itemView){
        super(itemView);
        bucketImage = (ImageView) itemView.findViewById(R.id.imageView);
        titleText = (TextView) itemView.findViewById(R.id.listTitleTextView);
        regDateText = (TextView) itemView.findViewById(R.id.regDateTextView);
        //priorityText = (TextView) itemView.findViewById(R.id.priorityTextView);
    }
}
