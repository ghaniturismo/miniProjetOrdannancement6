package miniProjetOrdannancement6;

public class Tool {

	public static void triFusion (int [] tab, int debut, int fin){
		int milieu;
		if(debut < fin){
			milieu = (debut + fin) / 2;
			triFusion(tab, debut, milieu);
			triFusion(tab, milieu + 1, fin);
			fusionner (tab, debut, milieu, fin);
		}
	}

	public static void fusionner (int tab[], int debut, int milieu, int fin)
	{
		int [] old_tab = (int[]) tab.clone(); 
	        // tab.clone est tres gourmand en temps d'execution surtout dans un algo recursif
	        // il faudrait passer par un tableau temporaire pour stocker les données triées.
	        // puis recopier la partie triée a la fin de la méthode.

		int i1 = debut; //indice dans la première moitié de old_tab
		int i2 = milieu + 1; // indice dans la deuxième moitié de old_tab
		int i = debut; //indice dans le tableau tab

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
	
	
}
