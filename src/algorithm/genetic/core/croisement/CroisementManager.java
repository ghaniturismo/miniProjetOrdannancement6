package algorithm.genetic.core.croisement;

import algorithm.genetic.core.Population;

public interface CroisementManager {

	Population crossover(Population p);

	enum CrossoverManagerType {
		RANDOM_CROSSOVER,
	}
}
