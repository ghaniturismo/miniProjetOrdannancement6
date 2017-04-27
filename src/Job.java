/**
 * Created by mulhauser on 27/04/2017.
 */
public class Job {
    // temps d'execution du Job sur chaque machine
    private static final int NB_MACHINES = 4;

    private int id;
    private int[] tpsExecMachines;
    private int weight;

    public Job(int id){
        this.id = id;
        this.tpsExecMachines = new int[NB_MACHINES];
        this.weight = 0;
    }

    public void initJob(int[] tpsExecMachines, int weight){
        this.weight = weight;
        for(int i = 0; i < NB_MACHINES; i++){
            this.tpsExecMachines[i] = tpsExecMachines[i];
        }
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("Job "+id+"\n");
        for(int i = 0; i < NB_MACHINES; i ++) {
            sb.append("Machine "+i+" : "+tpsExecMachines[i]+ " s avec un poids de "+weight+"\n");
        }
        return sb.toString();
    }

    public static int getNbMachines() {
        return NB_MACHINES;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getTpsExecMachines() {
        return tpsExecMachines;
    }

    public void setTpsExecMachines(int[] tpsExecMachines) {
        this.tpsExecMachines = tpsExecMachines;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
