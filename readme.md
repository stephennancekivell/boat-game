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

Usage
---------
sbt run


Display
----------

```
S ship
H hit ship
M miss
```

```

Player A - Own Ships
   0123456789
   __________
0 |          
1 |          
2 |          
3 |         S
4 |          
5 |  SHSSS   
6 |       SSS
7 |   SSSS   
8 |     SS   
9 |          
Player A - Targets
   0123456789
   __________
0 |          
1 |          
2 |      H   
3 |          
4 |          
5 |   M      
6 |          
7 |          
8 |          
9 |          
Player B - Own Ships
   0123456789
   __________
0 |          
1 |          
2 |      H S 
3 |   SSSS   
4 |      S   
5 |      S SS
6 |      S   
7 |          
8 |SSSS      
9 |          
Player B - Targets
   0123456789
   __________
0 |          
1 |          
2 |          
3 |          
4 |          
5 |   H      
6 |          
7 |          
8 |          
9 |          
player B enter a location to attack

7,7
```