/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.core.chromosomes

import cl.ravenhill.keen.core.KeenCore
import cl.ravenhill.keen.core.genes.BoolGene
import java.util.Objects

/**
 * A boolean chromosome.
 */
class BoolChromosome(genes: List<BoolGene>) : AbstractChromosome<Boolean>(genes) {

    constructor(size: Int, truesProbability: Double) : this(
        List(size) {
            if (KeenCore.generator.nextDouble() < truesProbability) {
                BoolGene.True
            } else {
                BoolGene.False
            }
        }
    )

    /**
     * Returns the number of true genes in this chromosome.
     */
    fun trues() = genes.count { it == BoolGene.True }

    override fun verify() = genes.isNotEmpty()

    fun toByteArray() = ByteArray(genes.size) { i -> if (genes[i] == BoolGene.True) 1 else 0 }

    fun toTwosComplement() = toByteArray().toTwosComplement()

    override fun equals(other: Any?) = when {
        this === other -> true
        other !is BoolChromosome -> false
        other::class != BoolChromosome::class -> false
        genes != other.genes -> false
        else -> true
    }

    override fun hashCode() = Objects.hash(BoolChromosome::class, genes)
}

fun ByteArray.toTwosComplement(): ByteArray {
    TODO("Not yet needed")
}
