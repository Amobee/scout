package com.amobee.commons.utils;

import java.util.*;

/**
  * @author Anand Natrajan
  */
public class StringUtils
{
	/**
	 * The indexOf method finds the first location of the pattern in the
	 * target, or -1 if the pattern is absent. This implementation uses the
	 * Scout algorithm described in the paper "Scout Algorithm for Fast
	 * Substring Matching", by A. Natrajan and M. Anand.
	 *
	 * @param target the sequence of characters to check
	 * @param pattern the sequence of characters to find
	 * @return the first index of the pattern, -1 if no match
	 * @see http://www.anandnatrajan.com/papers/CACM20a.pdf
	 * @see http://www.anandnatrajan.com/papers/ARXIV20.pdf
	 */
	public static int indexOf(final char[] target, final char[] pattern)
	{
		final int targetLen = target.length;
		final int patternLen = pattern.length;
		final int lenDiff = targetLen - patternLen;
		int targetPos = -1;
		char scoutChar = '\u0000';
		int scoutPos = -1;

		// Loop through the target string.
		while (++targetPos <= lenDiff)
		{
			boolean scootUp = false;
			// Brute-force move forward to find the next scout.
			int patternPos = -1;
			char currChar = '\u0000';
			while (!scootUp && ++patternPos < patternLen &&
				((currChar = pattern[patternPos]) == target[targetPos + patternPos]))
			{
				// If pattern position char is equal to scout char,
				// move target position forward.
				final int tmpPos = scoutPos - patternPos - 1;
				if (targetPos < tmpPos && currChar == scoutChar)
				{
					targetPos = tmpPos;
					scootUp = true;
				}
			}
			// Found it!
			if (patternPos == patternLen)
				return targetPos;
			if (scootUp)
				continue;

			// If we're here, we found a scout. Let it march forward.
			final int tmpPos = lenDiff + patternPos;
			scoutChar = currChar;
			scoutPos = targetPos + patternPos;
			while (++scoutPos <= tmpPos &&
				scoutChar != target[scoutPos])
			{
			}
			if (scoutPos > tmpPos)
				return -1;
			// If we're here, the scout found a match.
			targetPos = scoutPos - patternPos - 1;
		}
		return -1;
	}

	/**
	 * The indexOf method finds the first location of the pattern in the
	 * target, or -1 if the pattern is absent.
	 *
	 * @param target the sequence of characters to check
	 * @param pattern the sequence of characters to find
	 * @return the first index of the pattern, -1 if no match
	 */
	public static int indexOf(final String target, final String pattern)
	{
		if (target == null || pattern == null)
			return -1;
		return indexOf(target.toCharArray(), pattern.toCharArray());
	}

	// Everything past this point is just for testing alone. For testing,
	// we will compare the return value from the Scout algorithm against
	// the built-in Java method for a variety of simple, weird and
	// real-life cases.

	// Simple cases.
	private static final String[][] testCases =
	{
		{ null, "abc" },
		{ "abc", null },
		{ null, null },
		{ "", "abc" },
		{ "abc", "" },
		{ "", "" },
		{ null, "" },
		{ "", null },
		{ "cbba", "aabcaacbcbaabccbbcab" },
		{ "b", "aaabaaaa" },
		{ "aaabccc", "aaaabcccdxxxxxxx" },
		{ "aaabccc", "aaaabdccxxxbccxxxxbcccxxxx" },
		{ "aabca", "xxxxxxxxxxxxxxxxxxxxxxxxxaabcaxxxxxxxxxxxxxxxxxxxxxxxxx" },
		{ "abc", "acxxabcabbc" },
		{ "abc", "acxxxabcabbc" },
		{ "abc", "acdabcabbc" },
		{ "b", "aaab" },
		{ "aaaaaxaa", "aaaaaxaxaabbbbbbbbbb" },
		{ "aa", "aaab" },
		{ "ab", "aaab" },
		{ "aba", "abacaacbabccba" },
		{ "abbcba", "aabbccaaabbcbaccacaabbcbbccc" },
		{ "aba", "aabcaacbabaabbacbb" },
		{ "aba", "aabcaacbaba" },
		{ "abaccab", "aabcaacbabaabbacbb" },
		{ "cbba", "aabcaacbcbaabccbbcab" },
		{ "heagJZiEjON24MaN", "ghedhheaeehadfgdfhbhfcf" },
		{ "heagJZiEjON24MaN", "dbbefbcfchdfbbeceheabdfddedefdeebfcacfbaaehffffbfbddffbfbhagagfdccaafbebgfdebacabchdbcfchhbehceghdaaefdhbaffdhhahbdghaecgcafecbcdbabdhadececagefgaefdacecdbdggggcabcgedacebfddgbfhadfgfddebfbahhfeaegdhahbhfhhfebhggbbddhhffhgadfbdhfebaccechaedehhhfgfbheefhfabdegceffbfafaebdhaeadafbcgdfcdbgfdhfacbadegcaebabdhagbhbfbfbcbhddeeadggadgddbfddbfbahchaabbfbgeccdcadcagfafeeabbbchfgegagdbdbcdachbgbacefhgeagacagaaebgbhdgbfdhbcdbabdcbgchgecgabebacgcdhffbahaaefeaefbgagahgecchccfacfbeeghhgaddcdfebbaedfbbhabgffehbbfachedbccbcabcabdcbbhghcagfhbbffdagdbhgaffcdgcadfhgdeccdcgafgdadcaccbgebdecfbghaghdfcbghdfgghchdecchhaffhhdbdahfhedhfffcffdfadhagfhcehcedeafcagchafagehabfcaeabhddeacaachddhdbggfhdecfabehbhfggcgdaegcbfebcafghbbbbfedcbacfcbcececcgfhdafbdedbaabgddbceccbfaheddhddfbfbfhgchbhgcabcgfhdhaecfgdbhgbhebhafdgbdbgehaeghdgggfaechheafhfbfahbefedbfehhhcefbhbgcbafdfcfcbeghffaggfaebafhhcbadaabfebbdgdhhbfbchhhcahehdbgcbgehbgacgceccfccgfacbdbhfhdhggcdddedabehbeaeadebdefchgegghcfhhddgdgffgfgghedhheaeheagJZiEjON24MaNehadfgdfhbhfcf" },
		{ "ox ju", "the quick brown fox jumps over the lazy dog" },
		{ "expunge", "waltz nymph for quick jigs vex bud" },
		{ "nymph", "pack my box with five dozen liquor jugs" },
		{ "pack my box with five dozen liquor jugs", "pack my box with five dozen liquor jugs" },
		{ "jugse", "pack my box with five dozen liquor jugs" },
		{ "hi five street", "pack my box with five dozen liquor jugs" },
		{ "ay, there's the rub", "To die, to sleep.  To sleep, perchance to dream-ay, there's the rub, For in that sleep of death what dreams may come, " },
	};

	// Weird cases, all involving Unicode.
	private static final String[][] testWeirdCases =
	{
		{ "\u0926", "\u0906\u0928\u0902\u0926" },
		{ "a", "\u0906\u0928\u0902\u0926" },
		{ "ab\u0201c", "adab\u0101caaa" },
		{ "aaa", "aaaa\uD83D\uDF01bbbb" },
		{ "bbb", "aaaa\uD83D\uDF01bbbb" },
		{ "\uD83D\uDF01", "aaaa\uD83D\uDF01bbbb" },
		{ "\u0926", "\u0126\u0826\u0F26\u1026\u1126\u8026\u8126\uFF26\u1126" + new String(Character.toChars(0x10026)) + new String(Character.toChars(0x80026)) + new String(Character.toChars(0xF0026)) + new String(Character.toChars(0x100026)) },
		{ "\u0026", "\u0126\u0826\u0F26\u1026\u1126\u8026\u8126\uFF26\u1126" + new String(Character.toChars(0x10026)) + new String(Character.toChars(0x80026)) + new String(Character.toChars(0xF0026)) + new String(Character.toChars(0x100026)) },
		{ "a", "\u0126\u0826\u0F26\u1026\u1126\u8026\u8126\uFF26\u1126" + new String(Character.toChars(0x10026)) + new String(Character.toChars(0x80026)) + new String(Character.toChars(0xF0026)) + new String(Character.toChars(0x100026)) },
	};

	// Real-life cases, from Hamlet. The first entry is always the target,
	// and each of the remaining entries is a pattern to search.
	private static final String[] hamletCases =
	{
		"To be, or not to be, that is the question: Whether 'tis nobler in the mind to suffer The slings and arrows of outrageous fortune, Or to take arms against a sea of troubles, And by opposing end them? To die-to sleep, No more; and by a sleep to say we end The heart-ache, and the thousand natural shocks That flesh is heir to: 'tis a consummation Devoutly to be wish'd. To die, to sleep.  To sleep, perchance to dream-ay, there's the rub, For in that sleep of death what dreams may come, When we have shuffled off this mortal coil, Must give us pause. There's the respect That makes calamity of so long life.  For who would bear the whips and scorns of time, The oppressor's wrong, the proud man's contumely, The pangs of dispriz'd love, the law's delay, The insolence of office, and the spurns That patient merit of the unworthy takes, When he himself might his quietus make With a bare bodkin? Who would these fardels bear, To grunt and sweat under a weary life, But that the dread of something after death, The undiscover'd country, from whose bourn No traveller returns, puzzles the will, And makes us rather bear those ills we have Than fly to others that we know not of?  Thus conscience does make cowards of us all, And thus the native hue of resolution Is sicklied o'er with the pale cast of thought, And enterprises of great pith and moment, With this regard their currents turn awry And lose the name of action. Soft you now, The fair Ophelia! Nymph, in thy orisons Be all my sins remember'd.",
		"To be, or not to be, that is the question",
		"The slings and arrows of outrageous fortune",
		"To sleep, perchance to dream",
		"ay, there's the rub",
		"shuffled off this mortal coil",
		"the proud man's contumely",
		"bare bodkin",
		"the dread of something after death,",
		"conscience does make cowards of us all",
		"Be all my sins remember'd.",
	};

	/**
	 * Test method for simple, weird and real-life cases for indexOf.
	 */
	private static void testCorrectness()
	{
		// Make one giant list of all test cases.
		final List<String[]> allCases = new ArrayList<>();
		for (final String[] testCase : testCases)
			allCases.add(testCase);
		for (final String[] testCase : testWeirdCases)
			allCases.add(testCase);
		for (int i = 1; i < hamletCases.length; i++)
		{
			final String[] testCase = { hamletCases[i], hamletCases[0] };
			allCases.add(testCase);
		}
		for (final String[] testCase : allCases)
		{
			final String pattern = testCase[0];
			final String target = testCase[1];
			// Use the Java built-in String indexOf as the baseline result.
			final int stdResult = (target == null || pattern == null) ? -1 : target.indexOf(pattern);
			System.out.println(
				(pattern == null ? "null" : ("'" + pattern + "'"))
				+ " in "
				+ (target == null ? "null" : ("'" + target + "'"))
				+ "? " + stdResult);
			try
			{
				// Invoke the Scout indexOf method to check.
				final int res = indexOf(target, pattern);
				// The result should match the baseline.
				if (res != stdResult)
					System.out.println("\tmismatch: " + res + " != " + stdResult);
				else
					System.out.println("\tmatch: " + res + " == " + stdResult);
			}
			catch (Throwable t)
			{
				System.out.println("\tfail");
			}
		}
	}

	// MAIN method. All it does is invoke test cases.
	public static void main(String... args)
	{
		System.out.println("\n-- CORRECTNESS ----------");
		testCorrectness();
	}
}
