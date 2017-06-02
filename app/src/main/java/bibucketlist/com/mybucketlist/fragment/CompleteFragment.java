package bibucketlist.com.mybucketlist.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bibucketlist.com.mybucketlist.R;

/**
 * Created by pineone on 2017-04-25.
 */

public class CompleteFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.complete_layout, container, false);

        return view;
    }

}
