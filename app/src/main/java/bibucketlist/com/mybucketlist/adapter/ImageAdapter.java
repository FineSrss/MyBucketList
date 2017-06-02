package bibucketlist.com.mybucketlist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bibucketlist.com.mybucketlist.ListViewHolder;
import bibucketlist.com.mybucketlist.model.ListViewItem;

/**
 * Created by pineone on 2017-04-27.
 */

public class ImageAdapter extends RecyclerView.Adapter<ListViewHolder> {
    private List<ListViewItem> listViewItemList;
    private int itemLayout;

    public ImageAdapter(List<ListViewItem> items, int itemLayout) {
        this.listViewItemList = items;
        this.itemLayout = itemLayout;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position){
        ListViewItem item = listViewItemList.get(position);
        holder.titleText.setText(item.getTitle());
        holder.regDateText.setText(item.getRegDate());
        holder.bucketImage.setImageDrawable(item.getIcon());
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount(){
        return listViewItemList.size();
    }

 /*   @Override
    public int getCount(){
        return listViewItemList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Context context = parent.getContext();
        if(convertView == null){
            LayoutInflater imageInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = imageInflater.inflate(R.layout.imgae_layout, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.listTitleTextView);
        TextView regDateTextView = (TextView) convertView.findViewById(R.id.regDateTextView);
        TextView priorityTextView = (TextView) convertView.findViewById(R.id.priorityTextView);

        ListViewItem listViewItem = listViewItemList.get(position);
        imageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        regDateTextView.setText(listViewItem.getRegDate());
        priorityTextView.setText(listViewItem.getPriority());

        return convertView;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    public void addItem(Drawable icon, String title, String regDate, String priority, String categories){
        ListViewItem item = new ListViewItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setRegDate(regDate);
        item.setPriority(priority);
        item.setCategories(categories);

        listViewItemList.add(item);
    }*/
}
