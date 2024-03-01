package com.javier.estudy.controller;

import com.javier.estudy.connection.DatabaseConnection;
import com.javier.estudy.connection.Implementacion;
import com.javier.estudy.view.TestConnectionView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ConnectionController implements ActionListener{
    
    private TestConnectionView connectionView;
    private DatabaseConnection db;
    private Implementacion impl;

    public ConnectionController(TestConnectionView connectionView, DatabaseConnection db) {
        this.connectionView = connectionView;
        this.db = db;
        impl = new Implementacion();
        connectionView.btnConectar.addActionListener(this);
    }
    
    public void start() {
        connectionView.setTitle("Test de conexión");
        connectionView.setLocationRelativeTo(null);
        connectionView.setResizable(false);
        connectionView.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connectionView.btnConectar) {
            if (DatabaseConnection.getInstance().getConnection() != null) {
                JOptionPane.showMessageDialog(null, "Conexión exitosa");
                impl.ejecutadorConsulta();
            }
        }
    }
    
    
}
