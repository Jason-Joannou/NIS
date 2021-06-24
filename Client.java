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
static String choice="";
static ImageIcon image;
static JFrame jframe;
static JLabel label;
    public static void main(String [] args) {
        try{
            Socket s = new Socket("localhost",4999);
            System.out.println("You are now chatting with Bob");
            
        jframe = new JFrame("Alice");
        jframe.setSize(400,400);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        String [] imageNames = {"cat","fish","painting"};
        image = new ImageIcon("C:\\Users\\jjoan\\Documents\\NetBeansProjects\\TestNIS\\Images\\"+choice+".jpg");
        label = new JLabel(image);
        JComboBox comboBox = new JComboBox(imageNames);
        jframe.add(comboBox,BorderLayout.NORTH);
        comboBox.addActionListener(new ActionListener (){
            String option;
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==comboBox){
                  option=(String)comboBox.getSelectedItem();
                  choice = option;
                  image = new ImageIcon("C:\\Users\\jjoan\\Documents\\NetBeansProjects\\TestNIS\\Images\\"+choice+".jpg");
                  label.setIcon(image);
                  jframe.add(label,BorderLayout.CENTER);
                  jframe.setVisible(true);
                  
                }
            }
        });
        
        
        label = new JLabel(image);
        
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
        
        
             InputStream input = s.getInputStream();
             BufferedInputStream buffInput = new BufferedInputStream(input);
             BufferedImage buffImage = ImageIO.read(buffInput);
             buffInput.close();
             s.close();
             ImageIcon recieved = new ImageIcon(buffImage);
             
             label.setIcon(recieved);

            
        }
        catch(IOException e){
            System.out.println("Couldnt reach Bob, please try again");
        }
        
    }
    
   
        
}
