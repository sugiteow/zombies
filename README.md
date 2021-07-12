# zombies

### Pre-requisites

* Java 1.8
* Maven (https://maven.apache.org/)

### How To Run

Once you have all pre-requisites installed, to run all test, just do
`mvn clean test`
from root directory of the project.

The main "acceptance test" that exercises the requested scenario in the problem statement is in ZombieSimulatorTest

### Assumptions

* Placing any thing outside of the boundary of the world is not allowed.
* Multiple things of different type could occupy the same space in the world.
* Pushing instruction to immobile thing (e.g. Creature) will just be ignored by that thing.
* When moving, if the zombie passes a creature multiple times, the effect will only be applied once.
* The effect of zombies passing through a creature is assumed to be applied only when in between the movement of the
  zombie, it stands on the same grid as the creature. At the moment this doesn't affect anything, as the zombie is
  moving grid by grid, but will matter if the zombie has a long range movement (e.g. say if it moves 8 grid at once). As
  we can't assume that it walks when moving multiple grid at once (i.e. it could be jumping really high ;p), we can't
  assume that it will actually meet and/or affects anything in between the starting and the end grid of the movement.
* Tag on Things are not going to be reset when they are removed from the world (as the tag acts as a unique identifier
  of that thing)

### Design decisions and/or considerations

* Grid in the world is Map of Coordinates, instead of just an array to allow nicer way to remove/add/check the content
  on the grid, and provide better extensibility
  (e.g. in case we want to add third dimension into it)
* The content inside the world is represented by Thing object (apology for the naming, can't think of better name for
  it). This is to provide a bit more specifics on what could be put into a grid in the world. It also encapsulates
  common Thing behaviour, like say: tagging, and ability to receive move instruction
* Movement and Status Effect is also designed as a separate hierarchy of object, making it easier to extend (making a
  new movement type, say "Jumping" for example)
  and also allowing for consistent behaviour across Things that uses the same movement type/can be hit with the same
  status effect. This also allows each individual effect/movement to be reused for a new type of Thing (when/if
  necessary).