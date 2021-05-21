package com.example.smartcalendar.recyclerview.share;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcalendar.R;

import java.util.ArrayList;

public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ShareViewHolder> {

    Context context;
    ArrayList<ShareItem> shareItems;

    public ShareAdapter(Context context, ArrayList<ShareItem> shareItems) {
        this.context = context;
        this.shareItems = shareItems;
    }

    @NonNull
    @Override
    public ShareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_share, parent, false);

        return new ShareViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ShareAdapter.ShareViewHolder holder, int position) {
        holder.shareIcon.setImageResource(shareItems.get(position).getIcon());
        holder.shareCard.setBackgroundTintList(ContextCompat.getColorStateList(context, shareItems.get(position).getColor()));
        holder.shareCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Shared on " + shareItems.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return shareItems.size();
    }

    public class ShareViewHolder extends RecyclerView.ViewHolder {

        ImageView shareIcon;
        CardView shareCard;

        public ShareViewHolder(@NonNull View itemView) {
            super(itemView);

            shareIcon = itemView.findViewById(R.id.shareIcon);
            shareCard = itemView.findViewById(R.id.shareCard);
        }
    }
}
