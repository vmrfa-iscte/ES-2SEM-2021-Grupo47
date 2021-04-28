package detection;


import java.util.ArrayList;
import java.util.HashMap;

import classes.HasCodeSmell;
import classes.MethodMetrics;
import classes.Rule;
import gui.mainGUI;

public class EvaluateAndDetect {
	private static final String IS_LONG_METHOD_DETECTION = "IsLong Method Detection";
	private static final String IS_GOD_CLASS_DETECTION = "IsGod Class Detection";
	private ArrayList<MethodMetrics> actualmetrics;
	private DetectionChooser chooser = new DetectionChooser();

	public ArrayList<MethodMetrics> getActualmetrics() {
		return actualmetrics;
	}

	public void setActualmetrics(ArrayList<MethodMetrics> actualmetrics) {
		this.actualmetrics = actualmetrics;
	}

	// Atrvés dos métodos escolhidos para a regra vai escolher a avaliação adequada
	public HashMap<String,ArrayList<HasCodeSmell>> evaluationChooser(Rule thisCurrentRule) {
		if (thisCurrentRule.getMethod1().equals(gui.mainGUI.LOC_METHOD))
			return evaluateLocMethod(thisCurrentRule);
		else if (isWMCAndNOM(thisCurrentRule))
			return evaluateGodClassWithWMC_NOM(thisCurrentRule);
		else if (isWMCAndLOC(thisCurrentRule))
			return evaluateGodClassWithWMC_LOC(thisCurrentRule);
		else if (isNOMAndLOC(thisCurrentRule))
			return evaluateGodClassWithNOM_LOC(thisCurrentRule);
		else if (isGodClass(thisCurrentRule))
			return evaluateGodClassWithWMC_NOM_LOC(thisCurrentRule);
		return null;
	}

	public boolean isGodClass(Rule thisCurrentRule) {
		return thisCurrentRule.getMethod1().equals(gui.mainGUI.WMC_CLASS) && thisCurrentRule.getMethod2().equals(gui.mainGUI.NOM_CLASS) && thisCurrentRule.getMethod3().contains("a");
	}

	public boolean isNOMAndLOC(Rule thisCurrentRule) {
		return thisCurrentRule.getMethod1().equals(gui.mainGUI.NOM_CLASS) && thisCurrentRule.getMethod2().equals(gui.mainGUI.LOC_CLASS) && !thisCurrentRule.getMethod3().contains("a");
	}

	private boolean isWMCAndLOC(Rule thisCurrentRule) {
		return thisCurrentRule.getMethod1().equals(gui.mainGUI.WMC_CLASS) && thisCurrentRule.getMethod2().equals(gui.mainGUI.LOC_CLASS) 
				&& !thisCurrentRule.getMethod3().contains("a");
	}

	private boolean isWMCAndNOM(Rule thisCurrentRule) {
		return thisCurrentRule.getMethod1().equals(gui.mainGUI.WMC_CLASS) && thisCurrentRule.getMethod2().equals(gui.mainGUI.NOM_CLASS)
				&& !thisCurrentRule.getMethod3().contains("a");
	}

	// Os seguintes métodos escolhem a deteção adequada perante a regra escolhida
	public HashMap<String,ArrayList<HasCodeSmell>> evaluateLocMethod(Rule ruleReceived) {
		HashMap<String, ArrayList<HasCodeSmell>> nameAndResults = new HashMap<String,ArrayList<HasCodeSmell>>();
		ArrayList<HasCodeSmell> hasCodeSmellResult = chooser.chooseDetectionLocMethod(ruleReceived, actualmetrics);
		nameAndResults.put(IS_LONG_METHOD_DETECTION,hasCodeSmellResult);
		return nameAndResults;
	}

	public HashMap<String, ArrayList<HasCodeSmell>> evaluateGodClassWithWMC_NOM(Rule ruleReceived) {
		HashMap<String, ArrayList<HasCodeSmell>> nameAndResults = new HashMap<String,ArrayList<HasCodeSmell>>();
		ArrayList<HasCodeSmell> hasCodeSmellResult = chooser.chooseDetectionWMC_NOM(ruleReceived, actualmetrics);
		nameAndResults.put(IS_GOD_CLASS_DETECTION,hasCodeSmellResult);
		return nameAndResults;
	}

	public HashMap<String, ArrayList<HasCodeSmell>> evaluateGodClassWithWMC_LOC(Rule ruleReceived) {
		HashMap<String, ArrayList<HasCodeSmell>> nameAndResults = new HashMap<String,ArrayList<HasCodeSmell>>();
		ArrayList<HasCodeSmell> hasCodeSmellResult = chooser.chooseDetectionWMC_LOC(ruleReceived, actualmetrics);
		nameAndResults.put(IS_GOD_CLASS_DETECTION,hasCodeSmellResult);
		return nameAndResults;
	}

	public HashMap<String, ArrayList<HasCodeSmell>> evaluateGodClassWithNOM_LOC(Rule ruleReceived) {
		HashMap<String, ArrayList<HasCodeSmell>> nameAndResults = new HashMap<String,ArrayList<HasCodeSmell>>();
		ArrayList<HasCodeSmell> hasCodeSmellResult = chooser.chooseDetectionNOM_LOC(ruleReceived, actualmetrics);
		nameAndResults.put(IS_GOD_CLASS_DETECTION,hasCodeSmellResult);
		return nameAndResults;
	}

	public HashMap<String, ArrayList<HasCodeSmell>> evaluateGodClassWithWMC_NOM_LOC(Rule ruleReceived) {
		HashMap<String, ArrayList<HasCodeSmell>> nameAndResults = new HashMap<String,ArrayList<HasCodeSmell>>();
		ArrayList<HasCodeSmell> hasCodeSmellResult = chooser.chooseDetectionWMC_NOM_LOC(ruleReceived, actualmetrics);
		nameAndResults.put(IS_GOD_CLASS_DETECTION,hasCodeSmellResult);
		return nameAndResults;
	}
}