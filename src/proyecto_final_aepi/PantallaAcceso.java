/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_aepi;

import javax.swing.*;

/**
 *
 * @author Chema
 */
public class PantallaAcceso extends JFrame
{
    JPanel jpanel;
    JLabel labelUser,labelPass;
    JTextField textUser;
    JPasswordField textPass;
    JButton buttonEnter;
    
    public PantallaAcceso()
    {
        jpanel = new JPanel();
        labelUser = new JLabel("Usuario");
        labelPass = new JLabel("Contraseña");
        textUser = new JTextField(20);
        textPass = new JPasswordField(20);
        buttonEnter = new JButton("Entrar");
        
        this.initComponents();
    }
    
    public void initComponents()
    {
        setSize(300,300);
        setLocationRelativeTo(null);
        setTitle("Pantalla de acceso");
        jpanel.setLayout(null);
        
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        labelUser.setBounds(20, 50, 75, 23);
        jpanel.add(labelUser);
        
        textUser.setBounds(92, 50,150,23);
        jpanel.add(textUser);
        
        labelPass.setBounds(20,100,75, 23);
        jpanel.add(labelPass);
          
        textPass.setBounds(92,100,150, 23);
        jpanel.add(textPass);
        
        
        ControladorPantallaAcceso c = new ControladorPantallaAcceso(this);
        buttonEnter.addActionListener(c);    
        textUser.addKeyListener(c);
        
                
        buttonEnter.setBounds(110, 165, 80, 26);
        jpanel.add(buttonEnter);     
        
        this.setContentPane(this.jpanel);
        setVisible(true);
        
    }

    
}
