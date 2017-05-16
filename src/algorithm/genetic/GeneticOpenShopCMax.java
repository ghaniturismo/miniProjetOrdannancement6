package algorithm.genetic;

import algorithm.BaseSolver;
import algorithm.Solver;
import algorithm.genetic.core.croisement.CroisementManager;
import algorithm.genetic.core.croisement.RandomCrossover;
import algorithm.genetic.core.croisement.selection.CroisementRoue;
import algorithm.genetic.core.croisement.selection.ParentingManager;
import algorithm.genetic.core.evolution.EvolutionManager;
import algorithm.genetic.core.makespan.MakespanManager;
import algorithm.genetic.core.makespan.OpenShopMakespan;
import algorithm.genetic.core.makespan.OpenShopSimpleMakespan;
import algorithm.genetic.core.mutation.MutationManager;
import algorithm.genetic.core.mutation.SwapMutation;
import algorithm.genetic.core.selection.EliteSelection;
import algorithm.genetic.core.selection.SelectionManager;
import problem.Problem;
import problem.Schedule;

public class GeneticOpenShopCMax extends BaseSolver implements Solver {

    private MakespanManager makespanManager;
    private ParentingManager parentingManager;
    private CroisementManager croisementManager;
    private MutationManager mutationManager;
    private SelectionManager selectionManager;

    private EvolutionManager evolutionManager;

    private int sizeOfPopulation;

    private int maxIterations;

    private double equalityCoeff;
    private int timesToRepeat;

    public GeneticOpenShopCMax(Problem problem,
                               MakespanManager.MakespanManagerType makespanManagerType,
                               ParentingManager.ParentingManagerType parentingManagerType,
                               CroisementManager.CrossoverManagerType crossoverManagerType,
                               MutationManager.MutationManagerType mutationManagerType,
                               double mutation,
                               SelectionManager.SelectionManagerType selectionManagerType,
                               int sizeOfPopulation,
                               int iterations,
                               double equalityCoeff

    ) {
        this(problem, makespanManagerType, parentingManagerType, crossoverManagerType, mutationManagerType, mutation,
                selectionManagerType,
                sizeOfPopulation,
                iterations,
                equalityCoeff,
        1);
    }

    public GeneticOpenShopCMax(Problem problem,
                               MakespanManager.MakespanManagerType makespanManagerType,
                               ParentingManager.ParentingManagerType parentingManagerType,
                               CroisementManager.CrossoverManagerType crossoverManagerType,
                               MutationManager.MutationManagerType mutationManagerType,
                               double mutation,
                               SelectionManager.SelectionManagerType selectionManagerType,
                               int sizeOfPopulation,
                               int iterations,
                               double equalityCoeff,
                               int timesToRepeat
    ) {
        super(problem);
        switch (makespanManagerType) {
            case OPEN_SHOP_SIMPLE:
                makespanManager = new OpenShopSimpleMakespan(problem);
                break;
            case OPEN_SHOP_MODIFIED:
                makespanManager = new OpenShopMakespan(problem);
                break;
            default:
                throw new UnsupportedOperationException("Makespan manager type " + makespanManagerType.name() + " is not supported");
        }

        switch (parentingManagerType) {
            case CROSSOVER_WHEEL:
                this.parentingManager = new CroisementRoue();
                break;
            default:
                throw new UnsupportedOperationException("Parenting manager type " + parentingManagerType.name() + " is not supported");
        }
        switch (crossoverManagerType) {
            case RANDOM_CROSSOVER:
                this.croisementManager = new RandomCrossover(parentingManager);
                break;
            default:
                throw new UnsupportedOperationException("Crossover manager type " + crossoverManagerType.name() + " is not supported");
        }
        switch (mutationManagerType) {
            case SWAP_MUTATION:
                this.mutationManager = new SwapMutation(mutation);
                break;
            default:
                throw new UnsupportedOperationException("Selection manager type " + selectionManagerType.name() + " is not supported");
        }
        switch (selectionManagerType) {
            case ELITE_SELECTION:
                this.selectionManager = new EliteSelection(makespanManager);
                break;
            default:
                throw new UnsupportedOperationException("Selection manager type " + selectionManagerType.name() + " is not supported");
        }
        evolutionManager = new EvolutionManager(croisementManager, mutationManager, selectionManager, makespanManager);

        this.sizeOfPopulation = sizeOfPopulation;
        this.maxIterations = iterations;
        this.equalityCoeff = equalityCoeff;
        this.timesToRepeat = timesToRepeat;
    }

    @Override
    public Schedule generateSchedule() {
        if (maxIterations > 0) {
            return evolutionManager.generateSchedule(maxIterations, sizeOfPopulation);
        } else {
            return evolutionManager.generateSchedule(equalityCoeff, timesToRepeat, sizeOfPopulation);
        }
    }

}
