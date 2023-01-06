package com.example.labo2;

import android.os.Parcel;
import android.os.Parcelable;

public class Amendes implements Parcelable {
    private String nom;
    private String prenom;
    private int zone;
    private boolean checkbox;
    private int vitesse;
    private int montant;
    private String date;

    public Amendes(String nom, String prenom, int zone, boolean checkbox, int vitesse, int montant, String date) {
        this.nom = nom;
        this.prenom = prenom;
        this.zone = zone;
        this.checkbox = checkbox;
        this.date = date;
        this.montant = montant;
        this.vitesse = vitesse;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getZone() {return zone;}

    public boolean isCheckbox() {
        return checkbox;
    }

    public int getVitesse() {
        return vitesse;
    }

    public String getNom() { return nom; }

    public String getDate() {
        return date;
    }

    public int getMontant() {
        return montant;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Amendes(Parcel in) {
        nom = in.readString();
        prenom = in.readString();
        date = in.readString();
        montant = in.readInt();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(nom);
        out.writeString(prenom);
        out.writeString(date);
        out.writeInt(montant);
    }

    public static final Parcelable.Creator<Amendes> CREATOR = new Parcelable.Creator<Amendes>() {
        public Amendes createFromParcel(Parcel in) {
            return new Amendes(in);
        }
        public Amendes[] newArray(int size) {
            return new Amendes[size];
        }
    };
}
