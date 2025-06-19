package com.library.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Connections {
    private final String folderPath = "data";

    public Student getStudentIfValid(String username, String password) {
        File file = new File(folderPath + "/Student.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                // Format: username,id,faculty,major,email
                if (row.length >= 5) {
                    if (row[0].equals(username) && row[1].equals(password)) {
                        return new Student(row[0], row[1], row[2], row[3], row[4]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Admin getAdminIfValid(String username, String password) {
        File file = new File(folderPath + "/Admin.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                // Format: username,id
                if (row.length >= 2) {
                    if (row[0].equals(username) && row[1].equals(password)) {
                        return new Admin(row[0], row[1]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean verifyStudent(String studentId, String email) {
        File file = new File(folderPath + "/Student.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length >= 5) {
                    if (row[1].equals(studentId) && row[4].equalsIgnoreCase(email)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updatePassword(String studentId, String newPassword) {
        File file = new File(folderPath + "/Student.csv");
        List<String> lines = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length >= 5 && row[1].equals(studentId)) {
                    while (row.length < 6) {
                        line += ",";
                        row = line.split(",");
                    }
                    row[5] = newPassword;
                    line = String.join(",", row);
                    updated = true;
                }
                lines.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return updated;
    }
}
