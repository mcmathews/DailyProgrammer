import random
c="bcdfghjklmnpqrstvwxyz"
v="aeiou"
print("".join([random.choice(globals()[lower(i)]) for i in input()]))