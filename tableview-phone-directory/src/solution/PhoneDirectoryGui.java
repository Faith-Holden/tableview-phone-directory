package solution;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;
import java.util.Optional;


public class PhoneDirectoryGui extends Application{
    TableView<PhoneDirectoryEntry> directoryEntryTableView  = new TableView<>();
    HashMap<String, PhoneDirectoryEntry> directory;
    Stage window;
    PhoneDirectory phoneDirectory;
    public void start(Stage primaryStage){
        window = primaryStage;
        phoneDirectory = new PhoneDirectory();
        directory = phoneDirectory.get();
        load(phoneDirectory);

        BorderPane root = new BorderPane();
        root.setCenter(directoryEntryTableView);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        directoryEntryTableView.setEditable(true);


        Button deleteButton = new Button("Delete Entry");
        deleteButton.setOnAction(e-> doDeleteEntry());


        Button addButton = new Button("New Entry");
        addButton.setOnAction((e)-> doAddEntry());
        HBox buttonBar = new HBox(addButton, deleteButton);
        buttonBar.setAlignment(Pos.BASELINE_CENTER);

        window.setOnHiding(e->{
            Alert confirm = new Alert( Alert.AlertType.CONFIRMATION,
                    "Click OK to Save the file before exiting.");
            confirm.setHeaderText(null);
            Optional<ButtonType> alertResponse = confirm.showAndWait();
            if ( alertResponse.isPresent() && alertResponse.get() == ButtonType.OK ) {
                save();
            }else{
                return;
            }

        });
        

        root.setBottom(buttonBar);


        TableColumn<PhoneDirectoryEntry, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<PhoneDirectoryEntry, String>("lastName"));
        lastNameCol.setCellFactory( TextFieldTableCell.forTableColumn() );
        TableColumn<PhoneDirectoryEntry, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<PhoneDirectoryEntry, String>("firstName"));
        firstNameCol.setCellFactory( TextFieldTableCell.forTableColumn() );
        TableColumn<PhoneDirectoryEntry, String> phoneNumCol = new TableColumn<>("Phone Number");
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<PhoneDirectoryEntry, String>("phoneNumber"));
        phoneNumCol.setCellFactory( TextFieldTableCell.forTableColumn() );
        phoneNumCol.setPrefWidth(100);

        directoryEntryTableView.getColumns().addAll(lastNameCol, firstNameCol, phoneNumCol);




        primaryStage.show();
    }


    public void load(PhoneDirectory phoneDirectory){
        Alert loadOrNew = new Alert( Alert.AlertType.CONFIRMATION,
                "Click OK to load an existing file.");
        Optional<ButtonType> loadResponse = loadOrNew.showAndWait();
        loadOrNew.setHeaderText(null);

        if ( loadResponse.isPresent() && loadResponse.get() == ButtonType.OK ) {
            FileChooser fileDialog = new FileChooser();
            fileDialog.setInitialDirectory(
                    new File( System.getProperty("user.home") ) );
            File selectedFile = fileDialog.showOpenDialog(window);
            try{
                phoneDirectory.readXMLPhoneDirectory(selectedFile);
            }catch(Exception e){
                Alert couldNotLoad = new Alert(Alert.AlertType.CONFIRMATION, "Couldn't load from that file. Click OK to choose a different file, " +
                        "\nor click cancel to start a new file.");
                couldNotLoad.setHeaderText(null);
                Optional<ButtonType> alertResponse = couldNotLoad.showAndWait();
                if ( alertResponse.isPresent() && alertResponse.get() == ButtonType.OK ) {
                    load(phoneDirectory);
                }else {
                    return;
                }
            }
        }
        directory = phoneDirectory.get();
        for(HashMap.Entry<String, PhoneDirectoryEntry> entry : directory.entrySet()){
            directoryEntryTableView.getItems().add(entry.getValue());
        }



    }


    public void save(){
        FileChooser fileDialog = new FileChooser();
        fileDialog.setInitialDirectory(
                new File( System.getProperty("user.home") ) );
        fileDialog.setTitle("Select File to Save.");
        File selectedFile = fileDialog.showSaveDialog(window);

        try {
            phoneDirectory.writeXMLPhoneDirectory(selectedFile);
        } catch (Exception e) {
            Alert confirm = new Alert( Alert.AlertType.CONFIRMATION,
                    "Invalid save location. Click OK to try again, or cancel to exit without saving.");
            confirm.setHeaderText(null);
            Optional<ButtonType> alertResponse = confirm.showAndWait();
            if ( alertResponse.isPresent() && alertResponse.get() == ButtonType.OK ) {
                save();
            }else{
                return;
            }
        }
    }


    public void doAddEntry(){

        String lastName;
        String firstName;
        String phoneNumber;

        TextInputDialog getInfoDialog = new TextInputDialog();
        getInfoDialog.setHeaderText("Please enter the last name.");
        Optional<String> response = getInfoDialog.showAndWait();
        if (response.isPresent() && response.get().trim().length() > 0) {
            lastName = response.get().trim();
        }else{
            lastName = "unknown";
        }
        getInfoDialog.setHeaderText("Please enter the first name.");
        response = getInfoDialog.showAndWait();
        if (response.isPresent() && response.get().trim().length() > 0) {
            firstName = response.get().trim();
        }else{
            firstName = "unknown";
        }
        getInfoDialog.setHeaderText("Please enter the phone number.");
        response = getInfoDialog.showAndWait();
        if (response.isPresent() && response.get().trim().length() > 0) {
            phoneNumber = response.get().trim();
        }else{
            phoneNumber = "unknown";
        }
        if(phoneNumber.equals("unknown") && lastName.equals("unknown") && firstName.equals("unknown")){
            Alert confirm = new Alert( Alert.AlertType.CONFIRMATION,
                    "You didn't enter and data. Press OK to enter data, or press cancel.");
            confirm.setHeaderText(null);
            Optional<ButtonType> alertResponse = confirm.showAndWait();
            if ( alertResponse.isPresent() && alertResponse.get() == ButtonType.OK ) {
                doAddEntry();
            }else{
                return;
            }
        }

        PhoneDirectoryEntry entry = new PhoneDirectoryEntry(lastName, firstName, phoneNumber);
        directory.put(lastName, entry);
        directoryEntryTableView.getItems().add(entry);
    }

    public void doDeleteEntry(){
        if(directoryEntryTableView.getSelectionModel().isEmpty()){
            return;
        }
        else{
            Alert confirmDeletion = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete that entry?");
            Optional<ButtonType> alertResponse = confirmDeletion.showAndWait();
            confirmDeletion.setHeaderText(null);

            if ( alertResponse.isPresent() && alertResponse.get() == ButtonType.OK ) {
                PhoneDirectoryEntry entry = directoryEntryTableView.getSelectionModel().getSelectedItem();
                phoneDirectory.removeFromDirectory(entry.lastNameProperty().get());
                directoryEntryTableView.getItems().remove(entry);
            }else{
                return;
            }
        }
    }


    public static void main(String[] args){
        launch(args);
    }
}
