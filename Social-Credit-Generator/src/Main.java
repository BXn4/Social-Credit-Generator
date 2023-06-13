import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main implements ChangeListener {
    public static JSlider slider = new JSlider();
    public static JLabel creditsLabel = new JLabel();
    public static Clip clip;
    public static Clip sounds;
    public static float value;
    public static float range;
    public static float gainValue;
    public static Integer credits = 0;
    static Random random = new Random();

    public static void GUI() {
        JOptionPane.showMessageDialog(null, "这个软件并非由中国政府开发。\nOk?", "你好！",JOptionPane.INFORMATION_MESSAGE);
        JFrame frame = new JFrame("Social Credit Generator");
        JPanel panel = new JPanel();
        Color red = Color.decode("#c04943");
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("images/plus.jpg"));
        }
        catch (IOException e) {
        } /*
        ImageIcon imgIcon = new ImageIcon(img);
        JLabel lbl = new JLabel();
        lbl.setIcon(imgIcon);
        lbl.setBounds(0,0,10,1);
        frame.getContentPane().add(lbl);
        frame.pack(); */
        //JOptionPane.showMessageDialog(null, label);
        creditsLabel = new JLabel("Social Credits: 0 :(");
        JButton generateBtn = new JButton("Generate");
        slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 70);
        slider.setBounds(530,330,150,30);
        slider.setMinorTickSpacing(5);
        slider.addChangeListener(new Main());
        slider.setBackground(red);
        creditsLabel.setFont(new Font("Serif", Font.BOLD, 24));
        creditsLabel.setBounds(10,-30,600,100);
        generateBtn.setBounds(10,50,100,20);
        BufferedImage finalImg = img;
        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int randomNum = random.nextInt(100) + 1;
                credits += randomNum;
                creditsLabel.setText("Social Credits: " + credits);
                File path = new File("sounds/earned.wav");
                if (path.exists()) {
                    AudioInputStream audioInputStream2 = null;
                    try {
                        audioInputStream2 = AudioSystem.getAudioInputStream(path);
                    } catch (UnsupportedAudioFileException ex) {
                    } catch (IOException ex) {
                    }
                    try {
                        sounds = AudioSystem.getClip();
                    } catch (LineUnavailableException ex) {
                    }
                    try {
                        sounds.open(audioInputStream2);
                    } catch (LineUnavailableException ex) {
                    } catch (IOException ex) {
                    }
                    FloatControl gainControl = (FloatControl) sounds.getControl(FloatControl.Type.MASTER_GAIN);
                    value = slider.getValue();
                    range = gainControl.getMaximum() - gainControl.getMinimum();
                    gainValue = (value / 100f) * range + gainControl.getMinimum();
                    gainControl.setValue(gainValue);
                    sounds.start();
                    BufferedImage bufferedImage = new BufferedImage(finalImg.getWidth(), finalImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    Graphics2D graphics = bufferedImage.createGraphics();
                    graphics.drawImage(finalImg, 0, 0, null);
                    graphics.setFont(new Font("Arial", Font.BOLD, 80));
                    graphics.drawString("+" + randomNum, 190, 150);
                    graphics.dispose();
                    ImageIcon icon = new ImageIcon(bufferedImage);
                    JOptionPane.showMessageDialog(null, "", "好工作！", JOptionPane.INFORMATION_MESSAGE, icon);
                    sounds.stop();
                }
            }
        });
        frame.add(slider);
        frame.add(creditsLabel);
        frame.add(generateBtn);
        frame.setSize(700,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        panel.setBackground(red);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void PlayMusic(String url) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File path = new File(url);
        if(path.exists()) {
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
        }
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String path = "sounds/Red Sun in the Sky.wav";
        GUI();
        PlayMusic(path);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        value = slider.getValue();
        range = gainControl.getMaximum() - gainControl.getMinimum();
        gainValue = (value / 100f) * range + gainControl.getMinimum();
        gainControl.setValue(gainValue);
    }

}
