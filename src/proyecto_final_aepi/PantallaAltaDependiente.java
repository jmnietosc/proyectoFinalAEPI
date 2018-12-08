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
public class PantallaAltaDependiente extends JFrame{
    
    JLabel labelId,labelPass;
    JTextField textoId,textoPass;
    JButton botonAceptar,botonSalir;
    JPanel jpanel;
    
    public PantallaAltaDependiente()
    {
        jpanel = new JPanel();
        
        labelId = new JLabel("Id.dependiente");
        labelPass = new JLabel("Contraseña");
        
        textoId = new JTextField(20);
        textoPass = new JTextField(40);
        
        botonAceptar = new JButton("Aceptar");
        botonSalir =  new JButton("Salir");
        
        this.initComponents();
        
    }
    
    public void initComponents()
    {
        setSize(325,250);
        setLocationRelativeTo(null);
        setTitle("Alta nuevo dependiente");
        
        jpanel.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        //posiciones de las label
        labelId.setBounds(20,40, 100, 23);
        labelPass.setBounds(20, 80, 130, 23);

        
        //posiciones de los jtext
        textoId.setBounds(130, 40, 100, 23);
        textoPass.setBounds(130, 80, 100, 23);
        
        //posiciones de los botones
        botonAceptar.setBounds(60, 150, 80, 26);
        botonSalir.setBounds(170, 150, 80, 26);
   
        //añadimos los elementos al jpanel     
        jpanel.add(labelId);
        jpanel.add(labelPass);
        jpanel.add(textoId);
        jpanel.add(textoPass);
        jpanel.add(botonAceptar);
        jpanel.add(botonSalir);
        
        //Creamos el controlador  de eventos y se lo asignamos a los botones
        
        ControladorPantallaAltaDependiente controlador = new ControladorPantallaAltaDependiente(this);
        botonAceptar.addActionListener(controlador);
        botonSalir.addActionListener(controlador);
        
        this.setContentPane(this.jpanel);
        setVisible(true);
 
    }
    
}
