// 
// Decompiled by Procyon v0.5.36
// 

package Controller;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import Model.Settings.StyleMap;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import Model.CommandMessages;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import Model.Project.User;
import java.util.Map;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UserNamePasswordRelatedController
{
    @FXML
    private TextField textUserName;
    @FXML
    private PasswordField textPassword;
    @FXML
    private Label labelUsername;
    @FXML
    private AnchorPane popUpAnchorPane;
    @FXML
    private Label labelInvalid;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private CheckBox checkBoxPassword;
    private String userName;
    private String password;
    private Stage stage;
    private OuterTemplateController main;
    private UserManager userManager;
    public Map<String, User> users;
    private List<String> usernames;
    private static final String usernamePattern = "[0-9a-zA-Z]{3,7}";
    private static final int minPasswordLength = 3;
    private static final int maxPasswordLength = 14;
    private final String passwordPattern = "[0-9a-zA-Z]{3,7}";
    private final String defaultUser = "Default User";
    private final int stageWidth = 333;
    private final int stageHeight = 290;
    private final String invalidUserNameOrPassword;
    private final String alreadyExistsUserName;
    private final String expectUserNameToMatch;
    private final String expectPasswordToMatch;
    private final String notAllowedUserName;
    
    public UserNamePasswordRelatedController() {
        this.users = new TreeMap<String, User>();
        this.usernames = new ArrayList<String>();
        this.invalidUserNameOrPassword = CommandMessages.getMessage("INVALID_USERNAME_OR_PASSWORD");
        this.alreadyExistsUserName = CommandMessages.getMessage("ALREADY_EXISTS_USERNAME");
        this.expectUserNameToMatch = CommandMessages.getMessage("EXPECT_USERNAME_TO_MATCH");
        this.expectPasswordToMatch = CommandMessages.getMessage("EXPECT_PASSWORD_TO_MATCH");
        this.notAllowedUserName = CommandMessages.getMessage("NOT_ALLOWED_USERNAME");
    }
    
    public void initializeLogInController(final OuterTemplateController main, final UserManager userManager) {
        this.main = main;
        this.userManager = userManager;
        this.initializePassword();
        this.textUserName.requestFocus();
        (this.stage = new Stage()).setResizable(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Log In");
        final Scene scene = new Scene((Parent)this.popUpAnchorPane, 333.0, 290.0);
        this.stage.setScene(scene);
        scene.getStylesheets().clear();
        scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        this.stage.show();
    }
    
    @FXML
    public void clickLogin() throws NoSuchAlgorithmException {
        this.userName = this.textUserName.getText();
        this.password = this.textPassword.getText();
        if (this.userManager.isPasswordMatchTheUserName(this.userName, this.password)) {
            if (this.userName.equals("Default User") && this.password.matches("")) {
                this.stage.close();
            }
            else {
                this.stage.close();
                this.main.switchUser(this.userManager.users.get(this.userName), false);
            }
        }
        else {
            this.labelInvalid.setText(this.invalidUserNameOrPassword);
        }
    }
    
    public void initializeRegisterPart1Controller(final OuterTemplateController main, final UserManager userManager) {
        this.main = main;
        this.userManager = userManager;
        this.initializePassword();
        this.textUserName.requestFocus();
        (this.stage = new Stage()).setResizable(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Register Part 1");
        final Scene scene = new Scene((Parent)this.popUpAnchorPane, 333.0, 290.0);
        scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        this.stage.setScene(scene);
        this.stage.show();
    }
    
    @FXML
    public void clickNext() throws NoSuchAlgorithmException, IOException {
        this.userName = this.textUserName.getText();
        this.password = this.textPassword.getText();
        this.labelInvalid.setWrapText(true);
        if (this.userManager.isUserNameExists(this.userName)) {
            this.labelInvalid.setText(this.alreadyExistsUserName);
        }
        else if (!this.userName.matches("[0-9a-zA-Z]{3,7}")) {
            this.labelInvalid.setText(this.expectUserNameToMatch);
        }
        else if (this.password.length() < 3 || this.password.length() > 14) {
            this.labelInvalid.setText(this.expectPasswordToMatch);
        }
        else if (this.userName.equals(this.password)) {
            this.labelInvalid.setText(this.notAllowedUserName);
        }
        else {
            this.initializeRegisterPart2Controller();
            this.stage.close();
        }
    }
    
    public void initializeRegisterPart2Controller() throws IOException {
        final FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(this.getClass().getResource("/View/User/registerPart2.fxml"));
        final AnchorPane loginAnchorPane = (AnchorPane)loginLoader.load();
        final UserProfileRelatedController loginController = (UserProfileRelatedController)loginLoader.getController();
        loginController.initializeRegisterPart2Controller(this.main, this.userManager, this.userName, this.password);
    }
    
    public void initializeDeleteController(final OuterTemplateController main, final UserManager userManager, final String userName) {
        this.main = main;
        this.userManager = userManager;
        this.labelUsername.setText(userName);
        this.initializePassword();
        this.textFieldPassword.requestFocus();
        this.textPassword.requestFocus();
        (this.stage = new Stage()).setResizable(false);
        this.stage.setTitle("Delete User");
        final Scene scene = new Scene((Parent)this.popUpAnchorPane, 333.0, 290.0);
        scene.getStylesheets().clear();
        scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setScene(scene);
        this.stage.show();
    }
    
    private void showConfirmDeleteAlert() {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete user");
        alert.setContentText("Are you sure you want to delete the user '" + this.userName + "'?" + "\nAll settings for the user will be lost.");
        Scene scene = alert.getDialogPane().getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        final Optional<ButtonType> result = (Optional<ButtonType>)alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            this.userManager.users.remove(this.userName);
            this.main.updateMenus();
            this.stage.close();
            final Alert afteralert = new Alert(Alert.AlertType.INFORMATION);
            afteralert.setTitle("User Deleted");
            afteralert.setHeaderText((String)null);
            afteralert.setContentText("Successfully deleted the user '" + this.userName + "'");
            scene = afteralert.getDialogPane().getScene();
            scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
            afteralert.showAndWait();
        }
    }
    
    @FXML
    public void clickDelete() {
        this.userName = this.labelUsername.getText();
        this.password = this.textPassword.getText();
        if (this.userManager.isPasswordMatchTheUserName(this.userName, this.password)) {
            this.showConfirmDeleteAlert();
        }
        else {
            this.labelInvalid.setText(this.invalidUserNameOrPassword);
        }
    }
    
    private void initializePassword() {
        this.textFieldPassword.setManaged(false);
        this.textFieldPassword.setVisible(false);
        this.textFieldPassword.managedProperty().bind((ObservableValue)this.checkBoxPassword.selectedProperty());
        this.textFieldPassword.visibleProperty().bind((ObservableValue)this.checkBoxPassword.selectedProperty());
        this.textPassword.managedProperty().bind((ObservableValue)this.checkBoxPassword.selectedProperty().not());
        this.textPassword.visibleProperty().bind((ObservableValue)this.checkBoxPassword.selectedProperty().not());
        this.textFieldPassword.textProperty().bindBidirectional((Property)this.textPassword.textProperty());
    }
    
    public void updateStageStyle() {
        this.stage.getScene().getStylesheets().clear();
        this.stage.getScene().getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
    }
}
