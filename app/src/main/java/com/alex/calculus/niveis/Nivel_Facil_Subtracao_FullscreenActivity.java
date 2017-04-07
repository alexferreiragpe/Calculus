package com.alex.calculus.niveis;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexf.mathkids.R;

import java.util.Locale;
import java.util.Random;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Nivel_Facil_Subtracao_FullscreenActivity extends AppCompatActivity {
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

        setContentView(R.layout.activity_nivel__facil__subracao__fullscreen);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        Intent intent = getIntent();
        final String nome = intent.getStringExtra("nome");
        final String idade = intent.getStringExtra("idade");


        final TextView MsgResult = (TextView) findViewById(R.id.lblResultado);
        final EditText Result = (EditText) findViewById(R.id.TxtResultado);
        Result.setText("");

        final ImageButton Avanca = (ImageButton) findViewById(R.id.BtnNovaJogada);
        Avanca.setEnabled(false);
        MsgResult.setText("");

        final TextView RandomValor1, RandomValor2;
        Random Number;
        final int Rnumber1, Rnumber2;
        RandomValor1 = (TextView) findViewById(R.id.TxtValor1);
        RandomValor2 = (TextView) findViewById(R.id.TxtValor2);


        Number = new Random();
        Rnumber1 = Number.nextInt(30) + 1;
        Rnumber2 = Number.nextInt(20) + 2;

        if (Rnumber1 < Rnumber2) {
            RandomValor1.setText(Integer.toString(Rnumber2));
            RandomValor2.setText(Integer.toString(Rnumber1));

        } else {
            RandomValor1.setText(Integer.toString(Rnumber1));
            RandomValor2.setText(Integer.toString(Rnumber2));
        }


        //variaveis dos acertos/erros
        final int Max = 10;
        final int[] Cont = {0};

        final int[] Certo = {0};
        final int[] Errado = {0};

        final ImageButton Validar = (ImageButton) findViewById(R.id.BtnVerifica);
        Validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TextView RandomValor1, RandomValor2;
                RandomValor1 = (TextView) findViewById(R.id.TxtValor1);
                RandomValor2 = (TextView) findViewById(R.id.TxtValor2);
                int Valor1 = Integer.parseInt(RandomValor1.getText().toString());
                int Valor2 = Integer.parseInt(RandomValor2.getText().toString());

                final int SomaRandom = Valor1 - Valor2;
                final EditText Result = (EditText) findViewById(R.id.TxtResultado);
                final TextView MsgResult = (TextView) findViewById(R.id.lblResultado);
                final ImageButton Calc = (ImageButton) findViewById(R.id.BtnVerifica);
                final ImageButton Avanca = (ImageButton) findViewById(R.id.BtnNovaJogada);
                final TextView Acertos = (TextView) findViewById(R.id.TxtAcertos);
                final TextView Erros = (TextView) findViewById(R.id.TxtErros);

                String valor = Result.getText().toString();

                if (Cont[0] < Max) {

                    if (valor.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Digite o Resultado", Toast.LENGTH_SHORT).show();
                    } else {
                        //Verifica se o valor inserido é correto
                        int intV3;
                        intV3 = Integer.parseInt(Result.getText().toString());
                        if (SomaRandom == intV3) {
                            Toast.makeText(getApplicationContext(), "Parabéns", Toast.LENGTH_SHORT).show();
                            MsgResult.setText("Correto");
                            Certo[0] += 1;
                            Acertos.setText("Acertos: " + Certo[0]);

                        } else {
                            MsgResult.setText("Errado -> " + SomaRandom);
                            Errado[0] += 1;
                            Erros.setText("Erros:    " + Errado[0]);
                        }
                        //bloqueia botões
                        Result.setEnabled(false);
                        Calc.setEnabled(false);
                        Avanca.setEnabled(true);
                        Cont[0]++;
                        Toast.makeText(getApplicationContext(), "Jogada: " + Cont[0] + " de " + Max, Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Result.setEnabled(false);
                    Calc.setEnabled(false);
                    Avanca.setEnabled(false);
                    MsgResult.setText("Fase Concluída");

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Nivel_Facil_Subtracao_FullscreenActivity.this);
                    alertDialog.setTitle("Parabéns...");
                    alertDialog.setMessage("Fase Concluída!");

                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent Main = new Intent(Nivel_Facil_Subtracao_FullscreenActivity.this, Nivel_Facil_FullscreenActivity.class);
                            startActivity(Main);
                            dialog.dismiss();
                            finish();
                        }
                    });
                    alertDialog.setIcon(R.drawable.icone);
                    alertDialog.show();

                }


            }
        });


        Avanca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Cont[0] < Max) {
                    EditText Result = (EditText) findViewById(R.id.TxtResultado);
                    ImageButton Calc = (ImageButton) findViewById(R.id.BtnVerifica);
                    TextView MsgResult = (TextView) findViewById(R.id.lblResultado);

                    TextView RandomValor1, RandomValor2;
                    RandomValor1 = (TextView) findViewById(R.id.TxtValor1);
                    RandomValor2 = (TextView) findViewById(R.id.TxtValor2);


                    Result.setEnabled(true);
                    Result.setText("");
                    Calc.setEnabled(true);
                    MsgResult.setText("");

                    Random Number1 = new Random();
                    int Rnumber11, Rnumber22;
                    Rnumber11 = Number1.nextInt(30) + 1;
                    Rnumber22 = Number1.nextInt(20) + 2;

                    if (Rnumber11 < Rnumber22) {
                        RandomValor1.setText(Integer.toString(Rnumber22));
                        RandomValor2.setText(Integer.toString(Rnumber11));

                    } else {
                        RandomValor1.setText(Integer.toString(Rnumber11));
                        RandomValor2.setText(Integer.toString(Rnumber22));
                    }
                    Avanca.setEnabled(false);


                } else {

                    Result.setEnabled(false);
                    Avanca.setEnabled(false);
                    Validar.setEnabled(false);
                    MsgResult.setText("Fase Concluída.");

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Nivel_Facil_Subtracao_FullscreenActivity.this);
                    alertDialog.setTitle("Parabéns...");
                    alertDialog.setMessage("Fase Concluída!");

                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent Main = new Intent(getApplicationContext(), Nivel_Facil_FullscreenActivity.class);
                            Main.putExtra("nome", nome);
                            Main.putExtra("idade", idade);
                            startActivity(Main);
                            dialog.dismiss();

                        }
                    });
                    alertDialog.setIcon(R.drawable.icone);
                    alertDialog.show();

                    final SQLiteDatabase db;
                    db = openOrCreateDatabase("Calculus", SQLiteDatabase.CREATE_IF_NECESSARY, null);
                    db.setVersion(1);
                    db.setLocale(Locale.getDefault());
                    db.setLockingEnabled(true);
                    db.execSQL("CREATE TABLE IF NOT EXISTS RecordeFacilSubtracao(_id INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT, Idade TEXT, Recorde INTEGER)");
                    db.execSQL("INSERT INTO RecordeFacilSubtracao (Nome,Idade,Recorde) VALUES('" + nome + "','" + idade + "','" + Certo[0] + "')");
                    db.close();

                }


            }
        });


        Button Voltar = (Button) findViewById(R.id.dummy_button);
        Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Main = new Intent(getApplicationContext(), Nivel_Facil_FullscreenActivity.class);
                Main.putExtra("nome", nome);
                Main.putExtra("idade", idade);
                startActivity(Main);

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
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
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
