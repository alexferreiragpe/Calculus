package com.alex.calculus.inicio;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alex.calculus.niveis.Nivel_Dificil_FullscreenActivity;
import com.alex.calculus.niveis.Nivel_Facil_FullscreenActivity;
import com.alex.calculus.niveis.Nivel_Medio_FullscreenActivity;
import com.alex.calculus.recordes.Recordes_FullscreenActivity;
import com.example.alexf.mathkids.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //String nomes ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final String Nome = intent.getStringExtra("nome");
        final String Idade = intent.getStringExtra("idade");
        setTitle("Calculus");


        final SQLiteDatabase db;

        db = openOrCreateDatabase("Calculus", Context.MODE_PRIVATE, null);

        Cursor c = db.rawQuery("SELECT Nome,Idade FROM Usuarios WHERE Nome='" + Nome + "' AND Idade='" + Idade + "'", null);

        db.execSQL("CREATE TABLE IF NOT EXISTS RecordeFacilDivisao(_id INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT, Idade TEXT, Recorde INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS RecordeFacilMultiplicacao(_id INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT, Idade TEXT, Recorde INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS RecordeFacilSoma(_id INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT, Idade TEXT, Recorde INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS RecordeFacilSubtracao(_id INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT, Idade TEXT, Recorde INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS RecordeNivelMedio(_id INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT, Idade TEXT, Recorde INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS RecordeNivelDificil(_id INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT, Idade TEXT, Recorde INTEGER)");
        TextView usuario = (TextView) findViewById(R.id.TxtUsuario);
        if (c.moveToFirst()) {
            usuario.setText("Bem Vindo, " + c.getString(c.getColumnIndex("Nome")));
        } else {
            usuario.setText("Bem Vindo! " + Nome);
        }


        Button BtnNivelFacil = (Button) findViewById(R.id.BtnFacil);
        Button BtnNivelMedio = (Button) findViewById(R.id.BtnMedio);
        Button BtnNivelDificil = (Button) findViewById(R.id.BtnDificil);


        BtnNivelFacil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NivelFacil = new Intent(getApplicationContext(), Nivel_Facil_FullscreenActivity.class);
                NivelFacil.putExtra("nome", Nome);
                NivelFacil.putExtra("idade", Idade);
                startActivity(NivelFacil);
                db.close();

            }
        });

        BtnNivelMedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NivelMedio = new Intent(getApplicationContext(), Nivel_Medio_FullscreenActivity.class);
                NivelMedio.putExtra("nome", Nome);
                NivelMedio.putExtra("idade", Idade);
                startActivity(NivelMedio);
                db.close();
            }
        });

        BtnNivelDificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NivelDificil = new Intent(getApplicationContext(), Nivel_Dificil_FullscreenActivity.class);
                NivelDificil.putExtra("nome", Nome);
                NivelDificil.putExtra("idade", Idade);
                startActivity(NivelDificil);
                db.close();

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent = getIntent();
        final String Nome = intent.getStringExtra("nome");
        final String Idade = intent.getStringExtra("idade");


        if (id == R.id.nav_camera) {
            Intent NovoJogo = new Intent(this, IdentificaActivity.class);
            startActivity(NovoJogo);

        } else if (id == R.id.nav_gallery) {
            Intent Recordes = new Intent(getApplicationContext(), Recordes_FullscreenActivity.class);
            Recordes.putExtra("nome", Nome);
            Recordes.putExtra("idade", Idade);
            startActivity(Recordes);

        } else if (id == R.id.nav_slideshow) {
            Intent Opcao = new Intent(getApplicationContext(), Opcoes_FullscreenActivity.class);
            Opcao.putExtra("nome", Nome);
            Opcao.putExtra("idade", Idade);
            startActivity(Opcao);

        } else if (id == R.id.nav_manage) {
            Intent Ajuda = new Intent(getApplicationContext(), Help_FullscreenActivity.class);
            Ajuda.putExtra("nome", Nome);
            Ajuda.putExtra("idade", Idade);
            startActivity(Ajuda);

        } else if (id == R.id.nav_Sobre) {
            Intent sobre = new Intent(getApplicationContext(), Sobre_FullscreenActivity.class);
            sobre.putExtra("nome", Nome);
            sobre.putExtra("idade", Idade);
            startActivity(sobre);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
