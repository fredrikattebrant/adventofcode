# Day 6, 2020
import unittest

def getSum1(group):
    uniq = ''.join(set(group))
    return len(uniq)

def getSum2(group):

    return 

def solvePart1(filename):
    sum = 0
    group = ""
    with open("2020/input/" + filename) as file:
        for line in file:
            trimmedLine = line.strip()
            if not trimmedLine:
                sum += getSum1(group)
                group = ""
            group += trimmedLine
    if group:
        sum += getSum1(group)
    return sum

def solvePart2(filename):
    sum = 0
    group = None #set()
    inGroup = False
    with open("2020/input/" + filename) as file:
        for line in file:
            trimmedLine = line.strip()
            if not trimmedLine: 
                sum += len(group)
                inGroup = False
                group = set()
                continue
            if not inGroup:
                group = set(trimmedLine)
                inGroup = True
            else:
                group = group.intersection(set(trimmedLine))
    if inGroup:
        sum += len(group)
    return sum

class Aoc2020Day6(unittest.TestCase):

    def test_part1a(self):
        sum = solvePart1("input06test1.txt")
        self.assertEqual(6, sum)

    def test_part1b(self):
        sum = solvePart1("input06test2.txt")
        self.assertEqual(11, sum)        

    def test_part2a(self):
        sum = solvePart2("input06test1.txt")
        self.assertEqual(3, sum)

    def test_part2b(self):
        sum = solvePart2("input06test2.txt")
        self.assertEqual(6, sum)

if __name__ == '__main__':
    #unittest.main()
    sum = solvePart1("input06.txt")
    print("Part1: sum = ", sum)

    sum = solvePart2("input06.txt")
    print("Part2: sum = ", sum)