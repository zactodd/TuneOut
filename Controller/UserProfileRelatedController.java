// 
// Decompiled by Procyon v0.5.36
// 

package Controller;

import javafx.scene.control.Alert;
import java.security.NoSuchAlgorithmException;
import Model.Settings.StyleMap;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import Model.CommandMessages;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserProfileRelatedController
{
    @FXML
    private Label labelUserName;
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private AnchorPane popUpAnchorPane;
    @FXML
    private Label labelFirstName;
    @FXML
    private Label labelLastName;
    @FXML
    private Label labelInvalid;
    @FXML
    private Button saveChangesBtn;
    private String userName;
    private String password;
    private Stage stage;
    private OuterTemplateController main;
    private UserManager userManager;
    private final String defaultUser = "Default User";
    private final String invalidNameLength;
    private final int maxNameLength = 20;
    private final int stageWidth = 333;
    private final int stageHeight = 300;
    
    public UserProfileRelatedController() {
        this.invalidNameLength = CommandMessages.getMessage("INVALID_NAME_LENGTH");
    }
    
    public void initializeRegisterPart2Controller(final OuterTemplateController main, final UserManager userManager, final String userName, final String password) {
        this.main = main;
        this.userManager = userManager;
        this.userName = userName;
        this.password = password;
        (this.stage = new Stage()).setResizable(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Register Part 2");
        final Scene scene = new Scene((Parent)this.popUpAnchorPane, 333.0, 300.0);
        this.stage.setScene(scene);
        scene.getStylesheets().clear();
        scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        this.stage.show();
    }
    
    @FXML
    public void clickRegister() throws NoSuchAlgorithmException {
        final String firstName = this.textFieldFirstName.getText();
        final String lastName = this.textFieldLastName.getText();
        if (this.validateFullName(firstName, lastName)) {
            this.userManager.addNewUser(this.main, this.userName, this.password, firstName, lastName);
            this.stage.close();
        }
        else {
            this.labelInvalid.setText(this.invalidNameLength);
        }
    }
    
    public void initializeViewUserProfileController(final OuterTemplateController main, final UserManager userManager) {
        this.main = main;
        this.userManager = userManager;
        this.setUpUserProfile();
        (this.stage = new Stage()).setResizable(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("View User Profile");
        final Scene scene = new Scene((Parent)this.popUpAnchorPane, 333.0, 300.0);
        scene.getStylesheets().clear();
        scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        this.stage.setScene(scene);
        this.stage.show();
    }
    
    private void enableFieldForDefaultUser(final boolean visible) {
        this.labelFirstName.setVisible(visible);
        this.labelLastName.setVisible(visible);
        this.textFieldFirstName.setVisible(visible);
        this.textFieldLastName.setVisible(visible);
        this.saveChangesBtn.setVisible(visible);
    }
    
    public void setUpUserProfile() {
        if (this.userManager.getCurrentUser().getUserName().equals("Default User")) {
            this.enableFieldForDefaultUser(false);
        }
        else {
            this.enableFieldForDefaultUser(true);
        }
        this.labelUserName.setText(this.userManager.getCurrentUser().getUserName());
        this.textFieldFirstName.setText(this.userManager.getCurrentUser().getFirstName());
        this.textFieldLastName.setText(this.userManager.getCurrentUser().getLastName());
        this.popUpAnchorPane.requestFocus();
    }
    
    private boolean validateFullName(final String firstName, final String lastName) throws NoSuchAlgorithmException {
        return firstName.length() <= 20 && lastName.length() <= 20;
    }
    
    @FXML
    public void saveChanges() throws NoSuchAlgorithmException {
        final String firstName = this.textFieldFirstName.getText();
        final String lastName = this.textFieldLastName.getText();
        if (this.validateFullName(firstName, lastName)) {
            this.userManager.getCurrentUser().setFirstName(firstName);
            this.userManager.getCurrentUser().setLastName(lastName);
            this.stage.close();
            final Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successfully save changes");
            alert.setHeaderText((String)null);
            alert.setContentText("Successfully changes the user information");
            final Scene scene = alert.getDialogPane().getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
            alert.showAndWait();
        }
        else {
            this.labelInvalid.setText(this.invalidNameLength);
        }
    }
    
    public void updateStageStyle() {
        this.stage.getScene().getStylesheets().clear();
        this.stage.getScene().getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
    }
}
