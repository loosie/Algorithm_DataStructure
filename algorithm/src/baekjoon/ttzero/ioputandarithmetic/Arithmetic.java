package baekjoon.ttzero.ioputandarithmetic;

import java.util.Scanner;

public class Arithmetic {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int a = s.nextInt(), b=s.nextInt();
		s.close();
		
		System.out.println(a+b);
		System.out.println(a-b);
		System.out.println(a*b);
		System.out.println(a/b);
		System.out.println(a%b);
		
	}
}
