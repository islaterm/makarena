package cl.ravenhill.makarena


fun evaluate(gameState: Board): Double {
  gameState.checkStatus()
  return when (gameState.winner) {
    null -> 0.0
    Marker.X -> 1.0
    Marker.O -> -1.0
    Marker.EMPTY -> throw IllegalStateException("Winner can't be Marker.EMPTY")
  }
}

open class Node(open var value: Double?, vararg val children: Node)
class Leaf(override var value: Double?) : Node(value)

/**
 * Implementation of the Minimax backtracking algorithm.
 *
 * @param node
 *    root node of the tree to evaluate.
 * @param depth Int
 *    depth of the current recursive step.
 * @param maximizingPlayer
 *    indicates if the current step should maximize the evaluation of nodes.
 * @return the value of the best move.
 */
fun minimax(node: Node, depth: Int, maximizingPlayer: Boolean): Double {
  if (depth == 0) {
    return node.value!!
  }
  node.value = try {
    if (maximizingPlayer) {
      node.children.maxOf { minimax(it, depth - 1, false) }
    } else {
      node.children.minOf { minimax(it, depth - 1, true) }
    }
  } catch (ex: NoSuchElementException) {
    node.value
  }
  return node.value!!
}
