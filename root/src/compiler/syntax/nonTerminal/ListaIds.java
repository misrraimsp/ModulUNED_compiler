package compiler.syntax.nonTerminal;

import java.util.ArrayList;

public class ListaIds extends NonTerminal {

	private ArrayList<String> list;
	
	public ListaIds (String id) {
		list = new ArrayList<String>();
		addId(id);
	}
	
	public void addId (String id) {
		list.add(id);
	}
	
	public void addId (int index, String id) {
		list.add(index, id);
	}
	
	public int getSize() {
		return list.size();
	}
	
	public String getId(int index) {
		if (index < 0 || index > (list.size() - 1)) return null;
		return list.get(index);
	}
}
