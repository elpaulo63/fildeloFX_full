package com.paul.filedelofx;

public class Etudiant extends Personne{
    private String ddn;
    private int id ;

    public Etudiant(int id,String nom, String prenom, String ddn) {
        super(nom, prenom);
        this.ddn = ddn;
        this.id = id ;
        
    }

    public String getDdn() {
        return ddn;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    
    
    
}
