public class NeutritionFacts1 {
    
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;
    
    
    public NeutritionFacts1(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }



    public static void main(String[] args) {

        // 1.번 방법 - 기존의 파라미터를 그냥 생성자로 세팅하는 방법
        NeutritionFacts1 neutritionFacts1 = new NeutritionFacts1(240, 8, 100, 0, 35, 27); // 어떤 값이 어디에 들어있는 지 명확하지 않음. IDE에 따라 보여주는 경우도 있음
        System.out.println(neutritionFacts1);
    
        // 2.번 방법 - setter를 이용한 파라미터 세팅하는 방법
        // set으로 무엇을 세팅할지 확실하지만, 코드가 길어지고 또한 set을 안해줄 수 있는 상황이 올 수 있어서 안정성에서 떨어지고
        // setter를 사용하기 때문에 Thread-safe하지 않고, 불변으로 만들 수 없음.
        // NeutritionFacts neutritionFacts2 = new NeutritionFacts();
        // neutritionFacts2.setServingSize(240);
        // neutritionFacts2.setServings(8);
        // neutritionFacts2.setCalories(100);
        // neutritionFacts2.setFat(0);
        // neutritionFacts2.setSodium(35);
        // neutritionFacts2.setCarbohydrate(27);
    

        // 3. 방법 - 빌더패턴으로 세팅하는 방법


    }


}
