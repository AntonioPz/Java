package juegoserpiente;
    
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import org.omg.CORBA.Current;

public class Nivel {
    private Random random;
    private ImageIcon Img;
    private int numNivel;
    private ArrayList<Point> PosicionNivel = new ArrayList<Point>();/* arrayList para 
    almacenar la posicion de los obstaculos*/
    
    public void Level(){//Constructor de la clase
        PosicionNivel = new ArrayList<>();
        numNivel=1;
    }
    
    public ArrayList<Point> getPosicionNivel() {//metodo para obtener el array en otras clases
        return PosicionNivel;
    }
    
    public void crearMuros(){
            for (int i = 0; i < 10; i++) {//ciclo para crear posiciones de objetos aleatorias
                random = new Random();
                PosicionNivel.add(new Point(random.nextInt(38) + 1, random.nextInt(28) + 2));
            }
    }
    
    public void nivelUno(Graphics g)//método para dibujar los obstaculos
    {
        numNivel=1;
        for (int i = 0; i < PosicionNivel.size(); i++) {
            Point muro = PosicionNivel.get(i);
            Img = new ImageIcon(getClass().getResource("/Resources/piedra1.png"));
            g.drawImage(Img.getImage(), muro.x*20,muro.y*20, 20, 20, null);
        } 
    }
    
    public void agregarMurosNivel(){//Método para aumentar mas obstaculos
        for (int i = 0; i < 5; i++) {
            random = new Random();
            PosicionNivel.add(new Point(random.nextInt(38) + 1, random.nextInt(28) + 2));
        }
    }
}