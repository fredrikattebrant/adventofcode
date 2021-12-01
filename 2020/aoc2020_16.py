# AoC Day 16, 2020

# field: range, range
# where range is: low-high
validFields = {}

tickets = []

def isValid(number):
    for rules in validFields.values():
        for range in rules:
            (low, high) = range.split('-')
            if int(number) >= int(low) and int(number) <= int(high):
                return True
    return False


def readFileAndSolvePart1(filename):
    global validFields
    with open("2020/input/" + filename) as file:
        readYourTicket = True
        scanningErrorRate = 0
        yourNumbers = []
        for line in file:
            line = line.strip()
            if not line:
                continue
            elif ' or ' in line:
                # parse rules
                (field, rawRules) = line.strip().split(':')
                ruleSplit = rawRules.split(' or ')
                rules = []
                for rule in ruleSplit:
                    rules.append(rule.strip())
                validFields[field] = rules
            elif 'your ticket:' in line:
                readYourTicket = True
            elif readYourTicket:
                readYourTicket = False
                yourNumbers = line.split(',')
            elif not 'nearby tickets' in line:
                # scan nearby tickets
                for number in line.split(','):
                    if not isValid(number):
                        scanningErrorRate += int(number)
                    else:
                        tickets.append(line)
    print("Part 1: Scanning error rate: ", scanningErrorRate)  


if __name__ == "__main__":
    readFileAndSolvePart1('input16.txt')
