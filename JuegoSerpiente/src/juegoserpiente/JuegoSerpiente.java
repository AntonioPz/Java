package juegoserpiente;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class JuegoSerpiente extends JFrame implements KeyListener {

    //Atributos
    private int anchoVentana = 800; //Ancho de ventana
    private int altoVentana = 600; //Alto de ventana
    private Serpiente serpiente; //Instancia 
    private Nivel nivel; // Instancia de la clase nivel
    private Frutita frutita; //Instancia de la clase frutita
    private int puntos; //variable para almacenar puntos
    private int record; //variable para mayor Puntaje
    private boolean bandera; //evita el repetimiento de agregarMurosNivel();
    private boolean m; //controla menu de funcionamiento de teclas
    private int vecesMensaje; //variable para almacenar veces que se inicia el juego
    private long demora; //variable para almacenar tiempo de retardo
    private int tiempoDemora;
    private boolean funcionamiento = true, stop = true; //variable para controlar el funcionamiento
    private ImageIcon Img; // Instancia de la clase para usar imagenes
    private Random random;

    public static void main(String[] args) throws LineUnavailableException, IOException,
            UnsupportedAudioFileException, InterruptedException {
        JuegoSerpiente juego = new JuegoSerpiente(); //Creacion de una instancia del juego
    }

    public JuegoSerpiente() throws LineUnavailableException, IOException, 
            UnsupportedAudioFileException, InterruptedException {
        super("Snake");//Aignación de nombre de la ventana
        this.setSize(anchoVentana, altoVentana);//Aignación de tamaño de la ventana
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Resources/snake.jpg"));
        setIconImage(icon);//Aignación de imagen de la ventana
        //Asignación de la operacion por defecto al cerrar la ventana
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);//Bloquear la redimension de la ventana
        this.setLocationRelativeTo(null);//Desactivar la posicion relativa de la ventana
        addKeyListener(this);//uso de la interfaz KeyListener
        this.setVisible(true);//Hacer visible
        //crear espacio de almacenamientpo, la imagen se muestra cuando esta acabada
        this.createBufferStrategy(2);
        vecesMensaje = 0;
        record=0;

        inicializoObjetos();//Método para inicializar los valores del juego
        sonidoSnakeTheme();
        while (true) {// Ciclo infinito para relizar el juego
            juego();
            sleep();//Crear tiempo para realizar cada iteración del ciclo
        }
    }

    private void inicializoObjetos() {
        serpiente = new Serpiente(); //Instancia de la clase serpiente
        nivel = new Nivel();//Instancia de la clase nivel
        nivel.crearMuros();//Metodo para crear muros aleatorios
        serpiente.crecerColaSnake();
        frutita = new Frutita();//Instancia de la clase frutita
        frutita.nuevaFrutita();//Creacion de frutita
        verificarPosicionFrutita();//evita que la frutita este sobre un abstaculo
        puntos = 0; //inicializacion de puntaje
        bandera=true;
        m=false;
        tiempoDemora = 100;//ajuste de velocidad de juego
        
    }

    private void juego() throws LineUnavailableException, IOException, 
            UnsupportedAudioFileException, InterruptedException {
        if (funcionamiento) {//crea una pausa en el juego
            serpiente.muevoSnake();
        }
        if (puntos==50 && bandera) {
            nivel.agregarMurosNivel();
            bandera=false;
        }
        if (puntos==100 && !bandera) {
            nivel.agregarMurosNivel();
            bandera=true;
        }
        if (puntos==150 && bandera) {
            nivel.agregarMurosNivel();
            bandera=false;
        }
        if (puntos==200 && !bandera) {
            nivel.agregarMurosNivel();
            bandera=true;
        }
        if (puntos==250 && bandera) {
            nivel.agregarMurosNivel();
            bandera=false;
        }
        dibujoPantalla();//agrega los elementos graficos
        chequearColision();//verifica posicion respecto a los demas objetos
    }

    private void dibujoPantalla() {
        BufferStrategy bf = this.getBufferStrategy();//asignacion del buffer a una variable
        Graphics g = null;//inicializacion del objeto
        try {
            g = bf.getDrawGraphics();// aisgnacion del valor devuelto del buffer
            
            if (puntos >= 0) {
                //Abajo de define el valor de la instancia ImageIcon
                Img = new ImageIcon(getClass().getResource("/Resources/desert1.jpg"));
                //se dibuja en pantalla el fondo
                g.drawImage(Img.getImage(), 0, 0, anchoVentana, altoVentana, null);
                nivel.nivelUno(g);//metodo para dibujar obstaculos
            }
            if (vecesMensaje == 0 || m==true) {//Muestra el emnsaje de inicio del juego
                Img = new ImageIcon(getClass().getResource("/Resources/teclaM.png"));
                g.drawImage(Img.getImage(), 12 * 20, 11 * 20, 350, 150, null);//se dibuja en pantalla
            }
            frutita.dibujoFrutita(g);//metodo para dibujar alimentos
            serpiente.dibujoSnake(g);//metodo para dibujar serpiente
            if (m) {
                controlJuego(g);
            }
            muestroRecord(g);
            muestroPuntos(g);//metodo para mostrar puntos
        } finally {
            g.dispose();//desecha el objeto(no se necesita)
        }
        bf.show();//muestra el buffer
        Toolkit.getDefaultToolkit().sync();//sincroniza el
    }

    private void chequearColision() throws LineUnavailableException, IOException, 
            UnsupportedAudioFileException, InterruptedException {
        //condicion para evaluar si la serpiente comio el alimento
        if (serpiente.getLargo().get(0).equals(frutita.getFrutita())) {

            puntos += 10;
            //switch para seleccionar los efectos del alimento en la serpiente
            switch (frutita.getNuevoTipo()) {
                case 0:
                    serpiente.crecerColaSnake();
                    tiempoDemora -= 5;//aumento de velocidad
                    sonidoMordidasManzana();
                    break;
                case 1:
                    tiempoDemora -= 5;
                    sonidoLata();//sonido del aliemnto
                    sonidoBeber();
                    break;
                case 2:
                    tiempoDemora += 5;
                    sonidoMordidasPastel();//sonido del aliemnto
                    break;
                case 3:
                    tiempoDemora += 10;
                    serpiente.crecerColaSnake();
                    serpiente.crecerColaSnake();
                    sonidoMordidas();
                    break;
                case 4:
                    tiempoDemora -= 10;
                    sonidoCafe();//sonido del aliemnto
                    break;
                case 5:
                    serpiente.crecerColaSnake();
                    tiempoDemora -= 5;
                    sonidoMordidas();
                    break;
                case 6:
                    serpiente.crecerColaSnake();
                    tiempoDemora -= 5;
                    sonidoMordidas();
                    break;
                case 7:
                    tiempoDemora -= 5;
                    serpiente.crecerColaSnake();
                    sonidoMordidas();
                    break;
            }
            frutita.nuevaFrutita();//crea una nueva fruta despues de deborarla
            verificarPosicionFrutita();//evita frutas sobre obstaculos
        }

        //condicion para detectar la colision con las paredes
        if (serpiente.getLargo().get(0).x < 0 || serpiente.getLargo().get(0).x > 39
                || serpiente.getLargo().get(0).y < 1 || serpiente.getLargo().get(0).y > 29) {
            sonidoFail();
            pausa();
            inicializoObjetos();//reinicio de valores del juego
        }
        //ciclo para saber si toca sus cuerpo
        for (int n = 1; n < serpiente.getLargo().size(); n++) {
            if (serpiente.getLargo().get(0).equals(serpiente.getLargo().get(n)) && 
                        serpiente.getLargo().size() > 2) {
                sonidoFail();
                pausa();
                inicializoObjetos();
            }
        }
        //ciclo para saber si toca un obstaculo
        for (int n = 0; n < nivel.getPosicionNivel().size(); n++) {
            if (serpiente.getLargo().get(0).equals(nivel.getPosicionNivel().get(n))) {
                sonidoFail();
                pausa();
                inicializoObjetos();
            }
        }
    }

    private void pausa() throws InterruptedException {
        Thread t = new Thread();// instancia de la clase Thread
        t.sleep(2000); //Detiene el hilo por 2 secundos
    }

    private void verificarPosicionFrutita() {
        boolean cond1 = true;
        while (cond1) {
            boolean cond2 = false;
            for (int i = 0; i < nivel.getPosicionNivel().size(); i++) {
                //evalua si la fruta tiene la posicion de un obstaculo
                if (frutita.getFrutita().equals(nivel.getPosicionNivel().get(i))) {
                    //si tiene una posicion igual se crea otra fruta
                    frutita.nuevaFrutita();
                    cond2 = true;
                }
            }
            if (cond2 == false) {
                //si no tiene posicion igual el ciclo while termina
                cond1 = false;
            }
        }
    }

    private void sonidoCafe() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip sonido = AudioSystem.getClip();// creacion de una instacia de sonido
        //asignacion del sonido de la instancia
        sonido.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Audio/sorber.wav")));
        sonido.start();//se llama al método para la ejecucion del sonido
    }

    private void sonidoSnakeTheme() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip sonido = AudioSystem.getClip();
        sonido.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Audio/SnakeTheme.wav")));
        sonido.loop(99999);//se llama al método para reproducir continuamente el sonido del jeugo
        
    }
    private void sonidoFail() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip sonido = AudioSystem.getClip();
        sonido.stop();
        sonido.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Audio/fail.wav")));
        sonido.start();
    }
    private void sonidoMordidas() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip sonido = AudioSystem.getClip();
        sonido.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Audio/mordidas1.wav")));
        sonido.start();
    }
    private void sonidoMordidasPastel() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip sonido = AudioSystem.getClip();
        sonido.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Audio/m2.wav")));
        sonido.start();
    }
    private void sonidoMordidasManzana() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip sonido = AudioSystem.getClip();
        sonido.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Audio/m1.wav")));
        sonido.start();
    }
    private void sonidoLata() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip sonido = AudioSystem.getClip();
        sonido.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Audio/openingCan.wav")));
        sonido.start();
    }
    private void sonidoBeber() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip sonido = AudioSystem.getClip();
        sonido.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Audio/agua.wav")));
        sonido.start();
    }

    //muestra el puntaje

    private void muestroPuntos(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Puntos: " + puntos, 20, 50);
        
    }
    //Método para mostrar record
    private void muestroRecord(Graphics g) {
        if (puntos>record) {
            record=puntos;
        }
        if (record !=0) {
           g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Record: " + record, 700, 50);
        }
        
    }
    //Método para mostrar imagen con instrucciones de control de juego
    private void controlJuego(Graphics g) {
        vecesMensaje++;
        Img = new ImageIcon(getClass().getResource("/Resources/control.png"));
        g.drawImage(Img.getImage(), 10 * 20, 10 * 20, 400, 250, null);
    }

    //metodo para crear una pausa en loop principal del juego.
    private void sleep() {
        demora = (System.currentTimeMillis() + tiempoDemora);/* tiempo de referencia
         mas un tiempo adicional*/

        while (System.currentTimeMillis() < demora) {/*ciclo que crea la pause hasta
             que se alcanze el tiempo de referencia */
        }
    }

    @Override
//motodo de la interfaz KeyListener
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();//guarda en tecla el valor del metodo de la interfaz
        switch (tecla) {//asigna al metodo direccion informacion de la tecla presionada
            case KeyEvent.VK_UP:
                if (funcionamiento) {//condicion para no mover el cuerpo en modo pausa
                    serpiente.direccion("ARR");
                    vecesMensaje++;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (funcionamiento) {
                    serpiente.direccion("ABA");
                    vecesMensaje++;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (funcionamiento) {
                    vecesMensaje++;
                    serpiente.direccion("IZQ");
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (funcionamiento) {
                    vecesMensaje++;
                    serpiente.direccion("DER");
                }
                break;
            case KeyEvent.VK_E:
                System.exit(0);
                break;
            case KeyEvent.VK_ENTER:
                funcionamiento = false;
                break;
            case KeyEvent.VK_C:
                funcionamiento = true;
                break;
            case KeyEvent.VK_M:
                if (!m) {
                     m=true;
                }
                else{
                    m=false;
                }        
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    

}
