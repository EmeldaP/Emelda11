package com.example.codetribe.emelda11;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name, surname;
    Button later, save, retrieve;

    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter<Person, PersonViewHolder> adapter;
    Person person;
String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Person");


        adapter = new FirebaseRecyclerAdapter<Person, PersonViewHolder>(Person.class, R.layout.person,
                PersonViewHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(PersonViewHolder viewHolder, Person model, final int position) {
                viewHolder.setName(model.getName());
                viewHolder.setSurname(model.getSurname());
                key = getRef(position).getKey();

                viewHolder.cardV().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Options")
                                .setNegativeButton("Update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                                        intent.putExtra("key",key);
                                        startActivity(intent);
                                    }
                                }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getRef(position).removeValue();
                            }
                        });

                        builder.create();
                        builder.show();
                    }
                });
            }
        };

        RecyclerView recycle;
        recycle = (RecyclerView) findViewById(R.id.recyclerV);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        private View view;

        public PersonViewHolder(View itemView) {
            super(itemView);
            view = itemView;

        }

        public void setName(String name) {
            TextView namm = (TextView) view.findViewById(R.id.name);
            namm.setText(name);
        }

        public void setSurname(String surname) {
            TextView surn = (TextView) view.findViewById(R.id.surname);
            surn.setText(surname);
        }

        public CardView cardV() {
            CardView cardV = (CardView) view.findViewById(R.id.card);
            return cardV;
        }


    }


}



