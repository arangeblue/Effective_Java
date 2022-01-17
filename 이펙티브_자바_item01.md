# 서문

<br>
<br>

이펙티브 자바(Effective Java 3/E)라는 책은 그 명성이 높아서 꼭 봐야겠다는 생각을 가지고 있었다. 경영학에서는 여러 책이 있지만 보통 피터 드러커의 경영의 실제, 경제학에서는 맨큐의 경제학, 통계학에서는 ISLR(An Introduction to Statistical Learning)과 PRML(Pattern Recognition & Machien Learning, Bishop) 등 각 학문에서 유명한 서적들이 있다. 이 책도 위의 작품들과 마찬가지로 자바 언어를 사용하는 사람에겐 꼭 읽어야 하는 100선과 같은 것이다. 

<br>

이 책의 주된 내용은 서적의 제목 그대로 효과적으로 자바(명료, 간단)를 사용하기 위해서 어떠한 것들을 고려해보아야 하는지를 여러 관점에 따라 서술하고 예시를 가지고 설명한다. 우리가 개발을 할 때에 있어서 개발의 환경은 내부적 요소, 외부적 요소가 천차만별이며 우리는 어떤 코드 진행과 아키텍쳐가 우리 조직, 상황, 목적에 맞는지를 판단해야 한다. 단순한 for문이 더 좋을 때도 있고, switch문이 더 효율적일 때도 있다. 가장 중요한 점은 계속적으로 바뀌는 환경 속에서 문제를 파악하고 어떤 아키텍쳐와 코드구성이 문제를 효과적이고 효율적으로 풀어 갈 수 있는가이다. 우리는 이 책을 읽으므로 다양한 상황에 대처하는 코드안목과 기준을 제시할 수 있게 되는 것이다. 

<br>

이 책을 다 읽고 난 후의 나의 모습을 기대하며 하루에 하나의 아이템을 보는 것을 목표로 시작해본다. 

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

- 객체를 만들어야 할 때와 만들지 말아야 할 때를 구분
- 올바른 객체 생성 방법과 불필요한 생성을 피하는 방법
- 제때 파괴됨을 보장하고 파괴 전에 수행해야 할 정리 작업

<br>
<br>
<br>

## ITEM 01 - 생성자 대신 정적 팩토리 메서드를 고려하라

우리가 클래스의 인스턴스를 얻는 방법은 기본적으로 public 생성자이다. 그러나 다른 방법이 또 있는데 그것은 정적 팩토리 메서드이다(static factory method). 이 정적 팩토리 메서드의 역할은 단순히 그 클래스의 인스턴스를 반환하는 메서드를 의미한다.

<br>
<br>
<br>

### 정적 팩토리 메서드의 장점

<br>
<br>

  - **1. 이름을 가질 수 있음**
  - **2. 호출될 때마다 인스턴스를 새로 생성하지 않아도 됨**
  - **3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있음**
  - **4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있음**
  - **5. 정적 팩토리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 됨**


<br>
<br>
<br>

#### 1. 이름을 가질 수 있다(더욱 명확한 설명이 가능)

우리가 클래스의 생성자만을 가지고 반환될 객체를 설명하기에 너무 제한적이다. 하지만 정적 팩토리 메서드를 사용하면 생성자를 통해 나올 반환값을 더 잘 설명할 수 있는 장점이 있다. 

```java
1. BigInteger(int, int, Random)

2. BigInteger.probablePrime

이 중 probablePrime이라는 이름으로부터 우리는 메서드가 소수를 판단하여 어떤 것을 반환할지를 알 수 있다.
```

```java

public Item01(String name){}
public Item01(String address){} // error

--------------------------------------------------

public Item01(){}

public static Item01 withName(String name) { 

  Item01 instance = new Item01();
  instance.name = name;
  return instance;
}

public static Item01 withAddress(String address){
  Item01 instance = new Item01();
  instance.address = addresss;
  return instance;
}
```

<br>

- **참고 :** **메서드 시그니처**는 메서드 명과 파라미터의 순서, 타입, 개수를 의미

만약에 한 클래스에 시그니처가 같은 생성자가 여러 개라면? 이를 정적 팩토리 메서드로 바꾸고 각각의 차이를 드러내는 이름을 지어주는 것이 더욱 명료한 코드를 만들 수 있다.

<br>
<br>
<br>

#### 2. 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다


불변 클래스는 인스턴스를 미리 만들어 두거나 새로 생성한 인스턴스를 캐싱(데이터나 값을 미리 복사해 놓는 임시 장소를 가리킴, 접근시간을 절약하며 추가적인 연산없이 데이터 접근 가능)하여 재활용하는 식으로 불필요한 객체 생성을 피할 수 있다. 만약에 객체생성(생성비용이 큰)이 빈번할 때, 이 생성의 비용을 줄일 수 있는 방법으로 불필요한 생성을 줄일 수 있다.

<br>
<br>

##### 불변 클래스 ( Immutable )

<br>

- 변경이 불가능한 클래스를 의미
- Thread-safe함
- 래퍼런스 타입의 객체 
- Boolean, String, Integer, Float, Long...

<br>

##### 참고 : 플라이웨이트 패턴 ( Flywight pattern ) - 비슷한 기법

<br>

- 동일하거나 유사한 객체들 사이에 가능한 많은 데이터를 서로 공유하여 사용하도록 하여 메모리 사용량을 최소화하는 소프트웨어 디자인 패턴
- 어떤 클래스의 인스턴스 하나만 가지고 여러 가상 인스턴스를 제공하고 싶을 때 사용
- 인스턴스를 가능한 대로 공유시켜 불필요한 객체 생성을 막아 메모리 낭비를 줄임
- '공유(Sharing)'를 통하여 대량의 객체들을 효과적으로 지원하는 방법

  - Flyweight : 공유에 사용할 클래스들의 인터페이스(API)를 선언
  - ConcreteFlyweight : Flyweight의 내용을 정의. 실제 공유될 객체
  - FlyweightFactory : 해당 공장을 사용해서 Flyweight의 인스턴스를 생성 또는 공유해주는 역할


<p align="center">
<img src='http://drive.google.com/uc?export=view&id=1MafnhGtQ7QW1i76ZfNsB6eAD6wOcKs6K' width="400px" height="350px" /></p>

<br>

```java
private static final Item01 sg = new Item01(); // static으로 불변 인스턴스를 미리 만들어 두고

public static Item01 getInstance(){ // 정적 메서드를 통해 이를 반환하면 된다.
  return sg;
}

public static void main(String[] args){
  Item01 instance = Item01.getInstance();
}

------------------------------------------------------

class SingletonEx{
  private static SingletonEx singletonInstance = null;

  private SingletonEx(){}

  static SingletonEx getInstance(){
    if(singletonInstance == null){
      singletonInstance = new SingletonEx();
    }
    
    return singletonInstance;
  }


  public static void main(String[] args){
    SingletonEx instance1 = SingletonEx.getInstance();
    SingletonEx instance2 = SingletonEx.getInstance();

    System.out.println(instance1 == instance2); // 주소비교, 같은 객체인지 확인
  }
}


> true
```

<br>
<br>
<br>

#### 3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.

이를 통해 반환할 객체의 클래스를 자유롭게 선택할 수 있는 **유연성**을 가질 수 있다. 반환 타입의 하위 타입 인스턴스를 만들어도 되므로 리턴 타입을 인터페이스로 지정하고 그 인터페이스의 구현체는 API로 노출 시키지 않지만 구현체를 바꾸면서 유연하게 만들 수 있다.

- java.util.Collections가 이에 해당
- 45개의 인터페이스의 구현체가 제공됨(non-public)
- 즉 인터페이스 뒤에 감추어져 있고, 인터페이스만 노출되어 있기에 API를 줄였을 뿐 아니라 **개념적 무게**까지 줄일 수 있음
  - **개념적 무게** : 프로그래머가 어떤 인터페이스가 제공하는 API를 사용할 때 알아야 할 개념의 개수와 난이도

<br>

- JAVA 8부터 인터페이스에 public static 메서드를 추가할 수 있음
- JAVA 9부터 인터페이스에 private static 메서드를 추가할 수 있음

```java
public interface Item01Interface {


    public static Item01 getItem() { // 자바 8부터 인터페이스에 public 정적메서드를 추가할 수 있음, 또한 구현체도 추가 가능
        return new Item01();
    }

    private static Item01 getItem2() { // 자바 9부터는 private static method를 추가할 수 있음
        return new Item01();
    }

}
```

<br>
<br>
<br>

#### 4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있음

반환 타입의 하위 타입이기만 하면 어떤 클래스의 객체를 반환하든 상관 없음

- EnumSet 클래스는 생성자 없이 public static 메서드, allOf(), of() 등을 제공
- 그 안에 리턴하는 객체의 타입은 enum 타입의 개수에 따라 RegularEnumSet(64개 이하) 또는 JumboEnumSet(65개 이상)으로 달라짐
- 클라이언트는 이 두 클래스의 존재를 모름

```java
class Item01Sub extends Item01{}

public static Item01 getInstance(boolean flag){ 
  return flag ? new Item01() : new Item01Sub(); // 매개변수에 따라 하위 타입의 클래스를 반환할 수도 있음
}
```

<br>
<br>
<br>

#### 5. 정적 팩토리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 됨

이 장점도 3,4 처럼 유연성에 대한 장점

- 서비스 제공자 프레임워크를 만드는 근간
  - 서비스 제공자 프레임워크는 서비스의 구현체를 대표하는 서비스 인터페이스와 구현체를 등록하는데 사용하는 프로바이더 등록 API, 클라이언트가 해당 서비스의 인스턴스를 가져갈 때 사용하는 서비스 엑세스 API가 필수로 필요함

  - 서비스 인터페이스(service interface) : 구현체의 동작을 정의
  - 제공자 등록 API(provider registration API) : 제공자가 구현체를 등록할 때 사용함
  - 서비스 접근 API(service access API) : 클라이언트가 서비스의 인스턴스를 얻을 때 사용

<br>

- JDBC 예시
  - getConnect를 하면 반환되는 객체가 DB마다 다 다름
  - 즉 getConnect안에서 특정 객체를 얻어서 인터페이스의 구현체로 사용함
  - 이는 getConnect의 작성 시점에 객체가 없어도 사용할 때 특정 객체를 얻어서 구현체로 사용함을 의미

    - JDBC의 Connection : 서비스 인터페이스 역할
    - DriverManager.registerDriver : 제공자 등록 API 역할
    - DriverManager.getConnect : 서비스 접근 API 역할



<br>
<br>
<br>

### 정적 팩토리 메서드의 단점

- **1. 상속을 하려면 public이나 protected 생성자가 필요하니 정적 팩토리 메서드만 제공하면 하위 클래스를 만들 수 없음**
- **2. 정적 팩토리 메서드는 프로그래머가 찾기 어려움**

<br>
<br>
<br>

#### 1. 상속을 하려면 public이나 protected 생성자가 필요하니 정적 팩토리 메서드만 제공하면 하위 클래스를 만들 수 없음

Collections 프레임워크에서 제공하는 편의성 구현체는 상속할 수 없음, 오히려 불변 타입인 경우나 상속 대신 컴포지션을 권장하기 때문에 장점이라고 할 수 있음.

<br>
<br>
<br>

#### 2. 정적 팩토리 메서드는 프로그래머가 찾기 어려움

생성자처럼 API에 설명이 명확하게 드러나지 않아서(JAVA DOC API의 설명) 사용자가 정적 팩토리 메서드 방식 클래스를 인스턴스화할 방법을 알아야 함

<br>

- **from** : 매개변수를 하나 받아서 해당 타입의 인스턴스를 반환하는 형변환 메서드

```java
Date d = Date.from(instant);
```
<br>

- **of** : 여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집계 메서드
  
```java
Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);
```
<br>

- **valueOf** : from과 of의 더 자세한 버전

```java
BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);
```
<br>

- **instance** 혹은 **getInstance** : (매개변수를 받는다면) 매개변수로 명시한 인스턴스를 반환하지만 같은 인스턴스임을 보장하지 않음

```java
StackWalker kim = StackWalker.getInstance(options);
```
<br>

- **create** 혹은 **newInstance** : instance 혹은 getInstance와 같지만, 매번 새로운 인스턴스를 생성해 반환함을 보장

```java
Object newArray = Array.newInstance(classObject, arrayLen);
```
<br>

- **getType** : getInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩토리 메서드를 정의할 때 사용, "Type"은 팩토리 메서드가 반환할 객체의 타입

```java
FileStore fs = Files.getFileStore(path)
```
<br>

- **newType** : newInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩토리 메서드를 정의할 때 사용, "Type"은 팩토리 메서드가 반환할 객체의 타입

```java
BufferedReader br = Files.newBufferedReader(path);
```
<br>

- **type** : getType과 newType의 간결한 버전

```java
List<Complaint> litany = Collections.list(legacyLitany);
```

<br>
<br>
<br>

### ITEM01의 핵심 

> **정적 팩토리 메서드와 public 생성자는 각자의 쓰임새가 있으니 상대적인 장단점을 이용하고 사용하는 것이 좋음, 그렇다고 하더라도 정적 팩토리를 사용하는 게 유리한 경우가 더 많으므로 무작정 public 생성자를 제공하던 습관이 있다면 고치자**


