# AoC Day 21, 2020
DEBUG=False

ingredientCount = {}
allergen2ingredient = []
allergens = set()

def loadFile(filename):
    global allergen2ingredient
    global allergens
    global ingredientCount
    with open("2020/input/" + filename) as file:
        for line in file:
            entry = line.strip().split('(contains ')
            ingredients = set()
            ingredients.update(entry[0].split())
            allergens = set()
            entry1 = entry[1].replace(')', '').split(',')
            for allergen in entry1:
                a1 = allergen.strip()
                allergens.add(a1)

            for ingredient in ingredients:
                if ingredient in ingredientCount:
                    ingredientCount[ingredient] += 1
                else:
                    ingredientCount[ingredient] = 1
                if ingredient in ingredient2allergens.keys():
                    # reduce to intersection
                    currentAllergens = ingredient2allergens[ingredient]
                    same = set()
                    same = currentAllergens.intersection(allergens)
                    ingredient2allergens[ingredient] = same
                    print("Update: ", ingredient, " -> ", same)
                else:
                    # first time - add allergens
                    ingredient2allergens[ingredient] = allergens
                    print("First:  ", ingredient, " -> ", allergens)

            # for allergen in allergens:
            #     if allergen in allergen2ingredients.keys():
            #         # already mapped
            #         allergen2ingredients[allergen].update(ingredients)
            #     else:
            #         # no previous entry
            #         allergen2ingredients[allergen]= ingredients

            print('', end='') # just for debugging and setting a break...


if __name__ == "__main__":
    # loadFile('input21test1.txt')
    loadFile('input21.txt')
    print("The ingredient to allergens map:")

    total = 0
    for (ingredient, allergens) in ingredient2allergens.items():
        print(ingredient, ": ", end='')
        for allergen in allergens:
            print(allergen, ", ", end='')
        if len(allergens) == 0:
            total += ingredientCount[ingredient]
        print()

    print()
    print("Total: ", total)
