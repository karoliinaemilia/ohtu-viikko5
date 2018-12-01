package ohtu.intjoukkosovellus;

import java.util.Arrays;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla.
    private static IntJoukko x;
    private static int[] aTaulu;
    private static int[] bTaulu;

    public IntJoukko() {
        alustaNollilla(KAPASITEETTI, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti < 0) {
            return;
        }
        alustaNollilla(kapasiteetti, OLETUSKASVATUS);

    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0 || kasvatuskoko < 0) {
            return;
        }
        alustaNollilla(kapasiteetti, kasvatuskoko);

    }

    public void alustaNollilla(int kapasiteetti, int kasvatuskoko) {
        ljono = new int[kapasiteetti];
        Arrays.fill(ljono, 0, kapasiteetti, 0);
        this.alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;
    }

    public boolean lisaa(int luku) {
        if (alkioidenLkm == 0) {
            ljono[0] = luku;
            alkioidenLkm++;
            return true;
        } else if (!kuuluu(luku)) {
            ljono[alkioidenLkm] = luku;
            alkioidenLkm++;
            suurennaLukujono();
            return true;
        }
        return false;
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                return true;
            }
        }
        return false;
    }

    private void suurennaLukujono() {
        if (alkioidenLkm % ljono.length == 0) {
            int[] apu = ljono;
            for (int i = 0; i < ljono.length; i++) {
                apu[i] = ljono[i];
            }
            ljono = new int[alkioidenLkm + kasvatuskoko];
            for (int i = 0; i < apu.length; i++) {
                ljono[i] = apu[i];
            }
        }
    }

    public boolean poista(int luku) {
        int kohta = -1;
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                kohta = i;
                ljono[kohta] = 0;
                break;
            }
        }
        siirraLuvut(kohta);
        return true;
    }

    public void siirraLuvut(int kohta) {
        int apu;
        for (int i = kohta; i < alkioidenLkm - 1; i++) {
            apu = ljono[i];
            ljono[i] = ljono[i + 1];
            ljono[i + 1] = apu;
        }
        alkioidenLkm--;
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        } else {
            String tuotos = "{";
            for (int i = 0; i < alkioidenLkm - 1; i++) {
                tuotos += ljono[i] + ", ";
            }
            tuotos += ljono[alkioidenLkm - 1] + "}";
            return tuotos;
        }
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = ljono[i];
        }
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        alusta(a, b);
        x = lisaaJoukkoon(x, aTaulu);
        x = lisaaJoukkoon(x, bTaulu);
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        alusta(a, b);
        for (int i = 0; i < aTaulu.length; i++) {
            for (int j = 0; j < bTaulu.length; j++) {
                if (aTaulu[i] == bTaulu[j]) {
                    x.lisaa(bTaulu[j]);
                }
            }
        }
        return x;
    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        alusta(a, b);
        x = lisaaJoukkoon(x, aTaulu);
        for (int i = 0; i < bTaulu.length; i++) {
            x.poista(i);
        }
        return x;
    }
    
    public static void alusta(IntJoukko a, IntJoukko b) {
        x = new IntJoukko();
        aTaulu = a.toIntArray();
        bTaulu = b.toIntArray();
    }

    public static IntJoukko lisaaJoukkoon(IntJoukko x, int[] taulu) {
        for (int i = 0; i < taulu.length; i++) {
            x.lisaa(taulu[i]);
        }
        return x;
    }
}
