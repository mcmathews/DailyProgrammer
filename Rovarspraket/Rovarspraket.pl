print join '',map {$_=~/[bcdfghj-nq-tv-z]/i?$_.'o'.lc $_:$_} split //,$ARGV[0]