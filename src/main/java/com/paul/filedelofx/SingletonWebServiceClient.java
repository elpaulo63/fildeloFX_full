package com.paul.filedelofx;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SingletonWebServiceClient {

    private static SingletonWebServiceClient Instance = null;
    private OkHttpClient leSeulClientHttp = null;
    private String BASE_URL = "https://sio.jbdelasalle.com" ;
    //private String BASE_URL = "http://192.168.154.3" ;

    private SingletonWebServiceClient() {
        this.leSeulClientHttp = SSLUtil.getUnsafeOkHttpClient();
    }

    public static SingletonWebServiceClient getInstance() {
        if (SingletonWebServiceClient.Instance == null) {
            SingletonWebServiceClient.Instance = new SingletonWebServiceClient();
        }
        return SingletonWebServiceClient.Instance;
    }

    public Enseignant identifier(String login, String mdp) throws IOException, ParseException {
        Request request = new Request.Builder()
                .url(BASE_URL+"/~ptourret/fildelo/ws.php?action=identifier&login=" + login + "&mdp=" + mdp)
                .build();
        Response response = leSeulClientHttp.newCall(request).execute();
        String repStr = response.body().string();
        System.out.println("Reponse : "+repStr);
        JSONParser parser = new JSONParser();
        JSONObject jsono = (JSONObject) parser.parse(repStr);
        Enseignant e = new Enseignant(
                (String) jsono.get("login"),
                (String) jsono.get("nom"),
                (String) jsono.get("prenom"));
        return e;

    }

    public ObservableList<Enseignant> getAllEnseignantsAdmin() throws IOException, ParseException {
        ObservableList<Enseignant> allEnseignantsAdmin = FXCollections.observableArrayList();
        Request request = new Request.Builder()
                .url(BASE_URL+"/~ptourret/fildelo/ws.php?action=getLesAdmins")
                .build();
        Response response = leSeulClientHttp.newCall(request).execute();
        String repStr = response.body().string();
        System.out.println("Reponse : "+repStr);
        JSONParser parser = new JSONParser();
        JSONArray jsona = (JSONArray) parser.parse(repStr);
        for (int cpt = 0; cpt < jsona.size(); cpt++) {
            JSONObject jsono = (JSONObject) jsona.get(cpt);
            Enseignant e = new Enseignant(
                    (String) jsono.get("login"),
                    (String) jsono.get("nom"),
                    (String) jsono.get("prenom"));
            allEnseignantsAdmin.add(e);

        }
        return allEnseignantsAdmin;

    }
    
    
    public ObservableList<Promo> getAllPromos() throws IOException, ParseException {
        ObservableList<Promo> AllPromo = FXCollections.observableArrayList();
        Request request = new Request.Builder()
                .url(BASE_URL+"/~ptourret/fildelo/ws.php?action=getAllPromos")
                .build();
        Response response = leSeulClientHttp.newCall(request).execute();
        String repStr = response.body().string();
        System.out.println("Reponse : "+repStr);
        JSONParser parser = new JSONParser();
        JSONArray jsona = (JSONArray) parser.parse(repStr);
        for (int cpt = 0; cpt < jsona.size(); cpt++) {
            JSONObject jsono = (JSONObject) jsona.get(cpt);
            Promo p = new Promo((String) jsono.get("promo"));
            AllPromo.add(p);

        }
        return AllPromo;

    }
    
    public ObservableList<Promo> getPromobyAnneePromo(String annee) throws IOException, ParseException {
        ObservableList<Promo> allPromos = FXCollections.observableArrayList();
        Request request = new Request.Builder()
                .url(BASE_URL+"/~ptourret/fildelo/ws.php?action=getPromobyAnneePromo&promo=" +annee)
                .build();
        Response response = leSeulClientHttp.newCall(request).execute();
        String repStr = response.body().string();
        System.out.println("Reponse : "+repStr);
        JSONParser parser = new JSONParser();
        JSONArray jsona =(JSONArray) parser.parse(repStr);
        for (int cpt = 0; cpt < jsona.size(); cpt++) {
            JSONObject jsono = (JSONObject) jsona.get(cpt);
            Promo p = new Promo((String) jsono.get("promo"));
            allPromos.add(p);
            
            
            
        }
        return allPromos;
    }
    
    
    public ObservableList<Etudiant> getEtudiantbyPromo(Promo promo) throws IOException, ParseException {
        ObservableList<Etudiant> allEtudiants = FXCollections.observableArrayList();
        Request request = new Request.Builder()
                .url(BASE_URL + "/~ptourret/fildelo/ws.php?action=getEtudiantsbypromo&promo=" + promo.getPromo())
                .build();
        Response response = leSeulClientHttp.newCall(request).execute();
        String repStr = response.body().string();
        System.out.println("Reponse : " + repStr);
        JSONParser parser = new JSONParser();
        JSONArray jsona = (JSONArray) parser.parse(repStr);
        for (int cpt = 0; cpt < jsona.size(); cpt++) {
            JSONObject jsono = (JSONObject) jsona.get(cpt);
            Etudiant e = new Etudiant(
                    Integer.parseInt(jsono.get("id").toString()) ,
                    (String) jsono.get("nom"),
                    (String) jsono.get("prenom"),
                    (String) jsono.get("dh"));

            allEtudiants.add(e);

        }
        return allEtudiants;
    }
    
    
            public ObservableList<Etudiant> getEtudiantsSansPromo() throws IOException, ParseException {
        ObservableList<Etudiant> allEtudiantsSanspromo = FXCollections.observableArrayList();
        Request request = new Request.Builder()
                .url(BASE_URL+"/~ptourret/fildelo/ws.php?action=getEtudiantssanspromo")
                .build();
        Response response = leSeulClientHttp.newCall(request).execute();
        String repStr = response.body().string();
        System.out.println("Reponse : "+repStr);
        JSONParser parser = new JSONParser();
        JSONArray jsona =(JSONArray) parser.parse(repStr);
        for (int cpt = 0; cpt < jsona.size(); cpt++) {
            JSONObject jsono = (JSONObject) jsona.get(cpt);
            Etudiant e = new Etudiant(
                     Integer.parseInt(jsono.get("id").toString()) ,
                    (String)jsono.get("nom"), 
                    (String) jsono.get("prenom"),
                    (String) jsono.get("dh"));
            
            allEtudiantsSanspromo.add(e);           
            
        }
        return allEtudiantsSanspromo;
    }
            
            
    public ObservableList<Enseignant> getEncadrantPromo( Enseignant ens){
        return null ;
    }
    
    

}
