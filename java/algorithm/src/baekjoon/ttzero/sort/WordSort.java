package baekjoon.ttzero.sort;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class WordSort {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());

		HashSet<String> set = new HashSet<String>();

		for (int i = 0; i < n; i++) {
			set.add(br.readLine());
		}

		ArrayList<String> list = new ArrayList<String>(set);

		Collections.sort(list, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if (o1.length() > o2.length())
					return 1;
				else if (o1.length() < o2.length())
					return -1;
				else
					return o1.compareTo(o2);
			}
		});

		for (String s : list) {
			System.out.println(s);
		}

	}
}
