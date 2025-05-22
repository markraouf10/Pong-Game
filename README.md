# 🏓 Pong Game (Java Swing)

This is a desktop Pong game built using **Java Swing**, featuring:

- 🔵🔴 Two-player paddle gameplay
- 🎮 Game start, pause (P), restart (R), and exit (ESC) support
- 🏆 Game ending at 15 points
- 👤 Player login with MySQL database (username + code)
- 🥇 Leaderboard tracking with wins stored in MySQL

---

## 💾 Requirements

- Java JDK 8+
- NetBeans (recommended)
- MySQL server running
- MySQL Connector/J added to project libraries

---

## 🔌 Database Setup

1. Create a database called `pong_game`
2. Create table with:

```sql
CREATE TABLE players (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  player_code VARCHAR(50) NOT NULL,
  games_won INT DEFAULT 0
);
