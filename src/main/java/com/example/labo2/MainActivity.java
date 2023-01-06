package com.example.labo2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements MyListAdapter.OnItemClicked {

    final String ENTRY_ERR = "entry error";
    final String KEY_RECYCLER = "key_recycler";
    private boolean entryErr = false;
    @SuppressLint("StaticFieldLeak")
    private static MyListAdapter adapter;
    private ArrayAdapter<CharSequence> adapter2;
    private ArrayList<Amendes> listAmendes;
    private TextView name;
    private TextView firstName;
    private TextView zone;
    private TextView speed;
    private Spinner spinner;
    private TextView erreur;
    private EditText nom;
    private EditText prenom;
    private EditText vitesse;
    private EditText amendeMontant;
    private CheckBox checkBox;
    private MenuItem boutonAjout;
    private MenuItem boutonDelete;
    private int limit;
    private int amende = 0;
    private int pos;
    Object spinnerSelectionPosition;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = findViewById(R.id.textNom);
        firstName = findViewById(R.id.textPrenom);
        zone = findViewById(R.id.textZone);
        speed = findViewById(R.id.textVitesse);
        erreur = findViewById(R.id.textErr);
        nom = findViewById(R.id.editTextNom);
        prenom = findViewById(R.id.editTextPrenom);
        vitesse = findViewById(R.id.editTextVitesse);
        spinner = findViewById(R.id.spinner);
        checkBox = findViewById(R.id.checkBox);
        amendeMontant = findViewById(R.id.editTextAmende);

        if(savedInstanceState != null) {
            entryErr = savedInstanceState.getBoolean(ENTRY_ERR);
            listAmendes = savedInstanceState.getParcelableArrayList(KEY_RECYCLER);
            if (entryErr) {
                erreur.setVisibility(View.VISIBLE);
                setColor();
            }
        }
        else
            listAmendes = new ArrayList<>();

        @SuppressLint("CutPasteId") RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new MyListAdapter(listAmendes, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(MainActivity.this);

        spinner = findViewById(R.id.spinner);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.limitations, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);

        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedHandler(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        boutonAjout = menu.getItem(0);
        boutonDelete = menu.getItem(2);
        boutonDelete.setEnabled(false);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu:
                adapter.delete(pos);
                visibility();
                boutonAjout.setEnabled(true);
                return true;

            case R.id.ajout:
                ajout();
                return true;

            case R.id.delete:
                delete();
                return true;

            default:
                return MainActivity.super .onOptionsItemSelected(item);
        }
    }

    private void onItemSelectedHandler(int position) {
        spinnerSelectionPosition = position;
        switch (position) {
            case 0: limit = 0;
                break;
            case 1: limit = 10;
                break;
            case 2: limit = 20;
                break;
            case 3: limit = 30;
                break;
            case 4: limit = 40;
                break;
            case 5: limit = 50;
                break;
            case 6: limit = 60;
                break;
            case 7: limit = 70;
                break;
            case 8: limit = 80;
                break;
            case 9: limit = 90;
                break;
            case 10: limit = 100;
                break;
        }
    }

    public void ajout() {
        String nomValue = nom.getText().toString();
        String prenomValue = prenom.getText().toString();
        String vitesseValue = vitesse.getText().toString();
        int vitesseUsager = vitesse.getText().toString().isEmpty() ? 0 : Integer.parseInt(vitesse.getText().toString());
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        boolean isAllFieldsChecked = setColor();

        if(isAllFieldsChecked) {
            erreur.setVisibility(View.GONE);
            entryErr = false;
            if (Integer.parseInt(vitesseValue) <= limit) {
                vitesseTropPetite();
            }
            else {
                setColor2();
                amende = calculAmende.calcul(limit, amende, vitesseUsager);
                if (checkBox.isChecked()) {
                    adapter.addAmende(new Amendes(nomValue, prenomValue, (Integer) spinnerSelectionPosition, true, vitesseUsager, amende * 2, currentDate));
                } else {
                    adapter.addAmende(new Amendes(nomValue, prenomValue, (Integer) spinnerSelectionPosition, false, vitesseUsager, amende, currentDate));
                }
                visibility();
            }
        }
    }

    public void delete() {
        erreur.setVisibility(View.GONE);
        entryErr = false;
        setColor2();
        visibility();
        boutonAjout.setEnabled(true);
        boutonDelete.setEnabled(false);
    }

    public void vitesseTropPetite() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("La vitesse est plus petite que la limite permise.");
        builder.setPositiveButton("OK", (dialog, id) -> {});
        AlertDialog dialog1 = builder.create();
        dialog1.show();
        vitesse.setText("");
        setColor2();
    }

    public void visibility() {
        nom.setText("");
        prenom.setText("");
        vitesse.setText("");
        checkBox.setChecked(false);
        spinner.setAdapter(adapter2);
        amendeMontant.setText("");
    }

    public boolean setColor() {
        String nomValue = nom.getText().toString();
        String prenomValue = prenom.getText().toString();
        String vitesseValue = vitesse.getText().toString();
        erreur.setVisibility(View.VISIBLE);
        entryErr = true;

        if(nomValue.isEmpty() && prenomValue.isEmpty() && limit == 0 && vitesseValue.isEmpty()) {
            name.setTextColor(Color.RED);
            firstName.setTextColor(Color.RED);
            zone.setTextColor(Color.RED);
            speed.setTextColor(Color.RED);
            return false;
        }if(nomValue.isEmpty() && !prenomValue.isEmpty() && limit != 0 && !vitesseValue.isEmpty()) {
            name.setTextColor(Color.RED);
            firstName.setTextColor(Color.BLACK);
            zone.setTextColor(Color.BLACK);
            speed.setTextColor(Color.BLACK);
            return false;
        } if(!nomValue.isEmpty() && prenomValue.isEmpty() && limit != 0 && !vitesseValue.isEmpty()) {
            name.setTextColor(Color.BLACK);
            firstName.setTextColor(Color.RED);
            zone.setTextColor(Color.BLACK);
            speed.setTextColor(Color.BLACK);
            return false;
        } if(!nomValue.isEmpty() && !prenomValue.isEmpty() && limit == 0 && !vitesseValue.isEmpty()) {
            name.setTextColor(Color.BLACK);
            firstName.setTextColor(Color.BLACK);
            zone.setTextColor(Color.RED);
            speed.setTextColor(Color.BLACK);
            return false;
        } if (!nomValue.isEmpty() && !prenomValue.isEmpty() && limit != 0 && vitesseValue.isEmpty()) {
            name.setTextColor(Color.BLACK);
            firstName.setTextColor(Color.BLACK);
            zone.setTextColor(Color.BLACK);
            speed.setTextColor(Color.RED);
            return false;
        } if (nomValue.isEmpty() && prenomValue.isEmpty() && limit != 0 && !vitesseValue.isEmpty()) {
            name.setTextColor(Color.RED);
            firstName.setTextColor(Color.RED);
            zone.setTextColor(Color.BLACK);
            speed.setTextColor(Color.BLACK);
            return false;
        } if(nomValue.isEmpty() && !prenomValue.isEmpty() && limit == 0 && !vitesseValue.isEmpty()) {
            name.setTextColor(Color.RED);
            firstName.setTextColor(Color.BLACK);
            zone.setTextColor(Color.RED);
            speed.setTextColor(Color.BLACK);
            return false;
        } if(nomValue.isEmpty() && !prenomValue.isEmpty() && limit != 0 && vitesseValue.isEmpty()) {
            name.setTextColor(Color.RED);
            firstName.setTextColor(Color.BLACK);
            zone.setTextColor(Color.BLACK);
            speed.setTextColor(Color.RED);
            return false;
        } if(!nomValue.isEmpty() && prenomValue.isEmpty() && limit == 0 && !vitesseValue.isEmpty()) {
            name.setTextColor(Color.BLACK);
            firstName.setTextColor(Color.RED);
            zone.setTextColor(Color.RED);
            speed.setTextColor(Color.BLACK);
            return false;
        } if(!nomValue.isEmpty() && !prenomValue.isEmpty() && limit == 0 && vitesseValue.isEmpty()) {
            name.setTextColor(Color.BLACK);
            firstName.setTextColor(Color.BLACK);
            zone.setTextColor(Color.RED);
            speed.setTextColor(Color.RED);
            return false;
        } if(!nomValue.isEmpty() && prenomValue.isEmpty() && limit != 0 && vitesseValue.isEmpty()) {
            name.setTextColor(Color.BLACK);
            firstName.setTextColor(Color.RED);
            zone.setTextColor(Color.BLACK);
            speed.setTextColor(Color.RED);
            return false;
        } if(!nomValue.isEmpty() && prenomValue.isEmpty() && limit == 0 && vitesseValue.isEmpty()) {
            name.setTextColor(Color.BLACK);
            firstName.setTextColor(Color.RED);
            zone.setTextColor(Color.RED);
            speed.setTextColor(Color.RED);
            return false;
        } if(nomValue.isEmpty() && !prenomValue.isEmpty() && limit == 0 && vitesseValue.isEmpty()) {
            name.setTextColor(Color.RED);
            firstName.setTextColor(Color.BLACK);
            zone.setTextColor(Color.RED);
            speed.setTextColor(Color.RED);
            return false;
        } if(nomValue.isEmpty() && prenomValue.isEmpty() && limit != 0 && vitesseValue.isEmpty()) {
            name.setTextColor(Color.RED);
            firstName.setTextColor(Color.RED);
            zone.setTextColor(Color.BLACK);
            speed.setTextColor(Color.RED);
            return false;
        } if (nomValue.isEmpty() && prenomValue.isEmpty() && limit == 0 && !vitesseValue.isEmpty()) {
            name.setTextColor(Color.RED);
            firstName.setTextColor(Color.RED);
            zone.setTextColor(Color.RED);
            speed.setTextColor(Color.BLACK);
            return false;
        }
        return true;
    }

    void setColor2() {
        name.setTextColor(Color.BLACK);
        firstName.setTextColor(Color.BLACK);
        zone.setTextColor(Color.BLACK);
        speed.setTextColor(Color.BLACK);
    }

    @Override
    public void onItemClick(Amendes amendes) {
        boutonAjout.setEnabled(false);
        boutonDelete.setEnabled(true);
        nom.setText(amendes.getNom());
        prenom.setText(amendes.getPrenom());
        spinner.setSelection(amendes.getZone());
        checkBox.setChecked(amendes.isCheckbox());
        vitesse.setText(String.valueOf(amendes.getVitesse()));
        amendeMontant.setText(String.valueOf(amendes.getMontant()));
        pos = listAmendes.indexOf(amendes);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(KEY_RECYCLER, listAmendes);
        savedInstanceState.putBoolean(ENTRY_ERR, entryErr);
        super.onSaveInstanceState(savedInstanceState);
    }

}