package com.example.codetribe.emelda11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.key;
import static android.R.attr.name;

public class Main3Activity extends AppCompatActivity {
    EditText name, surname;
    Button update, save, retrieve;

    DatabaseReference databaseReference;
    Person person = new Person();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        save = (Button) findViewById(R.id.save);
        update = (Button) findViewById(R.id.update);
        retrieve = (Button) findViewById(R.id.ret);

        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);

//        databaseReference = FirebaseDatabase.getInstance().getReference()
//                .child("Person");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person.setName(name.getText().toString());
                person.setSurname(surname.getText().toString());

                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Person").push().setValue(person);
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        });

        Intent intent = getIntent();
        String key = intent.getStringExtra("key");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Person").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Person person = dataSnapshot.getValue(Person.class);
                name.setText(person.getName().toString());
                surname.setText(person.getSurname().toString());

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        person.setName(name.getText().toString());
                        person.setSurname(surname.getText().toString());

                        databaseReference.setValue(person);
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
