package com.riseworld.reddit.android.ui.adapter;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.riseworld.reddit.android.ui.pojo.DataObject;
import com.riseworld.reddit.android.ui.view.supportphonetable.R;
import com.riseworld.reddit.android.ui.view.supportphonetable.databinding.CardViewRowBinding;

import java.util.ArrayList;

/**
 * This class is basically a bridge between the UI components and the
 * data source that fill data into the UI Component
 */
public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {

    private static MyClickListener myClickListener;
    private ArrayList<DataObject> mDataset;

    public MyRecyclerViewAdapter(ArrayList<DataObject> myDataset) {
        mDataset = myDataset;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        MyRecyclerViewAdapter.myClickListener = myClickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);
        DataObjectHolder dataObjectHolder;
        dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
       DataObject status = mDataset.get(position);
        holder.bind(status);
    }

    /**
     * @param index
     * @return
     */
    public DataObject getItemDataObject(int index) {
        return mDataset.get(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        ArrayList itemsSelected = new ArrayList<View>();
        private CardViewRowBinding binding;
        public DataObjectHolder(View itemView) {
           super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
 //           releaseBg(); In progress
//            v.setBackgroundColor(Color.GREEN);
 //           itemsSelected.add(v);
        }

        private void releaseBg(){
            for (int i = 0; i < itemsSelected.size(); i++) {
                View v = (View)itemsSelected.get(i);
                v.setBackgroundColor(Color.TRANSPARENT);
            }
        }
        public void bind(DataObject dataObject) {
            binding.setDataObject(dataObject);
        }
    }
}