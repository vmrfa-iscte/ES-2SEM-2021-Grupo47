//package G47.Grupo47;
//
//import java.io.File;
//import java.util.Optional;
//
//import com.github.javaparser.JavaParser;
//import com.github.javaparser.ParseResult;
//import com.github.javaparser.ast.CompilationUnit;
//import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
//import com.github.javaparser.ast.body.ConstructorDeclaration;
//
//import junit.framework.TestCase;
//
//public class ExtractMetricsTest extends TestCase {
//	
//	protected File file;
//	protected String path;
//	protected ExtractMetrics extractM;
//	protected JavaParser jp;
//	protected ParseResult<CompilationUnit> cu;
//	protected CompilationUnit comp;
//	protected Optional<ClassOrInterfaceDeclaration> cid;
//	protected ConstructorDeclaration cd;
//	
//
//	protected void setUp() throws Exception {
//		super.setUp();
//		file = new File("C:\\Users\\rui.fontoura\\Downloads\\jasml_0.10");
//		path = "/src/com/jasml/compiler/GrammerException.java";
//		extractM = new ExtractMetrics(file,path);
//		jp  = new JavaParser();
//		cu = jp.parse(file);
//		comp = cu.getResult().get();
//		cid = comp.getClassByName("GrammerException");
//		cd = cid.get().getConstructorByParameterTypes(Integer.class,String.class).get();
//	}
//
//	protected void tearDown() throws Exception {
//		super.tearDown();
//	}
//	
//	public void testCreation() {
//		assertNotNull(extractM);
//	}
//	
//	public void testExtractMetrics() {
//		assertEquals(3,extractM.getLOC_method_Cons(cd));
//		
//	}
//	
//	public void testGetLOC_method_cons() {
//		
//	}
//
//}
