
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates{
    
    private int x;
    private int y;

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}
public class Oyun extends JPanel implements KeyListener,ActionListener {
    
    Timer timer = new Timer(5, this);
    private int gecen_sure = 0;
    private int harcanan_ates = 0;
    private BufferedImage image1;
    private BufferedImage image2;
    private ArrayList<Ates> atesler = new ArrayList<Ates>();
    private int atesdirY = 1;
    private int topX = 500; 
    private int topdirX = 2;
    private int flagdirX = 2;
    private int uzayGemisiX = 0;
    private int flagX = 0;
    private int dirUzayX = 20;
    
    
    public boolean kontrolEt(){
    
        for(Ates ates: atesler){
            
            if(new Rectangle(ates.getX(),ates.getY(),10,20).intersects(new Rectangle(topX,0,20,20))){
                return true;
            }
            if(new Rectangle(ates.getX(),ates.getY(),10,20).intersects(new Rectangle(flagX,0,20,20))){
                return true;
            }
        }
        return false;
    }

    public Oyun() {
        
        try {
            image1 = ImageIO.read(new FileImageInputStream(new File("roket1.png")));
            image2 = ImageIO.read(new FileImageInputStream(new File("flag1.png")));

        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);
        
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        gecen_sure += 5;
        g.setColor(Color.red);
        g.fillOval(topX, 0, 20, 20);  
        g.drawImage(image2, flagX,0,this);
        g.drawImage(image1, uzayGemisiX, 490,this);
        
        for(Ates ates: atesler){
            if(ates.getY() < 0){
                atesler.remove(ates);
            }
        
        }
       
        g.setColor(Color.BLUE);
        
        for(Ates ates: atesler){
           
            g.fillRect(ates.getX(), ates.getY(), 10, 20);
        }
        
        if(kontrolEt()){
            timer.stop();
            String message = "Kazandınız!\n"
                            +"Harcanan Ateş: " +harcanan_ates
                            +"\nGeçen Süre: " +gecen_sure/1000.0 + " saniye";
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }    
    
    @Override
    public void repaint() {
        super.repaint(); 
    }
    
    
    
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int c = e.getKeyCode();
        
        if(c == KeyEvent.VK_LEFT){
            if(uzayGemisiX <= 0){
                
            }
            else{
                uzayGemisiX -= dirUzayX;
            }
        }
        else if(c == KeyEvent.VK_RIGHT){
            if(uzayGemisiX >= 750){
                uzayGemisiX = 750;
            }
            else{
                uzayGemisiX += dirUzayX;
            }
        }
            else if (c == KeyEvent.VK_CONTROL){
                  atesler.add(new Ates(uzayGemisiX +15, 475));
                  harcanan_ates++;
                    
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        for(Ates ates: atesler){
            ates.setY(ates.getY() - atesdirY);
            
        }
        topX += topdirX;
        
        if(topX >= 750){
            topdirX = -topdirX;
        }
        if (topX <= 0) {
            topdirX = -topdirX;
        }
       
        flagX += flagdirX;
         if(flagX >= 750){
            flagdirX = -flagdirX;
        }
        if (topX <= 0) {
            flagdirX = -flagdirX;
        }
        
        repaint();
    }
    
}
