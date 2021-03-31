package coursework;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import model.Fitness;
import model.Individual;
import model.LunarParameters.DataSet;
import model.NeuralNetwork;

/**
 * Implements a basic Evolutionary Algorithm to train a Neural Network
 * 
 * You Can Use This Class to implement your EA or implement your own class that extends {@link NeuralNetwork} 
 * 
 */

public class ExampleEvolutionaryAlgorithm extends NeuralNetwork {
	

	/**
	 * The Main Evolutionary Loop
	 */
	@Override
	public void run() {		
		//Initialise a population of Individuals with random weights
		population = initialise();

		//Record a copy of the best Individual in the population
		best = getBest();
		System.out.println("Best From Initialisation " + best);

		/**
		 * main EA processing loop
		 */		
		
		while (evaluations < Parameters.maxEvaluations) {

			/**
			 * this is a skeleton EA - you need to add the methods.
			 * You can also change the EA if you want 
			 * You must set the best Individual at the end of a run
			 * 
			 */

			// Select 2 Individuals from the current population. Currently returns random Individual
			Individual parent1 = select(); 
			Individual parent2 = select();

			// Generate a child by crossover. Not Implemented			
			ArrayList<Individual> children = reproduce(parent1, parent2);			
			
			//mutate the offspring
			mutate(children);
			
			// Evaluate the children
			evaluateIndividuals(children);			

			// Replace children in population
			replace(children);

			// check to see if the best has improved
			best = getBest();
			
			// Implemented in NN class. 
			outputStats();
			
			//Increment number of completed generations			
		}

		//save the trained network to disk
		saveNeuralNetwork();
	}

	

	/**
	 * Sets the fitness of the individuals passed as parameters (whole population)
	 * 
	 */
	private void evaluateIndividuals(ArrayList<Individual> individuals) {
		for (Individual individual : individuals) {
			individual.fitness = Fitness.evaluate(individual, this);
		}
	}


	/**
	 * Returns a copy of the best individual in the population
	 * 
	 */
	private Individual getBest() {
		best = null;;
		for (Individual individual : population) {
			if (best == null) {
				best = individual.copy();
			} else if (individual.fitness < best.fitness) {
				best = individual.copy();
			}
		}
		return best;
	}

	/**
	 * Generates a randomly initialised population
	 * 
	 */
	private ArrayList<Individual> initialise() {
		population = new ArrayList<>();
		for (int i = 0; i < Parameters.popSize; ++i) {
			//chromosome weights are initialised randomly in the constructor
			Individual individual = new Individual();
			population.add(individual);
		}
		evaluateIndividuals(population);
		return population;
	}

	/**
	 * Selection --
	 * 
	 * NEEDS REPLACED with proper selection this just returns a copy of a random
	 * member of the population
	 */

	private Individual randSelect(){
		Individual parent = population.get(Parameters.random.nextInt(Parameters.popSize));
		return parent.copy();
	}


//	Tournament Selection
//	private Individual select(){
//		Individual parent1 = population.get(Parameters.random.nextInt(Parameters.popSize));
//		Individual parent2 = population.get(Parameters.random.nextInt(Parameters.popSize));
//
//		if(parent1 == parent2){
//			parent2 = population.get(Parameters.random.nextInt(Parameters.popSize));
//		}
//
//		if(parent1.fitness > parent2.fitness){
//			return parent1.copy();
//		}
//		else{
//			return parent2;
//		}
//
//	}

//	Roulette selection


	private Individual select(){
		Individual parent2 = new Individual();



//
		ArrayList<Double> cumulativeProportions = new ArrayList<>();



		double sum = 0;
		ArrayList<Double> proportions = new ArrayList<>();
		double proportion_sum;
		double normalisedProportions;

		for(Individual parent : population){
			sum += parent.fitness;
		}

		for(int i = 0; i < population.size(); i++){
			proportions.set(i, sum / fitness);
		}


		double cumulativeTotal = 0.0;



//

		return parent2;

	}


//	private Individual select(){
//
////		set new collection of fitness values
//		ArrayList<Double> fitnessValues = new ArrayList<>();
//		ArrayList<Double> fitnessProportions = new ArrayList<>();
//		double fitnessSum = 0;
//
//
//
////		Add all fitness values to arraylist
//		for(Individual parent : population){
//			fitnessValues.add(parent.fitness);
//		}
//
////		Find the sum of the fitness values to get the inverse proportional values
//		for(Double i : fitnessValues){
//			fitnessSum += i;
//		}
//
////		Calculate the inverse proportional values and replace each value in the arraylist
//		for(int i = 0; i < fitnessValues.size(); i++){
//			fitnessProportions.set(i, fitnessSum / fitnessValues.get(i));
//		}
//
////		Calculate probabilites
//		double probability = 0;
//
//		for(int i = 0; i < fitnessProportions.size(); i++){
//			probability = fitnessProportions.get(i) / fitnessSum;
//		}
//
//
//		Individual parent1 = population.get(Parameters.random.nextInt(Parameters.popSize));
//		Individual parent2 = population.get(Parameters.random.nextInt(Parameters.popSize));
//
//
//		return parent1.copy();
//
//	}

	/**
	 * Crossover / Reproduction
	 * 
	 * NEEDS REPLACED with proper method this code just returns exact copies of the
	 * parents. 
	 */
	private ArrayList<Individual> randReproduce(Individual parent1, Individual parent2) {
		ArrayList<Individual> children = new ArrayList<>();
		children.add(parent1.copy());
		children.add(parent2.copy());
		return children;
	}

//	One Point

	private ArrayList<Individual> onePointCrossover(Individual parent1, Individual parent2) {
		ArrayList<Individual> children = new ArrayList<>();

		Individual child1 = new Individual();
		Individual child2 = new Individual();

		int crossPoint= Parameters.random.nextInt(parent1.chromosome.length);

		for(int i = 0; i < parent1.chromosome.length; i++){
			if(i < crossPoint){
				child1.chromosome[i] = parent1.chromosome[i];
				child2.chromosome[i] = parent2.chromosome[i];
			}
			else{
				child1.chromosome[i] = parent2.chromosome[i];
				child2.chromosome[i] = parent1.chromosome[i];
			}
		}


		children.add(child1);
		children.add(child2);
		return children;


	}

//	Two point crossover
	private ArrayList<Individual> reproduce(Individual parent1, Individual parent2) {
		ArrayList<Individual> children = new ArrayList<>();

		Individual child1 = new Individual();
		Individual child2 = new Individual();

		int crossPoint= Parameters.random.nextInt(parent1.chromosome.length);
		int crossPoint2 = Parameters.random.nextInt(parent1.chromosome.length);

		if(crossPoint >= crossPoint2){
			crossPoint2 = Parameters.random.nextInt(parent1.chromosome.length);
		}

		for(int i = 0; i < parent1.chromosome.length; i++){
			if(i < crossPoint || i > crossPoint2){
				child1.chromosome[i] = parent1.chromosome[i];
				child2.chromosome[i] = parent2.chromosome[i];
			}
			else{
				child1.chromosome[i] = parent2.chromosome[i];
				child2.chromosome[i] = parent1.chromosome[i];
			}
		}


		children.add(child1);
		children.add(child2);
		return children;


	}
	
	/**
	 * Mutation
	 * 
	 * 
	 */
	private void mutate(ArrayList<Individual> individuals) {		
		for(Individual individual : individuals) {
			for (int i = 0; i < individual.chromosome.length; i++) {
				if (Parameters.random.nextDouble() < Parameters.mutateRate) {
					if (Parameters.random.nextBoolean()) {
						individual.chromosome[i] += (Parameters.mutateChange);
					} else {
						individual.chromosome[i] -= (Parameters.mutateChange);
					}
				}
			}
		}		
	}

	/**
	 * 
	 * Replaces the worst member of the population 
	 * (regardless of fitness)
	 * 
	 */

//	Random replacement method
	private void randReplace(ArrayList<Individual> individuals){
		for(Individual individual : individuals){
			int idx = Parameters.random.nextInt(population.size());
			population.set(idx, individual);
		}
	}

	private void replace(ArrayList<Individual> individuals) {
		for(Individual individual : individuals) {
			int idx = getWorstIndex();		
			population.set(idx, individual);
		}		
	}

	

	/**
	 * Returns the index of the worst member of the population
	 * @return
	 */
	private int getWorstIndex() {
		Individual worst = null;
		int idx = -1;
		for (int i = 0; i < population.size(); i++) {
			Individual individual = population.get(i);
			if (worst == null) {
				worst = individual;
				idx = i;
			} else if (individual.fitness > worst.fitness) {
				worst = individual;
				idx = i; 
			}
		}
		return idx;
	}	

	@Override
	public double activationFunction(double x) {
		if (x < -20.0) {
			return -1.0;
		} else if (x > 20.0) {
			return 1.0;
		}
		return Math.tanh(x);
	}
}
