package com.example.labo2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{

    private final List <Amendes> listAmendes;
    private OnItemClicked onClick;
    Context context;

    public MyListAdapter(List<Amendes> listAmendes, Context context) {
        this.context = context;
        this.listAmendes = listAmendes;
    }

    public interface OnItemClicked {
        void onItemClick(Amendes amendes);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textNom;
        public TextView textDate;
        public TextView textMontant;
        public ConstraintLayout ConstraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textNom = itemView.findViewById(R.id.textNom);
            this.textDate = itemView.findViewById(R.id.textPrenom);
            this.textMontant = itemView.findViewById(R.id.textZone);
            ConstraintLayout = itemView.findViewById(R.id.constraintLayout);

            itemView.setOnClickListener(view -> {
            });
        }
    }

    @NonNull
    @Override
    public MyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.list_item, parent, false);

        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Amendes myListData = listAmendes.get(position);
        holder.textNom.setText(myListData.getNom() + " " + myListData.getPrenom());
        holder.textDate.setText(myListData.getDate());
        holder.textMontant.setText(String.valueOf(myListData.getMontant()));

        holder.ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(myListData);
                //Toast.makeText(v.getContext(),"click on item: " + myListData.getNom(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAmendes.size();
    }

    public void addAmende(Amendes list) {
        listAmendes.add(0, list);
        notifyItemInserted(0);
    }

    public void delete(int position) {
        if(position != RecyclerView.NO_POSITION);
            listAmendes.remove(position);
            notifyItemRemoved(position);
    }

    public void setOnClick(OnItemClicked onClick){
        this.onClick=onClick;
    }

}
