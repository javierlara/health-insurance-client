package com.apres.apresmovil.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.apres.apresmovil.models.Member;
import com.google.gson.Gson;

/**
 * Created by javierlara on 3/5/17.
 */

public class Session {

    private SharedPreferences mPrefs;

    public Session(Context context) {
        mPrefs = context.getSharedPreferences(
                "APRES", Context.MODE_PRIVATE);
    }

    public void setCurrentMember(Member member) {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(member);
        prefsEditor.putString("CurrentMember", json);
        prefsEditor.apply();
    }

    public Member getCurrentMember() {
        Gson gson = new Gson();
        String json = mPrefs.getString("CurrentMember", "");
        return gson.fromJson(json, Member.class);
    }
}
