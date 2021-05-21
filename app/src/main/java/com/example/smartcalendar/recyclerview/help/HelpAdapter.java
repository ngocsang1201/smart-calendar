package com.example.smartcalendar.recyclerview.help;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcalendar.R;

import java.util.ArrayList;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpViewHolder> {

    Context context;
    ArrayList<HelpItem> helpItems;

    public HelpAdapter(Context context, ArrayList<HelpItem> helpItems) {
        this.context = context;
        this.helpItems = helpItems;
    }

    @NonNull
    @Override
    public HelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_help, parent, false);

        return new HelpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpAdapter.HelpViewHolder holder, int position) {
        holder.helpTitle.setText(helpItems.get(position).getTitle());
        holder.helpDesc.setText(helpItems.get(position).getDesc());
        holder.helpIcon.setImageResource(helpItems.get(position).getIcon());
        holder.helpIcon.setColorFilter(ContextCompat.getColor(context, helpItems.get(position).getColor()), android.graphics.PorterDuff.Mode.MULTIPLY);
        holder.helpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, holder.helpTitle.getText() + " coming soon", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return helpItems.size();
    }

    public class HelpViewHolder extends RecyclerView.ViewHolder {

        TextView helpTitle, helpDesc;
        ImageView helpIcon;
        CardView helpCard;

        public HelpViewHolder(@NonNull View itemView) {
            super(itemView);

            helpTitle = itemView.findViewById(R.id.helpTitle);
            helpDesc = itemView.findViewById(R.id.helpDesc);
            helpIcon = itemView.findViewById(R.id.helpIcon);
            helpCard = itemView.findViewById(R.id.helpCard);
        }
    }
}
