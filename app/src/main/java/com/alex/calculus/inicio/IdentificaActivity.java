package com.alex.calculus.inicio;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alexf.mathkids.R;

import java.util.Locale;

public class IdentificaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identifica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //final MediaPlayer som = MediaPlayer.create(this, R.raw.fundo);
        //som.start();

        final SQLiteDatabase db;
        db = openOrCreateDatabase("Calculus", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        db.setLockingEnabled(true);
        db.execSQL("CREATE TABLE IF NOT EXISTS Usuarios(_id INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT, Idade TEXT)");

        Button entrar = (Button) findViewById(R.id.BtnComecar);
        entrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String nome, idade;
                EditText TxtNome = (EditText) findViewById(R.id.TxtId);
                EditText TxtIdade = (EditText) findViewById(R.id.TxtIdade);


                nome = TxtNome.getText().toString();
                idade = TxtIdade.getText().toString();


                if (nome.isEmpty() == true || idade.isEmpty() == true) {
                    Toast.makeText(getApplicationContext(), "Preencha os Campos !", Toast.LENGTH_SHORT).show();


                } else {
                    Cursor c = db.rawQuery("SELECT Nome,Idade FROM Usuarios WHERE Nome='" + TxtNome.getText() + "' AND Idade='" + TxtIdade.getText() + "'", null);

                    if (c.moveToFirst()) {
                        Toast.makeText(getApplicationContext(), "Bem Vindo, " + TxtNome.getText(), Toast.LENGTH_LONG).show();


                    } else {

                        db.execSQL("INSERT INTO Usuarios(Nome,Idade) VALUES('" + TxtNome.getText() + "','" + TxtIdade.getText() + "')");

                        Toast.makeText(getApplicationContext(), "Usuário Cadastrado com Sucesso!", Toast.LENGTH_LONG).show();


                    }
                    Intent home = new Intent(getApplicationContext(), MainActivity.class);
                    home.putExtra("nome", nome);
                    home.putExtra("idade", idade);
                    startActivity(home);

                }

                //som.stop();
                db.close();


            }
        });


    }


}
