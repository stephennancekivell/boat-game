package boats

object Input {

  sealed trait Command

  case class Attack(x: Int, y: Int) extends Command
  case object Exit extends Command

  val coordsRe = "(\\d)\\s*,\\s*(\\d)".r

  def readCommand(msg: String): Command = {
    println(msg+" enter a location to attack")
    val line = scala.io.StdIn.readLine()
    val handleError = (x: String) => {
      println(x)
      readCommand(msg)
    }

    val crashOnError = (x: String) => {
      throw new RuntimeException(x)
    }

    parseCommandG(line)
        .fold[Command](handleError, identity)
  }

  def parseCommandG(line: String): Either[String, Command] = {
    if (line.toUpperCase.contains("EXIT"))
      Right(Exit)
    else
      parseCommand(line)
  }

  def parseCommand(line: String): Either[String,Attack] = line.split(",") match {
    case Array(x,y) =>
      for {
        x <- parseCoordinate(x.trim).right
        y <- parseCoordinate(y.trim).right
      } yield Attack(x,y)
    case _ =>
      Left("Unable to parse command from "+line)
  }

  def parseCoordinate(in: String): Either[String,Int] =
    util.Try(in.toInt)
      .toOption
      .fold[Either[String,Int]](Left("Unable to parse number from "+in))(Right(_))
      .right
      .flatMap { d =>
        if (d < 0 || d > 9)
          Left("number outside of range "+d)
        else
          Right(d)
      }
}
