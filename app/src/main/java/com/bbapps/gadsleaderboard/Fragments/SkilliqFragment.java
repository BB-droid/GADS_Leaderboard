package com.bbapps.gadsleaderboard.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bbapps.gadsleaderboard.Adapters.LeaderboardAdapter;
import com.bbapps.gadsleaderboard.Adapters.SkillIQAdapter;
import com.bbapps.gadsleaderboard.Model.LeadersModel;
import com.bbapps.gadsleaderboard.Model.LeadersSkillIQModel;
import com.bbapps.gadsleaderboard.R;
import com.bbapps.gadsleaderboard.ui.main.PageViewModel;
import com.bbapps.gadsleaderboard.util.HoursAPI;
import com.bbapps.gadsleaderboard.util.HoursService;
import com.bbapps.gadsleaderboard.util.Values;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkilliqFragment extends Fragment {


    private PageViewModel pageViewModel;
    private RecyclerView recyclerView;

    public SkilliqFragment() {
        // Required empty public constructor
    }

    public static SkilliqFragment newInstance(ArrayList<LeadersSkillIQModel> skill) {
        SkilliqFragment fragment = new SkilliqFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(Values.SKILLIQ_KEY, skill);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        ArrayList<LeadersSkillIQModel> skillIQModels = new ArrayList<>();
        if (getArguments() != null) {
            skillIQModels = getArguments().getParcelableArrayList(Values.SKILLIQ_KEY);
        }
        pageViewModel.setSkillData(skillIQModels);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_skilliq, container, false);
        recyclerView = view.findViewById(R.id.skillRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pageViewModel.getSkillData().observe(getViewLifecycleOwner(), leadersSkillIQModels -> {
            SkillIQAdapter adapter = new SkillIQAdapter(leadersSkillIQModels, getContext());
            recyclerView.setAdapter(adapter);
        });
    }

}