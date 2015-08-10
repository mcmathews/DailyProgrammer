r, n = gets.chomp.split
r = r.to_i
result = if r < 0
        n.chars.map.with_index { |c, i| c.to_i * (-r) ** (n.length - i - 1) }.reduce(&:+).to_s r.abs
    else
        nums = n.chars.map.with_index do |c, i|
            j = n.length - i - 1
            if j % 2 != 0
                r ** (j + 1) - (r - c.to_i) * r ** j
            else
                c.to_i * r ** j
            end
        end
        nums.reduce(&:+).to_s r
    end

puts result