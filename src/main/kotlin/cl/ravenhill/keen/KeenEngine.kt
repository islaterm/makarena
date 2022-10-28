/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen

import io.jenetics.Alterer
import io.jenetics.Gene
import io.jenetics.Mutator
import io.jenetics.Selector
import io.jenetics.SinglePointCrossover
import io.jenetics.TournamentSelector
import io.jenetics.engine.EvolutionStream
import java.util.concurrent.Executor
import io.jenetics.Genotype as JGenotype
import io.jenetics.engine.Engine as JEngine

/**
 *
 * @param GENE : Gene<*, DNA>
 * @param DNA : Comparable<T>
 * @property genotype Genotype<DNA>
 * @property offspringFraction Double
 * @property builder (Builder<(DNA..DNA?), (T..T?)>..Builder<(DNA..DNA?), (T..T?)>?)
 * @property jenetics Engine<DNA, T>
 *
 * @see JEngine
 */
class KeenEngine<GENE : Gene<*, GENE>, DNA : Comparable<DNA>>(
    fitnessFunction: (JGenotype<GENE>) -> DNA,
    private val genotype: Genotype<GENE>
) {
    lateinit var jeneticsCache: JEngine<GENE, DNA>

    val jenetics: JEngine<GENE, DNA>
        get() = if (!::jeneticsCache.isInitialized) {
            builder.build().also { jeneticsCache = it }
        } else {
            jeneticsCache
        }

    /**
     * The alterers used for alter the offspring population.
     *
     * Default values is set to ``SinglePointCrossover(0.2)`` followed by ``Mutator(0.15)``.
     *
     * @see [JEngine.Builder.alterers]
     */
    var alterers: List<Alterer<GENE, DNA>> = listOf(SinglePointCrossover(0.2), Mutator(0.15))
        set(value) {
            builder.alterers(value[0], *value.drop(1).toTypedArray())
            field = value
        }

    /**
     * The selector used for selecting the offspring population.
     *
     * Default value is set to ``TournamentSelector<>(3)``.
     *
     * @see [JEngine.Builder.offspringSelector]
     */
    var offspringSelector: Selector<GENE, DNA> = TournamentSelector(3)
        set(value) {
            builder.offspringSelector(value)
            field = value
        }

    /**
     * The selector used for selecting the survivors' population.
     * Default value is set to ``TournamentSelector(3)``.
     *
     * @see JEngine.Builder.survivorsSelector
     */
    var survivorsSelector: Selector<GENE, DNA> = TournamentSelector(3)
        set(value) {
            builder.survivorsSelector(value)
            field = value
        }

    /**
     * The offspring fraction.
     *
     * Default value is set to ``0.6``.
     *
     * @see JEngine.Builder.offspringFraction
     */
    var offspringFraction: Double = 0.6
        set(value) {
            builder.offspringFraction(field)
            field = value
        }

    var executor: Executor? = null
        get() = jenetics.executor()
        set(value) {
            builder.executor(value)
            field = value
        }

    private val builder = JEngine.builder(fitnessFunction, genotype.jenetics)

    fun stream(): EvolutionStream<GENE, DNA> = jenetics.stream()

    override fun toString() =
        "Engine { genotype: $genotype, " +
                "alterers: $alterers, " +
                "offspringSelector: $offspringSelector, " +
                "survivorsSelector: $survivorsSelector, " +
                "offspringFraction: $offspringFraction " +
                "executor: $executor }"
}

