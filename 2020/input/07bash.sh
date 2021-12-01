#!/usr/local/bin/bash

IFS="
"

myBag="$1"

input="$2"

# containers bag returns containing bags
function containers {
	grep $1 $input | pcol -Fbags 1 | grep -v $1
}


#bags=$(grep $myBag $input | pcol -Fbags 1 | grep -v $myBag)
bags=$(containers $myBag)
allBags=$bags
echo Bags: $bags

until [ -z "$bags" ]; do
	for b in $bags; do
		b2=$(grep $b $input | pcol -Fbags 1 | grep -v $b)
		allBags="$allBags $b2"
	done
done

for b in $bags
do
	b2=$(grep $b $input | pcol -Fbags 1 | grep -v $b)
	allBags=$(echo $allBags $b2)
	echo B = $b '->' B2: $b2
done

echo $allBags
count=$(echo $allBags | sed 's/ $//' | sed 's/  /\
/g' | sort -u | wc -l)
echo "Count = $count"

