package com.example.bdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static MainActivity singleton;
    private RecyclerView mRecyclerView;
    private MonRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button ajoutPlanetebtn;
    private Button actualiserBtn;
    CoordinatorLayout mcoordinatorLayout;
    final String PREFS_NAME = "preferences_file";
    List<Planete> planetes;
    PlaneteDao planeteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(),"onCreate() MainActivity",Toast.LENGTH_SHORT).show();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new GridLayoutManager(this,1,GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mcoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        ajoutPlanetebtn = (Button) findViewById(R.id.ajout_planete);
        planeteDao = SingletonDAO.getPlaneteDAO(this.getApplicationContext());

        ajoutPlanetebtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startAjoutPlanete();
                    }
                });

        actualiserBtn = (Button) findViewById(R.id.actualiser);
        actualiserBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadData(planeteDao);
                    }
                }
        );

        loadData(AppDatabase.getDatabase(this.getApplicationContext()).planeteDao());

        PlaneteDao planeteDao = AppDatabase.getDatabase(this.getBaseContext()).planeteDao();
    }

    public void startAjoutPlanete(){
        Intent intent = new Intent(this, AjoutPlanete.class);
        startActivity(intent);
    }
    @Override
    public void onResume() {

        super.onResume();
    }

    public void loadData(PlaneteDao planeteDao) {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        new Thread(new Runnable() {
            @Override
            public void run() {
/*
                if (settings.getBoolean("is_data_loaded", true)) {
                    initData(planeteDao);
                    settings.edit().putBoolean("is_data_loaded", false).commit();
                }
*/
                planetes = planeteDao.getAll();

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        mAdapter = new MonRecyclerViewAdapter(planetes);
                        mRecyclerView.setAdapter(mAdapter);

                    }
                });
            }
        }).start();

    }

    private void initData(PlaneteDao planeteDao) {

        ArrayList<Planete> planetes = new ArrayList<>();

        planetes.add(new Planete(1,"Mercure","4900"));
        planetes.add(new Planete(2,"Venus","12000"));
        planetes.add(new Planete(3,"Terre","12800"));
        planetes.add(new Planete(4,"Mars","6800"));
        planetes.add(new Planete(5,"Jupiter","144000"));
        planetes.add(new Planete(6,"Saturne","120000"));
        planetes.add(new Planete(7,"Uranus","52000"));
        planetes.add(new Planete(8,"Neptune","50000"));
        planetes.add(new Planete(9,"Pluton","2300"));

        for (int index = 0; index < planetes.size(); index++) {
            Planete planete = planetes.get(index);
            planeteDao.insert(planete);
        }
    }
}