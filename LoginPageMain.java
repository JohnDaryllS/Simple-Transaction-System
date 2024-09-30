public class LoginPageMain {
    public static void main(String[] args) {
        IDandPassword idandPassword = new IDandPassword();
        @SuppressWarnings("unused")
		LoginPage loginPage = new LoginPage(idandPassword.getLoginInfo());
    }
}