# 서문

<br>
<br>

이펙티브 자바(Effective Java 3/E) ITEM 04 번을 기재합니다. 독학으로 공부하고 있어서 이해하지 못한 점을 찾아서 보완하고 있습니다. 틀린 점이 있다면 알려주시면 감사하겠습니다.

<br>
<br>

- **참고 자료** 

  <br>

  - **이펙티브 자바 (Effective Java 3/E 조슈아 블로크 저)**
  - **강의** : https://www.youtube.com/watch?v=X7RXP6EI-5E&list=PLfI752FpVCS8e5ACdi5dpwLdlVkn0QgJJ ( 백기선님 유튜브 강의 ITEM09까지 )

<br>

<p align="center">
<img src='http://drive.google.com/uc?export=view&id=1qlHmvJG3fKJRyti2PtDxBQLq5HHQMaCi' width="450px" height="500px"/></p>


<br>
<br>


---------------------

<br>
<br>

# 제 1장 객체 생성과 파괴

<br>

이 장에서는 아래와 같은 큰 3가지 관점을 제시한다.

<br>

- 객체를 만들어야 할 때와 만들지 말아야 할 때를 구분
- 올바른 객체 생성 방법과 불필요한 생성을 피하는 방법
- 제때 파괴됨을 보장하고 파괴 전에 수행해야 할 정리 작업

<br>
<br>
<br>

## ITEM 05 - 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라

<br>

많은 클래스가 하나 이상의 자원에 의존한다. 가령 맞춤법 검사기는 사전(Dictionary)에 의존하고, 이런 클래스를 정적 유틸리티 클래스(ITEM04)와 싱글톤(ITEM03)으로 구현한 모습을 드물지 않게 볼 수 있다.


<br>
<br>

### 정적 유틸리티와 싱글톤

```java
5.1 정적 유틸리티를 잘못 사용한 예 - 유연하지 않고 테스트하기 어려움

public class SpellChecker{
    private static final Lexicon dictionary = ...;

    private SpellChecker(){} // 객체 생성 방지

    public static boolean isValid(String word){...}
    public static List<String> suggestions(String typo){...}
}

-----------------------------------------------------------------

5.1.1 정적 유틸리티 메서드를 사용한 예시

public class SpellChecker {
    
    private static final Lexicon dictionary = new KoreanDictionary(); // KoreanDictionary만을 사용함

    private SpellChecker() {}

    
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

-----------------------------------------------------------------

5.2 싱글톤을 잘못 사용한 예 - 유연하지 않고 테스트하기 어렵다

public class SellChecker{
    private final Lexicon dictionary = ...;
    public static SpellChecker INSTANCE = new SpellChecker(...);

    private SpellChecker(...){}
    
    public boolean isValid(String word){...}
    public List<String> suggestions(String typo){...}
}

------------------------------------------------------------------

5.2.1 싱글톤을 사용한 예시

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
    
    private SpellChecker() {}

    public static final SpellChecker INSTANCE = new SpellChecker(){};

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

interface Lexicon {}

class KoreanDictionary implements Lexicon{}
```

<br>

- 두 방식 모두 사전을 단 하나만 사용한다고 가정한다는 점
- 만약에 여러 사전을 사용해야 한다면 매우 유연하지 못한 경우
- 사용하는 자원에 따라 동작이 달라지는 클래스에는 정적 유틸리티 클래스나 싱글톤 방식은 적합하지 않음

<br>
<br>


### 의존 객체 주입

- 클래스(SpellChecker)가 여러 자원 인스턴스를 지원해야 하며, 클라이언트가 원하는 자원을 사용해야 함
- 또한 스레드 상황에서도 생성자가 만들어 질 때 고정되기 때문에 불변을 보장
- **이 때 사용하는 패턴이 인스턴스를 생성할 때 생성자에 필요한 자원을 넘겨주는 방식(DI, 의존성 주입)**


```java
5.3 의존 객체 주입은 유연성과 테스트 용이성을 높여줌

public class SpellChecker{
    private final Lexicon dictionary;

    public SpellChecker(Lexicon dictionary){ // 생성자를 만들 때 dictionary를 주입받아서 사용
        this.dictionary = dictionary;
    }

    public boolean isValid(String word){...}
    public List<String> suggestions(String typo){...}
}

-----------------------------------------------

5.3.1 의존 객체 주입을 활용한 예시

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
        SpellChecker EnglishDictionary = new SpellChecker(new EnglishDictionary());
        koreanSpellChecker.isValid("hello");
        EnglishDictionary.isValid("hello");

    }

}

interface Lexicon {}

class KoreanDictionary implements Lexicon{}

class EnglishDictionary implements Lexicon{}
```
<br>

- 이 방법을 사용하면, 자원이 몇 개든 의존 관계가 어떻든 상관없이 잘 작동
- 불변(ITEM17)을 보장하여(같은 자원을 사용하려는) 여러 클라이언트가 의존 객체들을 안심하고 공유할 수 있음
- 의존 객체 주입은 **생성자**, **정적팩토리**(ITEM01), **빌더**(ITEM02) 모두 똑같이 응용 가능
  - 여기서 파생한 방법이 있는데 생성자에 자원 팩토리를 넘겨주는 방식(**펙토리 메서드 패턴**)
    - JAVA 8에서 소개한 <code>Supplier<T></code> 인터페이스가 팩토리를 표현한 완벽한 예

```java
// Tile의 하위 클래스가 들어올 수 있다. 즉 Tile의 하위클래스를 받아 거기에 맞는 생성을 해줄 수 있음
Mosaic create(Supplier<? extends Tile> tileFactory){...} 
```
<br>

- 의존 객체 주입이 유연성과 테스트 용이성을 개선해줌
- but, 의존성이 많은 큰 프로젝트에서는 코드를 어지럽게 만들기도 함
- Spring, Dagger, Guice와 같은 프레임워크를 사용하면 이를 해결할 수 있음 

<br>
<br>


### 핵심

<br>

> 클래스가 내부적으로 하나 이상의 자원에 의존하고, 미래에 그럴 가능성이 있다면 싱글톤이나 정적 유틸리티 클래스는 부적합하다. 대신 **필요한 자원**을(혹은 그 자원을 만들어주는 팩토리를) **생성자**(혹은 **정적 팩토리**나 **빌더**)에 넘겨주자. 

>의존 객체 주입이라는 기법은 클래스의 **유연성, 재사용성, 테스트 용이성**을 개선해준다.

<br>
<br>


