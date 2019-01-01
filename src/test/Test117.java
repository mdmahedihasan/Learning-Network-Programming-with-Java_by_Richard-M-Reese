package test;

public class Test117 {
    {
        System.out.print("_INIT");
    }

    static {
        System.out.print("_STATIC");
    }

    Test117() {
        System.out.print("_CONST");
    }

    public static void main(String[] args) {
        System.out.print("_MAIN");
        new Test117();
    }
}
