package com.example.lab4korbachdmytro.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lab4korbachdmytro.R;
import com.example.lab4korbachdmytro.database.University;
import com.example.lab4korbachdmytro.viewModel.ViewModelShowDetailedInformation;
import com.example.lab4korbachdmytro.viewModel.ViewModelShowTableUniversity;

import java.util.List;

public class DetailedInformationActivity extends AppCompatActivity {
    private ViewModelShowDetailedInformation viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_information);
        viewModel = new ViewModelProvider(this).get(ViewModelShowDetailedInformation.class);
        viewModel.connectDatabase(getApplication());
        TextView nameTextView = findViewById(R.id.name_text_view);
        TextView alphaTwoCodeTextView = findViewById(R.id.alpha_two_code_text_view);
        TextView countryTextView = findViewById(R.id.country_text_view);
        TextView stateTextView = findViewById(R.id.state_text_view);
        TextView estabilishedYearTextView = findViewById(R.id.established_year_text_view);
        RecyclerView domainView = findViewById(R.id.domains_view);
        RecyclerView webPageView = findViewById(R.id.web_page_view);
        viewModel.getUniversityView().observe(this, new Observer<University>() {
            @Override
            public void onChanged(University u) {
                nameTextView.setText(u.getName());
                alphaTwoCodeTextView.setText("Alpha Two Code: " + u.getAlphaTwoCode());
                countryTextView.setText("Country: " + u.getCountry());
                stateTextView.setText("State/Province: " + u.getStateProvince());
                estabilishedYearTextView.setText("Established Year: " + String.valueOf(u.getEstablishedYear()));
                if(u.getDomains().size() > 0) {
                    domainView.setLayoutManager(new LinearLayoutManager(DetailedInformationActivity.this));
                    URLAdapter adapterDomain = new URLAdapter(DetailedInformationActivity.this, u.getDomains());
                    domainView.setAdapter(adapterDomain);
                }
                if(u.getWebPages().size() > 0) {
                    webPageView.setLayoutManager(new LinearLayoutManager(DetailedInformationActivity.this));
                    URLAdapter adapterWebPage = new URLAdapter(DetailedInformationActivity.this, u.getWebPages());
                    webPageView.setAdapter(adapterWebPage);
                }
            }
        });
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            String name = receivedIntent.getStringExtra("name");
            viewModel.setUniversity(name);
        }
    }
    private class URLAdapter extends RecyclerView.Adapter<URLAdapter.ReactionViewHolder> {
        private List<String> URLs;
        private Context context;

        public URLAdapter(Context context, List<String> URLs) {
            this.context = context;
            this.URLs = URLs;
        }

        @NonNull
        @Override
        public URLAdapter.ReactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_url, parent, false);
            return new URLAdapter.ReactionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull URLAdapter.ReactionViewHolder holder, int position) {
            String result = URLs.get(position);
            holder.bind(result);
        }

        @Override
        public int getItemCount() {
            return URLs.size();
        }

        public class ReactionViewHolder extends RecyclerView.ViewHolder {

            private TextView textUrl;

            public ReactionViewHolder(@NonNull View itemView) {
                super(itemView);
                textUrl = itemView.findViewById(R.id.item_url);
            }

            public void bind(String result) {
                textUrl.setText(result);
            }
        }
    }



}