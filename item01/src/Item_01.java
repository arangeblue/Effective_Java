import java.util.EnumSet;

public class Item_01 {
    
    String name;
    int num;
    
    private static final Item_01 sg = new Item_01();

    public Item_01() { // 기본 생성자
    }

    public Item_01(String name) { // 전통적인 생성자
        this.name = name;
    }

    public Item_01(String name, int num) { // 위의 생성자와 시그니처가 다른 생성자
        this.name = name;
        this.num = num;
    }

    public static Item_01 withName(String name) { // 클래스를 반환하지만 메서드 이름을 지정했기 때문에 어떤 시그니처로 어떤 반환값이 나올지 예상이 가능함
        return new Item_01(name);
    }

    public static Item_01 getItem() {
        return sg;
    }
    
    public static Item_01 withNum(int num) { // 시그니처의 제약이 적다
        Item_01 cls = new Item_01();
        cls.num = num;
        return cls;
    }

    public static Item_01 getItem(boolean flag){
        return flag ? new Item_01() : new ItemExtends(); 
    }
    

    public static void main(String[] args) {
        Item_01 Item_011 = new Item_01("wi");
        Item_01 Item_012 = Item_01.withName("wi"); // 이렇게 생성하는 것이 더욱 읽기 좋다.
        Item_01 Item_013 = Item_01.getItem();
        Item_01 Item_014 = Item_01.getItem();
        Item_01 Item_015 = Item_01.getItem(false);

        // System.out.println(Item_011);
        // System.out.println(Item_012);
        // System.out.println(Item_013);
        // System.out.println(Item_014);
        // System.out.println(Item_015);

        EnumSet<Color> Colors = EnumSet.allOf(Color.class);
        EnumSet<Item_01.Color> of = EnumSet.of(Color.BLUE,Color.WHITE);
        System.out.println(Colors);
        System.out.println(of);
    }
    

    static class ItemExtends extends Item_01{}
    enum Color{
        RED, BLUE, WHITE
    }
}
