package Main;

import java.util.Scanner;
import Auth.*;
import Action.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AuthSystem auth = new AuthSystem();
        LibraryInterface lib = new LibraryAction();

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

            System.out.println("Selamat datang, " + user.getName());

            int choice = 0;
            do {
                System.out.println("\n--- Menu ---");
                if (user.isAdmin()) {
                    System.out.println("1. Lihat Buku");
                    System.out.println("2. Lihat Riwayat Peminjaman");
                    System.out.println("3. Tambah Buku");
                    System.out.println("4. Logout ke Halaman Login");
                    System.out.print("Pilih: ");
                    String input = scanner.nextLine().trim();
                    try {
                        choice = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Pilihan tidak valid.");
                        continue;
                    }

                    switch (choice) {
                        case 1: lib.viewStock(); break;
                        case 2: lib.viewHistory(); break;
                        case 3:
                            System.out.print("Judul: ");
                            String t = scanner.nextLine();
                            System.out.print("Penulis: ");
                            String a = scanner.nextLine();
                            lib.addBook(t, a);
                            break;
                        case 4:
                            System.out.println("Logout. Kembali ke halaman login.");
                            break; // exit menu loop to login loop
                        default: System.out.println("Pilihan tidak valid.");
                    }
                } else {
                    System.out.println("1. Lihat Buku");
                    System.out.println("2. Pinjam Buku");
                    System.out.println("3. Kembalikan Buku");
                    System.out.println("4. Logout ke Halaman Login");
                    System.out.print("Pilih: ");
                    String input = scanner.nextLine().trim();
                    try {
                        choice = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Pilihan tidak valid.");
                        continue;
                    }

                    switch (choice) {
                        case 1: lib.displayBooks(); break;
                        case 2:
                            System.out.print("Judul: ");
                            String t = scanner.nextLine();
                            lib.borrowBook(t, user.getName());
                            break;
                        case 3:
                            System.out.print("Judul: ");
                            String rt = scanner.nextLine();
                            lib.returnBook(rt, user.getName());
                            break;
                        case 4:
                            System.out.println("Logout. Kembali ke halaman login.");
                            break; // exit menu loop to login loop
                        default: System.out.println("Pilihan tidak valid.");
                    }
                }
            } while (choice != 4);
        }

        scanner.close();
    }
}
