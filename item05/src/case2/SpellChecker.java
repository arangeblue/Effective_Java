package case2;

import java.util.List;

/**
 *@title : SpellChecker
 *@author : wikyubok 
 *@date : "2022-01-07 17:24:39"
 *@description : SpellChecker / item05 싱글톤으로 구현, 만약에 사용리소스에 따라 다른 인스턴스가 필요하다면 싱글톤 패턴은 부적합
*/

public class SpellChecker {

    private final Lexicon dictionary = new KoreanDictionary();

    int num;
    int count;
    int ban;


    public SpellChecker num(int num) {
        setNum(num);
        return this;
    }

    public SpellChecker count(int count) {
        setCount(count);
        return this;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setCount(int count) {
        this.count = count;
    }
    

    private SpellChecker() {

    }

    public static final SpellChecker INSTANCE = new SpellChecker() {
    };

    public boolean isValid(String word) {
        throw new UnsupportedOperationException();
    }

    public List<String> suggestions(String typo) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        SpellChecker.INSTANCE.isValid("hello");
    
        SpellChecker.INSTANCE.num(10);
    }



}

interface Lexicon {
}

class KoreanDictionary implements Lexicon{}
