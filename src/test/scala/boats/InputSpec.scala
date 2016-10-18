package boats

import org.specs2.mutable.Specification

class InputSpec extends Specification {
  "Input" should {
    "parse the input line with values" in {
      Input.parseCommand("1,2") === Right(Input.Attack(1,2))
    }

    "parse the input line with values padded " in {
      Input.parseCommand("1 , 2") === Right(Input.Attack(1,2))
    }

    "not parse invalid input" in {
      Input.parseCommand("hit the boat") === Left("Unable to parse command from hit the boat")
    }

    "not parse invalid coords" in {
      Input.parseCommand("-1,1") === Left("number outside of range -1")
    }
    "not parse invalid coords" in {
      Input.parseCommand("1,12") === Left("number outside of range 12")
    }
  }
}
