package com.apres.apresmovil.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apres.apresmovil.R;
import com.apres.apresmovil.models.News;
import com.apres.apresmovil.network.ApiHelper;
import com.apres.apresmovil.views.adapters.MyNewsItemRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private NewsItemFragment.OnListFragmentInteractionListener mListener;
    private ApiHelper mApiHelper;
    private List<News> mNewsList;
    private MyNewsItemRecyclerViewAdapter mNewsAdapter;

    public NewsItemFragment() {
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsItemFragment newInstance(int columnCount) {
        NewsItemFragment fragment = new NewsItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_healthcenteritem_list, container, false);

        mNewsList = new ArrayList<>();
        mNewsAdapter = new MyNewsItemRecyclerViewAdapter(mNewsList, mListener, getContext());

        mApiHelper.getNewses(new ApiHelper.ApiHelperCallback() {
            @Override
            public void onSuccess(List list) {
                mNewsList.clear();
                mNewsList.addAll(list);
                mNewsAdapter.notifyDataSetChanged();
                Log.i("NEWS", mNewsList.toString());
            }

            @Override
            public void onError(Exception e) {
                Log.e("NEWSERROR", e.getMessage());
            }
        });

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(mNewsAdapter);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        mApiHelper = new ApiHelper(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(News item);
    }
}
