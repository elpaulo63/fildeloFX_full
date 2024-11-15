package com.paul.filedelofx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author Paul
 */
public class SecondaryController implements Initializable {

    @FXML
    private ListView<Promo> promo_lv;
    @FXML
    private ListView<Etudiant> etu_promo_lv;
    @FXML
    private ListView<Etudiant> etu_spromo_lv;
    @FXML
    private ListView<Enseignant> ens_encapromo_lv;
    @FXML
    private ListView<Enseignant> ensei_promo_lv;
    @FXML
    private Label etu_label_id;
    @FXML
    private Label etu_label_prenom;
    @FXML
    private Label etu_label_nom;
    @FXML
    private DatePicker etu_datepick_naiss;
    @FXML
    private ComboBox<Promo> etu_combobox_promo;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        promo_lv.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            updateLVEtudiants() ;
        });
        etu_promo_lv.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            updateFicheEtudiant() ;
        });
        try {
            promo_lv.setItems(SingletonWebServiceClient.getInstance().getAllPromos());
            updateLVEtudiantsSansPromo();
        } catch (IOException | ParseException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    private void updateLVEtudiants() {
        Promo promo = promo_lv.getSelectionModel().getSelectedItem();
        try {
            etu_promo_lv.setItems(SingletonWebServiceClient.getInstance().getEtudiantbyPromo(promo));
        } catch (IOException | ParseException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateLVEtudiantsSansPromo()  {
        try {
            etu_spromo_lv.setItems(SingletonWebServiceClient.getInstance().getEtudiantsSansPromo());
        } catch (ParseException | IOException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void updateFicheEtudiant() {
        Etudiant e = etu_promo_lv.getSelectionModel().getSelectedItem() ;
        etu_label_id.setText(e.getId()+"");
        etu_label_nom.setText(e.getNom());
        etu_label_prenom.setText(e.getPrenom());
        //etu_datepick_naiss.setValue(e.getDdn());
        // datepicker find setitems
        
    }
    
}
