package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Other {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private String mCost;

    public Other(){
        this(UUID.randomUUID());
    }

    public Other(UUID id){
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getCost() {
        return mCost;
    }

    public void setCost(String cost) {
        mCost = cost;
    }
}
