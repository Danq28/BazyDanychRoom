package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ZwierzetaDataBase zwierzetaDataBase;

    private EditText editTextGatunek,editTextWiek;
    private Spinner spinnerSrodowisko;
    private CheckBox checkBoxRoslino;
    private Button button,buttonAktualizacja;
    private ListView listView;
    private ArrayAdapter<Zwierze> arrayAdapter;
    private List<Zwierze> zwierzeta;
    private Zwierze kliknieteZwierze;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.button);
        buttonAktualizacja=findViewById(R.id.buttonAktualizuj);
        editTextGatunek=findViewById(R.id.editTextText);
        editTextWiek=findViewById(R.id.editTextNumber);
        spinnerSrodowisko=findViewById(R.id.spinner);
        checkBoxRoslino=findViewById(R.id.checkBox);
        zwierzetaDataBase = Room.databaseBuilder(
                getApplicationContext(),
                ZwierzetaDataBase.class,
                "zwierzeta_db"
        )
                .allowMainThreadQueries()
                .build();
//        zwierzetaDataBase.zwrocZwierzeDao().wstawDoBazy(
//                new Zwierze("sarna europejska", "lądowe",true,12)
//        );

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Zwierze zwierze = new Zwierze(
                                editTextGatunek.getText().toString(),
                                spinnerSrodowisko.getSelectedItem().toString(),
                                checkBoxRoslino.isChecked(),
                                Integer.parseInt(editTextWiek.getText().toString())
                        );
                        zwierzetaDataBase.zwrocZwierzeDao().wstawDoBazy(zwierze);
                        zwierze.add(zwierze);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
        );
        listView=findViewById(R.id.zwierzetalist);
        zwierzeta=zwierzetaDataBase.zwrocZwierzeDao().zwrocWszystkieZwierzeta();
        arrayAdapter=new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                zwierzeta);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        zwierzetaDataBase.zwrocZwierzeDao().usunZBazy(zwierzeta.get(i));
                        zwierzeta.remove(i);
                        arrayAdapter.notifyDataSetChanged();
                        return false;
                    }
                }
        );
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        kliknieteZwierze = zwierzeta.get(i);
                        editTextGatunek.setText(kliknieteZwierze.gatunek);
                        checkBoxRoslino.setChecked(kliknieteZwierze.czyRoslinozerne);

                        ArrayList<String> srodowiska = new ArrayList<String>(
                                Arrays.asList("wodne", "lądowe", "powietrzne")
                        );
                        spinnerSrodowisko.setSelection(srodowiska.indexOf(kliknieteZwierze.srodowiskoZycia));
                        editTextWiek.setText(String.valueOf(kliknieteZwierze.sredniWiek));
                        buttonAktualizacja.setVisibility(View.VISIBLE);

                    }
                }
        );
        buttonAktualizacja.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        kliknieteZwierze.gatunek=editTextGatunek.getText().toString();
                        kliknieteZwierze.srodowiskoZycia=spinnerSrodowisko.getSelectedItem().toString();
                        kliknieteZwierze.czyRoslinozerne=checkBoxRoslino.isChecked();
                        kliknieteZwierze.sredniWiek= Integer.parseInt(editTextWiek.getText().toString());


                        zwierzetaDataBase.zwrocZwierzeDao().zaktualizujZwierze(kliknieteZwierze);
                        arrayAdapter.notifyDataSetChanged();
                        buttonAktualizacja.setVisibility(View.INVISIBLE);
                    }
                }
        );
    }
}

