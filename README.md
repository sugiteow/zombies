# zombies

### How To Run

### Assumptions

* Placing any thing outside of the boundary of the world is not allowed.
* Multiple things of different type could occupy the same space in the world.
* Pushing instruction to immobile thing (e.g. Creature) will just be ignored by that thing.
* When moving, if the zombie passes a creature multiple times, the effect will only be applied once.
* The effect of zombies passing through a creature is assumed to be applied only when in between the movement of the
  zombie, it stands on the same grid as the creature. At the moment this doesn't affect anything, as the zombie is
  moving grid by grid, but will matter if the zombie has a long range movement (e.g. say if it moves 8 grid at once). As
  we can't assume that it walks when moving multiple grid at once (i.e. it could be jumping really high ;p), we can't
  assume that it will actually pass anything in between the starting and the end grid of the movement.
* Tag on zombies and creatures are not going to be reset when they are removed from the world (as the tag acts as a
  unique identifier of that thing)
