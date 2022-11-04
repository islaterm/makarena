/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.core

import cl.ravenhill.keen.limits.GenerationCount
import cl.ravenhill.keen.limits.Limit
import cl.ravenhill.keen.operators.alterers.Alterer
import cl.ravenhill.keen.operators.selector.Selector
import cl.ravenhill.keen.operators.selector.TournamentSelector
import cl.ravenhill.keen.signals.EngineConfigurationException
import cl.ravenhill.keen.util.Maximizer

/**
 * Fundamental class of the library. It is the engine that will run the evolution process.
 *
 * @param DNA   The type of the DNA of the Genotype
 * @property genotype           The genotype that will be used to create the population
 * @property populationSize     The size of the population
 * @property selector           The selector that will be used to select the individuals
 * @property alterers           The alterers that will be used to alter the population
 * @property generation         The current generation
 * @property population         The current population
 * @property steadyGenerations  The number of generations that the fitness has not changed
 */
class Engine<DNA> private constructor(
    fitnessFunction: (Genotype<DNA>) -> Double,
    private val genotype: Genotype.Builder<DNA>,
    val populationSize: Int,
    private val selector: Selector<DNA>,
    val alterers: List<Alterer<DNA>>
) {

    init {
        // We need to set the genotype's fitness function to evolve the population
        genotype.fitnessFunction = fitnessFunction
    }

    var generation: Int = 0
        private set
    var steadyGenerations = 0
        private set

    var population: List<Genotype<DNA>> = emptyList()
        private set

    /** The limits that will be used to stop the evolution  */
    private var limits = mutableListOf<Limit>()

    fun createPopulation() {
        population = (0 until populationSize).map { genotype.build() }
    }

    fun select(n: Int) = selector(population, n, Maximizer())

    /**
     * Engine builder.
     *
     * @param DNA   The type of the DNA of the Genotype.
     *
     * @property populationSize The size of the population.
     *                          It must be greater than 0.
     *                          Default value is 50.
     */
    class Builder<DNA>(private val fitnessFunction: (Genotype<DNA>) -> Double) {
        var alterers: List<Alterer<DNA>> = emptyList()
        var selector: Selector<DNA> = TournamentSelector(3)

        var populationSize: Int = 50
            set(value) = if (value > 0) {
                field = value
            } else {
                throw EngineConfigurationException("Population size must be positive")
            }

        lateinit var genotype: Genotype.Builder<DNA>

        fun build() = Engine(fitnessFunction, genotype, populationSize, selector, alterers)
    }

    override fun toString() =
        "Engine { populationSize: $populationSize, " +
                "genotype: $genotype, " +
                "selector: $selector , " +
                "alterers: $alterers }"

    fun evolve(function: Engine<DNA>.() -> Unit) {
        createPopulation()
        if (limits.isEmpty()) { // If no limits are set, use a default one
            limits += GenerationCount(100)
        }
        while (limits.none { it(this) }) {  // While none of the limits are met
            population = select(populationSize)     // Select the population
            generation++                            // Increment the generation
        }
        println(generation)
        println(population)
    }
}