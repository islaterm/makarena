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
import cl.ravenhill.keen.util.Maximizer

class Engine<DNA>(
    fitnessFunction: (Genotype<DNA>) -> Double,
    private val genotype: Genotype.GenotypeBuilder<DNA>,
    private val populationSize: Int,
    private val selector: Selector<DNA>,
    private val alterers: List<Alterer<DNA>>
) {
    init {
        genotype.fitnessFunction = fitnessFunction
    }

    var generation: Int = 0
        private set

    private var limits = mutableListOf<Limit>()
    var population: List<Genotype<DNA>> = emptyList()
        private set
    var steadyGenerations = 0
        private set

    fun createPopulation() {
        population = (0 until populationSize).map { genotype.build() }
    }

    fun select(n: Int) = selector(population, n, Maximizer())

    class Builder<DNA>(private val fitnessFunction: (Genotype<DNA>) -> Double) {

        var alterers: List<Alterer<DNA>> = emptyList()
        var selector: Selector<DNA> = TournamentSelector(3)
        var populationSize: Int = 50

        lateinit var genotype: Genotype.GenotypeBuilder<DNA>

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