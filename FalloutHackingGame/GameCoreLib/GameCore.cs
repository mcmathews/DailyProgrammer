using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace GameCoreLib
{
	public class GameCoreFactory
	{
		private static Dictionary<int, List<string>> allWords;
		private static Random rand = new Random();

		static GameCoreFactory()
		{
			using (var reader = new StreamReader("enable1.txt"))
			{
				string line;
				while ((line = reader.ReadLine()) != null)
				{
					allWords[line.Length].Add(line);
				}
			}
		}

		public static GameCore GetInstance(int difficulty)
		{
			var numWords = 5 + 5 * difficulty;
			var wordLength = 6 + difficulty;

			var gameWords = new List<string>();
			var possibleWords = allWords[wordLength];
			while (gameWords.Count < numWords)
			{
				var possibleWord = possibleWords[rand.Next(possibleWords.Count)];
				if (!gameWords.Contains(possibleWord))
				{
					gameWords.Add(possibleWord);
				}
			}

			return new GameCore(gameWords, gameWords[rand.Next(numWords)]);
		}
	}

	public class GameCore
	{

		public List<string> GameWords { get; private set; }
		public int GuessesLeft { get; private set; }
		public bool IsWon { get; private set; }
		public string CorrectWord
		{
			get
			{
				if (IsWon || GuessesLeft == 0)
				{
					return CorrectWord;
				}
				else
				{
					throw new InvalidOperationException("CHEATER!!!!!");
				}
			}
			private set;
		}

		internal GameCore(List<string> gameWords, string correctWord)
		{
			this.GameWords = gameWords;
			this.GuessesLeft = 4;
			this.IsWon = false;
			this.CorrectWord = correctWord;
		}

		public int Guess(string guess)
		{
			var numCorrect = guess.Zip(CorrectWord, (gl, cl) => gl == cl ? 1 : 0).Sum();
			GuessesLeft--;
			IsWon = numCorrect == CorrectWord.Length;
			return numCorrect;
		}
	}
}