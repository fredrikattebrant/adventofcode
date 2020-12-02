def calcFuel(mass):
    return int(int(mass)/3) - 2

input = open("input01.txt")
masses = input.readlines()
#masses = [100756]
fuel = 0
for mass in masses:
    f1 = calcFuel(int(mass))
    #print(f1)
    fuel += f1
    while f1 > 0:
        f1 = calcFuel(f1)
        #print(f1)
        if (f1 > 0):
            fuel += f1

print("Total fuel: {}".format(fuel))
