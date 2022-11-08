/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */
package cl.ravenhill

import io.jenetics.MeanAlterer
import io.jenetics.Mutator
import io.jenetics.Optimize
import io.jenetics.engine.Codecs
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.EvolutionStatistics
import io.jenetics.engine.Limits
import io.jenetics.util.DoubleRange
import kotlin.math.cos

private const val A = 10.0
private const val R = 5.12
private const val N = 2
private fun fitness(x: DoubleArray): Double {
    var sum = 0.0
    for (i in 0 until N) {
        sum += x[i] * x[i] - A * cos(2 * Math.PI * x[i])
    }
    return A * N + sum
}

fun main() {
    val engine = Engine.builder(::fitness, Codecs.ofVector(DoubleRange.of(-R, R), N))
        .populationSize(500)
        .optimize(Optimize.MINIMUM)
        .alterers(Mutator(0.03), MeanAlterer(0.6))
        .build()
    val statistics: EvolutionStatistics<Double, *> = EvolutionStatistics.ofNumber()
    val result = engine.stream()
        .limit(Limits.bySteadyFitness(7))
        .peek(statistics)
        .collect(EvolutionResult.toBestPhenotype())
    println(statistics)
    println(result)
}