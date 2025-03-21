//Modified version of the Barnsley fern, based on https://rosettacode.org/wiki/Barnsley_fern#Java, but it uses an int[] pixels array instead of setRGB() in every iteration, 
//so that it is only used once at the end to update the entire image. It also uses Color.HSBtoRGB() to create a gradient effect across iterations, instead of having a uniform color. 
//Scaling is done dynamically based on width and height, making it adaptable to different resolutions. The resulting fern has also been modified by altering the coefficients and probabilities

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class BarnsleyFern extends JPanel {

    private final int width = 750, height = 750;
    private final BufferedImage img;

    public BarnsleyFern() {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        generateFern();
    }

    private void generateFern() {
        int[] pixels = new int[width * height];
        double x = 0, y = 0;

        for (int i = 0; i < 200_000; i++) {
            double r = Math.random();
            double tempX, tempY;

            if (r <= 0.01) {
                tempX = 0;
                tempY = 0.2 * y - 0.12;
            } else if (r <= 0.07) {
                tempX = 0.2 * x - 0.31 * y;
                tempY = 0.255 * x + 0.245 * y + 0.29;
            } else if (r <= 0.15) {
                tempX = -0.15 * x + 0.24 * y;
                tempY = 0.25 * x + 0.2 * y + 0.68;
            } else {
                tempX = -0.93 * x + 0.035 * y;
                tempY = -0.07 * x + 0.82 * y + 1.6;
            }
            x = tempX;
            y = tempY;

            int px = (int) Math.round(width / 2 + x * width / 11);
            int py = (int) Math.round(height - y * height / 11);

            if (px >= 0 && px < width && py >= 0 && py < height) {
                int color = Color.HSBtoRGB((float) i / 200_000, 1, 1); // Gradient effect
                pixels[py * width + px] = color;
            }
        }

        img.setRGB(0, 0, width, height, pixels, 0, width);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Barnsley Fern");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            BarnsleyFern fern = new BarnsleyFern();
            frame.add(fern);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

