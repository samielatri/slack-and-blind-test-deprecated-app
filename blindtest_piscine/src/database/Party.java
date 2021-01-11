package database;

import java.util.ArrayList;

public class Party {
    private int idParty;
    private String nameParty;
    private ArrayList<Joueur> listJoueurs;

    public Party(int idParty, String nameParty) {
        this.idParty = idParty;
        this.nameParty = nameParty;
    }

    public int getIdParty() {
        return idParty;
    }

    public void setIdParty(int idParty) {
        this.idParty = idParty;
    }

    public String getNameParty() {
        return nameParty;
    }

    public void setNameParty(String nameParty) {
        this.nameParty = nameParty;
    }

    public ArrayList<Joueur> getListJoueurs() {
        return listJoueurs;
    }

    public void setListJoueurs(ArrayList<Joueur> listJoueurs) {
        this.listJoueurs = listJoueurs;
    }
}
