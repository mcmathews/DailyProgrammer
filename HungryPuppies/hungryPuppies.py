import itertools

def happiness(sizes):
    hs = 0
    for i, size in enumerate(sizes):
        left = sizes[i-1] if i > 0 else sizes[i+1]
        right = sizes[i+1] if i < len(sizes)-1 else sizes[i-1]
        if size > left and size > right:
            hs += 1
        elif size < left and size < right:
            hs -= 1

    return hs

sizes = list(map(int, input().split()))

perms = list(itertools.permutations(sizes))
i, h = sorted(list(map(lambda x: (x[0], happiness(x[1])), enumerate(perms))), key=lambda a: a[1], reverse=True)[0]
print(h)
print(" ".join(map(str, perms[i])))
