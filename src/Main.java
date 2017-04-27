import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main{

    static List<Job> jobList;
    static final String PATH = "./configureJobs.txt";
    public static void main(String[] args){

        System.out.println("Hello World!");

        jobList = new ArrayList<Job>();
        configureJob(jobList);


    }

    public static void configureJob(List<Job> alj){
        try {
            String ligne ;
            BufferedReader fichier = new BufferedReader(new FileReader(PATH));
            String[] tab;
            while ((ligne = fichier.readLine()) != null) {
                tab = ligne.split(" ");


            }

            fichier.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}