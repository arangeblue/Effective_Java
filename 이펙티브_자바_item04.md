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

## ITEM 04 - 인스턴스화를 막으려거든 private 생성자를 사용하라

<br>

정적 메서드와 정적 필드만을 담은 클래스는 객체지향적인 사고는 아니지만 그래도 쓰임새가 있음

예컨대,

<br>

- java.lang.Math
- java.util.Arrays
  - 기본타입값이나 배열 관련 메서드를 모아둔 예시

- java.util.Collections
  - 특정 인터페이스를 구현하는 객체를 생성해주는 정적 메서드(혹은 팩토리)를 모아둘 수 있음
  - JAVA 8 부터 이런 메서드를 인터페이스에 넣을 수 있음

- final 클래스와 관련된 메서드를 모아 놓을 때
  - final 클래스를 상송해서 하위 클래스에 메서드를 넣는 건 불가능하기에


<br>
<br>

- 정적 멤버만 담은 유틸리티 클래스는 인스턴스를 만들려는 목적으로 설계한 것은 아님
- but, 생성자를 명시하지 않으면 컴파일러가 자동으로 기본 생성자를 만들어 줌
- 즉, 매개변수를 받지 않는 public 생성자가 만들어짐
- 이 때, 사용자는 자동으로 생성되었는지 구분할 수 없음
- 실제로 공개된 API들 에서도 이처럼 의도치 않게 인스턴스화할 수 있게 된 클래스가 종종 목격
- **추상 클래스로 만드는 것으로는 이런 인스턴스화를 막을 수 없음**

<br>


> 즉 생성자를 명시하지 않았을 때, 컴파일러가 자동으로 생성자를 만들어준 것이 혼돈을 야기함


<br>
<br>
<br>

### 인스턴스화 방지 방법 - private 생성자를 추가

- private 생성자를 추가하면 컴파일러는 명시된 생성자가 있다고 판단하며 생성자가 없을 때만 자동 생성해주는 컴파일러는 자동 생성을 하지 않음

```java
public class UtilityClass{

    // 기본 생성자가 만들어지는 것을 막음(인스턴스화 방지용)
    // 주석을 써주어 호출하는 생성자가 아님을 알림
    private UtilityClass(){
        throw new AssertionError();
    }

    ...
}

------------------------------------

public class UtilityClass{
    private UtilityClass(){
        throw new AssertionError();
    }

    public void test(){
        System.out.println("유틸리티 클래스 test 출력");
    }
}

public class Test{
    public static void main(String[] args){
        // 불가능
        UtilityClass instance = new UtilityClass();
        instance.test();
    }
}
```

<br>

- 꼭 Error를 던질 필요는 없지만, 클래스 안에서 실수라도 생성자를 호출하지 않도록 해줌
- but, 생성자가 있는데 목적은 인스턴스화를 막는 용도이기 때문에 호출은 막아야 하지만 방법이 없으므로 주석을 통해 알려줌
- **이 방법은 상속도 불가능하게 하는 효과가 있음**

<br>


> 즉 **컴파일러는 생성자가 없을 때 자동으로 생성자를 만들어주는데 이는 혼란을 야기함**

> **private 생성자를 만들어 줌으로 컴파일러에게 생성자가 있다고 인식시켜줌과 동시에 private 생성자이기에 하위 클래스가 상위 클래스의 생성자에 접근할 수 없음**


