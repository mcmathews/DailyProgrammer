package practice.reddit;

import javafx.util.Pair;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class SubstitutionCyptanalysis {

	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);

		List<String> inputWords = Arrays.asList(in.nextLine().split(" "));

		int numLines = Integer.parseInt(in.nextLine());

		List<Pair<Character, Character>> substitutions = new ArrayList<>();
		for (int i = 0; i < numLines; i++) {
			String sub = in.nextLine();
			substitutions.add(new Pair<>(sub.charAt(1), sub.charAt(0)));
		}

		long start = new Date().getTime();
		Dictionary dictionary = new Dictionary(Files.readAllLines(Paths.get("words.txt")));
		System.out.println("Build dict: " + (new Date().getTime() - start));

		start = new Date().getTime();

		List<String> cipherWords = new ArrayList<>(inputWords);
		Collections.sort(cipherWords, (w1, w2) -> w2.length() - w1.length());

		List<List<Pair<Character, Character>>> subs = decrypt(dictionary, substitutions, cipherWords);
		if (!subs.isEmpty()) {
			subs.forEach(sub ->
				System.out.println(String.join(" ", inputWords.stream()
						.map(inputWord -> makeSubstitutions(sub, inputWord))
						.collect(Collectors.toList())))
			);

		} else {
			System.out.println("None found");
		}
		System.out.println("Total: " + (new Date().getTime() - start));
	}

	private static List<List<Pair<Character, Character>>> decrypt(Dictionary dictionary, List<Pair<Character, Character>> substitutions, List<String> cipherWords) {
		List<List<Pair<Character, Character>>> returnSubs = new ArrayList<>();

		List<String> subbedWords = cipherWords.stream()
				.map(cipherWord -> makeSubstitutions(substitutions, cipherWord))
				.collect(Collectors.toList());

		if (!isValidSubstitutions(dictionary, subbedWords)) {
			return returnSubs;
		}

		if (isFullySubstituted(subbedWords)) {
			returnSubs.add(substitutions);
			return returnSubs;
		}

		List<List<Pair<Character, Character>>> possibleSubs = new ArrayList<>();
		for (int i = 0; i < subbedWords.size(); i++) {
			String subbedWord = subbedWords.get(i);
			if (subbedWord.chars().allMatch(c -> Character.isLowerCase(c) || c == '\'')) {
				continue;
			}
			Set<String> possibleWords = dictionary.getPotentialWordMatches(subbedWord.length(), getKnownChars(subbedWord));
			wordsLoop: for (String possibleWord : possibleWords) {
				List<Pair<Character, Character>> possibleWordSubs = new ArrayList<>();
				thisWordLoop: for (int j = 0; j < possibleWord.length(); j++) {
					if (possibleWord.charAt(j) != subbedWord.charAt(j)) {
						for (Pair<Character, Character> sub : possibleWordSubs) {
							if (sub.getKey() == subbedWord.charAt(j) && sub.getValue() == possibleWord.charAt(j)) {
								continue thisWordLoop;
							}
							if (sub.getKey() == subbedWord.charAt(j) || sub.getValue() == possibleWord.charAt(j)) {
								continue wordsLoop;
							}
						}
						for (Pair<Character, Character> sub : substitutions) {
							if (sub.getKey() == subbedWord.charAt(j) || sub.getValue() == possibleWord.charAt(j)) {
								continue wordsLoop;
							}
						}
						possibleWordSubs.add(new Pair<>(subbedWord.charAt(j), possibleWord.charAt(j)));
					}
				}
				if (!possibleWordSubs.isEmpty()) {
					possibleSubs.add(possibleWordSubs);
				}
			}
			if (!possibleSubs.isEmpty()) {
				break;
			}
		}


		for (List<Pair<Character, Character>> subs : possibleSubs) {
			subs.addAll(substitutions);

			List<List<Pair<Character, Character>>> foundSubs = decrypt(dictionary, subs, subbedWords);
			returnSubs.addAll(foundSubs);
		}

		return returnSubs;
	}

	private static String makeSubstitutions(List<Pair<Character, Character>> substitutions, String cipherWord) {
		for (Pair<Character, Character> sub : substitutions) {
			cipherWord = cipherWord.replace(sub.getKey(), sub.getValue());
		}
		return cipherWord;
	}

	private static List<Pair<Integer, Character>> getKnownChars(String word) {
		List<Pair<Integer, Character>> knownChars = new ArrayList<>();
		for (int i = 0; i < word.length(); i++) {
			if (Character.isLowerCase(word.charAt(i)) || word.charAt(i) == '\'') {
				knownChars.add(new Pair<>(i, word.charAt(i)));
			}
		}
		return knownChars;
	}

	private static boolean isFullySubstituted(List<String> words) {
		return words.stream().flatMapToInt(String::chars).allMatch(c -> Character.isLowerCase(c) || c == '\'');
	}

	private static boolean isValidSubstitutions(Dictionary dictionary, List<String> subbedWords) {
		for (String word : subbedWords) {
			List<Pair<Integer, Character>> knownChars = getKnownChars(word);
			if (!knownChars.isEmpty()) {
				if (dictionary.getPotentialWordMatches(word.length(), knownChars).isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}

	public static class Dictionary {
		private List<List<Map<Character, Set<String>>>> wordLookupTable;

		public Dictionary(List<String> words) {
			wordLookupTable = new ArrayList<>();
			wordLookupTable.add(0, null);
			int longestWordLength = words.stream().map(String::length).max(Integer::compare).get();
			for (int i = 1; i <= longestWordLength; i++) {
				wordLookupTable.add(i, new ArrayList<>(i));
				for (int j = 0; j < i; j++) {
					wordLookupTable.get(i).add(j, new HashMap<>(27));
					for (char c = 'a'; c <= 'z'; c++) {
						wordLookupTable.get(i).get(j).put(c, new HashSet<>());
					}
					wordLookupTable.get(i).get(j).put('\'', new HashSet<>());
				}
			}

			for (String word : words) {
				for (int i = 0; i < word.length(); i++) {
					wordLookupTable.get(word.length()).get(i).get(word.charAt(i)).add(word);
				}
			}
		}

		public Set<String> getPotentialWordMatches(int length, List<Pair<Integer, Character>> knownChars) {
			long start = new Date().getTime();
			if (knownChars == null || knownChars.isEmpty()) {
				return new HashSet<>();
			}

			List<Set<String>> potentialWordsSets = new ArrayList<>();
			for (Pair<Integer, Character> knownChar : knownChars) {
				potentialWordsSets.add(this.wordLookupTable.get(length).get(knownChar.getKey()).get(knownChar.getValue()));
			}
			System.out.println("\t**********");
			System.out.println("\tNum chars: " + knownChars.size());
			System.out.println("\tWords: " + potentialWordsSets.stream().flatMap(Set::stream).count());

			Set<String> potentialWords = new HashSet<>(potentialWordsSets.get(0));
			for (Set<String> singleCharPotentialWords : potentialWordsSets) {
				potentialWords.retainAll(singleCharPotentialWords);
			}
			System.out.println("\tTime: " + (new Date().getTime() - start));
			return potentialWords;
		}
	}
}
