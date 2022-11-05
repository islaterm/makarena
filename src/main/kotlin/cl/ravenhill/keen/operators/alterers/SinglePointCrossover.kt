/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.operators.alterers

import cl.ravenhill.keen.core.Genotype
import cl.ravenhill.keen.core.KeenCore
import cl.ravenhill.keen.core.chromosomes.Chromosome
import kotlin.random.asKotlinRandom


class SinglePointCrossover<DNA>(override val probability: Double) : Alterer<DNA> {
    override fun invoke(population: List<Genotype<DNA>>): List<Genotype<DNA>> {
        return population.map { genotype ->
            val mate = population.random(KeenCore.generator.asKotlinRandom())
            crossover(genotype to mate)
        }
    }

    private fun crossover(mates: Pair<Genotype<DNA>, Genotype<DNA>>): Genotype<DNA> {
        val chromosomes = mutableListOf<Chromosome<DNA>>()
        val cut = KeenCore.generator.nextInt(mates.first.chromosomes.size)
        chromosomes.addAll(mates.first.chromosomes.take(cut))
        chromosomes.addAll(mates.second.chromosomes.drop(cut))
        return mates.first.copy(chromosomes)
    }

    override fun toString() = "SinglePointCrossover { probability: $probability }"
}