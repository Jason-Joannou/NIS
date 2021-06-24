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
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.net.*;
import java.io.*;
import javax.imageio.ImageIO;
public class Client {
    public static void main(String [] args) {
        try{
            Socket s = new Socket("localhost",4999);
            System.out.println("You are now chatting with Bob");
            
        JFrame jframe = new JFrame("Alice");
        jframe.setSize(400,400);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ImageIcon image = new ImageIcon("C:\\Users\\jjoan\\Documents\\NetBeansProjects\\TestNIS\\Images\\cat.jpg");
        
        JLabel label = new JLabel(image);
        
        JButton button = new JButton("Send image to Bob");
        jframe.add(label,BorderLayout.CENTER);
        jframe.add(button,BorderLayout.SOUTH);
        
        jframe.setVisible(true);
        
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    
                    OutputStream output = s.getOutputStream();
                    BufferedOutputStream buffOutput = new BufferedOutputStream(output);
                    
                    Image i = image.getImage();
                    
                    BufferedImage buffImage = new BufferedImage(i.getWidth(null),i.getHeight(null),BufferedImage.TYPE_INT_RGB);
                    
                    Graphics graphics = buffImage.createGraphics();
                    graphics.drawImage(i, 0, 0, null);
                    graphics.dispose();
                    
                    
                    ImageIO.write(buffImage, "jpg", buffOutput);
                    buffOutput.close();
                    s.close();
                    
                    
                }
                catch(IOException ex){
                    System.out.println(ex);
                }
            }
        });

            
        }
        catch(IOException e){
            System.out.println("Couldnt reach Bob, please try again");
        }
        
    }
    
    
    
}
