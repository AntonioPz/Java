package juegoserpiente;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Serpiente {
    
    private ArrayList<Point> longitud = new ArrayList<Point>();/*Array para almacenar 
    la posicion del cuerpo de la serpiente*/
    private int serpienteX=0;
    private int serpienteY=0;
    private ImageIcon Img;
    
    public Serpiente(){//Constructor de la clase
        longitud.add(new Point(20, 15));
    }
    
    public ArrayList<Point> getLargo()//para acceder a longitud desde fuera de la clase
    {
        return longitud;
    }
    
    public void dibujoSnake(Graphics g){
        //juego.isFuncionamiento();
        for(int n = 0; n < longitud.size(); n++) {
            Point p = longitud.get(n);
           
            if (n==longitud.size()-1 && (longitud.size()!=1)) { //ciclo para dibujar la cola
                /*la imagen de la cola varia en funcion de la posicion de la parte
                 anterior a la cola de la serpiente*/
                if (longitud.get(n).x == longitud.get(n-1).x && longitud.get(n).y == (longitud.get(n-1).y)+1) {
                    Img = new ImageIcon(getClass().getResource("/Resources/tail/tailArr.png"));
                    g.drawImage(Img.getImage(), p.x*20, p.y*20, 20, 20, null);
                }
                if (longitud.get(n).x == longitud.get(n-1).x && longitud.get(n).y == (longitud.get(n-1).y)-1) {
                    Img = new ImageIcon(getClass().getResource("/Resources/tail/tailAba.png"));
                    g.drawImage(Img.getImage(), p.x*20, p.y*20, 20, 20, null);
                }
                if (longitud.get(n).y == longitud.get(n-1).y && longitud.get(n).x == (longitud.get(n-1).x)+1) {
                    Img = new ImageIcon(getClass().getResource("/Resources/tail/tailIzq.png"));
                    g.drawImage(Img.getImage(), p.x*20, p.y*20, 20, 20, null);
                }
                if (longitud.get(n).y == longitud.get(n-1).y && longitud.get(n).x == (longitud.get(n-1).x)-1) {
                    Img = new ImageIcon(getClass().getResource("/Resources/tail/tailDer.png"));
                    g.drawImage(Img.getImage(), p.x*20, p.y*20, 20, 20, null);
                }
            }
            //Condicion para dibujar el cuerpo
            /*Solo utiliza una imagen debido a que no cambia la apariencia con 
            respecto al resto de las partes del cuerpo*/
            if (n > 0 && n < longitud.size() -1 && longitud.size()!=1) {
                Img = new ImageIcon(getClass().getResource("/Resources/body/bodyVer.png"));
                g.drawImage(Img.getImage(), p.x*20, p.y*20, 20, 20, null);
            }
            //Condicion para dibujar la cabeza, solo requiere de los parámetro de
            //movimiento obtenidos de las variables serpienteX y serpienteY
            if (n==0) {
                if (serpienteX==0 && serpienteY==0) {
                    Img = new ImageIcon(getClass().getResource("/Resources/head/headArr.png"));
                    g.drawImage(Img.getImage(), p.x*20, p.y*20, 20, 20, null);
                }
                if (serpienteX==0 && serpienteY==-1) {
                    Img = new ImageIcon(getClass().getResource("/Resources/head/headArr.png"));
                    g.drawImage(Img.getImage(), p.x*20, p.y*20, 20, 20, null);
                }
                if (serpienteX==0 && serpienteY==1) {
                    Img = new ImageIcon(getClass().getResource("/Resources/head/headAba.png"));
                    g.drawImage(Img.getImage(), p.x*20, p.y*20, 20, 20, null);
                }
                if (serpienteX==-1 && serpienteY==0) {
                    Img = new ImageIcon(getClass().getResource("/Resources/head/headIzq.png"));
                    g.drawImage(Img.getImage(), p.x*20, p.y*20, 20, 20, null);
                }
                if (serpienteX==1 && serpienteY==0) {
                    Img = new ImageIcon(getClass().getResource("/Resources/head/headDer.png"));
                    g.drawImage(Img.getImage(), p.x*20, p.y*20, 20, 20, null);
                } 
            }
        }
    }   
    
    public void muevoSnake() {
        
        for(int n = longitud.size()-1; n > 0; n--) {
            longitud.get(n).setLocation(longitud.get(n-1));
            }
        longitud.get(0).x += serpienteX;
        longitud.get(0).y += serpienteY;
    }
    
    public void crecerColaSnake () {
            longitud.add(new Point());
        }
    
    public void direccion(String d)
    {
        switch(d){//swith para controlar los movimientos de la serpeinte
            case "ARR":
                serpienteX = 0;
                serpienteY = -1;
                break;
            case "ABA":
                serpienteX = 0;
                serpienteY = 1;
                break;
            case "IZQ":
                serpienteX = -1;
                serpienteY = 0;
                break;
            case "DER":
                serpienteX = 1;
                serpienteY = 0;
                break;
        }
    }   
}