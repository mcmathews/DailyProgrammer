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
			allWords = new Dictionary<int, List<string>>();
			using (var reader = new StreamReader(@"C:\Users\mcmsp_000\Documents\GitHub\DailyProgrammer\FalloutHackingGame\GameCoreLib\enable1.txt"))
			{
				string line;
				while ((line = reader.ReadLine()) != null)
				{
					if (!allWords.ContainsKey(line.Length))
					{
						allWords[line.Length] = new List<string>();
					}
					allWords[line.Length].Add(line);
				}
			}
		}

		public static GameCore GetInstance(int difficulty)
		{
			var numWords = 5 * difficulty;
			var wordLength = 5 + difficulty;

			var gameWords = new List<string>();

			if (wordLength <= 10)
			{
				var possibleWords = allWords[wordLength];
				while (gameWords.Count < numWords)
				{
					var possibleWord = possibleWords[rand.Next(possibleWords.Count)];
					if (!gameWords.Contains(possibleWord))
					{
						gameWords.Add(possibleWord);
					}
				}
			}
			else
			{
				var possibleWords = new List<string>(allWords[wordLength]);
				for (var i = 0; i < numWords; i++)
				{
					var wordIndex = rand.Next(possibleWords.Count);
					gameWords.Add(possibleWords[wordIndex]);
					possibleWords.RemoveAt(wordIndex);
				}
			}

			return new GameCore(gameWords, gameWords[rand.Next(numWords)]);
		}
	}

	public class GameCore
	{
		private string correctWord;

		public List<string> GameWords { get; private set; }
		public int GuessesLeft { get; private set; }
		public bool IsWon { get; private set; }
		public string CorrectWord
		{
			get
			{
				if (IsWon || GuessesLeft == 0)
				{
					return correctWord;
				}
				else
				{
					throw new InvalidOperationException("CHEATER!!!!!");
				}
			}
			private set
			{
				correctWord = value;
			}
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
			var numCorrect = guess.Zip(correctWord, (gl, cl) => gl == cl ? 1 : 0).Sum();
			GuessesLeft--;
			IsWon = numCorrect == correctWord.Length;
			return numCorrect;
		}
	}
}