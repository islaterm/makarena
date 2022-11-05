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
import io.jenetics.engine.EvolutionResult.toBestPhenotype
import io.jenetics.engine.EvolutionStatistics
import io.jenetics.engine.Limits.bySteadyFitness
import io.jenetics.util.DoubleRange
import kotlin.math.cos
import kotlin.math.sin
import io.jenetics.engine.Engine as JEngine

/**
 * Example of using the Jenetics library to calculate the minimum of a function.
 */

/**
 * The function to minimize.
 */
fun fitnessFunction(x: Double) = cos(0.5 + sin(x)) * cos(x)

/**
 * Calculates the minimum of the real function:
 * ```
 * f(x) = cos(1 / 2 + sin(x)) * cos(x)
 * ```
 */
fun main() {
//    val engine = engine()
    val jengine =
        JEngine.builder(::fitnessFunction, Codecs.ofScalar(DoubleRange.of(0.0, 2 * Math.PI)))
            .populationSize(500)
            .optimize(Optimize.MINIMUM)
            .alterers(
                Mutator(0.03),
                MeanAlterer(0.6)
            ).build()

    val statistics = EvolutionStatistics.ofNumber<Double>()

    val best = jengine.stream()
        .limit(bySteadyFitness(7))
        .limit(100)
        .peek(statistics)
        .collect(toBestPhenotype())

    println(statistics)
    println(best)
}