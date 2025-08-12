import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

class creatorImage extends JPanel {
    private BufferedImage creatorImage;

    public creatorImage() {
        try {
            creatorImage = ImageIO.read(new File(
                    "resources/creator.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(creatorImage, 0, 0, getWidth(), getHeight(), this);
    }
}

public class Creator extends JFrame implements ActionListener, WindowListener {
    JButton btnback = new JButton();
    JPanel btJPanel = new JPanel();
    JPanel imgPanel = new JPanel();

    Creator() {
        setTitle("rainClean Air");
        setBounds(500, 200, 2000, 1140);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        btJPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btJPanel.add(btnback);

        imgPanel.setLayout(new BorderLayout());
        imgPanel.add(new creatorImage(), BorderLayout.CENTER);
        imgPanel.add(btJPanel, BorderLayout.SOUTH);

        btnback.setText("Back");
        btnback.addActionListener(this);

        add(imgPanel, BorderLayout.CENTER);
        addWindowListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnback) {
            Main frame = new Main();
            setVisible(false);
            frame.setVisible(true);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Main myframe = new Main();
        setVisible(false);
        myframe.setVisible(true);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}