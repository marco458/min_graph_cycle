package Labos2;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Zadatak {

	private static int max = 0;	
	private static int[] maxCiklus;
	
	//u "lista" napuni sve moguce permutacije duljine n iz "arr"
	static void combinations(int[] arr, int len, int startPosition,int[] result,ArrayList<ArrayList<Integer>> lista) {
        
		if (len == 0) {
			ArrayList<Integer> pom = new ArrayList<Integer>();
			for(int i = 0; i < result.length; i++) {
				pom.add(result[i]);
			}
			lista.add(pom);
			return;
		}       
        for (int i = startPosition; i <= arr.length-len; i++){
            result[result.length - len] = arr[i];
            combinations(arr, len-1, i+1,result,lista);
        }
		return;
    }       
	
	public static boolean imalidalje(int[][] matrica, int i, int j) { //kaze jesi li mogao iz prosli(i) doci u j
		boolean povrat = false;
		if(matrica[i][j] == 1) {
			povrat = true;
		}
		return povrat;
	}
	
	public static boolean mozemoLiIzZadnjegVrhaUPrvi(int[][] matrica,int prvi,int zadnji) {
		if(matrica[zadnji][prvi] == 1) {return true;}
		return false;
	}
	
	public static int provjeriValjanost(int[][] matrica,int n,int br,int[] niz) {
		int prosli = niz[0];
		for(int i = 1; i<n; i++) {
			if(matrica[prosli][niz[i]] == 0) {  //ovo ustvari radi isto sto i "funkcija imalidalje"
				return 0;						//provjerava jeli nas niz moguc za dani graf
			}
			prosli=niz[i];
		}
		if(matrica[niz[0]][niz[n-1]]==0) {return 0;} //radi isto sto i mozemoLiIzZadnjegVrhaUPrvi
		return n;
	}

	public static void permute(int start, int[] ulaz,int n, int[][] matrica) throws InterruptedException { //funkcija generira sve permutacije zadanog niza "input"
		int br = 0;
		int c=0;
	    if (start == ulaz.length) {//za svaki niz provjerimo "valjanost"
	       	c = provjeriValjanost(matrica,n,br,ulaz);
	       	if(c>max) {max=c;
	        	maxCiklus = ulaz;
	        	/*for(int k=0;k<maxCiklus.length;k++) {
	        		System.out.printf("%d ",maxCiklus[k]);
	        	}System.out.println();*/		/*ovime mozemo dobiti ispis koji je to tocno najdulji ciklus*/
	        }
	        return;
	    }
	    //u ovom dijelu radimo jednu permutaciju
	    for (int i = start; i < ulaz.length; i++) {
	        int temp = ulaz[i];
	        ulaz[i] = ulaz[start];
	        ulaz[start] = temp;

	        permute(start + 1, ulaz,n,matrica);
	        
	        int temp2 = ulaz[i];
	        ulaz[i] = ulaz[start];
	        ulaz[start] = temp2;
	    }
	}   
	
	public static void main(String[] args) throws IOException, InterruptedException {
		int i=0,j=0;
		
		System.out.println("Unesite ime datoteke: ");
		Scanner sc = new Scanner(System.in);
		String ime = sc.nextLine();

		File f = new File(ime);//ako izbacuje iznimku da ne moze naci graf.txt premjestite graf.txt u root direktorija,tocnije u folder gdje se nalazi src,bin,.settings,.classpath...
		
		String s;
		BufferedReader br = new BufferedReader(new FileReader(f));
		s=br.readLine();
		final int n = Integer.parseInt(s);
		br.readLine();
		int[][] matrica = new int[n][n];
				
		while((s=br.readLine()) != null) {
			String[] arr =  s.split(" ");			
				for(j=0;j<n;j++) {
					int x = Integer.parseInt(arr[j]);

					matrica[i][j] = x;
				}
				i++;
		}
		
		//u ovom trenutku imamo popunjenu matricu koja predstavlja matricu susjedstva
		
		int[] predajem = new int[n];
		for(i=0;i<n;i++) {
			predajem[i] = i;
		}//sadrzaj predajem = 0,1,2,3,4,5,6...n-1

		ArrayList<ArrayList<Integer>> listOfList = new ArrayList<ArrayList<Integer>>();
		
		for(i=n;i>=3;i--) {
			combinations(predajem,i,0,new int[i],listOfList);
		}//u listOfList su sada svi nizovi koji su permutacije niza "predajem" duljine odredenog i
		
		for(ArrayList<Integer> l : listOfList) {
			int pom=0;									//
			int[] niz = new int[l.size()];				//prebacimo podatke iz 
			while(pom<l.size()) {						//ArrayList<Integer>
				niz[pom] = l.get(pom);					//u
				pom++;									//int[]
			}											//
			permute(0,niz,l.size(),matrica);	
			if(max==l.size()) {break;}			//optimizacija koja radi tim bolje cim je najdulji ciklus blize zadanoj dimenziji matrice
		}

		System.out.println(max);
		
	}

}
