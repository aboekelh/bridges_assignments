/*
	Sample solution for BinaryMatrix Assignment
 */

import bridges.base.Color;
import bridges.base.ColorGrid;
import bridges.connect.Bridges;

public class BinaryMatrix {

    public static String[] splitLyrics(String lyrics) {				// splits raw lyrics string into a parsable array
		lyrics = lyrics.replaceAll("\\[.+\\]","");			// removes the titles of song stage ex [Intro]
		lyrics = lyrics.trim();
		String[] lyricsSplit = lyrics.split("\\s+");

		for (int i = 0; i < lyricsSplit.length; i++) {					// clears special characters from individual terms
			lyricsSplit[i] = lyricsSplit[i].replaceAll("\\W+$", "");
			lyricsSplit[i] = lyricsSplit[i].replaceAll("^\\W+", "");
			lyricsSplit[i] = lyricsSplit[i].trim();
		}

		return lyricsSplit;
	}

	public static void main(String[] args) throws Exception {
		Bridges bridges = new Bridges(7, "bridges_workshop", "1298385986627" +
                "");
		String song = Bridges.getSong("Bohemian Rhapsody").getLyrics();
		String[] lyrics = splitLyrics(song);													// returns already split and cleaned array of the lyrics

		int wordCount = lyrics.length;															// makes the for loops and matrix initializations a bit cleaner, could be removed
		ColorGrid grid = new ColorGrid(wordCount, wordCount);

		Color defaultColor = new Color(0, 0, 0, 1);								// input your own RGBA values if you wish
		Color matchColor = new Color(255, 255, 255, 1);

		for (int i = 0; i < wordCount; ++i) {
			for (int j = 0; j < wordCount; ++j) {
				if (lyrics[i].equalsIgnoreCase(lyrics[j]))
					grid.set(i, j, matchColor);
				else
					grid.set(i, j, defaultColor);
			}
		}

		bridges.setDataStructure(grid);
		bridges.visualize();
	}
}
