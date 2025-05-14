package Action.stat;

import java.util.*;

public class FacultyStats {
    private Map<String, Integer> facultyCount = new HashMap<>();

    public void addBorrow(String faculty) {
        facultyCount.put(faculty, facultyCount.getOrDefault(faculty, 0) + 1);
    }

    public String getReport() {
        if (facultyCount.isEmpty()) return "Belum ada data peminjaman.";
        StringBuilder sb = new StringBuilder("Rasio peminjam per fakultas:\n");
        int total = facultyCount.values().stream().mapToInt(i -> i).sum();
        for (Map.Entry<String, Integer> entry : facultyCount.entrySet()) {
            double ratio = (double) entry.getValue() / total * 100;
            sb.append("- ").append(entry.getKey()).append(": ")
              .append(entry.getValue()).append(" peminjam (")
              .append(String.format("%.1f", ratio)).append("%)\n");
        }
        return sb.toString();
    }
}
