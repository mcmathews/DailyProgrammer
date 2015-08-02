using GameCoreLib;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GameUI
{
	class Program
	{

		public static void Main(string[] args)
		{
			IPlayer player;
			if (args.Length > 0 && args[0] == "-a") {
				player = new AiPlayer(); 
			}
			else
			{
				player = new ConsolePlayer();
			}

			var stats = new Dictionary<int, int>();

			GameCore core = null;
			do
			{
				var difficulty = player.GetDifficulty();
				core = GameCoreFactory.GetInstance(difficulty);

				player.TellWords(core.GameWords);

				int wordLength = core.GameWords[0].Length;

				while (core.GuessesLeft > 0 && !core.IsWon)
				{
					string currentGuess = player.GetGuess(core.GuessesLeft);
					int correctLetters = core.Guess(currentGuess);
					player.TellResult(currentGuess, correctLetters);
				}

				if (!stats.ContainsKey(difficulty))
				{
					stats.Add(difficulty, 0);
				}

				if (core.IsWon)
				{
					stats[difficulty]++;
				}

			} while (player.ShouldPlayAgain(core.IsWon));

			foreach (var stat in stats)
			{
				Console.WriteLine(stat.Key + ": " + stat.Value);
			}

			Console.WriteLine("Press any key to exit");
			Console.ReadKey();
		}
	}
}
