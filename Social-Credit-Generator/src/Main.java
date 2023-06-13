import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    static JSlider slider = new JSlider();
    public static Clip clip;
    public static float value;
    public static float range;
    public static float gainValue;
    public static void GUI() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 70);
        Color red = Color.decode("#c04943");
        panel.setBorder(BorderFactory.createEmptyBorder(30, 200, 275, 200));
        panel.setLayout(new GridLayout(0, 1));
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        panel.setBackground(red);
        panel.add(slider);
        frame.setTitle("Social Credit Generator");
        frame.pack();
        frame.setVisible(true);

    }

    public static void PlayMusic(String url) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File path = new File(url);
        if(path.exists()) {
            System.out.println("van");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(path);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            value = slider.getValue();
            range = gainControl.getMaximum() - gainControl.getMinimum();
            gainValue = (value / 100f) * range + gainControl.getMinimum();
            gainControl.setValue(gainValue);
            clip.start();
        }
        else {
            System.out.println("nincs");
        }
    }
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String path = "sounds/Red Sun in the Sky.wav";
        GUI();
        PlayMusic(path);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                value = slider.getValue();
                range = gainControl.getMaximum() - gainControl.getMinimum();
                gainValue = (value / 100f) * range + gainControl.getMinimum();
                gainControl.setValue(gainValue);
            }
        });
    }
}