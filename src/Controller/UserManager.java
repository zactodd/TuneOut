

package Controller;

import javafx.scene.layout.VBox;
import java.io.IOException;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import Model.Project.Project;

import java.util.TreeMap;
import org.apache.log4j.Logger;
import Model.Project.User;
import java.util.Map;

public class UserManager
{
    public Map<String, User> users;
    protected static final int maxNumberUsers = 10;
    private static final int maxNameLength = 20;
    Logger log;
    private final String defaultUser = "Default User";
    protected UserStatsController userStatsController;
    private UserNamePasswordRelatedController loginController;
    private UserNamePasswordRelatedController registerController;
    private UserNamePasswordRelatedController deleteController;
    private UserProfileRelatedController userProfileController;
    private UserProfileRelatedController viewUserProfileAndRegisterPart2Controller;
    
    public UserManager() {
        this.users = new TreeMap<String, User>();
        this.log = Logger.getLogger(UserManager.class.getName());
    }
    
    protected boolean isUserNameExists(final String username) {
        for (final User u : this.users.values()) {
            if (u.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean isPasswordMatchTheUserName(final String username, String password) {
        if (!username.equals("Default User")) {
            password = generatePassword(password.toString());
        }
        for (final User u : this.users.values()) {
            if (u.getUserName().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    
    protected User getCurrentUser() {
        for (final User u : this.users.values()) {
            if (u.isSelected()) {
                return u;
            }
        }
        return null;
    }
    
    protected void addNewUser(final OuterTemplateController main, final String userName, String password, final String firstName, final String lastName) throws NoSuchAlgorithmException {
        if (!userName.equals("Default User")) {
            password = generatePassword(password);
        }
        final User newUser = new User(userName, password, firstName, lastName);
        this.users.put(newUser.getUserName(), newUser);
        main.saveUserData(newUser);
        main.switchUser(newUser, true);
        main.project = new Project();
        main.newProject();
        main.resetUserSettings();
    }
    
    protected void addDefaultUser(final OuterTemplateController main) throws NoSuchAlgorithmException {
        this.addNewUser(main, "Default User", "", "", "");
        main.saveUserData(this.users.get("Default User"));
    }
    
    private static String generatePassword(final String password) {
        final String passwordToHash = password;
        String generatedPassword = null;
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            final byte[] bytes = md.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; ++i) {
                sb.append(Integer.toString((bytes[i] & 0xFF) + 256, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    
    public void showLogInDialog(final OuterTemplateController main) throws IOException {
        final FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(this.getClass().getResource("/View/User/logIn.fxml"));
        final AnchorPane loginAnchorPane = (AnchorPane)loginLoader.load();
        (this.loginController = (UserNamePasswordRelatedController)loginLoader.getController()).initializeLogInController(main, this);
    }
    
    public void showRegisterDialog(final OuterTemplateController main) throws IOException {
        final FXMLLoader registerLoader = new FXMLLoader();
        registerLoader.setLocation(this.getClass().getResource("/View/User/registerPart1.fxml"));
        final AnchorPane registerAnchorPane = (AnchorPane)registerLoader.load();
        (this.registerController = (UserNamePasswordRelatedController)registerLoader.getController()).initializeRegisterPart1Controller(main, this);
    }
    
    public UserStatsController showUserStatsDialog(final OuterTemplateController main) throws IOException {
        if (this.userStatsController == null) {
            final FXMLLoader userStatsLoader = new FXMLLoader();
            userStatsLoader.setLocation(this.getClass().getResource("/View/User/userStats.fxml"));
            final VBox userStatsVBox = (VBox)userStatsLoader.load();
            this.userStatsController = (UserStatsController)userStatsLoader.getController();
        }
        else {
            this.userStatsController.requestFocus();
        }
        this.userStatsController.initializeController(main);
        return this.userStatsController;
    }
    
    public void showUserProfileDialog(final OuterTemplateController main) throws IOException {
        final FXMLLoader userProfileLoader = new FXMLLoader();
        userProfileLoader.setLocation(this.getClass().getResource("/View/User/userProfile.fxml"));
        final AnchorPane userProfileAnchorPane = (AnchorPane)userProfileLoader.load();
        (this.viewUserProfileAndRegisterPart2Controller = (UserProfileRelatedController)userProfileLoader.getController()).initializeViewUserProfileController(main, this);
    }
    
    public void showDeleteDialog(final OuterTemplateController main, final String username) throws IOException {
        final FXMLLoader deleteUserLoader = new FXMLLoader();
        deleteUserLoader.setLocation(this.getClass().getResource("/View/User/delete.fxml"));
        final AnchorPane deleteAnchorPane = (AnchorPane)deleteUserLoader.load();
        (this.deleteController = (UserNamePasswordRelatedController)deleteUserLoader.getController()).initializeDeleteController(main, this, username);
    }
    
    public void updateStagesStyle() {
        if (this.loginController != null) {
            this.loginController.updateStageStyle();
        }
        if (this.registerController != null) {
            this.registerController.updateStageStyle();
        }
        if (this.deleteController != null) {
            this.deleteController.updateStageStyle();
        }
        if (this.userProfileController != null) {
            this.userProfileController.updateStageStyle();
        }
        if (this.viewUserProfileAndRegisterPart2Controller != null) {
            this.viewUserProfileAndRegisterPart2Controller.updateStageStyle();
        }
        if (this.userStatsController != null) {
            this.userStatsController.updateCss();
        }
    }
}
