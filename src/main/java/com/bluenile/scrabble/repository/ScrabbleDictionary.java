package com.bluenile.scrabble.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ScrabbleDictionary {

    private static List<String> dictionary = null;

    /**
     * Loads up the list of valid Scrabble words from given dictionary
     */
    static {
        try {
            log.info("Attempting to load Scrabble Dictionary");

            dictionary = Files.readAllLines(Path.of("src\\main\\resources\\Dictionary.txt"));

            log.info("Scrabble Dictionary successfully loaded");
        }
        catch (IOException e)
        {
            log.error("[SCRABBLE DICTIONARY] [LOAD] - Error while trying to load the dictionary: {}", e.getMessage());
            dictionary = new ArrayList<>();
        }
    }

    /**
     * Filters all valid words existing in the Scrabble dictionary
     * @param letterCombinations all the letter combinations obtained
     *                           from the original string provided
     * @return A list of valid words out of the Scrabble dictionary
     */
    public List<String> scrabbleWords(Set<String> letterCombinations)
    {
        log.info("[SCRABBLE DICTIONARY] [SCRABBLE WORDS] - Obtained {} letter combinations", letterCombinations.size());

        letterCombinations.retainAll(dictionary);

        log.info("[SCRABBLE DICTIONARY] [SCRABBLE WORDS] - Retrieved {} valid words", letterCombinations.size());

        return sortWordsByScrabbleScore(letterCombinations);
    }

    /**
     * Sorts all provided words by the Scrabble score from highest value to lowest
     * @param words all valid words from the Scrabble dictionary
     * @return A list of words ordered by their Scrabble score
     */
    private List<String> sortWordsByScrabbleScore(Set<String> words) {
        final int [] letterScores = {// a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p,  q, r, s, t, u, v, w, x,  y, z
                                        1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};

        IntUnaryOperator letterScore = letter -> letterScores[letter - 'a'];

        Function<String,  Integer> score = word -> word.chars().map(letterScore).sum();

        Map<Integer, Set<String>> wordsByScore = words.stream()
                .collect(Collectors.groupingBy(score, () -> new TreeMap<>(Comparator.reverseOrder()), Collectors.toSet()));

        List<String> returnList = new ArrayList<>();
        wordsByScore.values().forEach(returnList::addAll);

        return returnList;
    }
}
