package Classes;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class PlayerDAO {

    // Auto login or register a player
    public static boolean loginOrRegister(String username, String code) {
        try (Connection conn = DBConnection.getConnection()) {
            // Check if player exists
            String checkSql = "SELECT * FROM players WHERE username = ? AND player_code = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            checkStmt.setString(2, code);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("✅ Login successful.");
                return true;
            } else {
                // Register new user
                String insertSql = "INSERT INTO players (username, player_code) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, username);
                insertStmt.setString(2, code);
                insertStmt.executeUpdate();
                System.out.println("🆕 New player registered.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Increment wins after a player wins a game
    public static void incrementWins(String username) {
        String query = "UPDATE players SET games_won = games_won + 1 WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Print leaderboard to console
    public static void showLeaderboard() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT username, games_won FROM players ORDER BY games_won DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n=== Leaderboard ===");
            while (rs.next()) {
                System.out.println(rs.getString("username") + " - " + rs.getInt("games_won") + " wins");
            }
            System.out.println("====================\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<String[]> getLeaderboard() {
    List<String[]> leaderboard = new ArrayList<>();
    String sql = "SELECT username, games_won FROM players ORDER BY games_won DESC LIMIT 5";

    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            leaderboard.add(new String[]{
                rs.getString("username"),
                String.valueOf(rs.getInt("games_won"))
            });
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return leaderboard;
}

}
