/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */
package cl.ravenhill

import io.jenetics.BitGene
import io.jenetics.Mutator
import io.jenetics.RouletteWheelSelector
import io.jenetics.SinglePointCrossover
import io.jenetics.TournamentSelector
import io.jenetics.engine.Codec
import io.jenetics.engine.Codecs
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.EvolutionStatistics
import io.jenetics.engine.Limits
import io.jenetics.util.ISeq
import io.jenetics.util.RandomRegistry
import org.jetbrains.annotations.Contract
import java.util.function.Function
import java.util.stream.Collector
import java.util.stream.Stream

@Contract(pure = true)
private fun fitness(size: Double): Function<ISeq<Item>, Double> {
    return Function { items: ISeq<Item> ->
        val sum = items.stream().collect(Item.toSum())
        (if (sum.size <= size) sum.value else 0).toDouble()
    }
}

fun main() {
    val nItems = 15
    val knapsackSize = nItems * 100 / 3.0
    val items = Stream.generate { Item.random() }
        .limit(nItems.toLong())
        .collect(ISeq.toISeq())
    val codec: Codec<ISeq<Item>, BitGene> = Codecs.ofSubSet(items)
    val engine = Engine.builder(fitness(knapsackSize), codec)
        .populationSize(500)
        .survivorsSelector(TournamentSelector(5))
        .offspringSelector(RouletteWheelSelector())
        .alterers(
            Mutator(0.115),
            SinglePointCrossover(0.16)
        )
        .build()
    val statistics: EvolutionStatistics<Double, *> = EvolutionStatistics.ofNumber()
    val best = engine.stream()
        .limit(Limits.bySteadyFitness(7))
        .limit(100)
        .peek(statistics)
        .collect(EvolutionResult.toBestPhenotype())
    val knapsack = codec.decode(best.genotype())
    println(statistics)
    println(best)
    println("\n\n")
    System.out.printf("Genotype of best item: %s%n", best.genotype())
    val fillSize = knapsack.stream().mapToDouble { it: Item -> it.size }.sum()
    System.out.printf("%.2f%% filled.%n", 100 * fillSize / knapsackSize)
}

class Item(val value: Double, val size: Double) {
    companion object {
        fun random(): Item {
            val r = RandomRegistry.random()
            return Item(r.nextDouble() * 100, r.nextDouble() * 100)
        }

        fun toSum(): Collector<Item, *, Item> {
            return Collector.of(
                { DoubleArray(2) },
                { a: DoubleArray, b: Item ->
                    a[0] += b.size
                    a[1] += b.value
                },
                { a: DoubleArray, b: DoubleArray ->
                    a[0] += b[0]
                    a[1] += b[1]
                    a
                },
                { r: DoubleArray -> Item(r[0], r[1]) }
            )
        }
    }
}