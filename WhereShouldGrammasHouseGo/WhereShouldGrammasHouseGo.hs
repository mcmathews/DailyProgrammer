-- https://www.reddit.com/r/dailyprogrammer/comments/3l61vx/20150916_challenge_232_intermediate_where_should/
-- This code doesn't provide optimal solution
-- Only gives an estimate in O(nlog(n)) time

import Data.List

main = do
    inputN <- getLine
    let n = read inputN
    coords <- readCoords n
    let tx = maxX coords
        bx = minX coords
        ty = maxY coords
        by = minY coords
        topLeft = (bx, ty)
        topRight = (tx, ty)
        bottomRight = (tx, by)
        dists = [(x, (dist topLeft x) + (dist topRight x) + (dist bottomRight x)) | x <- coords]
    putStrLn $ show $ smallestDiff dists
    
    

readCoords :: (Floating a, Read a) => Int -> IO [(a, a)]
readCoords n = 
    if n == 0
        then return []
        else do
            input <- getLine
            let coord = read input
            rest <- readCoords (n-1)
            return $ coord : rest

dist :: Floating a => (a, a) -> (a, a) -> a
dist (x1, y1) (x2, y2) =
    (x1 - x2)^2 + (y1 - y2)^2

maxX coords = maximum [x | (x, _) <- coords]
maxY coords = maximum [y | (_, y) <- coords]
minX coords = minimum [x | (x, _) <- coords]
minY coords = minimum [y | (_, y) <- coords]

smallestDiff :: (Floating a, Floating b, Ord c, Floating c) => [((a, b), c)] -> ((a, b), (a, b))
smallestDiff dists = 
    smallestDiff' (sortOn snd dists) ((0, 0), (0, 0)) (100000)

smallestDiff' :: (Ord c, Floating c) => [((a, b), c)] -> ((a, b), (a, b)) -> c -> ((a, b), (a, b))
smallestDiff' [_] best bestDiff = best
smallestDiff' dists best bestDiff = 
    let rem = tail dists
        (coord1, dist1) = head dists
        (coord2, dist2) = head rem
        diff = dist2 - dist1
        in
            if diff < bestDiff
                then smallestDiff' rem (coord1, coord2) diff
                else smallestDiff' rem best bestDiff

