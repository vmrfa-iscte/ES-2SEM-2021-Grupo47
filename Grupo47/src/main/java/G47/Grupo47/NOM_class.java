package G47.Grupo47;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.math3.ode.events.FieldEventHandler;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import G47.Grupo47.DirExplorer.FileHandler;
public class NOM_class {

	public ArrayList<Metricas> extractNOMclass(File f, String path,ArrayList<Metricas> metricas) throws FileNotFoundException {
		JavaParser jp = new JavaParser();
		String[] path2 = path.split("/");
		String packageClass = "com.jasm."+path2[1];
		int sum = 0;
		ParseResult<CompilationUnit> compUnit = jp.parse(f);
		if(compUnit.isSuccessful()) {
			CompilationUnit comp=compUnit.getResult().get();
			List<MethodDeclaration> md = DirExplorer.getMethodList(comp,f.getName().replace(".java", ""));
			List<ConstructorDeclaration> coid = DirExplorer.getConstructors(comp, f.getName().replace(".java", ""));
			sum= md.size()+coid.size();
			for(Metricas m: metricas) {
				if(m.getClasse().equals(f.getName().replace(".java", "")) && m.getPacote().equals(packageClass) ) {
					m.setNOM_class(sum);
				}
			}
			extrair_NOM_class_inner(metricas, f, packageClass, comp);
		}

		return metricas;
	}


	public ArrayList<Metricas> extrair_NOM_class_inner(ArrayList<Metricas> metricas,File f, String packageClass,CompilationUnit cu){
		String nameClass = "";
		for(TypeDeclaration<?> type : cu.getTypes()) {
			List<BodyDeclaration<?>> members = type.getMembers();
			for(BodyDeclaration member : members) {
				if(member.isClassOrInterfaceDeclaration()) {
					int NOM = 0;
					if(member.asClassOrInterfaceDeclaration().getNameAsString() != f.getName().replace(".java", "")) {
						NOM = member.asClassOrInterfaceDeclaration().getMethods().size();
						NOM = NOM + member.asClassOrInterfaceDeclaration().getConstructors().size();
						nameClass = f.getName().replace(".java", "")+"."+member.asClassOrInterfaceDeclaration().getNameAsString();
						for(Metricas m: metricas) {
							if(m.getClasse().equals(nameClass) && m.getPacote().equals(packageClass)){
								m.setNOM_class(NOM);
							}
						}

					}
				}

			}
		}
		return metricas;
	}



}
