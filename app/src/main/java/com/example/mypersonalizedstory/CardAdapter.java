package com.example.mypersonalizedstory;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<CardItem> cardItemList;

    private CardItem selectedCard;

    private int selectedPosition = -1;
    int lastSelectedPosition = -1;

    public CardAdapter(List<CardItem> cardItemList) {
        this.cardItemList = cardItemList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardItem currentItem = cardItemList.get(position);

        holder.titleTextView.setText(currentItem.getTitle());
        holder.subTextView.setText(currentItem.getSubtext());
        if (selectedPosition == position) {
            holder.itemView.setSelected(true); //using selector drawable
            setCardItem(cardItemList.get(selectedPosition));
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

        } else {
            holder.itemView.setSelected(false);
            holder.itemView.setBackgroundColor(Color.parseColor("#DCC1E1"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastSelectedPosition = selectedPosition;
                selectedPosition = holder.getBindingAdapterPosition();
                notifyItemChanged(lastSelectedPosition);
                notifyItemChanged(selectedPosition);
            }
        });

    }

    private void setCardItem(CardItem cardItem) {
        selectedCard = cardItem;

    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }

    public CardItem getCardItem(){
        return selectedCard;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView subTextView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            subTextView = itemView.findViewById(R.id.subTextView);
        }
    }
}
