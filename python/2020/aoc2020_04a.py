# AOC 2020, Day 4, part 1
 
# Check if field has key (key:value)
def hasKey(key, passport):
    fields = passport.split()
    for field in fields:
        split = field.split(':')
        if split[0] == key:
            return True
    return False

# Load passports from file
def loadPassports(fileName):
    passports = []
    with open(fileName) as file:
        entry = ""
        sep = ""
        for line in file:
            trimmedLine = line.strip()
            if not trimmedLine:
                passports.append(entry)
                entry = ""
                sep = ""
                continue
            entry += sep + trimmedLine
            sep = " "
    passports.append(entry)
    return passports

# Validate passports
def validatePassports(passports, requiredKeys, optionalKeys):
    validPassports = []
    invalidPassports = []
    for passport in passports:
        isValid = True
        for key in requiredKeys:
            if hasKey(key, passport):
                continue
            isValid = False
            break
        if isValid:
            validPassports.append(passport)
        else:
            invalidPassports.append(passport)
    return (validPassports, invalidPassports)

# Main
testFile = "2020/input/input04test.txt"
liveFile = "2020/input/input04.txt"
passports = loadPassports(liveFile)

requiredKeys = ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"]
optionalKeys = ["cid"]

(validPassports, invalidPassports) = validatePassports(passports, requiredKeys, optionalKeys)

print("Valid passport count: ", len(validPassports))