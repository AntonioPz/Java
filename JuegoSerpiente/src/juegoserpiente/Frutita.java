package juegoserpiente;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

public class Frutita {

    private Random random;
    private Point frutita;
    private ImageIcon Img ;
    
    private int nuevoTipo;
    
    public Frutita(){//constructor de la clase
        random = new Random();//instacia de la clase random
        frutita = new Point();//instacia de la clase Point
    }

    public void nuevaFrutita() {
        frutita.x = random.nextInt(39);/*asigna un valor aleatoio a frutita en X de 0 a 38*/
        frutita.y = random.nextInt(28)+1;/*asigna un valor aleatoio a frutita en y de 0 a 28 */
        nuevoTipo= random.nextInt(8);//numero aleatorio para el tipo de alimento
        
    }
    

    public void dibujoFrutita(Graphics g) {
        switch (nuevoTipo) {//de acuerdo a la variable nuevoTipo de dibuja el tipo de alimento
            case 0:
                Img = new ImageIcon(getClass().getResource("/Resources/apple.png"));/*asignacion
                de la imagen al objeto*/
                g.drawImage(Img.getImage(), frutita.x * 20, frutita.y * 20, 20, 20, null);/*
                se dibuja la imagen con la posicion y el tamaño asignados*/
                break;
            case 1:
                Img = new ImageIcon(getClass().getResource("/Resources/cocaL.png"));
                g.drawImage(Img.getImage(), frutita.x*20, frutita.y*20, 20, 20, null);
                break;
            case 2:
                Img = new ImageIcon(getClass().getResource("/Resources/pastelito2.png"));
                g.drawImage(Img.getImage(), frutita.x*20, frutita.y*20, 20, 20, null);
                break;
            case 3:
                Img = new ImageIcon(getClass().getResource("/Resources/burger.png"));
                g.drawImage(Img.getImage(), frutita.x*20, frutita.y*20, 20, 20, null);
                break;
            case 4:
                Img = new ImageIcon(getClass().getResource("/Resources/cafe.png"));
                g.drawImage(Img.getImage(), frutita.x*20, frutita.y*20, 20, 20, null);
                break;
            case 5:
                Img = new ImageIcon(getClass().getResource("/Resources/lettuce.png"));
                g.drawImage(Img.getImage(), frutita.x*20, frutita.y*20, 20, 20, null);
                break;
            case 6:
                Img = new ImageIcon(getClass().getResource("/Resources/watermelon.png"));
                g.drawImage(Img.getImage(), frutita.x*20, frutita.y*20, 20, 20, null);
                break;
            case 7:
                Img = new ImageIcon(getClass().getResource("/Resources/orange.png"));
                g.drawImage(Img.getImage(), frutita.x*20, frutita.y*20, 20, 20, null);
                break;
        }   
        
    }
    public Point getFrutita()//metodo para usar el objeto en otras clases
    {
        return frutita;
    }
    public int getNuevoTipo()//metodo para usar la variable en otras clases
    {
        return nuevoTipo;
    }
}