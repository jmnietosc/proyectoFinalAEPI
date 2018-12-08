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
public class PantallaAltaLibro extends JFrame
{
    JPanel jpanel;
    JLabel labelId,labelTitulo,labelAutor,labelISBN;
    JTextField textoId,textoTitulo,textoAutor,textoISBN;
    JButton botonAceptar,botonLimpiar,botonCancelar;
    
    public PantallaAltaLibro()
    {
        jpanel = new JPanel();
        
        labelId = new JLabel("IdLibro(opcional)");
        labelTitulo = new JLabel("Titulo");
        labelAutor = new JLabel("Autor");
        labelISBN = new JLabel("ISBN");
        
        textoId = new JTextField(10);
        textoTitulo = new JTextField(30);
        textoAutor = new JTextField(45);
        textoISBN = new JTextField(20);
        
        botonAceptar = new JButton("Aceptar");
        botonLimpiar = new JButton("Limpiar");
        botonCancelar = new JButton("Volver");
        
        this.initComponents();
    }
    
    public void initComponents()
    {
        setSize(400,300);
        setLocationRelativeTo(null);
        setTitle("Alta de Libro");
        jpanel.setLayout(null);
        
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        //posiciones de las label
        labelId.setBounds(20,20, 100, 23);
        labelTitulo.setBounds(20, 60, 75, 23);
        labelAutor.setBounds(20, 100, 75, 23);
        labelISBN.setBounds(20, 150, 75, 23);
        
        //posiciones de los jtext
        textoId.setBounds(130, 20, 50, 23);
        textoTitulo.setBounds(80, 60, 250, 23);
        textoAutor.setBounds(80, 100, 250, 23);
        textoISBN.setBounds(80, 150, 150, 23);
        
        //posiciones de los botones
        botonAceptar.setBounds(40, 200, 80, 23);
        botonLimpiar.setBounds(140, 200, 80, 23);
        botonCancelar.setBounds(240, 200, 80, 23);
        
        //añadimos los elementos al jpanel para que se vean por pantalla
        
        //label
        jpanel.add(labelId);
        jpanel.add(labelTitulo);
        jpanel.add(labelAutor);
        jpanel.add(labelISBN);
        
        //jtext
        jpanel.add(textoId);
        jpanel.add(textoTitulo);
        jpanel.add(textoAutor);
        jpanel.add(textoISBN);
        
        //jbutton
        jpanel.add(botonAceptar);
        jpanel.add(botonLimpiar);
        jpanel.add(botonCancelar);
        
        //añadimos los listener a los botones
        ControladorPantallaAltaLibro controlador = new ControladorPantallaAltaLibro(this);
        botonAceptar.addActionListener(controlador);
        botonLimpiar.addActionListener(controlador);
        botonCancelar.addActionListener(controlador);
        textoId.addKeyListener(controlador);
        
        this.setContentPane(this.jpanel);
        setVisible(true);
    }
}
