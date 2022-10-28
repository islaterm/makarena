/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */
package cl.ravenhill.keen

import cl.ravenhill.keen.chromosomes.BoolChromosome
import cl.ravenhill.keen.chromosomes.Chromosome
import cl.ravenhill.keen.chromosomes.DoubleChromosome
import cl.ravenhill.keen.chromosomes.IntChromosome
import io.jenetics.BitGene
import io.jenetics.DoubleGene
import io.jenetics.LineCrossover
import io.jenetics.MeanAlterer
import io.jenetics.Mutator
import io.jenetics.RouletteWheelSelector
import io.jenetics.TournamentSelector
import io.jenetics.engine.EvolutionResult
import org.jetbrains.annotations.Contract
import java.util.concurrent.Executors
import java.util.stream.Stream
import io.jenetics.BitChromosome as JBitChromosome
import io.jenetics.BitGene as JBitGene
import io.jenetics.Genotype as JGenotype
import io.jenetics.PartialAlterer as JPartialAlterer

fun eval(gt: JGenotype<JBitGene>) =
    gt.chromosome().`as`(JBitChromosome::class.java).bitCount().toDouble()

fun identity(gt: JGenotype<DoubleGene>): Double = gt.gene().allele()

fun main(args: Array<String>) {
    val (genotype, engine) = helloWorld()
    geneTest(genotype)
    chromosomeTest(genotype)
    val genotype2 = genotypeTest()
    selectorTest(genotype)
    partialAltererTest(genotype2)
    testEvolutionStream(engine)
    testExecutor()
}

fun partialAltererTest(genotype2: Genotype<DoubleGene>) =
    KeenEngine(::identity, genotype2).also {
        println("--- PARTIAL ALTERER ---")
        it.alterers = listOf(
            JPartialAlterer.of(Mutator(), 0, 3),
            JPartialAlterer.of(MeanAlterer(), 1),
            LineCrossover()
        )
        println(it.alterers)
    }

fun selectorTest(genotype: Genotype<BitGene>) {
    println("--- SELECTOR ---")
    val engine = KeenEngine(::eval, genotype).also {
        it.offspringFraction = 0.5
        it.survivorsSelector = RouletteWheelSelector()
        it.offspringSelector = TournamentSelector()
        println(it)
    }
    val result = engine.stream()
        .limit(100)
        .collect(EvolutionResult.toBestGenotype()).also { println("Result: $it") }
}

@Contract(" -> new", pure = true)
fun helloWorld(): Pair<Genotype<BitGene>, KeenEngine<BitGene, Double>> {
    println("--- Hello World ---")
    val genotype = Genotype(BoolChromosome(10, 0.5)).also { println(it) }
    val engine = KeenEngine(::eval, genotype).also { println(it) }
    val result = engine.stream()
        .limit(100)
        .collect(EvolutionResult.toBestGenotype()).also { println(it) }
    return genotype to engine
}

@Contract(pure = true)
fun geneTest(genotype: Genotype<BitGene>) =
    genotype.chromosome.gene.also { gene: BitGene ->
        println("--- GENE ---")
        println("Gene: $gene")
        val allele = gene.allele().also { println("allele: $it") }
        println("isValid: ${gene.isValid}")
        println("newInstance: ${gene.newInstance()}")
        println("newInstance($allele): ${gene.newInstance(allele)}")
    }

@Contract(pure = true)
fun chromosomeTest(genotype: Genotype<BitGene>) =
    genotype.chromosome.also { chromosome: Chromosome<BitGene> ->
        println("--- CHROMOSOME ---")
        println("Chromosome: $chromosome")
        println("get(0): ${chromosome[0]}")
        println("length: ${chromosome.length}")
        println("newInstance: ${chromosome.newInstance()}")
        IntChromosome(0, 1000, 5..9).also { println("IntChromosome: $it") }
    }

@Contract(" -> new", pure = true)
fun genotypeTest() = Genotype(
    DoubleChromosome(0.0, 1.0, 8),
    DoubleChromosome(1.0, 2.0, 10),
    DoubleChromosome(0.0, 10.0, 9),
    DoubleChromosome(0.1, 0.9, 5)
).also { println(it) }

fun testEvolutionStream(
    engine: KeenEngine<BitGene, Double>
): Stream<EvolutionResult<BitGene, Double>> =
    engine.stream().limit(100).also {
        println("--- EVOLUTION STREAM ---")
        println(it.toList().map { result -> result.population() })
    }

@Contract(" -> new", pure = true)
fun testExecutor() =
    KeenEngine(::eval, Genotype(BoolChromosome(10, 0.5))).also {
        it.executor = Executors.newFixedThreadPool(10)
        println(it)
    }