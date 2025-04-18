# -Metro-Route-Planner-
A Java-based console application to simulate a Metro Route Planner using Dijkstra's algorithm. The program helps users find:

- The shortest distance between two metro stations.
- The fastest route based on travel time.
- Path with the minimum number of interchanges.

---

## 📁 Project Structure

. ├── Graph_M.java # Core class with graph and algorithm implementations ├── *.class files # Compiled Java classes (internal classes for UI/logic)

yaml
Copy
Edit

---

## 🚀 Features

- Add and display metro stations and their connections.
- Find shortest paths by distance or time.
- Calculate number of interchanges in a route.
- Station code support for quick lookups.
- Realistic travel time simulation (base time + distance multiplier).
- Simple CLI-based interaction for educational and functional use.

---

## 🧠 Algorithms Used

- **Dijkstra's Algorithm** for shortest path computation.
- **Depth-First Search** for path finding and validation.
- **Custom interchange detection** logic for metro-style route changes.

---

## 🛠️ How to Run

1. **Clone the Repository**
    ```bash
    git clone https://github.com/yourusername/metro-route-planner.git
    cd metro-route-planner
    ```

2. **Compile the Java File**
    ```bash
    javac Graph_M.java
    ```

3. **Run the Application**
    ```bash
    java Graph_M
    ```

---

## 📷 Sample Output

LIST ALL THE STATIONS IN THE MAP

SHOW THE METRO MAP

GET SHORTEST DISTANCE...

GET SHORTEST TIME... ...

yaml
Copy
Edit

---

## 🏙️ Metro Map Overview

Includes a sample map of the Delhi Metro with major lines:
- Blue Line
- Yellow Line
- Orange Line
- Pink Line

Each station includes a suffix (`~B`, `~Y`, `~O`, etc.) to represent its line for easy interchange detection.

---

## 📦 Dependencies

- Java 8 or above.
- No external libraries required.

---

## 🙌 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change or add.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 👨‍💻 Author

- Developed by [Your Name]
- Email: youremail@example.com
- GitHub: [@yourusername](https://github.com/yourusername)
