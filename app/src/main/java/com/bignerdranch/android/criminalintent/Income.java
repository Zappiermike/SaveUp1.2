package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Income {
    private Date mDate;
    private UUID mId;
    private String mIncomeName;
    private String mIncomeAmount;

    public Income(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Income(UUID id){
        mId = id;
    }

    public UUID getId(){
        return mId;
    }
    public String getIncomeName(){
        return mIncomeName;
    }
    public String getIncomeAmount(){
        return mIncomeAmount;
    }
    public void setIncomeName(String title){
        mIncomeName= title;
    }
    public void setIncomeAmount(String amount){
        mIncomeAmount=amount;
    }
    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
