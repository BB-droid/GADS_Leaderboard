package com.bbapps.gadsleaderboard.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.bbapps.gadsleaderboard.Model.LeadersModel;
import com.bbapps.gadsleaderboard.Model.LeadersSkillIQModel;

import java.util.ArrayList;

public class PageViewModel extends ViewModel {

    private MutableLiveData<ArrayList<LeadersModel>> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<LeadersSkillIQModel>> skillLiveData = new MutableLiveData<>();

    private LiveData<ArrayList<LeadersModel>> liveData1 = Transformations.map(mutableLiveData, input -> input);
    private LiveData<ArrayList<LeadersSkillIQModel>> skillData = Transformations.map(skillLiveData, input -> input);

    public void setData(ArrayList<LeadersModel> data) {
        mutableLiveData.setValue(data);
    }

    public void setSkillData(ArrayList<LeadersSkillIQModel> data) {
        skillLiveData.setValue(data);
    }

    public LiveData<ArrayList<LeadersModel>> getLiveData() {
        return liveData1;
    }
    public LiveData<ArrayList<LeadersSkillIQModel>> getSkillData() {
        return skillData;
    }
}