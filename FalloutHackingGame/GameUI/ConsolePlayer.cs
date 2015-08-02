using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GameUI
{
	class ConsolePlayer : IPlayer
	{

		public int GetDifficulty()
		{
			int input;
			Console.Write("Please enter a difficulty (1 - 20): ");
			while (!int.TryParse(Console.ReadLine(), out input))
			{
				Console.Write("Please enter a valid number: "); 
			}
			return input;
		}

		public string GetGuess(int guessesLeft)
		{
			Console.WriteLine(guessesLeft + " guesses left");

			Console.Write("Enter the Password: ");
			return Console.ReadLine();
		}

		public void TellWords(List<string> possiblePasswords)
		{
			Console.WriteLine("Here are the possible passwords: ");
			foreach (string password in possiblePasswords)
			{
				Console.WriteLine(password);
			}
		}

		public void TellResult(string guess, int correctLetters)
		{
			Console.WriteLine(correctLetters + "/" + guess.Length + " correct");
		}

		public bool ShouldPlayAgain(bool isWon)
		{
			if (isWon)
			{
				Console.WriteLine("You Win!");
			}
			else
			{
				Console.WriteLine("You Suck!");
			}

			Console.WriteLine("Play again? ");
			return !Console.ReadLine().ToLower().StartsWith("n");
		}
	}
}
