package kamisado;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 Created by Flo on 11/01/2017.
 */
public class BackgroundPanel extends JPanel{
    private int width;
    private int height;
    private Image image;

    public BackgroundPanel(int width, int height, String nomImage){
        super();
        try {
            this.image = new ImageIcon(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(nomImage)))).getImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.width = width;
        this.height = height;

        setSize(width, height);
    }

    public void paintComponent(Graphics g){
        //super.paintComponent(g);
        g.drawImage(image, 0, 0, width, height, null);
    }
}
