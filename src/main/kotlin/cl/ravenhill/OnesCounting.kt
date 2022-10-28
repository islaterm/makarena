/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill

import io.jenetics.BitChromosome
import io.jenetics.BitGene
import io.jenetics.Mutator
import io.jenetics.RouletteWheelSelector
import io.jenetics.SinglePointCrossover
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult.toBestPhenotype
import io.jenetics.engine.EvolutionStatistics
import io.jenetics.engine.Limits.bySteadyFitness
import io.jenetics.Genotype as JGenotype


/**
 * Documentation
 */
fun count(genotype: JGenotype<BitGene>) =
    (genotype.chromosome().`as`(BitChromosome::class.java).bitCount())

fun main() {
    val engine = Engine.builder(::count, BitChromosome.of(20, 0.15))
        .populationSize(500)
        .selector(RouletteWheelSelector())
        .alterers(
            Mutator(0.55),
            SinglePointCrossover(0.06)
        ).build()
    val statistics = EvolutionStatistics.ofNumber<Int>()
    val best = engine.stream()
        .limit(bySteadyFitness(7))
        .limit(100)
        .peek(statistics)
        .collect(toBestPhenotype())
    println(statistics)
    println(best)
}
