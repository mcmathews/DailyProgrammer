using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GameUI
{
	public class AiPlayer : IPlayer
	{
		private const int ROUNDS_PER_DIFFICULTY = 100;
		private const int START_DIFFICULTY = 1;
		private const int NUM_DIFFICULTIES = 15;

		private List<string> possibleWords;
		private int rounds = 0;

		public int GetDifficulty()
		{
			return rounds / ROUNDS_PER_DIFFICULTY + START_DIFFICULTY;
		}

		public string GetGuess(int guessesLeft)
		{
			return possibleWords[0];
		}

		public void TellWords(List<string> possiblePasswords)
		{
			this.possibleWords = possiblePasswords;
		}

		public void TellResult(string guess, int correctLetters)
		{
			possibleWords = possibleWords.FindAll(word => word.Zip(guess, (wl, gl) => wl == gl ? 1 : 0).Sum() == correctLetters);
		}

		public bool ShouldPlayAgain(bool isWon)
		{
			return ++rounds < NUM_DIFFICULTIES * ROUNDS_PER_DIFFICULTY;
		}
	}
}
