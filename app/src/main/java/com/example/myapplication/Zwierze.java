package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "zwierzeta")

public class Zwierze {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String gatunek;

    @ColumnInfo(name="srodowisko_zycia")
    public String srodowiskoZycia;

    public boolean czyRoslinozerne;
    //true roslinozerne
    //false wszystkozerne, miesozerne

    public int sredniWiek;

    public Zwierze(String gatunek, String srodowiskoZycia, boolean czyRoslinozerne, int sredniWiek) {
        this.gatunek = gatunek;
        this.srodowiskoZycia = srodowiskoZycia;
        this.czyRoslinozerne = czyRoslinozerne;
        this.sredniWiek = sredniWiek;
    }

    public Zwierze() {
    }
}
