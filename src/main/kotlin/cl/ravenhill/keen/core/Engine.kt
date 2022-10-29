/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.core

import cl.ravenhill.keen.core.chromosomes.Chromosome
import cl.ravenhill.keen.operators.selector.Selector
import cl.ravenhill.keen.operators.selector.TournamentSelector
import cl.ravenhill.keen.signals.Require

class Engine<DNA>(
    private val genotype: Genotype<DNA>,
    private val fitnessFunction: (Genotype<DNA>) -> Comparable<*>,
    builder: Engine<DNA>.() -> Unit
) {
    init {
        builder()
    }

    var selector: Selector<DNA> = TournamentSelector(3)
    var populationSize: Int = 50
        set(value) {
            Require.Parameter("population size", value) atLeast 1
            field = value
        }

    constructor(
        fitnessFunction: (Genotype<DNA>) -> Comparable<*>,
        chromosome: Chromosome<DNA>,
        vararg chromosomes: Chromosome<DNA>,
        builder: Engine<DNA>.() -> Unit = {}
    ) : this(Genotype(chromosome, *chromosomes), fitnessFunction, builder)

    override fun toString() = "Engine { genotype: $genotype, populationSize: $populationSize }"
}