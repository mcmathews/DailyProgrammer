import sys
s=sys.argv[1]
o=""
for c in s:
	o+=c
	if c.isalpha() and c.lower() not in "aeiou":
		o+="o"+c
print(o)
