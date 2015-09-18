from queue import PriorityQueue
import math
import time
import sys


def widthsToTickMarks(widths):
	marks = [0]
	for i, w in enumerate(widths):
		marks.append(marks[i] + w)
	
	return marks


def widthsToMeasurableDistances(widths):
	measurableDistances = {}
	for i, w in enumerate(widths):
		measurableDistances[w] = measurableDistances[w] + 1 if w in measurableDistances else 1
		sumWidth = w
		for j, wi in enumerate(widths[i+1:]):
			sumWidth += wi
			measurableDistances[sumWidth] = measurableDistances[sumWidth] + 1 if sumWidth in measurableDistances else 1

	return measurableDistances


def getChildren(widths, maxChildren, mds):
	children = []

	for md in range(1, maxChildren):
		if md not in mds:
			children.append(widths + [md])

	return children


def isValid(mds):
	return max(mds.values()) <= 1


def getCost(widths):
	return sum(widths)


def getHeuristic(widths, order, mds):
	unusedWidths = []
	md = 1
	while len(unusedWidths) + len(widths) < order - 1:
		if md not in mds:
			unusedWidths.append(md)
		md += 1

	return sum(unusedWidths)


def search(order):
	queue = PriorityQueue()
	maxChildren = math.floor(order * 1.5)
	while True:
		queue.put((0, [], {}))
		while not queue.empty():
			f, widths, mds = queue.get()
			if len(widths) == order - 1:
				return widths

			children = getChildren(widths, maxChildren, mds)
			for child in children:
				mds = widthsToMeasurableDistances(child)
				if not isValid(mds):
					continue
				g = getCost(child)
				h = getHeuristic(child, order, mds)
				queue.put((g + h, child, mds))
		print("Solution not found with maxChildren = " + str(maxChildren));
		maxChildren += 1


def main():
	order = int(sys.argv[1])
	start = time.time()
	print(widthsToTickMarks(search(order)))
	print("Time: " + str(time.time() - start))


if __name__ == "__main__":
	main()