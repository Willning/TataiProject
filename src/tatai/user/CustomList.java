package tatai.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import maths.Equation;

public class CustomList implements Serializable {
	
	private String listName;
	private ArrayList<Equation> equations = new ArrayList<Equation>();
	
	public CustomList(String listName) {
		this.listName = listName;
	}
	
	public void changeName(String listName) {
		this.listName = listName;
	}
	
	public void addEquation(Equation equation) {
		
		if (equationCanBeAdded(equation)) {
			equations.add(equation);
		}
	}
	
	public boolean equationCanBeAdded(Equation equation) {
		
		for (Equation checkEquation: equations) {
			if (equation.hasSameQuestion(checkEquation)) {
				return false;
			}
		}
		return true;
	}
	
	public void removeEquation(Equation equation) {
		equations.remove(equation);
	}
	
	public ArrayList<Equation> getEquations() {
		return equations;
	}
	
	public String getListName() {
		return listName;
	}
}
