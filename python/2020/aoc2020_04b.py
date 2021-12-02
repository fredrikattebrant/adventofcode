# AOC 2020, Day 4, part 2
import re
import unittest

# Check if field has key (key:value)
def hasValidKey(key, passport):
    fields = passport.split()
    for field in fields:
        split = field.split(':')
        if split[0] == key:
            return validateKey(key, split[1])
    return False

def validateYear(value, min, max):
    if value.isdigit():
        if len(value) == 4:
            year = int(value)
            return year >= min and year <= max
    return False

validEyeColors = ['amb', 'blu', 'brn', 'gry', 'grn', 'hzl', 'oth']
def validateEyeColor(value):
    return value in validEyeColors

def validateHairColor(value):
    if value[0] == '#':
        if len(re.findall('[a-z0-9]', value)) == 6:
            return True
    return False

def validateHeight(value):
    toCheck = value
    cmIx = value.find('cm')
    inIx = value.find('in')
    if cmIx != -1:
        toCheck = value[0:cmIx]
        if toCheck.isdigit():
            cm = int(toCheck)
            return cm >= 150 and cm <= 193
    elif inIx != -1:
        toCheck = value[0:inIx]
        if toCheck.isdigit():
            inches = int(toCheck)
            return inches >= 59 and inches <=76
    return False

def validatePassportId(value):
    return len(value) == 9 and value.isdigit()

def validateKey(key, value):
    if key == "byr":
        return validateYear(value, 1920, 2002)
    elif key == "iyr":
        return validateYear(value, 2010, 2020)
    elif key == "eyr":
        return validateYear(value, 2020, 2030)
    elif key == "hgt":
        return validateHeight(value)
    elif key == "hcl":
        return validateHairColor(value)
    elif key == "ecl":
        return validateEyeColor(value)
    elif key == "cid":
        return True
    elif key == "pid":
        return validatePassportId(value)    



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
            if hasValidKey(key, passport):
                continue
            isValid = False
            break
        if isValid:
            validPassports.append(passport)
        else:
            invalidPassports.append(passport)
    return (validPassports, invalidPassports)

def runTests():
    v='2002'
    print('byr ', v, ' -> ', validateYear(v, 1920, 2002))
    v='2003'
    print('byr ', v, ' -> ', validateYear(v, 1920, 2002))

    v='60in' 
    print('hgt ', v, ' ->', validateHeight(v))
    v='190cm' 
    print('hgt ', v, ' ->', validateHeight(v))
    v='190in' 
    print('hgt ', v, ' ->', validateHeight(v))
    v='190' 
    print('hgt ', v, ' ->', validateHeight(v))

    hcl = '#123abc'
    print('hcl ', hcl, ' -> ', validateHairColor(hcl))
    hcl = '#123abz'
    print('hcl ', hcl, ' -> ', validateHairColor(hcl))
    hcl = '123abc'
    print('hcl ', hcl, ' -> ', validateHairColor(hcl))

    ecl = 'brn'
    print('ecl ', ecl, ' -> ', validateEyeColor(ecl))
    ecl = 'wat'
    print('ecl ', ecl, ' -> ', validateEyeColor(ecl))

    pid = '000000001'
    print('pid ', pid, ' -> ', validatePassportId(pid))
    pid = '0123456789'
    print('pid ', pid, ' -> ', validatePassportId(pid))
    pid = 'abcdefghi'
    print('pid ', pid, ' -> ', validatePassportId(pid))


# Main
testFile = "2020/input/input04test.txt"
invalidFile = "2020/input/input04invalid.txt"
validFile = "2020/input/input04valid.txt"
liveFile = "2020/input/input04.txt"
passports = loadPassports(liveFile)
print("Loaded ", len(passports), " passports")

requiredKeys = ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"]
optionalKeys = ["cid"]

(validPassports, invalidPassports) = validatePassports(passports, requiredKeys, optionalKeys)

print("Valid passport count: ", len(validPassports))