/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill

import cl.ravenhill.keen.core.Engine
import cl.ravenhill.keen.core.Genotype
import cl.ravenhill.keen.core.chromosomes.BoolChromosome
import cl.ravenhill.keen.operators.selector.RouletteWheelSelector
import io.jenetics.BitChromosome
import io.jenetics.BitGene
import io.jenetics.Mutator
import io.jenetics.SinglePointCrossover
import io.jenetics.engine.EvolutionResult.toBestPhenotype
import io.jenetics.engine.EvolutionStatistics
import io.jenetics.engine.Limits.bySteadyFitness
import io.jenetics.Genotype as JGenotype
import io.jenetics.RouletteWheelSelector as JRouletteWheelSelector
import io.jenetics.engine.Engine as JEngine


/**
 * Documentation
 */
fun jCount(genotype: JGenotype<BitGene>) =
    (genotype.chromosome().`as`(BitChromosome::class.java).bitCount())

fun count(genotype: Genotype<Boolean>): Int = TODO()

fun main() {
    val engine = Engine(::count, BoolChromosome(20, 0.15)) {
        populationSize = 500
        selector = RouletteWheelSelector<Boolean>()
    }
    println(engine)
    val jEngine = JEngine.builder(::jCount, BitChromosome.of(20, 0.15))
        .populationSize(500)
        .selector(JRouletteWheelSelector())
        .alterers(
            Mutator(0.55),
            SinglePointCrossover(0.06)
        ).build()
    val statistics = EvolutionStatistics.ofNumber<Int>()
    val best = jEngine.stream()
        .limit(bySteadyFitness(7))
        .limit(100)
        .peek(statistics)
        .collect(toBestPhenotype())
    println(statistics)
    println(best)
}
