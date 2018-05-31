/*
	Sample solution for GridLyrics Assignment
 */

import bridges.base.Color;
import bridges.base.ColorGrid;
import bridges.connect.Bridges;

import java.util.ArrayList;
import java.util.Hashtable;

public class GridLyrics {

    public static String[] splitLyrics(String lyrics) {				// splits raw lyrics string into a parsable array
		lyrics = lyrics.replaceAll("\\[.+\\]","");	        // removes the titles of song stage ex [Intro]
		lyrics = lyrics.trim();
		String[] lyricsSplit = lyrics.split("\\s+");

		for (int i = 0; i < lyricsSplit.length; i++) {					// clears special characters from individual terms
			lyricsSplit[i] = lyricsSplit[i].replaceAll("\\W+$", "");
			lyricsSplit[i] = lyricsSplit[i].replaceAll("^\\W+", "");
			lyricsSplit[i] = lyricsSplit[i].trim();
		}

		return lyricsSplit;
	}

	public static String[][] splitLines(String lyrics) {
		lyrics = lyrics.replaceAll("\\[.+\\]", "");    // removes the titles of song stage ex [Intro]
		lyrics = lyrics.trim();
		String[] lyricsSplit = lyrics.split("\\n+");
		String[][] corpus = new String[lyricsSplit.length][];
		for (int i = 0; i < corpus.length; i++) {                    // clears special characters from individual terms
		    corpus[i] = splitLyrics(lyricsSplit[i]);
		}

		return corpus;
	}

    public static int termFrequency(String term, String[] document) {
        int tf = 0;

        for (String word : document) {
            tf += term.equalsIgnoreCase(word) ? 1 : 0;
        }

        return tf;
    }

    public static boolean hasTerm(String term, String[] document) {
        for (String word : document) {
            if (term.equalsIgnoreCase(word))
                return true;
        }
        return false;
    }

    public static int documentsContainingTerm(String term, String[][] corpus) {
        int n = 0;

        for (String[] document : corpus) {
            n += hasTerm(term, document) ? 1 : 0;
        }

        return n;
    }

    public static double inverseDocumentFrequency(String term, String[][] corpus) {
        return Math.log(corpus.length / (1 + documentsContainingTerm(term, corpus)));
    }

    public static double termFrequencyInverseDocumentFrequency(String term, String[] document, String[][] corpus) {
        return termFrequency(term, document) * inverseDocumentFrequency(term, corpus);
    }

    public static String[] getUniqueTerms(String[][] corpus) {
        ArrayList<String> uniqueTerms = new ArrayList<>();

        for (String[] document : corpus) {
            for (String term : document) {
                if (!uniqueTerms.contains(term))
                    uniqueTerms.add(term);
            }
        }

        return uniqueTerms.toArray(new String[0]);
    }

    public static Hashtable<String, Double> vectorize(String[] document, String[][] corpus, String[] uniqueTerms) {
        Hashtable<String, Double> vector = new Hashtable<>();

        for (String term : uniqueTerms) {
            vector.put(term, termFrequencyInverseDocumentFrequency(term, document, corpus));
        }

        return vector;
    }

    public static float cosine(Hashtable<String, Double> v1,  Hashtable<String, Double> v2) {
        return (float) (dotProduct(v1, v2)/(norm(v1) * norm(v2)));
    }

    public static double dotProduct(Hashtable<String, Double> v1,  Hashtable<String, Double> v2) {
        double sum = 0;

        for (String key : v1.keySet()) {
            sum += v1.get(key) * v2.get(key);
        }

        return sum;
    }

    public static double norm(Hashtable<String, Double> vector) {
        return Math.sqrt(dotProduct(vector, vector));
    }


	public static void main(String[] args) throws Exception {
		Bridges bridges = new Bridges(7, "bridges_workshop", "1298385986627");
        String song = Bridges.getSong("Bohemian Rhapsody").getLyrics();
		String[] lyrics = splitLyrics(song);									                // returns already split and cleaned array of the lyrics

		int wordCount = lyrics.length;															// makes the for loops and matrix initializations a bit cleaner, could be removed
		ColorGrid grid = new ColorGrid(wordCount, wordCount);

		Color matchColor = new Color(0, 0, 0, 1);								// input your own RGBA values if you wish
		Color defaultColor = new Color(255, 255, 255, 1);

		for (int i = 0; i < wordCount; ++i) {
		    for (int j = 0; j < wordCount; ++j) {
			    if (lyrics[i].equalsIgnoreCase(lyrics[j]))
			        grid.set(i, j, matchColor);
			    else
			        grid.set(i, j, defaultColor);
			}
		}

		bridges.setTitle("Song Grids");
		bridges.setDataStructure(grid);
		bridges.visualize();


		String[][] corpus = splitLines(song);
		String[] uniqueTerms = getUniqueTerms(corpus);	// returns unique terms
		int amountOfDocuments = corpus.length;
		Hashtable<String, Double>[] documentVectors = new Hashtable[amountOfDocuments];

		grid = new ColorGrid(amountOfDocuments, amountOfDocuments);

		for (int i = 0; i < amountOfDocuments; i++) {						//gets a hashtable representing the tfidf values of all terms in the corpus per document
			documentVectors[i] = vectorize(corpus[i], corpus, uniqueTerms);
		}

		for (int i = 0; i < amountOfDocuments; i++) {
			for (int j = 0; j < amountOfDocuments; j++) {
                // calculates the cosine between the vectors and stores it in the corresponding index of the matrix
                float cos = cosine(documentVectors[i], documentVectors[j]);
                grid.set(i, j, new Color(matchColor.getRed(), matchColor.getGreen(), matchColor.getBlue(), cos));
            }
		}
        bridges.setDataStructure(grid);
		bridges.visualize();
	}
}
