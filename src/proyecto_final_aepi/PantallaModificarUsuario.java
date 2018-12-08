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
public class PantallaModificarUsuario extends JFrame {
    
    
    JPanel jpanel;
    JLabel labelNombre,labelApellidos,labelTelefono,labelEmail,labelDireccion;
    JTextField textoNombre,textoApellidos,textoTelefono,textoEmail,textoDireccion;
    JButton botonAceptar,botonVolver,botonMenuInicio;
    int idUsuario;
    
    public PantallaModificarUsuario( int idUsuario)
    {
        jpanel = new JPanel();
        
        labelNombre = new JLabel("Nombre");
        labelApellidos = new JLabel("Apellidos");
        labelTelefono = new JLabel("Teléfono");
        labelEmail = new JLabel("E-mail");
        labelDireccion = new JLabel("Dirección");
        
        textoNombre = new JTextField(30);
        textoApellidos = new JTextField(45);
        textoTelefono = new JTextField(20);
        textoEmail = new JTextField(45);
        textoDireccion = new JTextField(45);
        
        botonAceptar = new JButton("Aceptar");
        botonVolver = new JButton("Volver");
        botonMenuInicio = new JButton("Menu Inicio");
        botonMenuInicio.setActionCommand("Menu");
        
        this.idUsuario = idUsuario;
        
        this.initComponents();
    }
    
    
    public void initComponents()
    {
        setSize(400,400);
        setLocationRelativeTo(null);
        setTitle("Modificar usuario");
        jpanel.setLayout(null);
        
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        //posiciones de las label
      
        labelNombre.setBounds(20, 40, 75, 23);
        labelApellidos.setBounds(20, 80, 75, 23);
        labelTelefono.setBounds(20, 130, 75, 23);
        labelEmail.setBounds(20, 180,75, 23);
        labelDireccion.setBounds(20, 220, 75, 23);
        
        //posiciones de los jtext

        textoNombre.setBounds(90, 40, 200, 23);
        textoApellidos.setBounds(90, 80, 200, 23);
        textoTelefono.setBounds(90, 130, 150, 23);
        textoEmail.setBounds(90, 180, 180, 23);
        textoDireccion.setBounds(90, 220, 150, 23);
        
        //posiciones de los botones
        botonAceptar.setBounds(25, 290, 80, 23);
        botonVolver.setBounds(130, 290, 95, 23);
        botonMenuInicio.setBounds(250, 290, 120, 23);
        
        //añadimos los elementos al jpanel para que se vean por pantalla
        
        //label

        jpanel.add(labelNombre);
        jpanel.add(labelApellidos);
        jpanel.add(labelTelefono);
        jpanel.add(labelEmail);
        jpanel.add(labelDireccion);
        
        //jtext

        jpanel.add(textoNombre);
        jpanel.add(textoApellidos);
        jpanel.add(textoTelefono);
        jpanel.add(textoEmail);
        jpanel.add(textoDireccion);
        
        //jbutton
        jpanel.add(botonAceptar);
        jpanel.add(botonVolver);
        jpanel.add(botonMenuInicio);
        
        //añadimos los eventos
        ControladorPantallaModificarUsuario controlador = new ControladorPantallaModificarUsuario(this);
        botonAceptar.addActionListener(controlador);
        botonVolver.addActionListener(controlador);
        botonMenuInicio.addActionListener(controlador);
        

        this.setContentPane(this.jpanel);
        setVisible(true);
    }
    
    
}
