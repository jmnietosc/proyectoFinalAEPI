/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_aepi;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author Chema
 */
public class PantallaAltaUsuario extends JFrame{
    
    JPanel jpanel;
    JLabel labelId,labelNombre,labelApellidos,labelTelefono,labelEmail,labelDireccion;
    JTextField textoId,textoNombre,textoApellidos,textoTelefono,textoEmail,textoDireccion;
    JButton botonAceptar,botonLimpiar,botonCancelar;
    
    public PantallaAltaUsuario()
    {
        jpanel = new JPanel();
        
        labelId = new JLabel("IdUsuario(opcional)");
        labelNombre = new JLabel("Nombre");
        labelApellidos = new JLabel("Apellidos");
        labelTelefono = new JLabel("Teléfono");
        labelEmail = new JLabel("E-mail");
        labelDireccion = new JLabel("Dirección");
        
        textoId = new JTextField(10);
        textoNombre = new JTextField(30);
        textoApellidos = new JTextField(45);
        textoTelefono = new JTextField(20);
        textoEmail = new JTextField(45);
        textoDireccion = new JTextField(45);
        
        botonAceptar = new JButton("Aceptar");
        botonLimpiar = new JButton("Limpiar");
        botonCancelar = new JButton("Volver");
        
        this.initComponents();
    }
    
    
    public void initComponents()
    {
        setSize(400,400);
        setLocationRelativeTo(null);
        setTitle("Alta de usuario");
        jpanel.setLayout(null);
        
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        //posiciones de las label
        labelId.setBounds(20,20, 150, 23);
        labelNombre.setBounds(20, 60, 75, 23);
        labelApellidos.setBounds(20, 100, 75, 23);
        labelTelefono.setBounds(20, 150, 75, 23);
        labelEmail.setBounds(20, 200, 75, 23);
        labelDireccion.setBounds(20, 250, 75, 23);
        
        //posiciones de los jtext
        textoId.setBounds(150, 20, 60, 23);
        textoNombre.setBounds(90, 60, 200, 23);
        textoApellidos.setBounds(90, 100, 200, 23);
        textoTelefono.setBounds(90, 150, 150, 23);
        textoEmail.setBounds(90, 200, 150, 23);
        textoDireccion.setBounds(90, 250, 150, 23);
        
        //posiciones de los botones
        botonAceptar.setBounds(40, 320, 80, 23);
        botonLimpiar.setBounds(140, 320, 80, 23);
        botonCancelar.setBounds(240, 320, 80, 23);
        
        //añadimos los elementos al jpanel para que se vean por pantalla
        
        //label
        jpanel.add(labelId);
        jpanel.add(labelNombre);
        jpanel.add(labelApellidos);
        jpanel.add(labelTelefono);
        jpanel.add(labelEmail);
        jpanel.add(labelDireccion);
        
        //jtext
        jpanel.add(textoId);
        jpanel.add(textoNombre);
        jpanel.add(textoApellidos);
        jpanel.add(textoTelefono);
        jpanel.add(textoEmail);
        jpanel.add(textoDireccion);
        
        //jbutton
        jpanel.add(botonAceptar);
        jpanel.add(botonLimpiar);
        jpanel.add(botonCancelar);
        
        //añadimos los listener a los botones
        ControladorPantallaAltaUsuario controlador = new ControladorPantallaAltaUsuario(this);
        botonAceptar.addActionListener(controlador);
        botonLimpiar.addActionListener(controlador);
        botonCancelar.addActionListener(controlador);
        textoId.addKeyListener(controlador);
        textoTelefono.addKeyListener(controlador);

        this.setContentPane(this.jpanel);
        setVisible(true);
    }
    
}
