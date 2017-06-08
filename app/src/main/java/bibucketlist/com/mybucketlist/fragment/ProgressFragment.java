package bibucketlist.com.mybucketlist.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bibucketlist.com.mybucketlist.R;
import bibucketlist.com.mybucketlist.adapter.BucketRealmAdapter;
import bibucketlist.com.mybucketlist.model.Bucket;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by pineone on 2017-04-25.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class ProgressFragment extends Fragment {

    private BucketRealmAdapter bucketRealmAdapter;
    private RealmRecyclerView realmRecyclerView;
    private Realm realm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.progress_layout, container, false);
        realm = Realm.getDefaultInstance();
        RealmResults<Bucket> buckets = realm.where(Bucket.class).findAllSorted("bucketNum", Sort.ASCENDING);
        bucketRealmAdapter = new BucketRealmAdapter(view.getContext(), buckets, true, true);
        realmRecyclerView = (RealmRecyclerView) view.findViewById(R.id.realm_progress_recycler_view);
        realmRecyclerView.setAdapter(bucketRealmAdapter);

        return view;

    }
}
