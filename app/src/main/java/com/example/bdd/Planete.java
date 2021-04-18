package com.example.bdd;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Planete {

    public static int uidMax = 1;
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "name")
    private String nom;

    @ColumnInfo(name = "size")
    private String taille;

    Planete(int uid, String nom, String taille){
        this.uid = uid;
        this.nom = nom;
        this.taille = taille;
        majUidMax(uid);
    }

    private void majUidMax(int uid) {
        if(uid>uidMax){
            uidMax = uid;
        }
    }

    public int getUid() {
        return uid;
    }

    public String getNom() {
        return nom;
    }

    public String getTaille() {
        return taille;
    }

}