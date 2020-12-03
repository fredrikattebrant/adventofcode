# AOC 2020 Day3, part 1
#
def isTree(map, row, col, wrap):
    wrappedCol = col % wrap
    return  map[row][wrappedCol] == '#'

test="input03test.txt"
puzzleInput="input03.txt"
# Load the base map
baseMap = []
with open("2020/input/" + puzzleInput) as file:
    for line in file:
        r = []
        r = list(line)
        baseMap.append(r)

wrap = len(baseMap[0]) - 1
rows = len(baseMap)

# Traverse => +(1,3)
r = 0
c = 0
treeCount = 0
while r < rows - 1:
    r += 1
    c += 3
    if isTree(baseMap, r, c, wrap):
        treeCount += 1

print("Tree count: " + str(treeCount))
