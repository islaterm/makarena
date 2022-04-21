package cl.ravenhill.makarena

class GameObserver {
  lateinit var winner: Marker
    private set

  fun notifyWinner(winner: Marker?) {
    if (winner != null) {
      this.winner = winner
      throw EndGame(winner)
    }
  }
}
