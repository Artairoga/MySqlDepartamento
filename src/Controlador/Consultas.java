/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.sql.*;

/**
 *
 * @author artairg
 */
public class Consultas {

    public static ResultSet VerEDepartamentos() throws SQLException {
        String consulta = "SELECT * FROM departamentos";
        Statement statement = Conexion.getBD().createStatement();
        ResultSet SetResultados = statement.executeQuery(consulta);
        return SetResultados;
    }

    public static ResultSet GetDepartamento(int ID) throws SQLException {
        String consulta = "SELECT * FROM departamentos WHERE dept_no=" + ID;
        Statement statement = Conexion.getBD().createStatement();
        ResultSet SetResultados = statement.executeQuery(consulta);
        return SetResultados;
    }

    public static ResultSet GetDepartamento(String Nombre) throws SQLException {
        String consulta = "SELECT * FROM departamentos WHERE dnombre=" + "'" + Nombre + "'";
        Statement statement = Conexion.getBD().createStatement();
        ResultSet SetResultados = statement.executeQuery(consulta);
        return SetResultados;
    }

    public static int UpdateDepartamento(int dept_id, String Nombre, String Localidad) throws SQLException {
        String consulta = "Update departamentos SET dnombre=" + "'" + Nombre + "'" + ",loc=" + "'" + Localidad + "'" + "  WHERE dept_no=" + dept_id;
        System.out.println(consulta);
        Statement statement = Conexion.getBD().createStatement();
        int Resultados = statement.executeUpdate(consulta);
        return Resultados;
    }

    public static int DeleteDepartamento(int dept_id) throws SQLException {
        ResultSet NumeroEmpleados = ContarEmplados(dept_id);/*Cuento los empleados que tiene ese departamento*/
        int i=0;
        if (!NumeroEmpleados.next()) {/*Si hay resultado es que ese departamento tiene algun empleado*/
            String consulta = "DELETE FROM departamentos WHERE dept_no=" + dept_id;
            Statement statement = Conexion.getBD().createStatement();
            int Resultados = statement.executeUpdate(consulta);
            return Resultados;/*Almaceno cuantas lineas fueron modificadas(es siempre 1)*/
        }else{/*Error el dept no puede ser borrado porque tiene empleados,muestro cuantos empleados*/
        i = NumeroEmpleados.getInt(1);
        System.out.println("El dept id " + dept_id + " no puede ser borrado,tiene " + i + " Empleados ");
        return 0;/*Si tiene empleados no lo puedo eliminar,no modifique ninguna liena*/
        }
    }

    public static void InsertDepartamento(int dept_no, String dnombre, String dloc) throws SQLException, UnsupportedOperationException {
        ResultSet Departamentos = GetDepartamento(dept_no);
        if (!Departamentos.next()) {
            String consulta = "INSERT INTO departamentos VALUES(" + dept_no + "," + "'" + dnombre + "'" + "," + "'" + dloc + "'" + ");";
            System.out.println(consulta);
            Statement statement = Conexion.getBD().createStatement();
            System.out.println("Numero de registros mod: " + statement.executeUpdate(consulta));
        } else {
            System.err.println("Key duplicada");
            throw new UnsupportedOperationException("Key Duplicada.");
        }
    }

    public static ResultSet ContarEmplados(int dept_no) throws SQLException {
        String consulta = "SELECT count(*) FROM empleados WHERE dept_no=" + dept_no + " group by dept_no";
        System.out.println(consulta);
        Statement statement = Conexion.getBD().createStatement();
        ResultSet numeroEmpleados = statement.executeQuery(consulta);
        return numeroEmpleados;
    }

    public static ResultSet GetEmpleado(String Apellido) throws SQLException, Exception {
        String consulta = "SELECT * FROM empleados WHERE apellido=" + "'" + Apellido + "'";
        Statement statement = Conexion.getBD().createStatement();
        ResultSet SetResultados = statement.executeQuery(consulta);
        return SetResultados;
    }

    public static void InsertEmpleado(int emp_no, String apellido, String director_ap, double Salario, String dept_no) throws SQLException, Exception {
        ResultSet Jefe = GetEmpleado(director_ap);
        ResultSet Departamentos = GetDepartamento(10);
        Integer id_jefe;
        Date fecha_alta = null;
        //director_no da el nombre,si esta vacio es nulo,sino tengo que hacer la query si esta no da nada es nulo,si esta da algo es es
        if (director_ap.isEmpty()) {//no tiene jefe
            id_jefe = null;
        } else {//Puede tener jefe
            if (Jefe.next()) {
                id_jefe = Jefe.getInt(1);
            } else {
                id_jefe = null;
            }
        }
        if (Departamentos.next()) {//Miro si existe el departamento
            String consulta = ("INSERT INTO empleados (emp_no, apellido, dir,fecha_alta,salario,dept_no)"
                    + "VALUES (" + emp_no + ","
                    + "'" + apellido + "'" + ","
                    + "'" + id_jefe + "'"
                    + "'" + fecha_alta + "'"
                    + "'" + Salario + "'"
                    + "'" + dept_no + "'" + ");");
            System.out.println(consulta);
        } else {//SI no devuelve ningun departamento no existe ese departamento
            System.err.println("No existe ese departamento");
        }

    }
    //SELECT count(*) FROM empleados WHERE dept_no=id a borrar si es 0 ese departamento no esta en uso y puede ser usado
}
