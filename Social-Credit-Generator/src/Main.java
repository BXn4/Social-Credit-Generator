import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

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
    private static int i = 0;

    public static void GUI() {
        JOptionPane.showMessageDialog(null, "这个软件并非由中国政府开发。\nOk?", "你好！",JOptionPane.INFORMATION_MESSAGE);
        JFrame frame = new JFrame("Social Credit Generator");
        JPanel panel = new JPanel();
        Color red = Color.decode("#c04943");
        BufferedImage img = null;
        BufferedImage img2 = null;
        try {
            img = ImageIO.read(Main.class.getResource("images/plus.jpg"));
            img2 = ImageIO.read(Main.class.getResource("images/minus.png"));
        }
        catch (IOException e) {
        }
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
        BufferedImage finalImg2 = img2;
        ImageIcon icon2 = new ImageIcon(finalImg2);
        BufferedImage bufferedImage = new BufferedImage(finalImg.getWidth(), finalImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (i < 6) {
                    i++;
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
                        Graphics2D graphics = bufferedImage.createGraphics();
                        graphics.setFont(new Font("Arial", Font.BOLD, 80));
                        graphics.drawImage(finalImg, 0, 0, null);
                        ImageIcon icon = new ImageIcon(bufferedImage);
                        graphics.drawString("+" + randomNum, 190, 150);
                        graphics.dispose();
                        FloatControl gainControl = (FloatControl) sounds.getControl(FloatControl.Type.MASTER_GAIN);
                        value = slider.getValue();
                        range = gainControl.getMaximum() - gainControl.getMinimum();
                        gainValue = (value / 100f) * range + gainControl.getMinimum();
                        gainControl.setValue(gainValue);
                        sounds.start();
                        JOptionPane.showMessageDialog(null, "", "好工作！", JOptionPane.INFORMATION_MESSAGE, icon);
                        sounds.stop();
                        audioInputStream2 = null;
                        System.gc();
                    }
                }
                else {
                    File path = new File("sounds/doit.wav");
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
                    JOptionPane.showMessageDialog(null, "ATTENTION CITIZEN! 市民请注意!\nWe have detected that you are actively engaged in the generating the social credits\n我们检测到您正在积极参与社会信用的产生,\nand we applaud your commitment to the principles set forth by our Party\n并对您对我们党制定的原则的承诺表示赞赏.\nBy actively participating in the generation of social credits\n通过积极参与社会信用的生成,\nyou are demonstrating your dedication to upholding the values of our society\n您展示了对我们社会价值的奉献精神.\n" +
                            "\n" +
                            "However, we regret to inform you that -30,000,000 credits have been removed from your social credit score\n然而，我们遗憾地通知您，您的社会信用分数减少了3000万分.\nThis deduction is due to an violation of the social credit system guidelines\n这是因为您违反了社会信用体系的指导方针.，以确保符合既定原则.", "市民请注意!", JOptionPane.INFORMATION_MESSAGE, icon2);
                    sounds.stop();
                    audioInputStream2 = null;
                    System.gc();
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

    public static void PlayMusic(File file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(file.exists()) {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            value = slider.getValue();
            range = gainControl.getMaximum() - gainControl.getMinimum();
            gainValue = (value / 100f) * range + gainControl.getMinimum();
            gainControl.setValue(gainValue);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
        else {
        }
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException, URISyntaxException {
        File file = new File(Main.class.getResource("sounds/Red Sun in the Sky.wav").toURI());
        GUI();
        PlayMusic(file);
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
