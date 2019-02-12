package vue;

import javax.swing.*;
import java.awt.*;

public class Textures {

    public static final ImageIcon iconePikachu=new ImageIcon(ClassLoader.getSystemClassLoader().getResource("pikachu.png"));
    public static final ImageIcon iconeRondoudou=new ImageIcon(ClassLoader.getSystemClassLoader().getResource("jigglypuff.png"));
    public static final ImageIcon iconeBlanche=new ImageIcon(ClassLoader.getSystemClassLoader().getResource("blanc.png"));
    /*public static ImageIcon redimensionner(ImageIcon imageIcon, int width, int height) {
        Image img = imageIcon.getImage() ;
        Image newimg = img.getScaledInstance( width, height,  java.awt.Image.SCALE_SMOOTH ) ;
        return new ImageIcon(newimg);
    }*/
}
