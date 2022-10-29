/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

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
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.haveSameHashCodeAs
import io.kotest.property.Arb
import io.kotest.property.arbitrary.double
import io.kotest.property.arbitrary.element
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.long
import io.kotest.property.arbitrary.positiveInt
import io.kotest.property.assume
import io.kotest.property.checkAll
import java.util.Random


class BoolChromosomeSpec : WordSpec({
    "Two chromosomes with the same genes" should {
        "be equal " {
            checkAll(Arb.list(Arb.element(BoolGene.True, BoolGene.False))) { genes ->
                BoolChromosome(genes) shouldBe BoolChromosome(genes)
            }
        }

        "have the same hash code" {
            checkAll(Arb.list(Arb.element(BoolGene.True, BoolGene.False))) { genes ->
                BoolChromosome(genes) should haveSameHashCodeAs(BoolChromosome(genes))
            }
        }
    }

    "Two chromosomes with different genes" should {
        "not be equal" {
            checkAll(
                Arb.list(Arb.element(BoolGene.True, BoolGene.False)),
                Arb.list(Arb.element(BoolGene.True, BoolGene.False))
            ) { genes1, genes2 ->
                assume(genes1 != genes2)
                BoolChromosome(genes1) shouldNotBe BoolChromosome(genes2)
            }
        }

        "not have the same hash code" {
            checkAll(
                Arb.list(Arb.element(BoolGene.True, BoolGene.False)),
                Arb.list(Arb.element(BoolGene.True, BoolGene.False))
            ) { genes1, genes2 ->
                assume(genes1 != genes2)
                BoolChromosome(genes1) shouldNot haveSameHashCodeAs(BoolChromosome(genes2))
            }
        }
    }

    "A bool chromosome should be valid" When {
        checkAll(Arb.list(Arb.element(BoolGene.True, BoolGene.False))) { genes ->
            BoolChromosome(genes).verify() shouldBe true
        }
    }

    "A bool chromosome should return the number of true genes" When {
        checkAll(Arb.list(Arb.element(BoolGene.True, BoolGene.False))) { genes ->
            BoolChromosome(genes).trues() shouldBe genes.count { it == BoolGene.True }
        }
    }

    "A random bool chromosome can be created" When {
        checkAll(
            Arb.long(),
            Arb.positiveInt(1000),
            Arb.double(0.0, 1.0)
        ) { seed, size, truesProbability ->
            KeenCore.generator = Random(seed)
            val chromosome1 = BoolChromosome(size, truesProbability)
            KeenCore.generator = Random(seed)
            val chromosome2 = BoolChromosome(size, truesProbability)
            chromosome1 shouldBe chromosome2
            KeenCore.generator = Random()
        }
    }
})