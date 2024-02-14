package cavasinni;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.uml2.uml.UMLPackage;

public class Comparator {

	public static void main (String[] args) {
		//QUI HO USATO 2 VOLTE LO STESSO MODELLO PERCHE QUELLO GENERATO DA CHATGPT NON è UN MODELLO VALIDO
		System.out.println(calculateSimilarity2("human.xmi", "human.xmi"));
		//VA DA SE CHE DEVI RENDERLO VALIDO PRIMA DI ESEGUIRE LA COMPARAZIONE
	}
	
	public static double calculateSimilarity2(String art1, String art2) {
		

		EPackage.Registry.INSTANCE.put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		
		
		URI uri1 = URI.createFileURI(art1);
		URI uri2 = URI.createFileURI(art2);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		ResourceSet resourceSet1 = new ResourceSetImpl();
		ResourceSet resourceSet2 = new ResourceSetImpl();
		resourceSet1.getResource(uri1, true);
		resourceSet2.getResource(uri2, true);

		
		
		IComparisonScope scope = new DefaultComparisonScope(resourceSet1, resourceSet2, null);
		int total = 0;
		try {
			Comparison comparisonDef = EMFCompare.builder().build().compare(scope);
			List<Match> matchesDef = comparisonDef.getMatches();
			int counterDef = 0;
			for (Match match : matchesDef) {
				List<Match> lm = new ArrayList<Match>(); 
				match.getAllSubmatches().forEach(lm::add);		
				total += lm.size();
				for (Match match2 : lm) {
					if (match2.getLeft() != null && match2.getRight() != null)
						counterDef++;
				}
				if (match.getLeft() != null && match.getRight() != null)
					counterDef++;
			}
			double value = ((counterDef * 1.0) / total);
			return value;
		} catch (Exception e) {
			return 0;
		}
	}
	
}
