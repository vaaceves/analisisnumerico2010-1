package co.edu.eafit.analisisnumerico.framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InterfaceAddress;
import java.util.Vector;

import co.edu.eafit.analisisnumerico.GUI.MetodoGUI1;
import co.edu.eafit.analisisnumerico.GUI.MetodoGUI2;

/**
 * Esta clase controla la interfaz dinamica numero 1
 * @author Sebastian
 *
 */
public class GestorInterfaz2 {
	static MetodoGUI2 interfaz = new MetodoGUI2();
	private static final String TEXTOINICIO="";
	private static final String TEXTOFIN=":";
	int numVariables=0;
	int numFunciones=0;

	public static void main(String[] args){
		GestorInterfaz2 gestor = new GestorInterfaz2();
		gestor.interfaz.setVisible(true);
	}

	public static void ejecutarMetodo(int metodo) {
		switch(metodo){
			case Constantes.GAUSSSIMPLE:
				GestorMetodos.ejecutarSistemaEcuacion(Constantes.GAUSSSIMPLE, "Eliminación Gaussiana Simple", interfaz.getTabla());
				break;
			case Constantes.PIVOTEOPARCIAL:
				GestorMetodos.ejecutarSistemaEcuacion(Constantes.PIVOTEOPARCIAL, "Pivoteo Parcial", interfaz.getTabla());
				break;
			case Constantes.PIVOTEOTOTAL:
				GestorMetodos.ejecutarSistemaEcuacion(Constantes.PIVOTEOTOTAL, "Pivoteo Total", interfaz.getTabla());
				break;				
			case Constantes.LUSIMPLE:
				GestorMetodos.ejecutarSistemaEcuacion(Constantes.LUSIMPLE, "LU Simple", interfaz.getTabla());
				break;				
		}
	}

}

