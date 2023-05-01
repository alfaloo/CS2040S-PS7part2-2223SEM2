public class Test {
    static int a() {
        int count = 0;
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                System.out.println("a: " + i);
                count++;
            }
        }
        return count;
    }
    static int b() {
        int count = 0;
        for (int i = 0; i < 10 || i % 2 == 0; i++) {
            System.out.println("b: " + i);
            count++;
        }
        return count;
    }
    public static void main(String[] args) {
        System.out.println(a());
        System.out.println(b());
    }
}
