package com.rashmiappd.grocerycart;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {

    private Context mcontext;  //member variable of class adapter: context and cursor
    private Cursor mcursor;

    public GroceryAdapter(Context context,Cursor cursor)  //constructor
    {
        mcontext = context;//A database cursor is an object that enables traversal over
            // the rows of a result set. It allows you to process individual row returned by a query.
        mcursor = cursor;
    }

    //A ViewHolder describes an item view and metadata about its place within the RecyclerView.
    public class GroceryViewHolder extends RecyclerView.ViewHolder{

        public TextView nametext;
        public TextView amounttext;
        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
            /*An ItemView in Android can be described as a single row item in a list.
              It references an item from where we find the view from its layout file.*/


            nametext = itemView.findViewById(R.id.textView_nameItemID);
            amounttext = itemView.findViewById(R.id.textView_amountItemID);
        }
    }

    /*A LayoutInflater is one of the Android System Services that is responsible for taking your XML files
    that define a layout, and converting them into View objects. */

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.grocery_item, parent,false);
        return new GroceryViewHolder(view);
    }

    @Override //See below for comments
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        if(!mcursor.moveToPosition(position))
            return;
        String name = mcursor.getString(mcursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_NAME));
        int amount = mcursor.getInt(mcursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_AMOUNT));
        long id = mcursor.getLong(mcursor.getColumnIndex(GroceryContract.GroceryEntry._ID));

        holder.nametext.setText(name);
        holder.amounttext.setText(String.valueOf(amount));
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return mcursor.getCount();
    }
    //When we want to change the data, we need to pass a new cursor,so make a new function for that

    public void swapCursor(Cursor newCursor)
    {
        if(mcursor != null)
        {
            mcursor.close();
        }

        mcursor = newCursor;
        if(newCursor!=null)
        {
            notifyDataSetChanged();  //We notify the recycler view about this change
        }
    }
}
/*RecyclerView is designed to display long lists (or grids) of items. Say you want to display
 100 rows of something. A simple approach would be to just create 100 views, one for each row
 and lay all of them out. But that would be wasteful, because most of them would be off screen,
 because lets say only 10 of them fit on screen.

So RecyclerView instead creates only the 10 views that are on screen.
This way you get 10x better speed and memory usage. But what happens when you start scrolling
and need to start showing next views?

Again a simple approach would be to create a new view for each new row that you need to show.
 But this way by the time you reach the end of the list you will have created 100 views and
 your memory usage would be the same as in the first approach. And creating views takes time,
  so your scrolling most probably wouldn't be smooth.

This is why RecyclerView takes advantage of the fact that as you scroll and new rows come on screen
also old rows disappear off screen. Instead of creating new view for each new row,
 an old view is recycled and reused by binding new data to it.

This happens exactly in onBindViewHolder(). Initially you will get new unused view holders and
 you have to fill them with data you want to display. But as you scroll you'll start getting
 view holders that were used for rows that went off screen and you have to replace old data that
 they held with new data.*/