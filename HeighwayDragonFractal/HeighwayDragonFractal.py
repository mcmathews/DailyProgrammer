def calcTurns(n):
	numTurns = (2 ** n) - 1
	turns = [0] * numTurns

	step = 2
	start = 0
	while start < numTurns:
		i = start
		even = False
		while i < numTurns:
			turns[i] = 1 if even else -1
			even = not even

			i += step
		step *= 2
		start = start * 2 + 1

	return turns

def turnsToCoords(turns):
	coords = [(0, 0)]

	curr = (0, 0)
	d = 1
	for turn in turns:
		curr = step(curr, d)

		d = (d + turn) % 4

		coords.append(curr)

	coords.append(step(curr, d))

	return coords

def printCoords(coords):
	for coord in coords:
		print(coord)

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

turnsToCoords(calcTurns(26))