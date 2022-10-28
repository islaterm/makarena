/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill

import cl.ravenhill.keen.KeenEngine
import io.jenetics.BitChromosome
import io.jenetics.BitGene
import io.jenetics.Genotype as JGenotype


/**
 * Documentation
 */
fun count(genotype: JGenotype<BitGene>): Int {
    return (genotype.chromosome().`as`(BitChromosome::class.java).bitCount())
}

fun main(args: Array<String>) {
    val engine = KeenEngine(
        ::count,
    )
}