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
import cl.ravenhill.keen.core.genes.Gene


class MeanAlterer<DNA : Number>(probability: Double) : AbstractCrossover<DNA>(probability) {

    override fun crossover(mates: Pair<Genotype<DNA>, Genotype<DNA>>): Genotype<DNA> {
        val chromosomes = mutableListOf<Chromosome<DNA>>()
        mates.first.chromosomes.forEach { chromosome ->
            val genes = mutableListOf<Gene<DNA>>()
            chromosome.genes.forEachIndexed { index, gene ->
                @Suppress("UNCHECKED_CAST")
                genes.add(
                    gene.copy(
                        if (KeenCore.generator.nextDouble() < probability) {
                            (gene.dna.toDouble() + mates.second.chromosomes[index].genes[index].dna.toDouble()) / 2
                        } else {
                            gene.dna
                        } as DNA
                    )
                )
            }
            chromosomes.add(chromosome.copy(genes))
        }
        return mates.first.copy(chromosomes)
    }
}