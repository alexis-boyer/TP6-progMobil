package com.example.bdd;

import android.content.Context;

public class SingletonDAO {

    private static PlaneteDao planeteDao;

    public static PlaneteDao getPlaneteDAO(Context context){
        if(planeteDao==null){
            planeteDao = AppDatabase.getDatabase(context).planeteDao();
        }
        return planeteDao;
    }

    public static PlaneteDao getPlaneteDAO(){
        if(planeteDao==null){
            return planeteDao;
        }
        return null;
    }
}
