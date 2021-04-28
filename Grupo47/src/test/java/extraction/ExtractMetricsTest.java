package extraction;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.sun.javafx.collections.ArrayListenerHelper;

import classes.MethodIdentity;
import classes.MethodMetrics;
import classes.Metrics;

class ExtractMetricsTest {

	protected ExtractMetrics extractClass,extractEnum;
	protected File classFile,enumFile;
	protected ArrayList<MethodMetrics> auxiliaryClass,auxiliaryEnum;
	
	@Test
	void testExtractMetrics() {
		classFile = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10(1)\\jasml_0.10\\src\\com\\jasml\\helper\\Util.java");
		enumFile = new File("C:\\Users\\rui.fontoura\\OneDrive - ISCTE-IUL\\Documents\\eclipseenterprise-workspace\\SID2021\\src\\packageSID\\TipoAlerta.java");
		extractClass = new ExtractMetrics(classFile);
		extractEnum = new ExtractMetrics(enumFile);
		assertNotNull(extractClass);
		assertNotNull(extractEnum);
	}

	@Test
	void testDoExtractMetrics() throws FileNotFoundException {
		auxiliaryClass = new ArrayList<MethodMetrics>();
		auxiliaryEnum = new ArrayList<MethodMetrics>();
		
		classFile = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10(1)\\jasml_0.10\\src\\com\\jasml\\classes\\Attribute.java");
		enumFile = new File("C:\\Users\\rui.fontoura\\OneDrive - ISCTE-IUL\\Documents\\eclipseenterprise-workspace\\SID2021\\src\\packageSID\\TipoAlerta.java");
		
		extractClass = new ExtractMetrics(classFile);
		extractEnum = new ExtractMetrics(enumFile);
		
		ArrayList<MethodMetrics> resultsClass = extractClass.doExtractMetrics(auxiliaryClass, 0);
		ArrayList<MethodMetrics> resultsEnum = extractEnum.doExtractMetrics(auxiliaryEnum, 0);
		
		assertNotNull(resultsClass);
		assertNotNull(resultsEnum);
		
		ArrayList<MethodMetrics> toCompareWithClass = new ArrayList<>();
		ArrayList<MethodMetrics> toCompareWithEnum = new ArrayList<>();
		
		String method_name = "Attribute(byte,int)";
		String class_name = "Attribute";
		String packageName = "com.jasml.classes";
		int method_id = 1;
		MethodIdentity identity = new MethodIdentity(method_name, class_name, packageName, method_id);
		int NOM_class = 2;
		int LOC_class = 32;
		int WMC_class = 4;
		int LOC_method = 5;
		int CYCLO_method = 2;
		Metrics metrics = new Metrics(LOC_method, LOC_class, CYCLO_method, NOM_class, WMC_class);
		MethodMetrics methodAttribute1 = new MethodMetrics(identity,metrics);
		toCompareWithClass.add(methodAttribute1);
		
		method_name = "Attribute(int,int,byte[])";
		class_name = "Attribute";
		packageName = "com.jasml.classes";
		method_id = 1;
		MethodIdentity identitySecond = new MethodIdentity(method_name,class_name,packageName,method_id);
		MethodMetrics methodAttribute2 = new MethodMetrics(identitySecond,metrics);
		toCompareWithClass.add(methodAttribute2);
		
		method_name = "TipoAlerta(int)";
		class_name = "TipoAlerta";
		packageName = "packageSID";
		method_id = 1;
		MethodIdentity enumMethod1 = new MethodIdentity(method_name,class_name,packageName,method_id);
		NOM_class = 2;
		LOC_class = 18;
		WMC_class = 4;
		LOC_method = 3;
		CYCLO_method = 1;
		Metrics metricsEnum1 = new Metrics(LOC_method, LOC_class, CYCLO_method, NOM_class, WMC_class);
		MethodMetrics enumFirstMethod = new MethodMetrics(enumMethod1,metricsEnum1);
		toCompareWithEnum.add(enumFirstMethod);
		
		method_name = "getValue(TipoAlerta)";
		method_id = 2;
		MethodIdentity enumMethod2 = new MethodIdentity(method_name,class_name,packageName,method_id);
		LOC_method = 6;
		CYCLO_method = 3;
		Metrics metricsEnum2 = new Metrics(LOC_method, LOC_class, CYCLO_method, NOM_class, WMC_class);
		MethodMetrics enumSecondMethod = new MethodMetrics(enumMethod2,metricsEnum2);
		toCompareWithEnum.add(enumSecondMethod);
		
		for(int i = 0; i<2;i++) {
			assertEquals(toCompareWithClass.get(i).toString(),resultsClass.get(i).toString());
			assertEquals(toCompareWithEnum.get(i).toString(),resultsEnum.get(i).toString());
		}
	}

	@Test
	void testGetCurrentMethodID() throws FileNotFoundException {
		auxiliaryClass = new ArrayList<MethodMetrics>();

		classFile = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10(1)\\jasml_0.10\\src\\com\\jasml\\helper\\Util.java");
		
		extractClass = new ExtractMetrics(classFile);
		
		extractClass.doExtractMetrics(auxiliaryClass, 0);
		
		assertEquals(24,extractClass.getCurrentMethodID());

	}

}
