# AoC Day 7, 2020
import re

def getBagTag(bagStr : str):
    # drop n, bags in bagStr
    # word1 word2 bags => word1 word2
    # n word1 word2 bags => word1 word2
    dropBag = bagStr.replace(' bags ', '')
    dropCount = re.sub('\d ','', dropBag).strip()
    return re.sub('bag[s]?[\.]?', '', dropCount).strip()

def readFile(filename):
    bagsDict = {}
    with open("2020/input/" + filename) as file:
        for line in file:
            split = line.split('contain')
            key = getBagTag(split[0])
            bagValue = split[1].strip()
            bags = bagValue.split(', ')
            valueDict = {}
            for bv in bags:
                bv = bv.strip().replace('.', '')
                if not "no other bags" in bv:
                    bvSplit = bv.strip().split(' ')
                    count = bvSplit[0]
                    name = bvSplit[1] + ' ' + bvSplit[2]
                    valueDict[name] = count
            bagsDict[key] = valueDict 
    return bagsDict

def findContainingBags(toMatch, bagsDict):
    matches = set()
    for (key, value) in bagsDict.items():
        if toMatch in value:
            matches.add(key)
    print(toMatch, " contained by ", matches)
    if not matches:
        print("Empty: ", matches)
    return matches

def checkChildBags(parent, bag, bagsDict):
    children = bagsDict[parent]
    for child in children:
        if bag in child:
            return True
        else:
            if checkChildBags(child, bag, bagsDict):
                return True
    return False

def getParentBags(bag, bagsDict):
    parents = set()
    for parent in bagsDict:
        if checkChildBags(parent, bag, bagsDict):
            parents.add(parent)
    return parents

def getNumberOfContainedBags(bagTag, bagsDict, indent):
    print(indent, "Checking: ", bagTag)
    containedBags = bagsDict[bagTag]
    sum = 1
    indent += ".."
    for item in containedBags.items():
        bagCount = int(item[1])
        itemTag = item[0]
        childBagCount = getNumberOfContainedBags(itemTag, bagsDict, indent)
        sum += bagCount * childBagCount
        print(indent, "Sum:      ", sum)
    print(indent, "Bag:      ", bagTag, " => ", sum)
    return sum


def solvePart1(bagTag, useTestData):
    fileName = "input07.txt" if useTestData else "input07test1.txt"
    bagsDict = readFile(fileName)
    parentBags = getParentBags(bagTag, bagsDict)
    print("Part 1: ", len(parentBags))

def solvePart2(bagTag, useTestData):
    fileName = "input07.txt" if useTestData else "input07test1.txt"
    bagsDict = readFile(fileName)
    bagCount = getNumberOfContainedBags(bagTag, bagsDict, "") - 1
    print("Part 2: ", bagCount)

#
# Main
#
if __name__ == '__main__':
    theBagTag = 'shiny gold'
    solvePart1(theBagTag, False) # True:  Use puzzle input
    solvePart2(theBagTag, True)  # False: Use test data
