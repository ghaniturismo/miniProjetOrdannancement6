package algorithm.genetic.core.mutation;

import algorithm.genetic.core.Population;
import algorithm.genetic.core.chromosomes.BaseChromosome;

import java.util.Random;

public class SwapMutation implements MutationManager {

	private double mutationProbability = 0.3d;

	private Random random = new Random(System.currentTimeMillis());

	public SwapMutation(double prob) {
		mutationProbability = prob;
	}

	@Override
	public void mutation(Population population) {
		int length = population.getOneLength();
		for (BaseChromosome chromosome : population.getIndividuals()) {
			if (random.nextDouble() < mutationProbability) {
				int index = random.nextInt(length);
				int g1 = index == 0 ? length - 1 : index - 1;
				int g2 = index == length - 1 ? 0 : index + 1;
				chromosome.swapGenes(g1, g2);
			}
		}
	}

}
