package com.example.alexf.calculus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.alexf.mathkids.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Recordes_FullscreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    ListView listView;
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recordes__fullscreen);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        Intent intent = getIntent();
        final String Nome = intent.getStringExtra("nome");
        final String idade = intent.getStringExtra("idade");

        listView = (ListView) findViewById(R.id.Lista);


        String[] nomes = new String[]{"Nível Fácil Soma", "Nível Fácil Subtração", "Nível Fácil Multiplicação", "Nível Fácil Divisão", "Nível Médio", "Nível Difícil"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomes);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int posicaoDaLinha, long id) {

                if (posicaoDaLinha == 0) {
                    Intent FacilSoma = new Intent(getApplicationContext(), Recordes_Facil_Soma_Novo.class);
                    FacilSoma.putExtra("nome", Nome);
                    FacilSoma.putExtra("idade", idade);
                    startActivity(FacilSoma);
                } else if (posicaoDaLinha == 1) {
                    Intent FacilSubtracao = new Intent(getApplicationContext(), Recordes_Nivel_Facil_Subtracao_Novo.class);
                    FacilSubtracao.putExtra("nome", Nome);
                    FacilSubtracao.putExtra("idade", idade);
                    startActivity(FacilSubtracao);

                } else if (posicaoDaLinha == 2) {
                    Intent FacilMultiplicacao = new Intent(getApplicationContext(), Recordes_Nivel_Facil_Multiplicacao.class);
                    FacilMultiplicacao.putExtra("nome", Nome);
                    FacilMultiplicacao.putExtra("idade", idade);
                    startActivity(FacilMultiplicacao);

                } else if (posicaoDaLinha == 3) {
                    Intent FacilDivisao = new Intent(getApplicationContext(), Recordes_Nivel_Facil_Divisao.class);
                    FacilDivisao.putExtra("nome", Nome);
                    FacilDivisao.putExtra("idade", idade);
                    startActivity(FacilDivisao);

                } else if (posicaoDaLinha == 4) {
                    Intent NivelMedio = new Intent(getApplicationContext(), Recordes_Nivel_Medio.class);
                    NivelMedio.putExtra("nome", Nome);
                    NivelMedio.putExtra("idade", idade);
                    startActivity(NivelMedio);

                } else if (posicaoDaLinha == 5) {
                    Intent NivelDificil = new Intent(getApplicationContext(), Recordes_Nivel_Dificil.class);
                    NivelDificil.putExtra("nome", Nome);
                    NivelDificil.putExtra("idade", idade);
                    startActivity(NivelDificil);

                }


            }
        });


        Button BtnVoltar = (Button) findViewById(R.id.dummy_button);
        BtnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeInicial = new Intent(getApplicationContext(), MainActivity.class);
                HomeInicial.putExtra("nome", Nome);
                HomeInicial.putExtra("idade", idade);
                startActivity(HomeInicial);
            }
        });


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
