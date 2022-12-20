package com.example.first_app_project;

import static com.example.first_app_project.PlaceActivity.PLACE_ID_KEY;

import android.content.Context;
import android.content.Intent;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PlaceRecViewAdapter extends RecyclerView.Adapter<PlaceRecViewAdapter.ViewHolder> {

    private static final String TAG = "PlaceRecViewAdapter";
    private ArrayList<Place> places = new ArrayList<>();
    private Context mContext;
    private String parentActivyty;
    private SQLiteManager sqLiteManager;

    public PlaceRecViewAdapter(Context mContext, String parentActivyty) {
        this.mContext = mContext;
        this.parentActivyty = parentActivyty;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        sqLiteManager = SQLiteManager.instanceOfDatabase(this.mContext);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_place,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d(TAG,"onBindViewHolder: Called");
        holder.txtName.setText(places.get(position).getName());
        holder.imgPlace.setImageURI(places.get(position).getImageUri());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlaceActivity.class);
                intent.putExtra(PLACE_ID_KEY, places.get(holder.getAdapterPosition()).getId());
                mContext.startActivity(intent);
            }
        });


        holder.txtDescryption.setText(places.get(position).getLongDesc());

        if(places.get(position).getExpanded()){
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);
// w sumie switcha mogłem dać xd
            if(parentActivyty.equals("allPlaces")){
                holder.btdDelete.setVisibility(View.GONE);
            }else if(parentActivyty.equals("alreadySeen")){
                holder.btdDelete.setVisibility(View.VISIBLE);
                holder.btdDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        places.get(holder.getAdapterPosition()).setIsAlreadySeen(false);
                        Toast.makeText(mContext,"Place removed ", Toast.LENGTH_SHORT).show();
                        sqLiteManager.updatePlaceRatingDB(places.get(holder.getAdapterPosition()));
                        notifyDataSetChanged();
                    }
                });



            }else if(parentActivyty.equals("WantToSee")){
                holder.btdDelete.setVisibility(View.VISIBLE);
                holder.btdDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        places.get(holder.getAdapterPosition()).setIsWantToSee(false);
                        Toast.makeText(mContext," Place removed",Toast.LENGTH_SHORT).show();
                        sqLiteManager.updatePlaceRatingDB(places.get(holder.getAdapterPosition()));
                        notifyDataSetChanged();
                    }
                });

            }
            else if(parentActivyty.equals("favoritePlaces")){
                holder.btdDelete.setVisibility(View.VISIBLE);
                holder.btdDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        places.get(holder.getAdapterPosition()).setIsFavourite(false);
                        Toast.makeText(mContext," Place removed",Toast.LENGTH_SHORT).show();
                        sqLiteManager.updatePlaceRatingDB(places.get(holder.getAdapterPosition()));
                        notifyDataSetChanged();
                    }
                });

            }
        }
        else{
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelLayout.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView txtName;
        private ImageView imgPlace;
        private CardView parent;
        private ImageView downArrow,upArrow;
        private RelativeLayout expandedRelLayout;
        private TextView txtDescryption;
        private TextView btdDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtPlaceName);
            imgPlace = itemView.findViewById(R.id.imgPlace);
            parent = itemView.findViewById(R.id.parent);
            downArrow = itemView.findViewById(R.id.btnDownArrow);
            upArrow = itemView.findViewById(R.id.btnUpArrow);
            expandedRelLayout= itemView.findViewById(R.id.expandRelativeLayout);

            txtDescryption= itemView.findViewById(R.id.txtShortDesc);
            btdDelete = itemView.findViewById(R.id.btnDelete);
            downArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Place place = places.get(getAdapterPosition());
                    place.setExpanded(!place.getExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            upArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Place place = places.get(getAdapterPosition());
                    place.setExpanded(!place.getExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }
}
