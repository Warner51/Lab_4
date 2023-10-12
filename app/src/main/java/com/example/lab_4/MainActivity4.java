package com.example.lab_4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity4 extends AppCompatActivity {
    private MyListAdapter adapter;
    private ArrayList<String> todo = new ArrayList<>(Arrays.asList());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        ListView myList = findViewById(R.id.myList);
        EditText editText = findViewById(R.id.editText);
        Button addButton = findViewById(R.id.Button);
        Switch urgent = findViewById(R.id.Switch);

        myList.setAdapter(adapter = new MyListAdapter());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = editText.getText().toString().trim();

                if (!newItem.isEmpty()) {
                    todo.add(newItem);
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                }
            }
        });

        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder build = new AlertDialog.Builder(MainActivity4.this);

                build.setTitle("Do you want to delete this?");
                build.setMessage("The selected row is: " + position);

                build.setPositiveButton("Yes", (click, arg) -> {
                    todo.remove(position);
                    adapter.notifyDataSetChanged();
                });

                build.setNegativeButton("No", (click, arg) -> {
                    //close the alert
                });

                build.show();
                return true;
            }
        });
    }

    private class MyListAdapter extends BaseAdapter {
        public int getCount() { return todo.size(); }

        public Object getItem(int position) { return todo.get(position); }

        public long getItemId(int position) { return (long) position; }

        public View getView(int position, View old, ViewGroup parent) {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            if(newView == null){
                newView = inflater.inflate(R.layout.list_items4, parent, false);
            }

            TextView tvItems = newView.findViewById(R.id.tvItems);
            tvItems.setText(getItem(position).toString());

            return newView;

        }
    }
}