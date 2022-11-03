/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.operators.selector

import cl.ravenhill.keen.core.Genotype

class RouletteWheelSelector<DNA>(
    sorted: Boolean = false
) : AbstractProbabilitySelector<DNA>(sorted) {

    override fun probabilities(population: List<Genotype<DNA>>, count: Int): List<Double> {
        val rawFitness = population.map { it.fitness.toDouble() }
        return rawFitness.map { (it - rawFitness.min()) / rawFitness.sum() }
    }

    override fun toString() = "RouletteWheelSelector { " +
            "sorted: $sorted }"
}