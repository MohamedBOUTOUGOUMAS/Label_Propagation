import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class LabelPropagation {
	public static ArrayList<Sommet> adjacencyArray = new ArrayList<>();

	public static Map<Integer, Integer> map = new HashMap<>();
	public static Set<Integer> indexTableSet = new HashSet();
	public static ArrayList<Integer> indexTableList;

	//public static String path = "com-youtube.ungraph.txt/data";
	public static String path = "com-youtube.ungraph.txt/test.txt";
	public static void read(String path) {
		try {
			FileReader f = new FileReader(path);
			BufferedReader buf = new BufferedReader(f);
			String ligne;
			int s_old = -1;
			int index = -1;
			int cpt = 0;
			while((ligne = buf.readLine()) != null) {
				String [] aretes = new String[2];
				aretes = ligne.split("\\s+", 2);
				String s = aretes[0];
				try {
					if(s_old == Integer.parseInt(s)) {
						adjacencyArray.get(index).neighbor.add(Integer.parseInt(aretes[1]));
					}else {
						ArrayList<Integer> nei = new ArrayList<>();
						nei.add(Integer.parseInt(aretes[1]));
						adjacencyArray.add(new Sommet(Integer.parseInt(s), nei));
						s_old = Integer.parseInt(s);
						index++;
					}
				} catch (NumberFormatException e) {
					// TODO: handle exception
				}
				
				int v = Integer.parseInt(aretes[1]);
				int k = Integer.parseInt(aretes[0]);

				indexTableSet.add(k);
				indexTableSet.add(v);
				
				
			}
			System.out.println("reading finished");
			
			indexTableList = new ArrayList<>(indexTableSet);
			Collections.sort(indexTableList);
			
			f.close();
			buf.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static int getMaxFrequence(ArrayList<Integer> labels) {
		int c = 0;
		int maxValue = labels.get(0);
		int freqMax = 0;
		int tmp = labels.get(0);
		for (int i = 0; i < labels.size(); i++) {
			if(labels.get(i) == tmp) {
				c++;
			}else if(freqMax < c) {
				freqMax = c;
				c = 1;
				maxValue = tmp;
				tmp = labels.get(i);
			}else {
				tmp = labels.get(i);
				c = 1;
			}
		}
		if(freqMax < c) {
			freqMax = c;
			maxValue = tmp;
		}
		return maxValue;
	}
	
	public static void labelPropagation() {
		
		// Affectation de label
		for(int i = 0; i<indexTableList.size();i++) {
			map.put(indexTableList.get(i), i);
			//System.out.println(indexTableList.get(i) + " label : "+i);
		}
		//System.out.println(map);
		int iterMax = 120;
		int iter = 0;
		while(iter<iterMax) {
			// Shuffle	
			Collections.shuffle(adjacencyArray);
			
			// etape 3
			for(Sommet node : adjacencyArray) {
				
				ArrayList<Integer> labels = new ArrayList<>();
				for (int i = 0; i < node.neighbor.size(); i++) {
					labels.add(map.get(node.neighbor.get(i)));
				} 
				
				Collections.sort(labels);
				
				int maxFrequency = getMaxFrequence(labels);
				
				map.put(node.s, maxFrequency);
			}
			iter ++;
		}
		Set<Integer> set = new HashSet<>(map.values());
		System.out.println(set.size());
		System.out.println(map);
	}
	
	
	
	public static void main(String [] args) {
		read(path);
		labelPropagation();
//		ArrayList<Integer> l = new ArrayList<>();
//		l.add(1);
//		l.add(1);
//		l.add(2);
//		l.add(2);
//		l.add(2);
//		l.add(3);
//		l.add(3);
//		l.add(4);
//		l.add(4);
//		l.add(4);
//		l.add(4);
//		System.out.println(getMaxFrequence(l));
	}
}
