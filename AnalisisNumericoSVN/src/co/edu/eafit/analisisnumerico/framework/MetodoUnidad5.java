package co.edu.eafit.analisisnumerico.framework;

import org.nfunk.jep.JEP;

public abstract class MetodoUnidad5 implements IntegracionInterfaz{

	JEP parser;
	
	public void inicializarParser(JEP parser) {
		this.parser=parser;
	}
	
	public double f(double... valorVariables) throws AnalisisException{
		if(parser==null){
			throw new AnalisisException("no se ha inicializado el parser");
		}
		parser.setVarValue("x", valorVariables[0]);
		if(parser.hasError()){
			throw new AnalisisException("Error asignando el valor al parser");
		}
		return parser.getValue();
	}

}
