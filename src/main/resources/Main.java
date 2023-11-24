import java.util.Scanner;

public class Main {
    private double c;  // 멤버 변수로 선언

    public static void main(String[] args) {
        Main mainInstance = new Main();  // 클래스 인스턴스 생성
        mainInstance.calculateAndPrint(Double.parseDouble(args[0]), Double.parseDouble(args[1]));  // 계산 및 출력 메소드 호출

        // getC 메소드를 호출하여 c 값을 얻음
        double result = mainInstance.getC();
        System.out.println(result);
    }

    public void calculateAndPrint(double a, double b) {
//        Scanner s = new Scanner(System.in);

        try {
//            double a = s.nextDouble();
//            double b = s.nextDouble();
            c = a / b;
//            System.out.println(c);
        } catch (Exception e) {
            System.out.println("올바른 숫자를 입력하세요.");
        } finally {
//            s.close();
        }
    }

    public double getC() {
        return c;  // c를 반환하는 메소드
    }
}
