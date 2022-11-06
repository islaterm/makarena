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

package cl.ravenhill.keen.operators.crossover

import cl.ravenhill.keen.core.Genotype
import cl.ravenhill.keen.core.KeenCore
import cl.ravenhill.keen.core.chromosomes.Chromosome


class SinglePointCrossover<DNA>(probability: Double) : AbstractCrossover<DNA>(probability) {
    override fun crossover(mates: Pair<Genotype<DNA>, Genotype<DNA>>): Genotype<DNA> {
        val chromosomes = mutableListOf<Chromosome<DNA>>()
        val cut = KeenCore.generator.nextInt(mates.first.chromosomes.size)
        chromosomes.addAll(mates.first.chromosomes.take(cut))
        chromosomes.addAll(mates.second.chromosomes.drop(cut))
        return mates.first.copy(chromosomes)
    }

    override fun toString() = "SinglePointCrossover { probability: $probability }"
}