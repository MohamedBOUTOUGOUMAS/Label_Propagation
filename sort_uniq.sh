# Pour le tri
sort -k 1,1 -n in.data > out.data
# Tri est suppression doublant
cut -d ' ' -f 1,2 in.data | awk '{if ($1 >= $2) {print $2, $1} else {print $1, $2}}' | sort -k 1,1 -n |uniq > out-clean.data

