Boat Game
---------
This is a simple command line based boat game. It usually goes by another name.

Implementation
--------------
The random game builder is built using scala's lazy streams to provide a mechanisim of traversing possible game maps. We only think about describing all possible maps by flatMapping the stream to provide more or filter then using the first satisfying map.

Error handling in the Input parsing is done using Either[String,T] to provide an easy way to compose good error messages for the user. As in this use case the user can very easily provide invalid input.

Next Steps
----------
Due to time constraints the following has been left as next steps

* Hide output from the opposing player.
* Increase test coverage.
* Manual input world builder.
* input should be from 1-10 a-j instead of 0-9 0-9