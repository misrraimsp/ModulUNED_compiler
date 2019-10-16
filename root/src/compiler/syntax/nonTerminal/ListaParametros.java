package compiler.syntax.nonTerminal;

import java.util.ArrayList;

public class ListaParametros extends NonTerminal {
	
	private ArrayList<Parametro> list;
	
	/*
	 * Constructor
	 */
	public ListaParametros() {
		list = new ArrayList<Parametro>();
	}
	
	/*
	 * Constructor
	 */
	public ListaParametros(Parametro p) {
		list = new ArrayList<Parametro>();
		list.add(p);
	}
	
	/*
	 * Constructor
	 */
	public ListaParametros(ListaParametros lp) {
		list = new ArrayList<Parametro>(lp.getList());
	}

	public ArrayList<Parametro> getList() {
		return list;
	}
	
	public void addParameter(Parametro p) {
		list.add(p);
	}
	
	public void addParameter(int index, Parametro p) {
		list.add(index, p);
	}
	
	public Parametro getParameter(int index) {
		if (index < 0 || index > (list.size() - 1)) return null;
		return list.get(index);
	}
	
	public int getSize() {
		return list.size();
	}

}
