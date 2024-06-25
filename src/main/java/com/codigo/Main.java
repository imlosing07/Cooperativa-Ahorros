package com.codigo;

import com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme;

/**
 *
 * @author wbbtester
 */
public class Main {

    public static void main(String[] args) {
        /*
        CConexion objetoConexion = new CConexion();
        objetoConexion.estableceConexion();
        */
        try {
            FlatArcDarkOrangeIJTheme.setup();
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        InterfazCooperativa form = new InterfazCooperativa();
        form.setVisible(true);

    }
}
