# 서문

<br>
<br>

이펙티브 자바(Effective Java 3/E) ITEM 02 번을 기재합니다. 독학으로 공부하고 있어서 이해하지 못한 점을 찾아서 보완하고 있습니다. 틀린 점이 있다면 알려주시면 감사하겠습니다.

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

## ITEM 02 - 생성자 대신 정적 팩토리 메서드를 고려하라

<br>

정적 팩토리와 생성자는 공통된 제약이 하나 있다. 그것은 선택적 매개변수가 많을 때 적절하게 대응하기 어렵다는 점이다. 예컨대 책에서는 필수 매개변수와 선택매개변수가 있다고 할 때 어떻게 코드를 작성하는 것이 좋을까에 대한 예시가 나온다.


<br>
<br>
<br>

### 1. 점층적 생성자 패턴( Telescoping Constructor Pattern )

- 매개변수를 점층적으로 늘려가며 생성자를 만들어주는 패턴


```java
public class NeutritionFacts {
    
    private final int servingSize; // 필수
    private final int servings; // 필수
    private final int calories; // 선택
    private final int fat; // 선택
    private final int sodium; // 선택
    private final int carbohydrate; // 선택
    
    public NeutritionFacts(int servingSize, int servings){
      this(servingSize, servings, 0);
    }

    public NeutritionFacts(int servingSize, int servings, int calories){
      this(servingSize, servings, calories, 0);
    }

    public NeutritionFacts(int servingSize, int servings, int calories, int fat){
      this(servingSize, servings, calories, fat, 0);
    }

    public NeutritionFacts(int servingSize, int servings, int calories, int fat, int sodium){
      this(servingSize, servings, calories, fat, sodium, 0);
    }

    public NeutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }

    public static void main(String[] args) {

        // 1.번 방법 - 기존의 파라미터를 그냥 생성자로 세팅하는 방법
        NeutritionFacts neutritionFacts1 = new NeutritionFacts(240, 8, 100, 0, 35, 27); // 어떤 값이 어디에 들어있는 지 명확하지 않음. IDE에 따라 보여주는 경우도 있음
    
        NeutritionFacts neutritionFacts2 = new NeutritionFacts(240, 8);
    
    }
}

```

<br>

#### 점층적 생성자 패턴의 단점

<br>

- 매개변수가 더 늘어간다면 코드를 작성하기 힘들다.
- 사용자가 매개변수의 순서를 알고 정확하게 값을 넣어 주어야 함
- 사용자가 잘못된 값을 넣을 수 있음(런타임 에러 발생)

<br>

> 점층적 생성자 패턴도 쓸 수 있지만, 매개변수의 개수가 많아지면 클라이언트 코드를 작성하거나 읽기 어렵다.


<br>
<br>
<br>


### 2. 자바 빈즈 패턴 ( JavaBeans Pattern )

매개변수가 없는 생성자로 객체를 만든 후, 세터(Setter) 메서드를 사용하여 원하는 매개변수의 값을 설정하는 패턴

```java

public class NeutritionFacts {
    
    private final int servingSize; // 필수
    private final int servings; // 필수
    private final int calories; // 선택
    private final int fat; // 선택
    private final int sodium; // 선택
    private final int carbohydrate; // 선택
    
    public NeutritionFacts(){}

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public static void main(String[] args) {

        NeutritionFacts neutritionFacts2 = new NeutritionFacts();
        neutritionFacts2.setServingSize(240);
        neutritionFacts2.setServings(8);
        neutritionFacts2.setCalories(100);
        neutritionFacts2.setFat(0);
        neutritionFacts2.setSodium(35);
        neutritionFacts2.setCarbohydrate(27);
    
    }
}

```

<br>

#### 자바빈즈 생성자 패턴의 단점

- 자바빈즈 생성자 패턴은 객체 하나를 만들려면 메서드를 여러 개 호출해야 함
- 객체가 완전히 생성되기 전까지는 일관성이 무너진 상태에 놓이게 됨 (setting을 못한 파라미터도 있을 수 있음)
- 이는 런타임 에러를 발생시키는 심각한 버그(디버깅이 어려움)를 초래함
- 일관성의 이슈로 자바빈즈 패턴에서는 클래스를 불변으로 만들 수 없음
- 만약 스레드를 사용하려면 스레드 안전성을 얻기 위해 프로그래머가 추가 작업을 해주어야 함

<br>

이런 단점을 완화하고자 생성이 끝난 객체를 수동으로 얼리거나(freezing), 얼리기 전에는 사용할 수 없도록 하기도 한다. 하지만 다루기 어려운 방법으로 거의 사용하지 않는다.

<br>
<br>

- **참고**
 
> **불변(Immutable or Immutability)**은 어떠한 변경도 허용하지 않는다는 뜻, 주로 변경을 허용하는 가변(Mutable) 객체와 구분하는 용도로 사용, 대표적으로 String 객체는 한 번 만들어지면 절대 값을 바꿀 수 없는 불변 객체


> **불변식(Invariant)**은 프로그램이 실행되는 동안, 혹은 정해진 기간 동안 반드시 만족해야 하는 조건을 뜻함, 예컨대 리스트의 크기는 반드시 0 이상이어야 하니 만약 한 순간이라도 음수 값이 된다면 불변식이 깨진다고 볼 수 있음, 가변 객체에도 불변식은 존재할 수 있으며 넓게 보면 불변은 불변식의 극단적인 예라고 볼 수 있음

<br>
<br>
<br>

### 3. 빌더 패턴( Builder Pattern )

필수 매개변수만으로 생성자를 호출해 빌더 객체를 얻은 다음 빌더 객체가 제공하는 일종의 세터 메서드를 사용하여 원하는 선택 매개변수를 설정하는 방법, 이는 점층적 생성자 패턴의 안전성과 자바빈즈 패턴의 가독성을 겸비한 패턴이다.


```java
public class NutritionFacts3 {
    
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public static class Builder {

        // 필수 매개변수
        private final int servingSize;
        private final int servings;

        // 선택 매개변수
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val) {
            calories = val;
            return this;
        }

        public Builder fat(int val) {
            fat = val;
            return this;
        }

        public Builder sodium(int val) {
            sodium = val;
            return this;
        }

        public Builder carbohydrate(int val) {
            carbohydrate = val;
            return this;
        }

        public NutritionFacts3 build() {
            return new NutritionFacts3(this);
        }
    }
    
    public NutritionFacts3(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }


    public static void main(String[] args) {
        NutritionFacts3 cocaCola = new NutritionFacts3.Builder(240, 8)
                .calories(100).sodium(35).carbohydrate(27).build();
                        

        System.out.println(cocaCola);
        System.out.println(cocaCola.calories);
    }
}

```

<br>

- 빌더의 세터 메서드들은 빌더 자신을 반환하기 때문에 연쇄적으로 호출이 가능
- 이런 메서드 호출 방식을 물이 흐르듯 연결된다는 뜻으로 **플루언트 API(Fluent API)** 혹은 **메서드 연쇄(Method Chaining)**이라 함
- 빌더패턴은 (파이썬, 스칼라에 있는) **명명된 선택적 매개변수(Named Optional Parameters)를 모방한 것**

<br>
<br>
<br>

#### 3.1 빌더 패턴은 계층적으로 설계된 클래스와 함께 쓰기 좋음

각 계층의 클래스에 관련 빌더를 멤버로 정의해보면 추상 클래스는 추상 빌더를, 구체 클래스는 구체 빌더를 갖게 된다. 

```java



public abstract class Pizza {
    
    public enum Topping { HAM, MUSHROOM, ONION, PEPPER, SAUSAGE }

    final Set<Topping> toppings; 

    abstract static class Builder<T extends Builder<T>> { // 자기 자신의 하위 클래스를 매개변수로 받는(재귀적)
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T addTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self(); // T 타입으로 반환
        }

        abstract Pizza build();

        // 하위 클래스는 이 메서드를 재정의(overriding)하여 this를 반환하도록 함
        // 즉 하위 클래스마다 build와 self를 재정의하여 반환해줌
        protected abstract T self();
    }

    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone(); // ITEM 50 참고
    }

}


-------------------------------------------------------------------------------

2. Pizza의 하위 클래스 1

public class NyPizza extends Pizza {
    
    public enum Size{ SMALL, MEDIUM, LARGE }

    private final Size size;

    public static class Builder extends Pizza.Builder<Builder> {
        private final Size size;

        public Builder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        @Override
        public NyPizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
    
    private NyPizza(Builder builder) {
        super(builder);
        size = builder.size;
    }

}

-----------------------------------------------------------------

3. Pizza의 하위 클래스 2

public class Calzone extends Pizza {
    
    private final boolean sauceInside;

    public static class Builder extends Pizza.Builder<Builder>
    {
        private boolean sauceInside = false; // 기본값

        public Builder sauceInsize() {
            sauceInside = true;
            return this;
        }

        @Override
        public Calzone build() {
            return new Calzone(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private Calzone(Builder builder) {
        super(builder);
        sauceInside = builder.sauceInside;
    }
}

-----------------------------------------------------

public class PizzaTest {
    
    public static void main(String[] args) {
        NyPizza nyPizza = new NyPizza.Builder(NyPizza.Size.SMALL)
                .addTopping(Pizza.Topping.HAM)
                .addTopping(Pizza.Topping.ONION)
                .build();
    
        Calzone calzonePizza = new Calzone.Builder()
                .addTopping(Pizza.Topping.HAM)
                .sauceInside()
                .build();

        System.out.println(nyPizza);
        System.out.println(calzonePizza);
    }
}

```

- Pizza.Builder 클래스는 재귀적 타입 한정을 이용한 제네릭 타입(T)
- 추상 메서드 self를 더해 하위 클래스에서는 형변환의 수고를 덜어주면서 메서드 연쇄를 지원 가능, 이를 시뮬레이트한 셀프 타입(Simulated Self-Type) 관용구라고 함
- 뉴욕피자는 사이즈를, 칼조네 피자는 소스를 넣을 지 선택하는 매개변수를 필수로 받음
- NyPizza.Builder는 NyPizza를 반환하고, Calzone.Builder는 Calzone을 반환,
- 하위 클래스의 메서드가 상위 클래스의 메서드가 정의한 반환 타입이 아닌 그 하위 타입을 반환하는 기능을 공변 반환 타이핑(Convariant Return Typing)이라 함, 이를 사용하면 클라이언트가 형변환에 신경쓰지 않고도 빌더를 사용할 수 있음
- 빌더를 이용하면 가변인수(VarArgs) 매개변수를 여러 개 사용할 수 있음
  
<br>
<br>
<br>

#### 빌더 패턴의 장단점

<br>

##### 장점

<br>

  - **유연성**
  - 가독성
  - 안정성

<br>
  
##### 단점

<br>

  - **빌더선언부를 만들어야 함**
  - **성능이 민감한 상황**에서는 **사소한 빌더의 생성비용이 문제**가 될 수 있음
  - **매개변수가 4개 이상은 되어야 가치**를 가짐
    - 결국 개발이 진행됨에 따라 API의 매개변수가 늘어날 경우가 더 많기 때문에 빌더 패턴을 사용하는 것이 좀 더 장기적인 관점에서 효율적임

<br>
<br>
<br>

### 핵심

> 생성자나 정적팩토리가 처리해야 할 매개변수가 많다면 빋더 패턴을 고려하는 것이 좋다. 매개변수 중 다수가 필수가 아니거나 같은 타입이면 더 그렇다. 빌더는 점층적 생성자보다 클라이언트 코드를 읽고 쓰기가 훨씬 간결하고 자바빈즈보다 훨씬 안전하다.