# 서문

<br>
<br>

이펙티브 자바(Effective Java 3/E) ITEM 03 번을 기재합니다. 독학으로 공부하고 있어서 이해하지 못한 점을 찾아서 보완하고 있습니다. 틀린 점이 있다면 알려주시면 감사하겠습니다.

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

## ITEM 03 - private 생성자나 열거 타입으로 싱글턴임을 보증하라

<br>

- **싱글톤(Singleton)** 이란 인스턴스를 오직 하나만 생성할 수 있는 클래스를 말함
- 싱글톤의 전형적인 예로는 함수(item24)와 같은 무상태(stateless) 객체나 설계상 유일해야 하는 시스템 컴포넌트를 들 수 있음
- 타입을 인터페이스로 정의한 닫음 그 인터페이스를 구현해서 만든 싱글톤이 아니라면 싱글톤 인스턴스를 가짜(mock) 구현으로 대체할 수 없음

<br>

> 클래스를 싱글톤으로 만들면 이를 사용하는 클라이언트를 테스트하기가 어려워 질 수 있음

<br>
<br>

### 1. 싱글톤 생성 방법 - public static final 필드 방식

```java

1. public static final 필드 방식

public class Elvis{
    public static final Elvis INSTANCE = new Elvis();
    private Elvis(){}
    
    public void leaveTheBuilding(){
        System.out.println("호출이 정상적으로 작동합니다.");
    }

    public static void main(String[] args){
        Elvis instance = Elvis.INSTANCE;
        instance.leaveTheBuilding();
    }
}



> 호출이 정상적으로 작동합니다.
```
<br>

- private 생성자는 public static final 필드인 Elvis.INSTANCE를 초기화할 때 딱 한 번만 호출됨
- public 또는 protected 생성자가 없으므로 시스템에 Elvis.INSTANCE가 하나임을 보장할 수 있음
- 그러나 권한이 있는 클라이언트는 리플렉션 API(ITEM 65)인 AccessibleObject.setAccessible을 사용해 private생성자를 호출할 수 있음
- 이를 막을려면 Elvis.INSTANCE가 NULL일 때 한 번만 만들고 만약 객체가 있을 때 다시 생성하는 호출이 발생한다면 예외를 던지게 하면 됨

<br>
<br>

#### public static final 필드 방식의 장단점

<br>
<br>

##### 장점

<br>

  - 해당 클래스가 싱글톤임이 API에 명백하게 들어남
  - final이므로 절대 다른 객체를 참조할 수 없음
  - 간결성

<br>

##### 단점

<br>

  - 리플렉션으로 private 생성자에 접근하여 인스턴스를 생성할 수 있음
  - 대안으로 2번 째 인스턴스가 생성될 때 예외를 던지면 됨

<br>
<br>

### 2. 싱글톤 생성 방법 - 정적 팩토리 방식

```java
2. 정적 팩토리 방식의 싱글톤
public class Elvis2{
    private static final Elvis2 INSTANCE = new Elvis2();
    private Elvis2(){}
    public static Elvis2 getInstance(){
        return INSTANCE;
    }

    public void leaveTheBuilding(){
        System.out.println("호출이 정상적으로 작동합니다.");
    }

    public static void main(String[] args){
        Elvis2 instance = Elvis2.getInstance();
        instance.leaveTheBuilding();
    }
}



>호출이 정상적으로 작동합니다.
```
<br>

- <code>Elvis.getInstance</code> 는 항상 같은 객체의 참조를 반환하므로 제 2의 Elvis 인스턴스란 결코 만들어지지 않음
- 그러나 이 역시 리플렉션을 통한 예외는 똑같이 적용됨

<br>
<br>

#### 정적 팩토리 방식의 싱글톤 장단점

<br>

##### 장점

- API를 바꾸지 않고도 싱글톤이 아니게 변경할 수 있음
  - 유일한 인스턴스를 반환하던 팩토리 메서드가 호출하는 스레드별로 다른 인스턴스를 넘겨주게 할 수 있음
- 원하면 정적 팩토리를 제네릭 싱글턴 팩토리로 만들 수 있음(ITEM30)
- 참조를 공급자(Supplier)로 사용할 수 있음
  - <code>Elvis::getInstance</code> 를 <code>Supplier\<Elvis></code>로 사용(ITEM 43,44)

<br>
<br>

##### 단점

- 리플랙션 API를 통해 private 생성자를 호출할 수 있음

```java

1. 리플렉션 예시

public class SingletonTest {
    public static void main(String[] args) throws  SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        
        Elvis1 instance1 = Elvis.instance;
       
        System.out.println(instance1);


        // 리플렉션을 사용하여 private 생성자를 호출하는 것
        try{
            Constructor<Elvis> constructor = Elvis.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Elvis newInstance = constructor.newInstance();
            
            System.out.println(newInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // supplier로 사용
        Supplier<Elvis2> supplier = Elvis2::getInstance;
        Elvis2 elvis22 = supplier.get();

        System.out.println(elvis22);
    }

}

-----------------------------------------------------

2. 리플렉션을 방지하는 방법

public class Elvis{
    private static final Elvis INSTANCE = new Elvis();
    private Elvis(){
        if(INSTANCE != null){
            throw new AssertionError("새로운 생성자 호출 에러, 싱글톤");
        }
    }
    public static Elvis getInstance(){ return INSTANCE;}
    public void leaveTheBuilding(){ System.out.println("호출이 정상적으로 작동했습니다.");}
    public static void main(String[] args){
        Elvis instance = Elvis.getInstance();
        instance.leaveTheBuilding();

        try{

            Constructor<Elvis> constructor = Elvis.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Elvis newInstance = constructor.newInstance();
            
            System.out.println(newInstance);
        } catch (Exception e) {
            e.printStackTrace();
            
        }finally{
            System.out.println("에러 후 시스템 종료");
        }
    }
    
}



> 호출이 정상적으로 작동했습니다.
> 에러 후 시스템 종료
> 
> java.lang.reflect.InvocationTargetException
> 	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
> 	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:77)
> 	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
> 	at java.base/java.lang.reflect.Constructor.newInstanceWithCaller(Constructor.java:499)
> 	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:480)
> 	at Elvis.main(Elvis.java:25)
> Caused by: java.lang.AssertionError: 새로운 생성자 호출 에러, 싱글톤
> 	at Elvis.<init>(Elvis.java:13)
> 	... 6 more
```

<br>
<br>

### 직렬화 구현

- 위의 두가지 방식으로 직렬화를 하려면 Serializable을 구현한다고 선언하는 것만으로 부족함
- 모든 인스턴스 필드를 일시적(transient)라고 선언하고 readResolve 메서드를 제공해야 함(ITEM89) 
- 이렇게 하지 않으면 **직렬화된 인스턴스를 역직렬화할 때마다 새로운 인스턴스가 생성됨**
- 이를 방지하려면 싱글톤을 보장하는 readResolve 메서드를 만들어야 함

```java
private Object readResolve(){
    // 진짜 Elvis를 반환하고, 가짜 Elvis는 gc에 맡김
    return INSTANCE;
}
```

<br>
<br>

### 싱글톤 생성 방법 3, 열거 타입 방식

```java
1. 열거 타입 방식

public class Test{
    public enum Elvis{
        INSTANCE;
    
        public void leaveTheBuilding(){
            System.out.println("호출이 정상적으로 작동했습니다.");
        }
    }    
    
    public static void main(String[] args){
        Elvis instance = Elvis.INSTANCE; // 아예 생성자가 없...
        instance.leaveTheBuilding();
    }
    
}



> 호출이 정상적으로 작동했습니다.
```
<br>
<br>

#### 열거 타입 방식 장단점

<br>
<br>

##### 장점

<br>

- public 필드 방식과 비슷하지만 더 간결하고 추가 노력 없이 직렬화할 수 있음
- 더 복잡한 직렬화 상황이나 리플렉션 공격에도 제2의 인스턴스가 생기는 일을 완벽하게 막아줌

<br>
<br>

##### 단점

<br>

- 싱글톤이 **Enum 이외의 클래스를 상속해야 한다면 이 방법은 사용할 수 없음**


<br>
<br>

### 핵심

> 대부분 상황에서는 원소가 하나뿐인 **열거 타입이 싱글톤을 만드는 가장 좋은 방법**
> 그러나 상황에 맞게 필드 방식과 정적 팩토리 방식을 쓰면 괜찮다.

<br>
<br>