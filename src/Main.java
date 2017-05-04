import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main{

    static List<Job> jobList;
    static final String PATH = "./configureJobs.txt";
    public static void main(String[] args){

        jobList = new ArrayList<Job>();
        configureJob(jobList);
        for(Job job : jobList){
            System.out.println(job);
        }

    }

    /**
     * Initialisation des jobs grâce au fichier de configuration
     * 1ère colonne : poids du job
     * colonnes suivantes : temps d'execution du chaque machine
     * @param alj
     */
    public static void configureJob(List<Job> alj){
        try {
            String ligne ;
            BufferedReader fichier = new BufferedReader(new FileReader(PATH));
            String[] tab;
            int id = 0;
            int weight;
            int[] tempsExec;
            while ((ligne = fichier.readLine()) != null) {
                Job job = new Job(id);
                tab = ligne.split(" ");
                tempsExec = new int[tab.length-1];
                weight = Integer.parseInt(tab[0]);
                for(int i = 1; i < tab.length; i++){
                    tempsExec[i-1] = Integer.parseInt(tab[i]);
                }
                job.initJob(tempsExec, weight);
                alj.add(job);
                id++;
            }

            fichier.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}