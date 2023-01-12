package com.example.agendap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.agendap.adapter.adapterNota;
import com.example.agendap.model.Nota;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class notasMainActivity extends AppCompatActivity {
    Button addnota, tohome;;
    RecyclerView mirecycle;
    adapterNota miadapter;
    FirebaseFirestore mifirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_main);
        this.setTitle("Notas");

        mifirestore = FirebaseFirestore.getInstance();
        mirecycle = findViewById(R.id.RVnotas);
        mirecycle.setLayoutManager(new LinearLayoutManager(this));
        Query query = mifirestore.collection("Nota");

        FirestoreRecyclerOptions<Nota> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Nota>().setQuery(query, Nota.class).build();
        miadapter = new adapterNota(firestoreRecyclerOptions, this);
        miadapter.notifyDataSetChanged();
        mirecycle.setAdapter(miadapter);

        addnota = findViewById(R.id.addnota);
        tohome = findViewById(R.id.tohome);

        addnota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CrearNotaActivity.class));
            }
        });

        tohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuPrincipal.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        miadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        miadapter.stopListening();
    }
}