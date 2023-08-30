package com.example.andtdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.andtdb.addEditTable1.Table1ListAdapter;
import com.example.andtdb.addEditTable1.Table1ViewModel;
import com.example.andtdb.data.Table1;
import com.example.andtdb.data.Table1Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Table1ListActivity extends AppCompatActivity {

    private Table1ViewModel mTable1ViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Table1 table1 = new Table1(data.getStringExtra(NewTable1Activity.EXTRA_REPLY));
            mTable1ViewModel.insert(table1);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table1_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final Table1ListAdapter adapter = new Table1ListAdapter(new Table1ListAdapter.Table1Diff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTable1ViewModel = new ViewModelProvider(this).get(Table1ViewModel.class);

        mTable1ViewModel.getAllTable1().observe(this, table1s -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(table1s);
        });

        // Starting new table 1 activity on clicking FAB Button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener( view -> {
            Intent intent = new Intent(Table1ListActivity.this, NewTable1Activity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

    }
}