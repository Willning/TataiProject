package tatai.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import maths.Equation;

public class CustomList implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String listName;
	private List<Equation> equations = new ArrayList<Equation>();
	
	public CustomList(String listName) {
		this.listName = listName;
	}
	
	public void changeName(String listName) {
		this.listName = listName;
	}
	
	public void addEquation(Equation equation) {
		equations.add(equation);
	}
	
	public void removeEquation(Equation equation) {
		equations.remove(equation);
	}
	
	public List<Equation> getEquations() {
		return equations;
	}
	
	public String getListName() {
		return listName;
	}
}
