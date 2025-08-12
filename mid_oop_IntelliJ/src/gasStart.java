import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class gasStart extends JFrame implements ActionListener, WindowListener {

    final int width = 150;
    final int height = 150;
    final double area = width * height; // 22,500 m²

    JPanel infoPanel;
    JPanel gridPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JPanel labelPanel;

    JLabel labelBase;
    JLabel labelTop;
    JLabel labelGasHeight;
    JLabel labelVolume;
    JLabel labelPercent;

    JTextField fluidField = new JTextField("2500", 10);
    JButton btnLoad = new JButton("Load dept.txt");
    JButton btnEnter = new JButton("Enter Fluid Depth");
    JButton btnBack = new JButton("Back");

    JButton[] cellButtons = new JButton[200];
    Cell[] cells = new Cell[200];

    String path = "";
    double fluidContactDepth = 2500.0;

    public gasStart() {
        setTitle("Gas Volume Calculator");
        setSize(2000, 1140);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));


        infoPanel = new JPanel(new BorderLayout());

        labelPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        labelBase = new JLabel("Base Horizon : -");
        labelTop = new JLabel("Top Horizon : -");
        labelGasHeight = new JLabel("Gas Thickness : -");
        labelVolume = new JLabel("Volume : -");
        labelPercent = new JLabel("Gas : -");

        labelPanel.add(labelBase);
        labelPanel.add(labelTop);
        labelPanel.add(labelGasHeight);
        labelPanel.add(labelVolume);
        labelPanel.add(labelPercent);
        labelPanel.setBackground(Color.decode("#41bfa9"));
        labelPanel.setPreferredSize(new Dimension(200, 200));
        labelPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        infoPanel.add(labelPanel, BorderLayout.CENTER);

        gridPanel.setLayout(new GridLayout(10, 20, 5, 5));

        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        inputPanel.add(fluidField);
        inputPanel.add(btnEnter);

        bottomPanel.add(btnLoad);
        bottomPanel.add(inputPanel);
        bottomPanel.add(btnBack);

        add(infoPanel, BorderLayout.WEST);
        add(gridPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);


        btnLoad.addActionListener(this);
        btnEnter.addActionListener(this);
        btnBack.addActionListener(this);

        addWindowListener(this);

        setVisible(true);
    }

    public void loadCells(boolean fromFile) {
        gridPanel.removeAll();

        try {
            if (fromFile) {
                File file = new File(path);
                Scanner scanner = new Scanner(file);
                int count = 0;
                while (scanner.hasNextInt() && count < 200) {
                    cells[count] = new Cell();
                    cells[count].baseHorizon = scanner.nextInt();
                    count++;
                }
                scanner.close();

                if (count != 200) {
                    showMessage("File must contain exactly 200 values.");
                    return;
                }
            }

            for (int i = 0; i < 200; i++) {
                final int index = i;
                JButton btn = new JButton();

                double top = cells[i].baseHorizon - 200;
                double gasH = fluidContactDepth - top;
                if (gasH < 0) gasH = 0;

                cells[i].gasHeight = gasH;
                cells[i].volume = gasH * area;
                cells[i].percentGas = (gasH / 200.0) * 100.0;

                if (gasH <= 0) {
                    btn.setBackground(Color.RED);
                } else if (cells[i].percentGas < 50.0) {
                    btn.setBackground(Color.YELLOW);
                } else {
                    btn.setBackground(Color.GREEN);
                }

                btn.setOpaque(true);
                btn.setBorderPainted(false);

                btn.addActionListener(e -> updateInfo(index));

                cellButtons[i] = btn;
                gridPanel.add(btn);
            }

            // แสดงข้อมูลของ cell แรกทันที
            if (cells[0] != null) {
                updateInfo(0);
            }

        } catch (FileNotFoundException e) {
            showMessage("File not found.");
        } catch (Exception ex) {
            showMessage("Error: " + ex.getMessage());
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void updateInfo(int index) {
        labelBase.setText("Base Horizon : " + cells[index].baseHorizon + " m");
        labelTop.setText("Top Horizon : " + (cells[index].baseHorizon - 200) + " m");
        labelGasHeight.setText("Gas Thickness : " + String.format("%.2f", cells[index].gasHeight) + " m");
        labelVolume.setText("Volume : " + String.format("%.2f", cells[index].volume) + " m³");
        labelPercent.setText(String.format("Gas : %.1f%%", cells[index].percentGas));
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selected = chooser.getSelectedFile();
            path = selected.getAbsolutePath();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLoad) {
            chooseFile();
            loadCells(true);
        }
        if (e.getSource() == btnEnter) {
            try {
                fluidContactDepth = Double.parseDouble(fluidField.getText());
                loadCells(false);
            } catch (NumberFormatException ex) {
                showMessage("Please input a valid number.");
            }
        }
        if (e.getSource() == btnBack) {
            this.dispose();
            new Main().setVisible(true);
        }
    }

    @Override public void windowOpened(WindowEvent e) {}
    @Override public void windowClosing(WindowEvent e) { System.exit(0); }
    @Override public void windowClosed(WindowEvent e) {}
    @Override public void windowIconified(WindowEvent e) {}
    @Override public void windowDeiconified(WindowEvent e) {}
    @Override public void windowActivated(WindowEvent e) {}
    @Override public void windowDeactivated(WindowEvent e) {}

    public static void main(String[] args) {
        new gasStart();
    }
}


class Cell {
    int baseHorizon;
    double gasHeight;
    double volume;
    double percentGas;
}