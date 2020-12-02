def calcFuel(mass):
    return int(int(mass)/3) - 2

input = open("input01.txt")

masses = input.readlines()
fuel = 0
for mass in masses:
    fuel += calcFuel(int(mass))

print("Total fuel: {}".format(fuel))
