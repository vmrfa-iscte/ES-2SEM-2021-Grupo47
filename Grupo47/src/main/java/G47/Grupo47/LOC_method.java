package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithMembers;

public class LOC_method {
	private String path;
	private File f;
	
	public LOC_method(File f, String path) {
		this.f = f;
		this.path = path;
		
	}

	public ArrayList<Metricas> extrair_LOC_method(File f,String path,ArrayList<Metricas> metricas) throws FileNotFoundException {
		String[] path2 = path.split("/");
		String packageClass = "com.jasm."+path2[1];
		JavaParser jp  = new JavaParser();
		ParseResult<CompilationUnit> cu = jp.parse(f);
		if(cu.isSuccessful()) {
			CompilationUnit comp = cu.getResult().get();
			metricas = getMethodsInnerClass(comp, f, metricas, packageClass);
			metricas = getConstructorsInnerClass(comp, f, metricas, packageClass);
			List<MethodDeclaration> method = DirExplorer.getMethodList(comp,f.getName().replace(".java", ""));
			List<ConstructorDeclaration> constructors = DirExplorer.getConstructors(comp, f.getName().replace(".java", ""));
			makeMetodosConstrutores(constructors,comp,metricas,packageClass,f.getName().replace(".java", ""));
			makeMetodosMethods(method,comp,metricas,packageClass,f.getName().replace(".java", ""));
		}
		
		return metricas;
	}
	
	
	public void makeMetodosConstrutores(List<ConstructorDeclaration> method,CompilationUnit comp,List<Metricas> metodos,String packageClass,String nameClass) {
		if(method.size() > 0) {
			for(ConstructorDeclaration md: method) {
				int sum = 0;
				
				for(Node noode: md.getChildNodes()) {
					if(noode.toString().startsWith("{") && noode.toString().endsWith("}")) {
						int tamanho = noode.getRange().map(range -> (range.end.line - range.begin.line)+1).orElse(0);
						sum = sum + tamanho;
					}
				}
				
				String nomeMetodoPar = getClassNameWithParameters(md.getNameAsString(),md.getParameters());
				
				Metricas m = new Metricas(nomeMetodoPar,nameClass,packageClass,sum);
				metodos.add(m);
			}
				
			}
		
	}
	
	public void makeMetodosMethods(List<MethodDeclaration> method,CompilationUnit comp,List<Metricas> metodos,String packageClass,String nameClass) {
		
		if(method.size() > 0) {
	
			for(MethodDeclaration md: method) {
				int sum = 0;
			
				for(Node noode: md.getChildNodes()) {
					if(noode.toString().startsWith("{") && noode.toString().endsWith("}")) {
						int tamanho = noode.getRange().map(range -> (range.end.line - range.begin.line)+1).orElse(0);
						sum = sum + tamanho;
					}
				}
				
				String nomeMetodoPar = getClassNameWithParameters(md.getNameAsString(),md.getParameters());
				Metricas m = new Metricas(nomeMetodoPar,nameClass,packageClass,sum);
//				System.out.println(m.toString());
				metodos.add(m);
				
			}
			}
	}
	

	
	public String getClassNameWithParameters(String ClassName,NodeList<Parameter> nodeList) {
		if(nodeList.size() == 0) {
			return ClassName+"()";
		}else {
			ClassName = ClassName+"(";
			for(Node n: nodeList) {
				String pars[] = n.toString().split(" ");
				String par = pars[0];
				if(nodeList.indexOf(n) == nodeList.size()-1) {
					ClassName = ClassName + par + ")";
				}else {
					ClassName = ClassName + par +",";
				}
				
			}
			return ClassName;
		}
	}

	
	public ArrayList<Metricas> getMethodsInnerClass(CompilationUnit cu,File f,ArrayList<Metricas> metricas,String packageClass){
		for(TypeDeclaration<?> type : cu.getTypes()) {
	        // first give all this java doc member
	        List<BodyDeclaration<?>> members = type.getMembers();

	        // check all member content
	        for(BodyDeclaration member : members) {
	            // if member state equal ClassOrInterfaceDeclaration, and you can identify it which is inner class
	            if(member.isClassOrInterfaceDeclaration()) {

	                if(member.asClassOrInterfaceDeclaration().getNameAsString() != f.getName().replace(".java", "")) {
	                	List<MethodDeclaration> met = member.asClassOrInterfaceDeclaration().getMethods();
	                	makeMetodosMethods(met,cu,metricas,packageClass,f.getName().replace(".java", "")+"."+member.asClassOrInterfaceDeclaration().getNameAsString());
	                	return metricas;
	                }
	                
	            }
	            if(member.isMethodDeclaration()) {
	            }
	            
	        }
	    }
	
		return metricas;
		
	}
	
	public ArrayList<Metricas> getConstructorsInnerClass(CompilationUnit cu,File f,ArrayList<Metricas> metricas,String packageClass){
		for(TypeDeclaration<?> type : cu.getTypes()) {
	        // first give all this java doc member
	        List<BodyDeclaration<?>> members = type.getMembers();
	        // check all member content
	        for(BodyDeclaration member : members) {
	            // if member state equal ClassOrInterfaceDeclaration, and you can identify it which is inner class
	            if(member.isClassOrInterfaceDeclaration()) {
//	            	System.out.println("Class(CONS): "+f.getName()+"  Member: "+member.asClassOrInterfaceDeclaration().getNameAsString());
	                if(member.asClassOrInterfaceDeclaration().getNameAsString() != f.getName().replace(".java", "")) {
	               
	                	List<ConstructorDeclaration> met = member.asClassOrInterfaceDeclaration().getConstructors();
	                	makeMetodosConstrutores(met,cu,metricas,packageClass,f.getName().replace(".java", "")+"."+member.asClassOrInterfaceDeclaration().getNameAsString());
	                	return metricas;
	                }
	            }
	        }
	    }
		return metricas;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public  ArrayList<Metricas> namesOfInnerClasses(CompilationUnit cu,File f,ArrayList<Metricas> metricas,String path) {
		String[] path2 = path.split("/");
		String packageClass = "com.jasm."+path2[1];
		List<Node> allNodes = cu.getChildNodes();
		String className = "";
		ArrayList<String> nomes = new ArrayList<>();
		for(Node n: allNodes) {
			if(n.toString().endsWith("}")) {
				for(Node nn: n.getChildNodes()) {
						if((nn.toString().startsWith("public class") || nn.toString().startsWith("private class") || nn.toString().startsWith("protected class") || nn.toString().startsWith("public static class") || nn.toString().startsWith("private static class") || nn.toString().startsWith("protected static class") ||nn.toString().startsWith("public interface") || nn.toString().startsWith("private interface") )&& nn.toString().endsWith("}")) {
//							System.out.println("FILE: "+f.getName()+" 0: "+nn.getChildNodes().get(0));
//							System.out.println("FILE: "+f.getName()+" 1: "+nn.getChildNodes().get(1));
//							System.out.println("FILE: "+f.getName()+" 2: "+nn.getChildNodes().get(2));
//							System.out.println("FILE: "+f.getName()+" NODE: "+nn);
							if(nn.getChildNodes().get(1).toString().contains("static")) {
								className = nn.getChildNodes().get(2).toString();
								nomes.add(className);
							}else {
								className = nn.getChildNodes().get(1).toString();
								nomes.add(className);
							}
//							System.out.println("NN: "+nn);
							for(Node nnn: nn.getChildNodes()) {
					
								if((nnn.toString().startsWith("private") || nnn.toString().startsWith("public")) && nnn.toString().endsWith("}")) {
//									System.out.println("NNN: "+nnn);
									int LOC = nnn.getRange().map(range -> (range.end.line - range.begin.line)+1).orElse(0);
									String[] separated = nnn.toString().replace("{", ";").split(";");
//									for(int i = 0; i<separated.length;i++) {
//										System.out.println("separated: "+ i+": "+separated[i]);
//										
//									}
									
									String[] separated2 = separated[0].replace("(", ";").split(";");
//									for(int i = 0; i<separated2.length;i++) {
//										System.out.println("separated2: "+ i+": "+separated2[i]);
//										
//									}
									String[] separated3 = separated2[0].split(" ");
//									for(int i = 0; i<separated3.length;i++) {
//										System.out.println("separated3: "+ i+": "+separated3[i]);
//										
//									}
									String nomeClass = "";
									
									if(separated2[1].startsWith(")")) {
										nomeClass = separated3[1] + "(" + separated2[1];
										System.out.println("Nome class: "+nomeClass+ "  LOC_method: "+LOC);
									}else {
										String[] separated4 = separated2[1].replace(")", "").split(",");
//										for(int i = 0; i<separated4.length;i++) {
//											System.out.println("separated4: "+ i+": "+separated4[i]);
//											
//										}
										ArrayList<String> intermediaria = new ArrayList<>();
										for(int i = 0; i<separated4.length;i++) {
											String nova;
											if(separated4[i].startsWith(" ")) {
												nova = separated4[i].replaceFirst(" ", "");
											}else {
												nova = separated4[i];
											}
//											System.out.println("separated4(2): "+i+": "+nova);
											intermediaria.add(nova);
										}
										ArrayList<String> parametros = new ArrayList<>();
										for(String s: intermediaria) {
											String[] temp = s.split(" ");
//											for(int i = 0; i < temp.length;i++) {
//												System.out.println("temp: "+i+": "+temp[i]);
//											}
											parametros.add(temp[0]);
										}
										String parametrosTotal = "(";
										int i = 0;
										for(String s: parametros) {
											if(i == parametros.size()-1) {
												parametrosTotal = parametrosTotal + s + ")";
											}else {
												parametrosTotal = parametrosTotal + s + ",";
											}
											i++;
											
										}
									
										nomeClass= separated3[1] + parametrosTotal;
//										System.out.println("Nome class: "+nomeClass+ "  LOC_method: "+LOC);
									}	
									Metricas m = new Metricas(nomeClass,f.getName().replace(".java", "")+"."+className,packageClass,LOC);
									metricas.add(m);
								}
								
							}
						}
					
				}
			}
		}
		return metricas;
	}
	

	

	
	
}