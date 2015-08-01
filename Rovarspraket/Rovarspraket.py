import sys
print(''.join([c+('o'+c.lower(),'')[c.lower() in 'aeiou' or not c.isalpha()] for c in sys.argv[1]]))
