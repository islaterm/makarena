/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.chromosomes

import io.jenetics.BitChromosome as JBitChromosome
import io.jenetics.Chromosome as JChromosome
import io.jenetics.Gene as JGene


/**
 * A chromosome consists of one or more genes.
 *
 * @see JChromosome
 */
interface Chromosome<T : JGene<*, T>> {
    /**
     * The _Jenetics_ chromosome.
     */
    val jenetics: JChromosome<T>   // Gosh darn curiously recurring generics

    /**
     * Return the value at the given `index`.
     *
     * @see JChromosome.get
     */
    operator fun get(index: Int): T = jenetics.get(index)

    /**
     * Create a new instance of type ``T``.
     *
     * @see JChromosome.newInstance
     */
    fun newInstance(): JChromosome<T> = jenetics.newInstance()

    fun toBitChromosome(): JBitChromosome

    /**
     * The first gene of this chromosome.
     *
     * @see JChromosome.gene
     */
    val gene: T
        get() = jenetics.gene()

    /**
     * The length of this chromosome.
     *
     * @see JChromosome.length
     */
    val length: Int
        get() = jenetics.length()
}