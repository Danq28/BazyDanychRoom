package com.example.myapplication;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ZwierzeDAO {

    @Insert
    public void wstawDoBazy(Zwierze zwierze);

    @Insert
    public void wstawWiecejZwierzat(Zwierze ... zwierzes);

    @Delete
    public void usunZBazy(Zwierze zwierze);

    @Update
    public void zaktualizujZwierze(Zwierze zwierze);

    @Query("SELECT * FROM zwierzeta")
    public List<Zwierze> zwrocWszystkieZwierzeta();

    @Query("SELECT gatunek FROM zwierzeta WHERE sredniWiek>:wiek")
    public List<String> zwrocGatunkiStarszeNiz(int wiek);
}
