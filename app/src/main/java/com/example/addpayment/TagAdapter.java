package com.example.addpayment;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder>{
    private List<String> mDataSet;
    private List<String> mDataPayment;
    private Context mContext;


    // This defines all the arguments passed to Adapter.
    public TagAdapter(Context context,List<String> list,List<String> paymentlist){
        mDataSet = list;
        mDataPayment = paymentlist;
        mContext = context;
    }
    // This defines all the views in tag element and also define viewholder.
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView,tv_paymentMode,tv_provider,tv_transactiom;
        public ImageButton mRemoveButton;
        public RelativeLayout mRelativeLayout;
        public ViewHolder(View v){
            super(v);
            mTextView = v.findViewById(R.id.tv_amount);
            tv_paymentMode = v.findViewById(R.id.tv_paymentMode);
            mRemoveButton = v.findViewById(R.id.ib_remove);
            mRelativeLayout = v.findViewById(R.id.rl);
        }
    }
    // This is Oncreate method for TagAdapter
    @Override
    public TagAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    //  On bind view for setting postions in tag views.
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        holder.mTextView.setText((String)mDataSet.get(position));
        holder.tv_paymentMode.setText(mDataPayment.get(position) + "=");
        holder.mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemLabel = mDataSet.get(position);
                mDataSet.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mDataSet.size());
            }
        });
    }
    // This function is to return item count in list
    @Override
    public int getItemCount(){
        return mDataSet.size();
    }
}