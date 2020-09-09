package com.bbapps.gadsleaderboard.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bbapps.gadsleaderboard.Model.LeadersModel;
import com.bbapps.gadsleaderboard.Model.LeadersSkillIQModel;
import com.bbapps.gadsleaderboard.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SkillIQAdapter extends RecyclerView.Adapter<SkillIQAdapter.BoardHolder> {

    private final ArrayList<LeadersSkillIQModel> learners;
    private Context context;

    public SkillIQAdapter(ArrayList<LeadersSkillIQModel> learners, Context context) {
        this.learners = learners;
        this.context = context;
    }


    @NonNull
    @Override
    public BoardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.board_list, parent, false);
        return new BoardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardHolder holder, int position) {
        LeadersSkillIQModel pos = learners.get(position);
        String desc = pos.score + " skill IQ Score, "+ pos.country;
        holder.leaderName.setText(pos.name);
        holder.description.setText(desc);

        Glide.with(context)
                .load(R.drawable.skill_iq)
                .into(holder.badge);
    }

    @Override
    public int getItemCount() {
        return learners.size();
    }

    public class BoardHolder extends RecyclerView.ViewHolder {
        ImageView badge;
        TextView leaderName, description;

        public BoardHolder(@NonNull View itemView) {
            super(itemView);
            badge = itemView.findViewById(R.id.badge);
            leaderName = itemView.findViewById(R.id.learner_name);
            description = itemView.findViewById(R.id.learner_desc);
        }
    }
}
