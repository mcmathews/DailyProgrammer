using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GameUI
{
	interface IPlayer
	{
		int GetDifficulty();

		string GetGuess(int guessesLeft);

		void TellWords(List<string> possiblePasswords);

		void TellResult(string guess, int correctLetters);

		bool ShouldPlayAgain(bool isWon);
	}
}
