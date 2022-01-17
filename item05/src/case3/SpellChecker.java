package case3;

import java.util.List;

public class SpellChecker {
    
    private final Lexicon dictionary;

    public SpellChecker(Lexicon dictionary) {
        this.dictionary = dictionary;
    }

    public boolean isValid(String word) {
        throw new UnsupportedOperationException();
    }

    public List<String> suggestions(String typo) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        SpellChecker koreanSpellChecker = new SpellChecker(new KoreanDictionary());
        SpellChecker testSpellChecker = new SpellChecker(new TestDictionary());
        koreanSpellChecker.isValid("hello");
        testSpellChecker.isValid("hello");

    }



}


interface Lexicon {
}

class KoreanDictionary implements Lexicon{}

class TestDictionary implements Lexicon{}