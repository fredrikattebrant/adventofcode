# AOC 2020 Day 2, part 1
from collections import Counter

def validateLine(line : str):
    # Format: range char password
    # where range is e.g. 1-3
    # char is a single character
    # password is the password string 
    split = line.split()
    range = split[0]
    rangeSplit = range.split("-")
    rangeFrom = int(rangeSplit[0])
    rangeTo = int(rangeSplit[1])
    charPart = split[1]
    char = str(charPart)[0]
    password = split[2]
    count = Counter(password)
    charCount = count[char]
    lowValid = charCount >= rangeFrom
    highValid = charCount <= rangeTo
    valid = lowValid and highValid
    return valid

lines = []
validCount = 0
with open("2020/input/input02.txt") as file:
    for line in file:
        if validateLine(line):
            validCount+=1
print("Valid password count: ", validCount)
