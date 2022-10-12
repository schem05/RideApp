package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MakePost extends AppCompatActivity {
    private FirebaseDatabase fdbreference;
    EditText firstName;
    EditText lastName;
    EditText zoneNumber;
    EditText eventName;
    EditText description;
    EditText date;
    EditText email;
    Button submit;
    RadioGroup type;
    RadioButton selected;
    ArrayList<String> data;
    ImageButton i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        data = new ArrayList<String>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);

        firstName = findViewById(R.id.editTextTextPersonName);
        lastName = findViewById(R.id.editTextTextPersonName2);
        zoneNumber = findViewById(R.id.editTextNumber);
        eventName = findViewById(R.id.editTextTextPersonName3);
        description = findViewById(R.id.editTextTextMultiLine);
        date = findViewById(R.id.editTextDate);
        email = findViewById(R.id.editTextTextEmailAddress2);
        submit = findViewById(R.id.button);
        type = findViewById(R.id.radioGroup);
        i = findViewById(R.id.imageButton);

        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MakePost.this, AvailableOptions.class);
                startActivity(in);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String total = "";
        
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference fName = database.getReference("queries/" + eventName.getText().toString() + "/firstName");
                fName.setValue(firstName.getText().toString());

                DatabaseReference lName = database.getReference("queries/" + eventName.getText().toString() + "/lastName");
                lName.setValue(lastName.getText().toString());

                DatabaseReference zNumber = database.getReference("queries/" + eventName.getText().toString() + "/zNumber");
                zNumber.setValue(zoneNumber.getText().toString());

                DatabaseReference eName = database.getReference("queries/" + eventName.getText().toString() + "/eName");
                eName.setValue(eventName.getText().toString());

                DatabaseReference desc = database.getReference("queries/" + eventName.getText().toString() + "/desc");
                desc.setValue(description.getText().toString());

                DatabaseReference date_input = database.getReference("queries/" + eventName.getText().toString() + "/date");
                date_input.setValue(date.getText().toString());

                DatabaseReference email_input = database.getReference("queries/" + eventName.getText().toString() + "/email");
                email_input.setValue(email.getText().toString());

                DatabaseReference type_input = database.getReference("queries/" + eventName.getText().toString() + "/type");
                int selected_button = type.getCheckedRadioButtonId();
                selected = findViewById(selected_button);
                type_input.setValue(selected.getText().toString());


                DatabaseReference users = database.getReference("queries");

                users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        data.clear();
                        for (DataSnapshot ds: dataSnapshot.getChildren()) {
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
                                    Log.d("chars", ""+ word);
                                    data.add(word);
                                    word="";
                                }
                                if (parse) {
                                    word += currentChar;
                                }


                            }
                        }

                        Log.d("chars", data.toString());
                        String total = "";
                        for (int i = 0; i < data.size(); i++) {
                            total += data.get(i) + " ";
                        }

                        Intent i = new Intent(MakePost.this, AvailableOptions.class);
                        i.putExtra("VALUE", total);
                        startActivity(i);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });





    }


}
