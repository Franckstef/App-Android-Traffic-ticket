package com.example.labo2;

public class calculAmende {

    public static int calcul(int limit, int amende, int vitesseUsager) {
        int[] ecartVitesse = {4,9,10,14,19,20,24,29,30,34,
                39,44,45,49,54,59,60,64,69,74,
                79,80,84,89,94,99,100,104,109,114,
                119,120,124,129,134,139,140,144,149,154,159,160};
        int[] montantAmende = {15,25,35,35,45,55,75,90,105,135,
                155,350,390,480,530,630,750,810,870,930,
                990,990,1050,1110,1170,1230,1230,1290,1350,1410,
                1470,1470,1470,1530,1590,1650,1710,1710,1770,1830,1890,1950};
        int[] montantAmende2 = {15,25,35,35,45,55,75,90,105,135,
                155,175,195,240,530,580,630,750,810,870,
                930,990,990,1050,1110,1170,1230,1230,1290,1350,
                1410,1470,1470,1530,1590,1650,1710,1710,1770,1830,1890,1950};
        int[] montantAmende3 = {15,25,35,35,45,55,75,90,105,135,
                155,175,195,240,265,290,630,750,810,870,
                930,990,990,1050,1110,1170,1230,1230,1290,1350,
                1410,1470,1470,1530,1590,1650,1710,1710,1770,1830,1890,1950};

        int[] tab;

        if(limit <= 60) {
            tab = montantAmende;
        } else if (limit <=90) {
            tab = montantAmende2;
        } else {
            tab = montantAmende3;
        }

        if (vitesseUsager <= limit + ecartVitesse[ecartVitesse.length-1]) {
            for (int i = 0; i <= tab.length; i++) {
                if (vitesseUsager <= (limit + ecartVitesse[i])) {
                    amende = tab[i];
                    break;
                }
            }
        } else {
            amende = 1950;
        }
        return amende;
    }
}
