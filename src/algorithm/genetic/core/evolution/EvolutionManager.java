package algorithm.genetic.core.evolution;

import algorithm.genetic.core.Population;
import algorithm.genetic.core.chromosomes.BaseChromosome;
import algorithm.genetic.core.croisement.CroisementManager;
import algorithm.genetic.core.makespan.MakespanManager;
import algorithm.genetic.core.mutation.MutationManager;
import algorithm.genetic.core.selection.SelectionManager;
import problem.Schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EvolutionManager {

    public int iterations = 0;

    protected CroisementManager croisementManager;
    protected MutationManager mutationManager;
    protected SelectionManager selectionManager;
    protected MakespanManager makespanManager;

    List<BaseChromosome> bests;

    protected final Comparator<BaseChromosome> chComp = new Comparator<BaseChromosome>() {

        @Override
        public int compare(BaseChromosome c1, BaseChromosome c2) {
            return Long.valueOf(makespanManager.makespan(c1)).compareTo(makespanManager.makespan(c2));
        }
    };

    public EvolutionManager(CroisementManager croisementManager, MutationManager mutationManager,
                            SelectionManager selectionManager, MakespanManager makespanManager) {
        this.croisementManager = croisementManager;
        this.mutationManager = mutationManager;
        this.selectionManager = selectionManager;
        this.makespanManager = makespanManager;
    }

    protected Population evolution(Population population) {
        Population ch = croisementManager.crossover(population);
        mutationManager.mutation(ch);
        ch.getIndividuals().addAll(population.getIndividuals());
        return selectionManager.selection(ch, population.getIndividuals().size());
    }

    public Schedule generateSchedule(int iterations, int size) {
        this.iterations = iterations;
        bests = new ArrayList<>();
        Population p = makespanManager.createPopulation(size);

        for (int i = 0; i < iterations; i++) {
            Population p1 = evolution(p);
            bests.add(p1.getBest(makespanManager));
            p = p1;
        }

        BaseChromosome best = p.getBest(makespanManager);

        return makespanManager.translate(best);
    }

    public Schedule generateSchedule(double coef, int timesToRepeat, int size) {
        bests = new ArrayList<>();
        Population p = makespanManager.createPopulation(size);
        BaseChromosome bestc = null, worstc = null;
        long best = 0, worst = 0;
        int criteria = 0;
        do {
            p = evolution(p);
            bestc = Collections.min(p.getIndividuals(), chComp);
            worstc = Collections.max(p.getIndividuals(), chComp);
            best = makespanManager.makespan(bestc);
            worst = makespanManager.makespan(worstc);
            bests.add(bestc);
            boolean foundBetter = ((double) (worst - best)) / worst > coef;
            if (!foundBetter) {
                criteria ++;
            } else {
                criteria = 0;
            }
        } while (criteria < timesToRepeat);

        this.iterations = bests.size();
        BaseChromosome bestC = Collections.min(p.getIndividuals(), chComp);
        return makespanManager.translate(bestC);

    }
}
