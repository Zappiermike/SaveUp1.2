package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.UUID;

public class IncomeFragment extends Fragment{
    private static final String ARG_INCOME_ID="income_id";

    private Income mIncome;
    private EditText mIncomeTitle;
    private EditText mIncomeAmount;
    private ImageView mImageView;

    public static IncomeFragment newInstance(UUID incomeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_INCOME_ID, incomeId);

        IncomeFragment fragment = new IncomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_income:
                IncomeLab.get(getActivity()).deleteIncome(mIncome);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_income, menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID incomeId = (UUID) getArguments().getSerializable(ARG_INCOME_ID);
        mIncome = IncomeLab.get(getActivity()).getIncome(incomeId);
        setHasOptionsMenu(true);
    }


    @Override
    public void onPause() {
        super.onPause();
        IncomeLab.get(getActivity())
                .updateIncome(mIncome);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v =inflater.inflate(R.layout.fragment_income, container, false);

        mImageView = (ImageView) v.findViewById(R.id.imageView02);
        Picasso.with(getContext())
                .load("https://cdn4.iconfinder.com/data/icons/economy-color-v-2/512/money_currency_bill_bills_stack_euro-256.png")
                .into(mImageView);


        mIncomeTitle = (EditText) v.findViewById(R.id.new_income_name);
        mIncomeTitle.setText(mIncome.getIncomeName());
        mIncomeTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mIncome.setIncomeName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mIncomeAmount = (EditText) v.findViewById(R.id.new_income_amount);
        mIncomeAmount.setText(mIncome.getIncomeAmount());
        mIncomeAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mIncome.setIncomeAmount(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
    }
}

