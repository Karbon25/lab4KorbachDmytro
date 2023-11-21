package com.example.lab4korbachdmytro.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4korbachdmytro.R;
import com.example.lab4korbachdmytro.database.University;

import java.util.List;

public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.ReactionViewHolder> {


    public interface OnItemClickListener {
        void onItemClickEvent(University university);
    }
    private List<University> universityList;
    private Context context;
    private UniversityAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public UniversityAdapter(Context context, List<University> universities) {
        this.context = context;
        this.universityList = universities;
    }

    @NonNull
    @Override
    public ReactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_university, parent, false);
        return new ReactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReactionViewHolder holder, int position) {
        University result = universityList.get(position);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickEvent(result);
            }
        });
        holder.bind(result);
    }

    @Override
    public int getItemCount() {
        return universityList.size();
    }

    public class ReactionViewHolder extends RecyclerView.ViewHolder {

        private TextView nameUniversity;

        public ReactionViewHolder(@NonNull View itemView) {
            super(itemView);
            nameUniversity = itemView.findViewById(R.id.item_university);
        }

        public void bind(University result) {
            nameUniversity.setText(result.getName());
        }
    }
}