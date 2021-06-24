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
import java.net.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class Server {
    static String choice="";
    static ImageIcon image;
    static JFrame jframe;
    static JLabel label;
    public static void main(String [] args) throws IOException{
        
        try{
             ServerSocket ss = new ServerSocket(4999);
             Socket s = ss.accept();
             //make exit button that closes sockets
             System.out.println("Connection successful"); 
             
             JFrame jframe = new JFrame("Bob");
             jframe.setSize(400,400);
             jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             String [] imageNames = {"cat","fish","painting"};
             image = new ImageIcon("C:\\Users\\jjoan\\Documents\\NetBeansProjects\\TestNIS\\Images\\"+choice+".jpg");
             //ImageIcon image = new ImageIcon("C:\\Users\\jjoan\\Documents\\NetBeansProjects\\TestNIS\\Images\\cat.jpg");
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
               //JLabel label = new JLabel("Waiting for image");
               //jframe.add(label,BorderLayout.SOUTH);
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
             //label.setText("Image recieved");
             //jframe.add(pic,BorderLayout.CENTER);
            
        }
        catch(IOException e){
            
            System.out.println("Could not reach Alice, please try again");
        }
        
    }
    
}
