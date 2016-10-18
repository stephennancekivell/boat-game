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


Display
----------
S ship
H hit ship
M miss

Player A - Own Ships
   0123456789
   __________
0 |     SSHH 
1 |          
2 |         S
3 |         H
4 |      H   
5 |          
6 |          
7 |   HHH    
8 |  HHHHH   
9 |          
Player A - Targets
          
          
      HH  
  HHHH    
          
          
          
          
      MMH 
   HHHHMH 
Player B - Own Ships
   0123456789
   __________
0 |          
1 |          
2 |      HH  
3 |  HHHH    
4 |          
5 |          
6 |        S 
7 |        S 
8 |        H 
9 |   HHHHHH 
Player B - Targets
       HH 
          
          
        MH
     MHM  
          
          
   HHH    
  HHHMHM  
          
player A enter a location to attack
7,8