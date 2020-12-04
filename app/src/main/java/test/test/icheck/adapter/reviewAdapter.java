package test.test.icheck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import test.test.icheck.R;
import test.test.icheck.entity.reviews;
import test.test.icheck.refreshDetails;

public class reviewAdapter extends RecyclerView.Adapter<reviewAdapter.MyViewHolder> {
    private ArrayList<reviews> dataSet;
    private Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,rate,message;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.id_reviewName);
            this.rate = (TextView) itemView.findViewById(R.id.id_reviewRate);
            this.message = (TextView) itemView.findViewById(R.id.id_reviewMessage);
            this.image = (ImageView) itemView.findViewById(R.id.id_imageReview);

        }
    }

    public reviewAdapter(ArrayList<reviews> data,Context context) {
        this.dataSet = data;
        this.context = context;
    }
    public void insertData(ArrayList<reviews> insertList){
        refreshDetails refresh = new refreshDetails(dataSet,insertList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(refresh);
        dataSet.addAll(insertList);
        diffResult.dispatchUpdatesTo(this);
    }
    public void updateData(ArrayList<reviews> insertList){
        refreshDetails refresh = new refreshDetails(dataSet,insertList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(refresh);
        dataSet.clear();
        dataSet.addAll(insertList);
        diffResult.dispatchUpdatesTo(this);
    }
    @Override
    public reviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_reviewscardview, parent, false);


        reviewAdapter.MyViewHolder myViewHolder = new reviewAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final reviewAdapter.MyViewHolder holder, final int listPosition) {
        String pathImage="https://polar-peak-71928.herokuapp.com/uploads/users/";
        String fullPath = pathImage+dataSet.get(listPosition).getUser().getAvatar();
        TextView name = holder.name;
        TextView rate = holder.rate;
        TextView message = holder.message;
        ImageView image = holder.image;
        String rate2 = Double.toString(dataSet.get(listPosition).getRate());
        String firstName =dataSet.get(listPosition).getUser().getFirstName();
        String lastName = dataSet.get(listPosition).getUser().getLastName();

        name.setText(firstName+" "+lastName);
        rate.setText(rate2);
        message.setText(dataSet.get(listPosition).getReview());
        Glide.with(context).load(fullPath).into(image);
        //image.setImageResource(dataSet.get(listPosition).getImage());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
