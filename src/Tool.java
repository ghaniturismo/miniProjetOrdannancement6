import java.io.BufferedReader;
import java.io.FileReader;


public class Tool {

	public static void triFusion (int [] tab, int début, int fin){
		int milieu;
		if(début < fin){
			milieu = (début + fin) / 2;
			triFusion(tab, début, milieu);
			triFusion(tab, milieu + 1, fin);
			fusionner (tab, début, milieu, fin);
		}
	}

	public static void fusionner (int tab[], int début, int milieu, int fin){
		int [] old_tab = (int[]) tab.clone(); 
	        // tab.clone est tres gourmand en temps d'execution surtout dans un algo recursif
	        // il faudrait passer par un tableau temporaire pour stocker les données triées.
	        // puis recopier la partie triée a la fin de la méthode.

		int i1 = début; //indice dans la première moitié de old_tab
		int i2 = milieu + 1; // indice dans la deuxième moitié de old_tab
		int i = début; //indice dans le tableau tab

		while (i1 <= milieu && i2 <= fin)
		{
			//quelle est la plus petite tête de liste?
			if(old_tab[i1] <= old_tab[i2])
			{
				tab[i] = old_tab[i1];
				i1++;
			}
			else
			{
				tab[i] = old_tab[i2];
				i2++;
			}
			i++;
		}
		if (i <= fin)
		{
			while(i1 <= milieu)  // le reste de la première moitié
			{
				tab[i] = old_tab[i1];
				i1++;
				i++;
			}
			while(i2 <= fin) // le reste de la deuxième moitié
			{
				tab[i] = old_tab[i2];
				i2++;
				i++;
			}
		}
	}
	
	
	void printResultTable(int[][] result, int nbJobs, int NB_MACHINES){
	    int i, j;
	    for (i = 0; i < NB_MACHINES; i++){
	        System.out.print("Sequence pour machine "+ i +" : [ ");
	        for (j = 0; j < nbJobs; j++){
	            System.out.print(result[i][j]+ " ");
	        }
	        System.out.println(" ]\n");
	    }
	}
	
	

	void getProcessingTime(Job jobs[], int nbJobs, double[] processingTimes,int[] jobsParMachines, int NB_MACHINES){
	
	    /*
	     * Calcule le temps de traitement cumulé des tâches de machines
	     */
	    for(int i=0;i<NB_MACHINES;i++){
	        processingTimes[i] = 0.;
	        jobsParMachines[i] = 0;
	    }
	    
	    for(int i=0;i<nbJobs;i++){
	        for(int j=0;j<NB_MACHINES;j++){
	            processingTimes[j] += jobs[i].getTpsExecMachines()[j];
	            if(jobs[i].getTpsExecMachines()[j] > 0){
	            	jobsParMachines[j]++;
	            }
	        }
	    }
	}
	
}
