package com.paul.filedelofx;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private Etudiant eSel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        promo_lv.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            updateLVEtudiants();
            updateLVEnsencaPromo();
        });
        etu_promo_lv.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            eSel = etu_promo_lv.getSelectionModel().getSelectedItem();
            updateFicheEtudiant();
        });

        try {
            ObservableList<Promo> allPromos = SingletonWebServiceClient.getInstance().getAllPromos();
            promo_lv.setItems(allPromos);
            etu_combobox_promo.setItems(allPromos);
            updateLVEtudiantsSansPromo();
        } catch (IOException | ParseException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        etu_spromo_lv.getSelectionModel().selectedItemProperty().addListener((o) -> {
            eSel = etu_spromo_lv.getSelectionModel().getSelectedItem();
            updateFicheEtudiant();

        });
        etu_spromo_lv.setOnMouseClicked((t) -> {
            eSel = etu_spromo_lv.getSelectionModel().getSelectedItem();
            updateFicheEtudiant();
        });

    }

    private void updateLVEtudiants() {
        Promo promo = promo_lv.getSelectionModel().getSelectedItem();
        try {
            etu_promo_lv.setItems(SingletonWebServiceClient.getInstance().getEtudiantbyPromo(promo));
        } catch (IOException | ParseException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateLVEtudiantsSansPromo() {
        try {
            etu_spromo_lv.setItems(SingletonWebServiceClient.getInstance().getEtudiantsSansPromo());
        } catch (ParseException | IOException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void updateFicheEtudiant() {
        Etudiant e = eSel;
        if (e != null) {
            etu_label_id.setText(e.getId() + "");
            etu_label_nom.setText(e.getNom());
            etu_label_prenom.setText(e.getPrenom());
            etu_combobox_promo.getSelectionModel().select(promo_lv.getSelectionModel().getSelectedItem());
            if (etu_spromo_lv.getItems().contains(e)) {
                etu_combobox_promo.getSelectionModel().clearSelection();

            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parse;
            try {
                parse = sdf.parse(e.getDdn());
                LocalDate toLocalDate = parse.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                etu_datepick_naiss.setValue(toLocalDate);
            } catch (java.text.ParseException ex) {
                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void onClickUpdate(ActionEvent event) {
        LocalDate value = etu_datepick_naiss.getValue();
        String format = value.format(DateTimeFormatter.ISO_LOCAL_DATE);
        eSel.setDdn(format);
        SingletonWebServiceClient.getInstance().updateEtudiant(eSel) ;
        // go ws update etu create bouton

    }

    private void updateLVEnsencaPromo() {
        Promo p = promo_lv.getSelectionModel().getSelectedItem();
        try {
            ens_encapromo_lv.setItems(SingletonWebServiceClient.getInstance().getEncadrantPromo(p));
        } catch (IOException | ParseException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
