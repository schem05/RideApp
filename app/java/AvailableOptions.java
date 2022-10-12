package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AvailableOptions extends AppCompatActivity {
    ListView lv;
    ArrayList<String> data;
    ArrayList<Query> queries;
    Button makeRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        data = new ArrayList<>();
        queries = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_options);

        lv = findViewById(R.id.listView);
        makeRequest = findViewById(R.id.button3);

        makeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(AvailableOptions.this, MakePost.class);
                startActivity(back);
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference users = database.getReference("queries");
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String event = ds.getKey();
                    boolean parse = false;
                    String word = "";
                    for (int i = 0; i < ds.getValue().toString().length(); i++) {
                        char currentChar = ds.getValue().toString().charAt(i);
                        if (currentChar == '=') {
                            parse = true;
                            continue;
                        }
                        if (currentChar == ',' || currentChar == '}') {
                            parse = false;
                            data.add(word);
                            word = "";
                        }
                        if (parse) {
                            word += currentChar;
                        }
                    }
                }
                for (int i = 0; i < data.size()-7; i++) {
                    queries.add(new Query(data.get(i + 1), data.get(i + 2), data.get(i + 4), data.get(i + 3), data.get(i + 7), data.get(i), data.get(i + 6), data.get(i + 5)));
                    i+=7;
                }
                ArrayAdapter aa = new ArrayAdapter<Query>(AvailableOptions.this, android.R.layout.simple_list_item_1, queries);
                lv.setAdapter(aa);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog ad = new AlertDialog.Builder(AvailableOptions.this).create();
                ad.setTitle(queries.get(position).getEvent());
                ad.setMessage("Enter your name below:");
                final EditText input = new EditText(AvailableOptions.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                ad.setView(input);
                ad.setButton(Dialog.BUTTON_NEGATIVE, "REMOVE CARPOOL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference users = database.getReference("queries/" + queries.get(position).getEvent());
                        users.removeValue();
                        Intent back = new Intent(AvailableOptions.this, AvailableOptions.class);
                        startActivity(back);
                    }
                });
                ad.setButton(Dialog.BUTTON_POSITIVE, "JOIN CARPOOL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference users = database.getReference("queries");
                        String curr_user = queries.get(position).getFirstName();
                        String click_user = input.getText().toString();
                        queries.get(position).setFirstName(curr_user + " | " + click_user);
                        users.child(queries.get(position).getEvent()).child("firstName").setValue(curr_user + " | " + click_user);
                        Intent back = new Intent(AvailableOptions.this, AvailableOptions.class);
                        startActivity(back);

                    }
                });
                ad.show();
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }
}