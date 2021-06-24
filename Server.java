/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jjoan
 */
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class Server {
    
    public static void main(String [] args) throws IOException{
        
        try{
             ServerSocket ss = new ServerSocket(4999);
             Socket s = ss.accept();
        
             System.out.println("Connection successful"); 
             
             JFrame jframe = new JFrame("Bob");
             jframe.setSize(400,400);
             jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
             //ImageIcon image = new ImageIcon("C:\\Users\\jjoan\\Documents\\NetBeansProjects\\TestNIS\\Images\\cat.jpg");
        
             //JLabel label = new JLabel(image);
               JLabel label = new JLabel("Waiting for image");
               jframe.add(label,BorderLayout.SOUTH);
             //JButton button = new JButton("Send image to Bob");
             //jframe.add(label,BorderLayout.CENTER);
             //jframe.add(button,BorderLayout.SOUTH);
        
             jframe.setVisible(true);
             
             InputStream input = s.getInputStream();
             BufferedInputStream buffInput = new BufferedInputStream(input);
             BufferedImage buffImage = ImageIO.read(buffInput);
             buffInput.close();
             s.close();
             
             JLabel pic = new JLabel(new ImageIcon(buffImage));
             label.setText("Image recieved");
             jframe.add(pic,BorderLayout.CENTER);
            
        }
        catch(IOException e){
            
            System.out.println("Could not reach Alice, please try again");
        }
        
    }
    
}
