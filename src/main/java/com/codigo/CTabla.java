package com.codigo;

import lombok.Getter;
import lombok.Setter;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

@Getter
@Setter
public class CTabla {

    String id;
    String desc;
    String estReg;
    String nombColumId;
    String nombColumDesc;
    String nombColumEstReg;

    public void Insertar(String nombTabla , JTextField desc) {
        setDesc(desc.getText());

        CConexion conexion = new CConexion();

        String getTablas = "SELECT STRING_AGG(COLUMN_NAME, ',') AS columnas " + "FROM information_schema.columns " + "WHERE table_schema = 'public' " + "AND table_name = '" + nombTabla + "';";
        String[] datos = new String[5];
        Statement st;
        try {
            st = conexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(getTablas);
            String columns = "";
            if (rs.next()) {
                columns = rs.getString("columnas");
            }
            datos = columns.split(",");

        } catch (Exception e) {
            e.printStackTrace();
        }

        String consulta = "INSERT INTO " + nombTabla + " (" + datos[1] + "," +datos[2] +") VALUES (?, 'A');";

        try {
            CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, desc.getText());
            cs.execute();

            JOptionPane.showMessageDialog(null, "Se insertó correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se insertó correctamente: " + e.toString());
        }


    }

    public void Mostrar(JTable tabla, String nomTabla) {
        CConexion conexion = new CConexion();

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        TableRowSorter<TableModel> OrdTabla = new TableRowSorter<TableModel>(modelo);
        tabla.setRowSorter(OrdTabla);

        String consulta = "SELECT * FROM " + nomTabla + ";";

        modelo.addColumn("Codigo");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Estado");

        String[] datos = new String[3];
        Statement st;
        try {
            st = conexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(consulta);

            while (rs.next()) {
                if(rs.getString(3).equals("*")){
                    continue;
                }
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo mostrar correctamente " + e.toString());
        }
    }

    public void Seleccionar(JTable tabla, JTextField id, JTextField desc, JTextField estReg) {
        try {
            int fila = tabla.getSelectedRow();

            if (fila >= 0) {
                id.setText(tabla.getValueAt(fila, 0).toString());
                desc.setText(tabla.getValueAt(fila, 1).toString());
                estReg.setText(tabla.getValueAt(fila, 2).toString());
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo seleccionar la fila ");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo seleccionar la fila " + e.toString());
        }
    }

    public void Modificar(String nomTabla, JTextField id, JTextField desc, JTextField estReg) {
        CConexion conexion = new CConexion();
        setId(id.getText());
        setDesc(desc.getText());
        setEstReg(estReg.getText());

        String getTablas = "SELECT STRING_AGG(COLUMN_NAME, ',') AS columnas " + "FROM information_schema.columns " + "WHERE table_schema = 'public' " + "AND table_name = '" + nomTabla + "';";
        String[] datos = new String[5];
        Statement st;
        try {
            st = conexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(getTablas);
            String columns = "";
            if (rs.next()) {
                columns = rs.getString("columnas");
            }
            datos = columns.split(",");

        } catch (Exception e) {
            e.printStackTrace();
        }
        String consulta = "UPDATE " + nomTabla + " SET " + datos[1] + " = ? WHERE " + datos[0] + " = ?;";
        try {
            CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, desc.getText());
            cs.setInt(2, Integer.parseInt(id.getText()));
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se modificó correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se modificó correctamente: " + e.toString());
        }

    }

    public void Eliminar(String nomTabla, JTextField id, JTextField desc, JTextField estReg) {
        setEstReg(id.getText());
        setDesc(desc.getText());
        setEstReg(estReg.getText());
        CConexion conexion = new CConexion();
        String getTablas = "SELECT STRING_AGG(COLUMN_NAME, ',') AS columnas " + "FROM information_schema.columns " + "WHERE table_schema = 'public' " + "AND table_name = '" + nomTabla + "';";
        String[] datos = new String[3];
        Statement st;
        try {
            st = conexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(getTablas);
            String columns = "";
            if (rs.next()) {
                columns = rs.getString("columnas");
            }
            datos = columns.split(",");

        } catch (Exception e) {
            e.printStackTrace();
        }

        String consulta = "UPDATE " + nomTabla + " SET " + datos[2] + " = '*' WHERE " + datos[0] + " = ?;";
        try {
            CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, Integer.parseInt(id.getText()));
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se eliminó correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se eliminó correctamente: " + e.toString());
        }

    }

    public void Reactivar(String nomTabla, JTextField id, JTextField desc, JTextField estReg) {
        setEstReg(id.getText());
        setDesc(desc.getText());
        setEstReg(estReg.getText());
        CConexion conexion = new CConexion();
        String getTablas = "SELECT STRING_AGG(COLUMN_NAME, ',') AS columnas " + "FROM information_schema.columns " + "WHERE table_schema = 'public' " + "AND table_name = '" + nomTabla + "';";
        String[] datos = new String[3];
        Statement st;
        try {
            st = conexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(getTablas);
            String columns = "";
            if (rs.next()) {
                columns = rs.getString("columnas");
            }
            datos = columns.split(",");

        } catch (Exception e) {
            e.printStackTrace();
        }

        String consulta = "UPDATE " + nomTabla + " SET " + datos[2] + " = 'A' WHERE " + datos[0] + " = ?;";
        try {
            CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, Integer.parseInt(id.getText()));
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se reactivó correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se reactivó correctamente: " + e.toString());
        }

    }

    public void Inactivar(String nomTabla, JTextField id, JTextField desc, JTextField estReg) {
        setEstReg(id.getText());
        setDesc(desc.getText());
        setEstReg(estReg.getText());
        CConexion conexion = new CConexion();
        String getTablas = "SELECT STRING_AGG(COLUMN_NAME, ',') AS columnas " + "FROM information_schema.columns " + "WHERE table_schema = 'public' " + "AND table_name = '" + nomTabla + "';";
        String[] datos = new String[3];
        Statement st;
        try {
            st = conexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(getTablas);
            String columns = "";
            if (rs.next()) {
                columns = rs.getString("columnas");
            }
            datos = columns.split(",");

        } catch (Exception e) {
            e.printStackTrace();
        }

        String consulta = "UPDATE " + nomTabla + " SET " + datos[2] + " = 'I' WHERE " + datos[0] + " = ?;";
        try {
            CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, Integer.parseInt(id.getText()));
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se eliminó correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se eliminó correctamente: " + e.toString());
        }

    }
}
