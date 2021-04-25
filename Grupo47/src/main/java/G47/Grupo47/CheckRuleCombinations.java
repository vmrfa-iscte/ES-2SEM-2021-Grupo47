package G47.Grupo47;

public class CheckRuleCombinations {
	
	private int rule1_threshold;
	private int rule2_threshold;
	private int rule3_threshold;
	
	public CheckRuleCombinations(int rule1_threshold,int rule2_threshold, int rule3_threshold) {
		this.rule1_threshold = rule1_threshold;
		this.rule2_threshold = rule2_threshold;
		this.rule3_threshold = rule3_threshold;
	}
	
	public CheckRuleCombinations(int rule1_threshold,int rule2_threshold) {
		this.rule1_threshold = rule1_threshold;
		this.rule2_threshold = rule2_threshold;
	}

	public boolean isGodLGLAndAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodLGLAndOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodLGLOrAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodLGLOrOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserGreaterGreater_AndAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserGreaterGreater_AndOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserGreaterGreater_OrAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserGreaterGreater_OrOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserLesserGreater_AndAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserLesserGreater_AndOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserLesserGreater_OrAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserLesserGreater_OrOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterGreaterLesser_AndAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterGreaterLesser_AndOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterGreaterLesser_OrAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterGreaterLesser_OrOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterLesserGreater_AndAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterLesserGreater_AndOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterLesserGreater_OrAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterLesserGreater_OrOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterLesserLesser_AndAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterLesserLesser_AndOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterLesserLesser_OrAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterLesserLesser_OrOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserLesserLesser_AndAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserLesserLesser_AndOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserLesserLesser_OrAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold && methodWithMetrics.getLOC_class() < rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserLesserLesser_OrOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold || methodWithMetrics.getLOC_class() < rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterGreaterGreater_AndAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterGreaterGreater_AndOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterGreaterGreater_OrAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold && methodWithMetrics.getLOC_class() > rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterGreaterGreater_OrOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold || methodWithMetrics.getLOC_class() > rule3_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserLesserNOMLOC_And(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getNOM_class() < rule1_threshold && methodWithMetrics.getLOC_class() < rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserLesserNOMLOC_Or(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getNOM_class() < rule1_threshold || methodWithMetrics.getLOC_class() < rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserGreaterNOMLOC_And(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getNOM_class() < rule1_threshold && methodWithMetrics.getLOC_class() > rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserGreaterNOMLOC_Or(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getNOM_class() < rule1_threshold || methodWithMetrics.getLOC_class() > rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterLesserNOMLOC_And(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getNOM_class() > rule1_threshold && methodWithMetrics.getLOC_class() < rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterLesserNOMLOC_Or(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getNOM_class() > rule1_threshold || methodWithMetrics.getLOC_class() < rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterGreaterNOMLOC_And(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getNOM_class() > rule1_threshold && methodWithMetrics.getLOC_class() > rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterGreaterNOMLOC_Or(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getNOM_class() > rule1_threshold || methodWithMetrics.getLOC_class() > rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserGreaterLOC_And(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getLOC_class() > rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserGreaterLOC_Or(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getLOC_class() > rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserLesserLOC_And(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getLOC_class() < rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserLesserLOC_Or(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getLOC_class() < rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterLesserLOC_And(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getLOC_class() < rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterLesserLOC_Or(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getLOC_class() < rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterGreaterLOC_And(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getLOC_class() > rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterGreaterLOC_Or(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getLOC_class() > rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserGreaterNOM_And(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserGreaterNOM_Or(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserLesserNOM_And(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodLesserLesserNOM_Or(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() < rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterLesserWMC_And(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() < rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterLesserNOM_Or(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() < rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterGreaterNOM_And(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold && methodWithMetrics.getNOM_class() > rule2_threshold) return true;
		else return false;
	}
	
	public boolean isGodGreaterGreaterNOM_Or(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getWMC_class() > rule1_threshold || methodWithMetrics.getNOM_class() > rule2_threshold) return true;
		else return false;
	}
	
	public boolean isLongLesserLesserAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getLOC_method() < rule1_threshold && methodWithMetrics.getCYCLO_method() < rule2_threshold) return true;
		else return false;
	}
	
	public boolean isLongLesserLesserOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getLOC_method() < rule1_threshold || methodWithMetrics.getCYCLO_method() < rule2_threshold) return true;
		else return false;
	}
	
	public boolean isLongLesserGreaterAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getLOC_method() < rule1_threshold && methodWithMetrics.getCYCLO_method() > rule2_threshold) return true;
		else return false;
	}
	
	public boolean isLongLesserGreaterOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getLOC_method() < rule1_threshold || methodWithMetrics.getCYCLO_method() > rule2_threshold) return true;
		else return false;
	}
	
	public boolean isLongGGAnd(MethodMetrics methodWithMetrics) {
		System.out.println("Corri o Greater Greater AND para LONG_METHOD");
		if (methodWithMetrics.getLOC_method() > rule1_threshold && methodWithMetrics.getCYCLO_method() > rule2_threshold) {
			System.out.println("TRUE: "+methodWithMetrics.getLOC_method()+">"+ rule1_threshold +"&&"+ methodWithMetrics.getCYCLO_method()+ ">"+ rule2_threshold);
			return true;
		}
		else {
			System.out.println("FALSE: "+methodWithMetrics.getLOC_method()+">"+ rule1_threshold +"&&"+ methodWithMetrics.getCYCLO_method()+ ">"+ rule2_threshold);
			return false;
		}
	}
	
	public boolean isLongGGOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getLOC_method() > rule1_threshold || methodWithMetrics.getCYCLO_method() > rule2_threshold) return true;
		else return false;
	}
	
	public boolean isLongGLAnd(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getLOC_method() > rule1_threshold && methodWithMetrics.getCYCLO_method() < rule2_threshold) return true;
		else return false;
	}
	
	public boolean isLongGLOr(MethodMetrics methodWithMetrics) {
		if (methodWithMetrics.getLOC_method() > rule1_threshold || methodWithMetrics.getCYCLO_method() < rule2_threshold) return true;
		else return false;
	}
	
}
