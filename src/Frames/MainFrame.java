package Frames;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public  class MainFrame extends JFrame {
    private static Dimension gameResolutionSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static void setGameResolutionSize(Dimension gameResolutionSize) {
        MainFrame.gameResolutionSize = gameResolutionSize;
    }

    public MainFrame() {
        setTitle("Menu");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color = new Color(1, 1, 1);
                Color color2 = new Color(58, 58, 58);
                GradientPaint gradient = new GradientPaint(0, 0, color, 0, getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JButton startButton = new JButton("Start");
        startButton.setSize(400, 100);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setForeground(Color.WHITE);
        startButton.setLocation((this.getWidth() / 2) - (startButton.getWidth() / 2), (this.getHeight() / 2) - (startButton.getHeight() / 2));
        startButton.setBackground(Color.BLACK);
        startButton.setFocusPainted(false);
        JButton resolutionButton = getjButton(startButton, screenSize);

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GameFrame gameFrame = new GameFrame(gameResolutionSize);
                setState(JFrame.ICONIFIED);
                gameFrame.setVisible(true);
                gameFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        setState(JFrame.NORMAL);
                    }
                });
            }
        });
        JButton shapesButton = new JButton("Shapes");
        shapesButton.setSize(400, 100);
        shapesButton.setLocation(resolutionButton.getLocation().x, (resolutionButton.getLocation().y + resolutionButton.getHeight() + 10));
        shapesButton.setFont(new Font("Arial", Font.BOLD, 20));
        shapesButton.setForeground(Color.WHITE);
        shapesButton.setBackground(Color.BLACK);
        shapesButton.setFocusPainted(false);

        panel.add(startButton);
        panel.add(resolutionButton);
        panel.add(shapesButton);

        add(panel);
        repaint();
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private static JButton getjButton(JButton startButton, Dimension screenSize) {
        JButton resolutionButton = new JButton("Resolution");
        resolutionButton.setSize(400, 100);
        resolutionButton.setLocation(startButton.getLocation().x, (startButton.getLocation().y + startButton.getHeight() + 10));
        resolutionButton.setFont(new Font("Arial", Font.BOLD, 20));
        resolutionButton.setForeground(Color.WHITE);
        resolutionButton.setBackground(Color.BLACK);
        resolutionButton.setFocusPainted(false);

        resolutionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ResolutionFrame resolutionFrame = new ResolutionFrame(screenSize);
                resolutionFrame.setVisible(true);
            }
        });
        return resolutionButton;
    }

    public static void main(String[] args) {
        startApplication();
    }
    private static void startApplication(){
        MainFrame frame = new MainFrame();
    }
}
class ResolutionFrame extends JFrame {

    public ResolutionFrame(Dimension screenSize) {
        setTitle("Select Resolution");
        setSize(300, 400);
        setLocation(0, 0);
        setResizable(false);

        CheckboxGroup group = new CheckboxGroup();
        Checkbox resolution1 = new Checkbox("1366 x 768", group, false);
        Checkbox resolution2 = new Checkbox("1440 x 900", group, false);
        Checkbox resolution3 = new Checkbox("1600 x 900", group, false);
        Checkbox resolution4 = new Checkbox("1920 x 1080", group, false);
        Checkbox resolution5 = new Checkbox("2560 x 1440", group, false);
        Checkbox resolution6 = new Checkbox("3840 x 2160", group, false);

        String[] resolutionTable = {"1366", "1440", "1600", "1920", "2560", "3840"};

        int screenWidth = screenSize.width;
        int max = -1;

        for (int i = 0; i < resolutionTable.length; i++) {
            if (Integer.parseInt(resolutionTable[i]) <= screenWidth) {
                max = i;
            }
        }
        Panel resolutionPanel = new Panel(new GridLayout(max + 2, 1));
        if (max >= 0) resolutionPanel.add(resolution1);
        if (max >= 1) resolutionPanel.add(resolution2);
        if (max >= 2) resolutionPanel.add(resolution3);
        if (max >= 3) resolutionPanel.add(resolution4);
        if (max >= 4) resolutionPanel.add(resolution5);
        if (max == 5) resolutionPanel.add(resolution6);

        JButton acceptButton = getjButton(group);
        resolutionPanel.add(acceptButton);
        add(resolutionPanel);
    }

    private JButton getjButton(CheckboxGroup group) {
        JButton acceptButton = new JButton("Accept");
        acceptButton.setBackground(Color.BLACK);
        acceptButton.setForeground(Color.WHITE);
        acceptButton.setFocusPainted(false);

        acceptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Checkbox selectedCheckbox = group.getSelectedCheckbox();
                if (selectedCheckbox != null) {
                    String resolution = selectedCheckbox.getLabel();
                    String[] parts = resolution.split(" x ");
                    Dimension resolutionSize = new Dimension(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                    MainFrame.setGameResolutionSize(resolutionSize);
                }
                dispose();
            }
        });
        return acceptButton;
    }
}