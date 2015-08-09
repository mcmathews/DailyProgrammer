def shuffle(a)
	a.length.times do |i|
		j = (rand (a.length - i)) + i
		a[i], a[j] = a[j], a[i]
	end
	a
end