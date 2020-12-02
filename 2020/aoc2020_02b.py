# AOC 2020 Day 2, part 2

def validateLine(line : str):
    # Format: indexes char password
    # where indexes is e.g. 1-3
    # only one can match the given char
    # char is a single character
    # password is the password string 
    split = line.split()
    range = split[0]
    rangeSplit = range.split("-")
    index1 = int(rangeSplit[0])
    index2 = int(rangeSplit[1])
    charPart = split[1]
    char = str(charPart)[0]
    password = split[2]
    charAtindex1 = password[index1 - 1]
    charAtindex2 = password[index2 - 1]
    valid1 = charAtindex1 == char
    valid2 = charAtindex2 == char
    valid = valid1 != valid2
    return valid

lines = []
validCount = 0
with open("input/input02.txt") as file:
    for line in file:
        if validateLine(line):
            validCount+=1
print("Valid password count: " + str(validCount))
