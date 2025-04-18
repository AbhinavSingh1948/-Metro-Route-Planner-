import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

public class GraphUI {

    private Graph_M graph;
    private JFrame frame;
    private JTextArea resultArea;
    private JComboBox<String> sourceComboBox, destinationComboBox;

    private static final String INVALID_INPUT_MESSAGE = "Invalid input or no path found.";
    private static final String DISTANCE_LABEL = "Shortest Distance: ";
    private static final String TIME_LABEL = "Shortest Time: ";

    public GraphUI() throws IOException {
        graph = new Graph_M();
        Graph_M.Create_Metro_Map(graph);

        frame = new JFrame("Delhi Metro App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null); // center screen

        frame.setBackground(new Color(240, 248, 255)); // light blue background

        addHeader();
        addMainContent();

        frame.setVisible(true);
    }

    private void addHeader() {
        JLabel headerLabel = new JLabel("Delhi Metro Route Finder", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(70, 130, 180)); // steel blue
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        frame.add(headerLabel, BorderLayout.NORTH);
    }

    private void addMainContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel controlsPanel = new JPanel(new GridBagLayout());
        controlsPanel.setBackground(new Color(245, 245, 255));
        controlsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Controls"));

        ArrayList<String> stations = new ArrayList<>(Graph_M.vtces.keySet());
        sourceComboBox = new JComboBox<>(stations.toArray(new String[0]));
        destinationComboBox = new JComboBox<>(stations.toArray(new String[0]));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlsPanel.add(new JLabel("Source Station:"), gbc);

        gbc.gridx = 1;
        controlsPanel.add(sourceComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        controlsPanel.add(new JLabel("Destination Station:"), gbc);

        gbc.gridx = 1;
        controlsPanel.add(destinationComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlsPanel.add(createButtonPanel(), gbc);

        mainPanel.add(controlsPanel, BorderLayout.NORTH);

        resultArea = new JTextArea(20, 60);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        resultArea.setEditable(false);
        resultArea.setBorder(new LineBorder(Color.GRAY));
        resultArea.setBackground(new Color(255, 255, 240));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Result"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(new Color(245, 245, 255));

        addStyledButton(panel, "Get Distance");
        addStyledButton(panel, "Get Time");
        addStyledButton(panel, "Get Path (Distance)");
        addStyledButton(panel, "Get Path (Time)");
        addStyledButton(panel, "Show Map");
        addStyledButton(panel, "Show Stations");

        return panel;
    }

    private void addStyledButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(100, 149, 237)); // cornflower blue
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setToolTipText("Click to " + text.toLowerCase());
        button.addActionListener(e -> handleButtonClick(text));
        panel.add(button);
    }

    private void handleButtonClick(String buttonLabel) {
        String source = (String) sourceComboBox.getSelectedItem();
        String destination = (String) destinationComboBox.getSelectedItem();

        HashMap<String, Boolean> processed = new HashMap<>();
        if (!graph.containsVertex(source) || !graph.containsVertex(destination) || !graph.hasPath(source, destination, processed)) {
            resultArea.setText(INVALID_INPUT_MESSAGE);
            return;
        }

        switch (buttonLabel) {
            case "Get Distance":
                displayResult(graph.Get_Minimum_Distance(source, destination), DISTANCE_LABEL);
                break;
            case "Get Time":
                displayResult(graph.Get_Minimum_Time(source, destination), TIME_LABEL);
                break;
            case "Get Path (Distance)":
                displayResult(formatPath(graph.get_Interchanges(graph.Get_Minimum_Distance(source, destination))), "");
                break;
            case "Get Path (Time)":
                displayResult(formatPath(graph.get_Interchanges(graph.Get_Minimum_Time(source, destination))), "");
                break;
            case "Show Map":
                displayResult(getMapAsString(), "");
                break;
            case "Show Stations":
                displayResult(getStationsAsString(), "");
                break;
        }
    }

    private void displayResult(String result, String prefix) {
        if (result == null || result.isEmpty()) {
            resultArea.setText(INVALID_INPUT_MESSAGE);
        } else {
            resultArea.setText(prefix + result);
        }
    }

    private String formatPath(ArrayList<String> path) {
        StringBuilder sb = new StringBuilder();
        int len = path.size();

        sb.append("SOURCE STATION : ").append(path.get(0)).append("\n");
        sb.append("DESTINATION STATION : ").append(path.get(len - 3)).append("\n");
        sb.append("NUMBER OF INTERCHANGES : ").append(path.get(len - 2)).append("\n");
        sb.append("~~~~~~~~~~~~~\n");
        sb.append("START  ==>  ").append(path.get(0)).append("\n");

        for (int i = 1; i < len - 3; i++) {
            sb.append(path.get(i)).append("\n");
        }

        sb.append(path.get(len - 3)).append("   ==>    END\n");
        sb.append("~~~~~~~~~~~~~\n");
        sb.append("Distance/Time: ").append(path.get(len - 1)).append("\n");

        return sb.toString();
    }

    private String getMapAsString() {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> keys = new ArrayList<>(Graph_M.vtces.keySet());

        sb.append("\t Delhi Metro Map\n");
        sb.append("\t------------------\n");
        sb.append("----------------------------------------------------\n");

        for (String key : keys) {
            String str = key + " =>\n";
            Graph_M.Vertex vtx = Graph_M.vtces.get(key);
            ArrayList<String> vtxnbrs = new ArrayList<>(vtx.nbrs.keySet());

            for (String nbr : vtxnbrs) {
                str = str + "\t" + nbr + "\t";
                if (nbr.length() < 16) str = str + "\t";
                if (nbr.length() < 8) str = str + "\t";
                str = str + vtx.nbrs.get(nbr) + "\n";
            }
            sb.append(str);
        }
        sb.append("\t------------------\n");
        sb.append("---------------------------------------------------\n");
        return sb.toString();
    }

    private String getStationsAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n***********************************************************************\n\n");
        ArrayList<String> keys = new ArrayList<>(Graph_M.vtces.keySet());
        int i = 1;
        for (String key : keys) {
            sb.append(i).append(". ").append(key).append("\n");
            i++;
        }
        sb.append("\n***********************************************************************\n\n");
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        new GraphUI();
    }
}
