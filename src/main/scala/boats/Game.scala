package boats

import Grid._

object App extends App {
  Game.startGameWithRandom()
}

object Game {

  def startNewGame(): Unit = {
    gameLoop(GameState.empty)
  }

  def newRandomGameState(): GameState = {
    def randomPlayer = Player(
      ownLocations = WorldBuilder.oneRandomShipsGrid(Seq(5,4,3,2,1)),
      enemyLocations = Seq.fill(10,10)(TargetUnknown)
    )

    GameState(
      playerA = randomPlayer,
      playerB = randomPlayer
    )
  }

  def startGameWithRandom(): Unit = {
    val game = newRandomGameState()
    GameState.draw(game)
    gameLoop(game)
  }

  def gameLoop(prevState: GameState): Unit = {
    val command = Input.readCommand("player A")
    val gameAfterA = CommandProcessor.process(prevState, command, true)
    GameState.draw(gameAfterA)
    if (GameState.hasPlayerLost(gameAfterA.playerB.ownLocations)) {
      println("Player A Won (ง'̀-'́)ง")
    } else {
      val command = Input.readCommand("player B")
      val gameAfterB = CommandProcessor.process(prevState, command, false)
      GameState.draw(gameAfterB)
      if (GameState.hasPlayerLost(gameAfterB.playerA.ownLocations)) {
        println("Player B Won (ง'̀-'́)ง")
      } else {
        gameLoop(gameAfterB)
      }
    }
  }
}

case class Player(
                 ownLocations: Seq[Seq[ShipsCell]],
                 enemyLocations: Seq[Seq[TargetCell]])
object Player {
  val empty = Player(
    ownLocations = Seq.fill(10,10)(ShipsEmpty),
    enemyLocations = Seq.fill(10,10)(TargetUnknown)
  )
}