/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_aepi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
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
public class PantallaModificarLibro extends JFrame
{
    JPanel jpanel;
    JLabel labelTitulo,labelAutor,labelISBN,labelPrestadoA;
    JTextField textoTitulo,textoAutor,textoISBN,textoPrestadoA;
    JButton botonAceptar,botonDeshacer,botonCancelar,botonDevolver,botonTomar;
    String tituloInicio,autorInicio,ISBNInicio,usuario;
    int idLibro;
   
    public PantallaModificarLibro (int idLibro,String titulo,String autor ,String isbn , String usuario)
    {
        jpanel = new JPanel(new BorderLayout(3,3)); 
            
        labelTitulo = new JLabel("Titulo");
        labelAutor = new JLabel("Autor");
        labelISBN = new JLabel("ISBN");
        labelPrestadoA = new JLabel("Prestado a");

        textoTitulo = new JTextField(30);
        textoAutor = new JTextField(45);
        textoISBN = new JTextField(20);
        textoPrestadoA = new JTextField(45);
        textoPrestadoA.setEditable(false);
        
        botonAceptar = new JButton("Modificar");
        botonDeshacer= new JButton("Valores inicio");
        botonDeshacer.setActionCommand("Deshacer");
        botonCancelar = new JButton("Volver");
        botonDevolver = new JButton("Devolver");
        botonTomar = new JButton("Tomar");
      
        this.tituloInicio = titulo;
        this.autorInicio = autor;
        this.ISBNInicio = isbn;
        this.idLibro = idLibro;
     
        this.usuario = usuario;
        textoPrestadoA.setText(this.usuario);
             
        this.initComponents();
    }
    
    public void initComponents()
    {
        setSize(500,400);
        setLocationRelativeTo(null);
        setTitle("Modificar Libro");
        jpanel.setLayout(null);
        
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        labelTitulo.setBounds(20, 45, 75, 23);
        labelAutor.setBounds(20, 95, 75, 23);
        labelISBN.setBounds(20, 145, 75, 23);
        labelPrestadoA.setBounds(20, 190, 75, 23);
        
 
        textoTitulo.setBounds(80, 45, 250, 23);
        textoAutor.setBounds(80, 95, 250, 23);
        textoISBN.setBounds(80, 145, 150, 23);
        textoPrestadoA.setBounds(95, 190, 150, 23);
        
        //inicializamos los campos de texto con los valores de entrada pasados en el constructor
        
        textoTitulo.setText(tituloInicio);
        textoAutor.setText(autorInicio);
        textoISBN.setText(ISBNInicio );
        
           
        //posiciones de los botones
        botonAceptar.setBounds(10, 250, 90, 23);
        botonTomar.setBounds(100, 250, 80, 23);
        botonDevolver.setBounds(180, 250, 90, 23);
        botonDeshacer.setBounds(270, 250, 120, 23);
        botonCancelar.setBounds(390, 250, 80, 23);
        
        //añadimos los elementos al jpanel para que se vean por pantalla
        
        //label  
        jpanel.add(labelTitulo);
        jpanel.add(labelAutor);
        jpanel.add(labelISBN);
        jpanel.add(labelPrestadoA);
        
        //jtext
        jpanel.add(textoTitulo);
        jpanel.add(textoAutor);
        jpanel.add(textoISBN);
        jpanel.add(textoPrestadoA);
        
        //jbutton
        jpanel.add(botonAceptar);
        jpanel.add(botonTomar);
        jpanel.add(botonDevolver);
        jpanel.add(botonDeshacer);
        jpanel.add(botonCancelar);
        
        //añadimos los listener a los botones
        
        ControladorPantallaModificarLibro controlador = new ControladorPantallaModificarLibro(this);
        botonAceptar.addActionListener(controlador);
        botonDevolver.addActionListener(controlador);
        botonTomar.addActionListener(controlador);
        botonDeshacer.addActionListener(controlador);
        botonCancelar.addActionListener(controlador);
          
        this.setContentPane(this.jpanel);
        setVisible(true);
    }
    
}
