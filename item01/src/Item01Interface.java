public interface Item01Interface {


    public static Item_01 getItem() { // 자바 8부터 인터페이스에 public 정적메서드를 추가할 수 있음, 또한 구현체도 추가 가능
        return new Item_01();
    }

    private static Item_01 getItem2() { // 자바 9부터는 private static method를 추가할 수 있음
        return new Item_01();
    }

}
