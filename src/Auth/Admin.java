////Admin
package Auth;

public class Admin extends User {
    private String password;

    public Admin(String username, String password) {
        super(username, "Administrator");
        this.password = password;
    }

    public boolean checkPassword(String input) {
        return password.equals(input);
    }

    @Override
    public boolean isAdmin() {
        return true;
    }
}