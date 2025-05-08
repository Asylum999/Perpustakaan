////Auth
package Auth;

import java.util.*;

public class AuthSystem {
    private User student; // Only one student
    private List<Admin> admins = new ArrayList<>();

    public AuthSystem() {
        student = new User("024", "dian");

        admins.add(new Admin("admin1", "123"));
    }

    public User login(String id, String nameOrPassword, boolean isAdmin) {
        if (isAdmin) {
            for (Admin admin : admins) {
                if (admin.getId().equals(id) && admin.checkPassword(nameOrPassword)) {
                    return admin;
                }
            }
        } else {
            if (student.getId().equals(id) && student.getName().equalsIgnoreCase(nameOrPassword)) {
                return student;
            }
        }
        return null;
    }
}

