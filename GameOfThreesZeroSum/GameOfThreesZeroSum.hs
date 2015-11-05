-- https://www.reddit.com/r/dailyprogrammer/comments/3rhzdj/20151104_challenge_239_intermediate_a_zerosum/

main = do
    inputX <- getLine
    let x = read inputX
    let solution = solve x
    if null solution
        then
            putStrLn "Impossible"
        else
            putStrLn $ show $ solution

solve :: Integral a => a -> [[a]]
solve x = gameOfThrees x []

gameOfThrees :: Integral a => a -> [a] -> [[a]]
gameOfThrees 1 a =
    if isZeroSum a
        then [a]
        else []
gameOfThrees x a =
    if x > 1
        then
            filter (\y -> not (null y)) (flatmap (\d -> gameOfThrees ((x + d) `quot` 3) (a ++ [d])) [d | d <- [-2..2], isDivisibleBy3 (x + d), x + d >= 3])
        else
            []


isDivisibleBy3 :: Integral a => a -> Bool
isDivisibleBy3 x =
    x `mod` 3 == 0

isZeroSum :: Integral a => [a] -> Bool
isZeroSum a =
    sum a == 0

flatmap _ [] = []  
flatmap f (x:xs) = f x ++ flatmap f xs