package com.rashmiappd.grocerycart;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

   private EditText mEdittextName ;
   private TextView mTextViewAmount;
   private int mAmount = 0;
   private SQLiteDatabase mDatabase;
    private GroceryAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GroceryDBHelper dbHelper = new GroceryDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase(); //As we want to add the items so writable database

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GroceryAdapter(this,getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);



        mEdittextName = findViewById(R.id.EditTextNameID);
        mTextViewAmount = findViewById(R.id.textViewAmountID);

        Button buttonIncrease = findViewById(R.id.buttonIncID);
        Button buttonDecrease = findViewById(R.id.buttonDecID);
        Button buttonAdd = findViewById(R.id.buttonAddID);

        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increase();
            }
        });

        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrease();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
    }

    private void increase()
    {
        mAmount++;
        mTextViewAmount.setText(String.valueOf(mAmount));
    }
    private void decrease()
    {
        if(mAmount>0) {
            mAmount--;
            mTextViewAmount.setText(String.valueOf(mAmount));
        }
    }
    private void addItem()
    {
        if(mAmount == 0 || mEdittextName.getText().toString().trim().length() == 0)
            return;
        String name = mEdittextName.getText().toString();
        ContentValues cv = new ContentValues();             //A row in our database
        cv.put(GroceryContract.GroceryEntry.COLUMN_NAME,name);  //In that row, in Column name put name of item
        cv.put(GroceryContract.GroceryEntry.COLUMN_AMOUNT,mAmount);  //Under column amount put amount of item
        mDatabase.insert(GroceryContract.GroceryEntry.TABLE_NAME,null,cv);

        mAdapter.swapCursor(getAllItems());
        mEdittextName.getText().clear();

    }

    private void removeItem(long id)
    {
        mDatabase.delete(GroceryContract.GroceryEntry.TABLE_NAME, GroceryContract.GroceryEntry._ID+"="+id,null);
        mAdapter.swapCursor(getAllItems());
    }

    //Till now we have inserted the entries in our database, now to display it in a recycler view format we'll create another class called
   // GroceryAdapter whose super class will be recycler view adapter

    private Cursor getAllItems()
    {
        return mDatabase.query(
                GroceryContract.GroceryEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                GroceryContract.GroceryEntry.COLUMN_TIMESSTAMP + " DESC" );
    }
}
