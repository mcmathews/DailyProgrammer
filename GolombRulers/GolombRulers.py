from queue import PriorityQueue
import math
import time


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


def getChildren(widths, maxChildren):
	children = []

	mds = widthsToMeasurableDistances(widths)

	# can be made more efficient
	# might need to make this go higher
	for md in range(1, maxChildren):
		if md not in mds:
			children.append(widths + [md])

	return children


def isValid(widths):
	return max(widthsToMeasurableDistances(widths).values()) <= 1


def getCost(widths):
	return sum(widths)


def getHeuristic(widths, order):
	unusedWidths = []
	mds = widthsToMeasurableDistances(widths)
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
		queue.put((0, []))
		while not queue.empty():
			f, widths = queue.get()
			if len(widths) == order - 1:
				return widths

			children = getChildren(widths, maxChildren)
			for child in children:
				if not isValid(child):
					continue
				g = getCost(child)
				h = getHeuristic(child, order)
				queue.put((g + h, child))
		print("Solution not found with maxChildren = " + str(maxChildren));
		maxChildren += 1


def main():
	order = int(input())
	start = time.time()
	print(widthsToTickMarks(search(order)))
	print("Time: " + str(time.time() - start))


if __name__ == "__main__":
	main()