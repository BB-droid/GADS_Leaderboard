package com.bbapps.gadsleaderboard.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bbapps.gadsleaderboard.Adapters.LeaderboardAdapter;
import com.bbapps.gadsleaderboard.Model.LeadersModel;
import com.bbapps.gadsleaderboard.R;
import com.bbapps.gadsleaderboard.ui.main.PageViewModel;
import com.bbapps.gadsleaderboard.util.Values;

import java.util.ArrayList;

public class LeaderBoardFragment extends Fragment {

    private RecyclerView recyclerView;
    private PageViewModel pageViewModel;

    public LeaderBoardFragment() {
    }

    public static LeaderBoardFragment newInstance(ArrayList<LeadersModel> model) {
        LeaderBoardFragment fragment = new LeaderBoardFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Values.HOURS_KEY,model);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        ArrayList<LeadersModel> models = new ArrayList<>();
        if (getArguments() != null) {
            models = getArguments().getParcelableArrayList(Values.HOURS_KEY);
        }
        pageViewModel.setData(models);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        pageViewModel.getLiveData().observe(getViewLifecycleOwner(), leadersModels -> {
            LeaderboardAdapter adapter = new LeaderboardAdapter(leadersModels, getContext());
            recyclerView.setAdapter(adapter);
        });


        return view;
    }

}
