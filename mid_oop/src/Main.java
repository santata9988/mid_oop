import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Start {
    public static void main(String[] args) {
        Main frame = new Main();
        frame.setVisible(true);
    }
}

/**
 * myframe extends JFrame
 */
public class Main extends JFrame implements ActionListener {
    // attibult
    JButton btnStart = new JButton();
    JButton btnCreator = new JButton();
    JButton btnExit = new JButton();
    JPanel btnpanel = new JPanel();
    JPanel imJPanel = new JPanel();

    // constuctor
    Main() {
        setTitle("rainClean Air");
        setBounds(500, 200, 2000, 1140);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        btnpanel.setLayout(new FlowLayout());
        btnpanel.setSize(400, 300);

        btnStart.setText("Start");
        btnStart.setPreferredSize(new Dimension(300, 50));
        btnCreator.setText("Creator");
        btnCreator.setPreferredSize(new Dimension(300, 50));
        btnExit.setText("Exit");
        btnExit.setPreferredSize(new Dimension(300, 50));

        imJPanel.setLayout(new BorderLayout());
        imJPanel.add(new startImage(), BorderLayout.CENTER);

        btnStart.addActionListener(this);
        btnCreator.addActionListener(this);
        btnExit.addActionListener(this);

        btnpanel.add(btnStart);
        btnpanel.add(btnCreator);
        btnpanel.add(btnExit);
        add(btnpanel, BorderLayout.SOUTH);
        add(imJPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStart) {
            gasStart pm = new gasStart();
            setVisible(false);
            pm.setVisible(true);
        } else if (e.getSource() == btnCreator) {
            Creator creator = new Creator();
            setVisible(false);
            creator.setVisible(true);
        } else if (e.getSource() == btnExit) {
            System.exit(0);
        }
    }
}

class startImage extends JPanel {
    private BufferedImage startImage;

    public startImage() {
        try {
            startImage = ImageIO.read(new File(
                    "resources/main.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(startImage, 0, 0, getWidth(), getHeight(), this);
    }
}
