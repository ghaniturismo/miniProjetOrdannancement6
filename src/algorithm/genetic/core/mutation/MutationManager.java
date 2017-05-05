package algorithm.genetic.core.mutation;

import algorithm.genetic.core.Population;

public interface MutationManager {

	void mutation(Population population);

	enum MutationManagerType {
		SWAP_MUTATION;
	}

}
