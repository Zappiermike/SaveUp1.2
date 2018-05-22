package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.android.criminalintent.Crime;
import com.bignerdranch.android.criminalintent.CrimeLab;
import com.bignerdranch.android.criminalintent.CrimePagerActivity;
import com.bignerdranch.android.criminalintent.Income;
import com.bignerdranch.android.criminalintent.IncomeActivity;
import com.bignerdranch.android.criminalintent.IncomeLab;
import com.bignerdranch.android.criminalintent.Other;
import com.bignerdranch.android.criminalintent.OtherLab;
import com.bignerdranch.android.criminalintent.OtherPagerActivity;
import com.bignerdranch.android.criminalintent.R;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private RecyclerView mIncomeRecyclerView;
    private RecyclerView mOtherRecyclerView;
    private CrimeAdapter mAdapter;
    private IncomeAdapter mIncomeAdapter;
    private OtherAdapter mOtherAdapter;
    private boolean mSubtitleVisible;
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private Button mNewCrime;
    private Button mNewIncome;
    private Button mNewOther;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        if (savedInstanceState != null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mIncomeRecyclerView = (RecyclerView) view.findViewById(R.id.income_recycler_view);
        mIncomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mOtherRecyclerView = (RecyclerView) view.findViewById(R.id.other_recycler_view);
        mOtherRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        mNewCrime = (Button) view.findViewById(R.id.new_crime);
        mNewCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCrime();
            }
        });

        mNewOther = (Button) view.findViewById(R.id.new_other);
        mNewOther.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                newOther();
            }
        });

        mNewIncome = (Button) view.findViewById(R.id.new_income);
        mNewIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                newIncome();
            }
        });

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }



    private void newCrime() {
        Crime crime = new Crime();
        CrimeLab.get(getActivity()).addCrime(crime);
        Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
        startActivity(intent);
    }

    private void newIncome() {
        Income income = new Income();
        IncomeLab.get(getActivity()).addIncome(income);
        Intent intent = IncomePagerActivity.newIncomeIntent(getActivity(), income.getId());
        startActivity(intent);
    }

    private void newOther(){
        Other other = new Other();
        OtherLab.get(getActivity()).addOther(other);
        Intent intent = OtherPagerActivity.newOtherIntent(getActivity(), other.getId());

        startActivity(intent);
    }


    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural, crimeCount, crimeCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        OtherLab otherLab = OtherLab.get(getActivity());
        List<Other> others = otherLab.getOthers();

        IncomeLab incomeLab = IncomeLab.get(getActivity());
        List<Income> incomes = incomeLab.getIncomes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.setCrimes(crimes);
            mAdapter.notifyDataSetChanged();
        }

        if (mOtherAdapter == null) {
            mOtherAdapter = new OtherAdapter(others);
            mOtherRecyclerView.setAdapter(mOtherAdapter);
        }else{
            mOtherAdapter.setOthers(others);
            mOtherAdapter.notifyDataSetChanged();
        }

        if (mIncomeAdapter == null) {
            mIncomeAdapter = new IncomeAdapter(incomes);
            mIncomeRecyclerView.setAdapter(mIncomeAdapter);
        }else{
            mIncomeAdapter.setIncomes(incomes);
            mIncomeAdapter.notifyDataSetChanged();
        }



        updateSubtitle();
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Crime mCrime;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mCostView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mCostView = (TextView) itemView.findViewById(R.id.new_bill_expense);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(DateFormat.format("EEEE, MMMM dd", mCrime.getDate()).toString());
            mCostView.setText(mCrime.getCost());
        }

        @Override
        public void onClick(View view) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }


        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }
    }

    private class IncomeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Income mIncome;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mCostView;

        public IncomeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mCostView = (TextView) itemView.findViewById(R.id.new_bill_expense);
        }

        public void bind(Income income) {
            mIncome = income;
            mTitleTextView.setText(mIncome.getIncomeName());
            mDateTextView.setText(DateFormat.format("EEEE, MMMM dd", mIncome.getDate()).toString());
            mCostView.setText(mIncome.getIncomeAmount());
        }

        @Override
        public void onClick(View view) {
            Intent intent = IncomePagerActivity.newIncomeIntent(getActivity(), mIncome.getId());
            startActivity(intent);
        }
    }

    private class IncomeAdapter extends RecyclerView.Adapter<IncomeHolder> {

        private List<Income> mIncomes;

        public IncomeAdapter(List<Income> incomes) {
            mIncomes = incomes;
        }

        @Override
        public IncomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new IncomeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(IncomeHolder holder, int position) {
            Income income= mIncomes.get(position);
            holder.bind(income);
        }

        @Override
        public int getItemCount() {
            return mIncomes.size();
        }


        public void setIncomes(List<Income> incomes) {
            mIncomes = incomes;
        }
    }


    private class OtherHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Other mOther;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mCostView;

        public OtherHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mCostView = (TextView) itemView.findViewById(R.id.new_bill_expense);
        }

        public void bind(Other crime) {
            mOther = crime;
            mTitleTextView.setText(mOther.getTitle());
            mDateTextView.setText(DateFormat.format("EEEE, MMMM dd", mOther.getDate()).toString());
            mCostView.setText(mOther.getCost());
        }

        @Override
        public void onClick(View view) {
            Intent intent = OtherPagerActivity.newOtherIntent(getActivity(), mOther.getId());
            startActivity(intent);
        }
    }

    private class OtherAdapter extends RecyclerView.Adapter<OtherHolder> {

        private List<Other> mOthers;

        public OtherAdapter(List<Other> crimes) {
            mOthers = crimes;
        }

        @Override
        public OtherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new OtherHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(OtherHolder holder, int position) {
            Other crime = mOthers.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mOthers.size();
        }


        public void setOthers(List<Other> crimes) {
            mOthers = crimes;
        }
    }
}
