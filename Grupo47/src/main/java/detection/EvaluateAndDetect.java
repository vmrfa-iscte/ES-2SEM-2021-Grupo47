package detection;


import java.util.ArrayList;

import classes.HasCodeSmell;
import classes.MethodMetrics;
import classes.Rule;
import gui.mainGUI;

public class EvaluateAndDetect {
	private ArrayList<MethodMetrics> actualmetrics;
	private DetectionChooser chooser = new DetectionChooser();

	public ArrayList<MethodMetrics> getActualmetrics() {
		return actualmetrics;
	}

	public void setActualmetrics(ArrayList<MethodMetrics> actualmetrics) {
		this.actualmetrics = actualmetrics;
	}

	// Atrvés dos métodos escolhidos para a regra vai escolher a avaliação adequada
	public void evaluationChooser(String method1, String method2, Rule thisCurrentRule, mainGUI mainGUI) {
		if (method1.equals(gui.mainGUI.LOC_METHOD))
			evaluateLocMethod(thisCurrentRule, mainGUI);
		else if (isWMCAndNOM(method1, method2, thisCurrentRule))
			evaluateGodClassWithWMC_NOM(thisCurrentRule, mainGUI);
		else if (isWMCAndLOC(method1, method2, thisCurrentRule))
			evaluateGodClassWithWMC_LOC(thisCurrentRule, mainGUI);
		else if (isNOMAndLOC(method1, method2, thisCurrentRule))
			evaluateGodClassWithNOM_LOC(thisCurrentRule, mainGUI);
		else if (isGodClass(method1, thisCurrentRule))
			evaluateGodClassWithWMC_NOM_LOC(thisCurrentRule, mainGUI);
	}

	private boolean isGodClass(String method1, Rule thisCurrentRule) {
		return method1.equals(gui.mainGUI.WMC_CLASS) && thisCurrentRule.getMethod3().contains("a");
	}

	private boolean isNOMAndLOC(String method1, String method2, Rule thisCurrentRule) {
		return method1.equals(gui.mainGUI.NOM_CLASS) && method2.equals(gui.mainGUI.LOC_CLASS)
				&& !thisCurrentRule.getMethod3().contains("a");
	}

	private boolean isWMCAndLOC(String method1, String method2, Rule thisCurrentRule) {
		return method1.equals(gui.mainGUI.WMC_CLASS) && method2.equals(gui.mainGUI.LOC_CLASS)
				&& !thisCurrentRule.getMethod3().contains("a");
	}

	private boolean isWMCAndNOM(String method1, String method2, Rule thisCurrentRule) {
		return method1.equals(gui.mainGUI.WMC_CLASS) && method2.equals(gui.mainGUI.NOM_CLASS)
				&& !thisCurrentRule.getMethod3().contains("a");
	}

	// Os seguintes métodos escolhem a deteção adequada perante a regra escolhida
	public void evaluateLocMethod(Rule ruleReceived, mainGUI mainGUI) {
		ArrayList<HasCodeSmell> hasCodeSmellResult = chooser.chooseDetectionLocMethod(ruleReceived, actualmetrics);
		mainGUI.createSecondaryGUI("IsLong Method Detection", hasCodeSmellResult);
	}

	public void evaluateGodClassWithWMC_NOM(Rule ruleReceived, mainGUI mainGUI) {
		ArrayList<HasCodeSmell> hasCodeSmellResult = chooser.chooseDetectionWMC_NOM(ruleReceived, actualmetrics);
		mainGUI.createSecondaryGUI("IsGod Class Detection", hasCodeSmellResult);
	}

	public void evaluateGodClassWithWMC_LOC(Rule ruleReceived, mainGUI mainGUI) {
		ArrayList<HasCodeSmell> hasCodeSmellResult = chooser.chooseDetectionWMC_LOC(ruleReceived, actualmetrics);
		mainGUI.createSecondaryGUI("IsGod Class Detection", hasCodeSmellResult);
	}

	public void evaluateGodClassWithNOM_LOC(Rule ruleReceived, mainGUI mainGUI) {
		ArrayList<HasCodeSmell> hasCodeSmellResult = chooser.chooseDetectionNOM_LOC(ruleReceived, actualmetrics);
		mainGUI.createSecondaryGUI("IsGod Class Detection", hasCodeSmellResult);
	}

	public void evaluateGodClassWithWMC_NOM_LOC(Rule ruleReceived, mainGUI mainGUI) {
		ArrayList<HasCodeSmell> hasCodeSmellResult = chooser.chooseDetectionWMC_NOM_LOC(ruleReceived, actualmetrics);
		mainGUI.createSecondaryGUI("IsGod Class Detection", hasCodeSmellResult);
	}
}