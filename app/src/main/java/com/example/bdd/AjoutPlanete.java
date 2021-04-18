package com.example.bdd;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AjoutPlanete extends AppCompatActivity {

    private Button ajout;
    private EditText nomText;
    private EditText masseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ajout_planete);
        ajout = (Button) findViewById(R.id.ajout_planete);
        nomText = (EditText) findViewById(R.id.nomPlanete);
        masseText = (EditText) findViewById(R.id.massePlanete);


        ajout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ajouterPlanete();
                    }
                }
        );
    }

    private void ajouterPlanete() {
        String masse = masseText.getText().toString();
        String nom = nomText.getText().toString();
        int uid = Planete.uidMax + 1;

        PlaneteDao planeteDao = AppDatabase.getDatabase(this.getApplicationContext()).planeteDao();

        new Thread(new Runnable() {
            @Override
            public void run() {
                planeteDao.insert(new Planete(uid,nom,masse));
            }
        }).start();

        finish();
    }

}
