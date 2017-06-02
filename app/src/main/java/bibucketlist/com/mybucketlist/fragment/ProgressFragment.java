package bibucketlist.com.mybucketlist.fragment;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bibucketlist.com.mybucketlist.R;
import bibucketlist.com.mybucketlist.adapter.ImageAdapter;
import bibucketlist.com.mybucketlist.model.ListViewItem;

/**
 * Created by pineone on 2017-04-25.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class ProgressFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SimpleDateFormat curDate = new SimpleDateFormat("yyyy-MM-dd");
    String toDate = curDate.format(new Date());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //ListView listView;
        //ImageAdapter adapter;

        List<ListViewItem> bucketLists = new ArrayList<>();

        for(int i = 0; i < 20; i++){
            ListViewItem buckList = new ListViewItem();
            buckList.setTitle("자전거 국토종주");
            buckList.setRegDate(toDate);
            buckList.setIcon(getContext().getDrawable(R.drawable.intro));
            bucketLists.add(buckList);
        }

        View view = inflater.inflate(R.layout.progress_layout, container, false);
        //if(view instanceof RecyclerView) {

        //    Context context = view.getContext();
            recyclerView = (RecyclerView) view.findViewById(R.id.progressListView);
            recyclerView.setHasFixedSize(true);
            mAdapter = new ImageAdapter(bucketLists, R.layout.imgae_layout);
            recyclerView.setAdapter(mAdapter);

            mLayoutManager = new LinearLayoutManager(this.getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

       // }
        //recyclerView = (RecyclerView) view.findViewById(R.id.progressListView);
        //List<ListViewItem> bucketLists = new ArrayList<>();



        //recyclerView.setAdapter(new ImageAdapter(bucketLists, R.layout.imgae_layout));
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        /*adapter = new ImageAdapter();

        listView = (ListView) view.findViewById(R.id.progressListView);
        listView.setAdapter(adapter);

        adapter.addItem(getContext().getDrawable(R.drawable.intro), "자전거 국토종주", toDate, "상", "하고싶은 것");
        adapter.addItem(getContext().getDrawable(R.drawable.intro), "클라이밍 배우기", toDate, "상", "하고싶은 것");
        adapter.addItem(getContext().getDrawable(R.drawable.intro), "마루노우치 새디스틱 완주", toDate, "중", "하고싶은 것");
        adapter.addItem(getContext().getDrawable(R.drawable.intro), "NAS로 개인서버 구축", toDate, "중", "하고싶은 것");
        adapter.addItem(getContext().getDrawable(R.drawable.intro), "유럽여행하기", toDate, "하", "하고싶은 것");
        adapter.addItem(getContext().getDrawable(R.drawable.intro), "반려동물 키우기", toDate, "중", "하고싶은 것");
        adapter.addItem(getContext().getDrawable(R.drawable.intro), "운전면허 따기", toDate, "상", "따고싶은 것");
        adapter.addItem(getContext().getDrawable(R.drawable.intro), "발렌시아가 신발 사기", toDate, "하", "사고싶은 것");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);
                String titleStr = item.getTitle();
                String regDate = item.getRegDate();
                String prior = item.getPriority();
                Drawable iconDrawable = item.getIcon();

                Toast.makeText(getActivity(), titleStr, Toast.LENGTH_SHORT).show();
            }

        });
                */
        //listView.setOnLongClickListener();


        return view;
    }
}
