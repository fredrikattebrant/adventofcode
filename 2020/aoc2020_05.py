# AOC 2020, Day 5

import unittest

ROWS = 128 
COLS = 8
#EMPTY_COLS = [ None ] * COLS

def lowerHalf(lo, hi):
    return int((hi - lo) / 2 + lo)

def higherHalf(lo, hi):
    return int(lo + (hi - lo) / 2 + 1)
    
def findSeat(seatCode):
    lo = int(0)
    hi = int(ROWS - 1)
    for i in range(COLS - 1):
        code = seatCode[i]
        if code == 'F':
            hi = lowerHalf(lo, hi)
        elif code == 'B':
            lo = higherHalf(lo, hi)
    row = lo 

    lo = 0
    hi = 7
    for i in range(7,10):
        code = seatCode[i]
        if code == 'L':
            hi = lowerHalf(lo, hi)
        else:
            lo = higherHalf(lo, hi)
    col = lo
    seatId = row * 8 + col
    return (row, col, seatId)

def solvePart1():
    highestId = 0
    theRow = 0
    theCol = 0
    with open("2020/input/input05.txt") as file:
        for line in file:
            (row, col, seatId) = findSeat(line)
            if seatId > highestId:
                highestId = seatId
                theRow = row
                theCol = col
    print("Part1: ", end=" ")
    print("Row: ", theRow, ", Col: ", theCol, " => ", end="")
    print("Highest Seat ID = ", highestId)

def solvePart2():
    seats = [ [ None for col in range(COLS)]
                     for row in range(ROWS)]

    lastRow = 0
    with open("2020/input/input05.txt") as file:
        for line in file:
            (row, col, seatId) = findSeat(line)
            seats[row][col] = seatId
            if row > lastRow:
                lastRow = row
    for row in range(lastRow):
        cols = seats[row]
        for col in range(COLS):
            # skip first row
            if row == 0 or row == lastRow:
                continue
            if cols[col] == None:
                print('Part 2: Seat ID =>', row * 8 + col)


class Aoc2020Day5(unittest.TestCase):

    def test_findSeat(self):
        self.assertEqual(findSeat('FBFBBFFRLR'), (44, 5, 357))
        self.assertEqual(findSeat('BFFFBBFRRR'), (70, 7, 567))
        self.assertEqual(findSeat('FFFBBBFRRR'), (14, 7, 119))
        self.assertEqual(findSeat('BBFFBBFRLL'), (102, 4, 820))

if __name__ == '__main__':
    #unittest.main()
    solvePart1()
    solvePart2()
