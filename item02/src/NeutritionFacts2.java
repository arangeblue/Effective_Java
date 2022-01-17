public class NeutritionFacts2 {
    private int servingSize;
    private int servings;
    private int calories;
    private int fat;
    private int sodium;
    private int carbohydrate;

    public NeutritionFacts2(){}

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


        // 2.번 방법 - setter를 이용한 파라미터 세팅하는 방법
        // set으로 무엇을 세팅할지 확실하지만, 코드가 길어지고 또한 set을 안해줄 수 있는 상황이 올 수 있어서 안정성에서 떨어지고
        // setter를 사용하기 때문에 Thread-safe하지 않고, 불변으로 만들 수 없음.
        NeutritionFacts2 neutritionFacts2 = new NeutritionFacts2();
        neutritionFacts2.setServingSize(240);
        neutritionFacts2.setServings(8);
        neutritionFacts2.setCalories(100);
        neutritionFacts2.setFat(0);
        neutritionFacts2.setSodium(35);
        neutritionFacts2.setCarbohydrate(27);
    }

}
