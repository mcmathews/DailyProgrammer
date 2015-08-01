class Walker:
    def __init__(self, foldNumber):
        self.turnNumber = (2 ** foldNumber) - 1
        self.step = 2 ** (foldNumber + 1)
        self.direction = -1

    def stepForward(self):
        self.turnNumber += self.step
        self.direction *= -1

def calcTurns(n):
    walkers = []
    for i in range(n):
        walkers.append(Walker(i))
    numTurns = (2 ** n) - 1

    currentDirection = 1
    currentPosition = (0, 0)
    turnNumber = 0
    

    for i in range(numTurns):
        for walker in walkers:
            if walker.turnNumber == turnNumber:
                currentPosition = step(currentPosition, currentDirection)
                currentDirection = (currentDirection + walker.direction) % 4
                walker.stepForward()
                turnNumber += 1

                break

def step(coord, d):
    if d == 0:
        c = coord[0], coord[1] + 1
    elif d == 1:
        c = coord[0] + 1, coord[1]
    elif d == 2:
        c = coord[0], coord[1] - 1
    elif d == 3:
        c = coord[0] - 1, coord[1]

    return c

calcTurns(24)