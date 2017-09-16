package newapp.com.example.dipanshugupta.truecar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Dipanshu Gupta on 9/8/2017.
 */

public class VivzAdapter extends RecyclerView.Adapter<VivzAdapter.MyViewHoldler> {

    private LayoutInflater inflater;
    List<Information> data= Collections.emptyList();
    private Context context;
    private ClickListener clickListener;

    public VivzAdapter(Context context,List<Information> data){
        inflater=LayoutInflater.from(context);
        this.data=data;

    }
    @Override
    public MyViewHoldler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row,parent,false);
        MyViewHoldler holder= new MyViewHoldler(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHoldler holder, int position) {
        Information current=data.get(position);
        holder.title.setText(current.title);

    }
    public void setClickListener(ClickListener clickListener){
        this.clickListener=clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyViewHoldler extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;

        public MyViewHoldler(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title=(TextView) itemView.findViewById(R.id.listText);

        }

        @Override
        public void onClick(View view) {
            context.startActivity(new Intent(context,SubActivity.class));
            /*
            if(clickListener!=null){
                clickListener.itemClicked(view,getAdapterPosition());
            }*/
        }
    }
    public interface ClickListener{
        public void itemClicked(View view,int position);
    }
}
