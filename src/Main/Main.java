/// main
package Main;

import Action.*;
import Auth.*;
import javax.swing.SwingUtilities;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AuthSystem auth = new AuthSystem();
        LibraryInterface lib = LibraryAction.getInstance();

        boolean exitProgram = false;

        while (!exitProgram) {
            System.out.println("=== Login Sistem Perpustakaan ===");
            System.out.println("1. Login sebagai Admin");
            System.out.println("2. Login sebagai Mahasiswa");
            System.out.println("3. Keluar");
            System.out.print("Pilih (1/2/3): ");
            String inputRole = scanner.nextLine().trim();

            boolean isAdmin;
            if (inputRole.equals("1")) {
                isAdmin = true;
            } else if (inputRole.equals("2")) {
                isAdmin = false;
            } else if (inputRole.equals("3")) {
                System.out.println("Keluar dari sistem. Terima kasih!");
                break;
            } else {
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                continue;
            }

            System.out.print(isAdmin ? "Username: " : "NIM: ");
            String id = scanner.nextLine().trim();
            System.out.print(isAdmin ? "Password: " : "Nama: ");
            String credential = scanner.nextLine().trim();

            User user = auth.login(id, credential, isAdmin);
            if (user == null) {
                System.out.println("Login gagal. Silakan coba lagi.");
                continue; // return to login screen
            }

            // Tampilkan UI MainMenuFrame dan keluar dari loop console
            SwingUtilities.invokeLater(() -> {
                new MainMenuFrame(lib, user);
            });
            break; // keluar dari loop utama setelah UI tampil
        }

        scanner.close();
    }
}
