package case1;

import java.util.List;

/**
 *@title : SpellChecker
 *@author : wikyubok 
 *@date : "2022-01-07 17:08:08"
 *@description : SpellChecker / item05 - static 유틸 클래스에 대한 코드
*/

public class SpellChecker {
    
    private static final Lexicon dictionary = new KoreanDictionary(); // KoreanDictionary만을 사용함

    private SpellChecker() {

    }
    
    public static boolean isValid(String word) {
        throw new UnsupportedOperationException();
    }

    public static List<String> suggestions(String typo) {
        throw new UnsupportedOperationException();
    }


    public static void main(String[] args) {
        SpellChecker.isValid("hello");
    }

}

interface Lexicon{}

class KoreanDictionary implements Lexicon{}