/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author artairg
 */
public class Conexion {
    
    
    private static Connection con;
    private static DriverManager dm;
    
    public static void CrearBD() {
        String url="jdbc:mysql://192.168.56.101:3306/ejemplo";
        String username="root";
        String password="root";
        try {
           con = DriverManager.getConnection(url, username, password);
           con.setAutoCommit(true);
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear la BD");
            System.err.println(e);
        }
    }
    
    public static Connection getBD() {
        return con;
    }
    
    public static void CerrarBD() {
        //Los cerramos en orden inverso a como se han creado
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la BD");
                System.err.println(e);
            }
        }
    }


}
