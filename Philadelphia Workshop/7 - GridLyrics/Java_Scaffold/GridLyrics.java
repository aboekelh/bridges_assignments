import bridges.base.Color;
import bridges.base.ColorGrid;
import bridges.connect.Bridges;

public class GridLyrics {

       public static String[] splitLyrics(String lyrics) {		// splits raw lyrics string into a parsable array
		lyrics = lyrics.replaceAll("\\[.+\\]","");	        // removes the titles of song stage ex [Intro]
		lyrics = lyrics.trim();
		String[] lyricsSplit = lyrics.split("\\s+");		// split words by whitespace

		for (int i = 0; i < lyricsSplit.length; i++) {		// clears special characters from individual terms
			lyricsSplit[i] = lyricsSplit[i].replaceAll("\\W+$", "");
			lyricsSplit[i] = lyricsSplit[i].replaceAll("^\\W+", "");
			lyricsSplit[i] = lyricsSplit[i].trim();
		}

		return lyricsSplit;
	}

       public static void main(String[] args) throws Exception {
		Bridges bridges = new Bridges(7, "USER_NAME", "API_KEY");

		/* TODO:
		 * Grab a song from the bridges server and get its lyrics
		 * Upon doing so, call the splitLyrics method, passing the lyrics
		 * to remove any punctuation or tags in the lyrics, returning
		 * an array of single cleaned up terms
		 *
		 * After that, create a new Bridges ColorGrid object, passing
		 * in the word count of the lyrics in as the dimensions.
		 * You may also specify a default color if you wish, if not
		 * it defaults to white
		 */
		

		// input your own RGBA values if you wish
		Color matchColor = new Color(0, 0, 0, 1);	

		/* TODO:
		 * Iterate over the lyrics, checking to see if there are matching terms
		 * if so, set that coordinate to a color representing a match.
		 *
		 * Each row and columin will represent an individual word in the lyrics,
		 * meaning your main diagonal of your matrix should be completely filled in
		 * as that represents each word compared against itself.
		 */

		// replace YourGridHere with your ColorGrid object and uncomment the line below
		//bridges.setDataStructure(YourGridHere);
		bridges.visualize();
	}
	
}
