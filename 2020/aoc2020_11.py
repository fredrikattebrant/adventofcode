# Day 11, 2020 

INPUT_FILE = "input11test1.txt"
# INPUT_FILE = "input11.txt"

layout = []
rows = 0
cols = 0

def readFile(filename):
    with open("2020/input/" + filename) as file:
        for line in file:
            seats = list(line.strip())
            layout.append(seats)

def printSeating():
    for row in layout:
        print(''.join(row))

def isOccupied(row, col):
    if row < 0 or col < 0 or row >= rows or col >= cols:
        # Outside - don't count
        return 0
    if layout[row][col] == '#':
        return 1
    else:
        return 0

def isOccupied2(row, col):
    if row < 0 or row >= rows or col < 0 or col >= cols:
        return False
    return layout[row][col] == '#'

def getAdjacents(row, col):
    '''c -1 1
          abc r -1
          d.e r  0
          fgh r +1
    '''
    return int(isOccupied(row-1, col-1)
        +  isOccupied(row-1, col)
        +  isOccupied(row-1, col+1)
        +  isOccupied(row,   col-1)
        +  isOccupied(row,   col+1)
        +  isOccupied(row+1, col-1)
        +  isOccupied(row+1, col)
        +  isOccupied(row+1, col+1))

def getVisiblyOccupied(row, col):
    # up, same col:
    count = 0
    for r in range(row - 1, 0):
        if isOccupied2(r, col):
            count += 1
            break
    # up, decrementing col:
    for r in range(row - 1, 0):
        c = col - 1
        if isOccupied2(r, c):
            count += 1
    # up, incrementing col:
    for r in range(row - 1, 0):
        c = col + 1
        if isOccupied2(r, c):
            count += 1
    # down, same col:
    for r in range(row + 1, rows):
        if isOccupied2(r, col):
            count += 1
    # down, decrementing col:
    for r in range(row + 1, rows):
        c = col - 1
        if isOccupied2(r, c):
            count += 1
    # down, incrementing col:
    for r in range(row + 1, rows):
        c = col + 1
        if isOccupied2(r, c):
            count += 1
    return count

def applyRules1(row, col):
    seat = layout[row][col]
    if seat == 'L' and getAdjacents(row, col) == 0:
        return '#'
    elif seat == '#' and getAdjacents(row, col) >= 4:
        return 'L'
    return seat

def applyRules2(row, col):
    seat = layout[row][col]
    if seat == 'L' and getVisiblyOccupied(row, col) == 0:
        return '#'
    elif seat == '#' and getVisiblyOccupied(row, col) >= 5:
        return 'L'
    return seat

def updateSeating1():
    newLayout = []
    for row in range(rows):
        newRow = layout[row].copy()
        newLayout.append(newRow)
        for col in range(cols):
            newRow[col] = applyRules1(row, col)
    return newLayout

def updateSeating2():
    newLayout = []
    for row in range(rows):
        newRow = layout[row].copy()
        newLayout.append(newRow)
        for col in range(cols):
            newRow[col] = applyRules2(row, col)
    return newLayout

def isEqual(l1, l2):
    for row in range(rows):
        for col in range(cols):
            if l1[row][col] != l2[row][col]:
                return False
    return True

def getOccupiedSeats():
    occupied = 0
    for row in range(rows):
        for col in range(cols):
            if layout[row][col] == '#':
                occupied += 1
    return occupied


if __name__ == "__main__":
    readFile(INPUT_FILE)

    rows = len(layout)
    cols = len(layout[0])

    # Part 1:
    while True:
        # printSeating()
        newLayout = updateSeating1()
        if isEqual(layout, newLayout):
            break
        layout = newLayout

    print("Part 1: Occupied seats: ", getOccupiedSeats())

    # Part 2:

    # Reload the seating:
    layout = list()
    readFile(INPUT_FILE)

    while True:
        print()
        newLayout = updateSeating2()
        if isEqual(layout, newLayout):
            break
        layout = newLayout
        printSeating()

    print("Part 2: Occupied seats: ", getOccupiedSeats())

    
    