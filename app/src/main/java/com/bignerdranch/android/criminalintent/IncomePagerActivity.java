package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class IncomePagerActivity extends AppCompatActivity {
    private static final String EXTRA_OTHER_ID =
            "com.bignerdranch.android.criminalintent.other_id";

    private ViewPager mViewPager;
    private List<Income> mIncomes;

    public static Intent newIncomeIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, IncomePagerActivity.class);
        intent.putExtra(EXTRA_OTHER_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_OTHER_ID);

        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);

        mIncomes = IncomeLab.get(this).getIncomes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Income income = mIncomes.get(position);
                return IncomeFragment.newInstance(income.getId());
            }

            @Override
            public int getCount() {
                return mIncomes.size();
            }
        });

        for (int i = 0; i < mIncomes.size(); i++) {
            if (mIncomes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
