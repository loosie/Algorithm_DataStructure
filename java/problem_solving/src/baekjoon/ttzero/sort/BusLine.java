package baekjoon.ttzero.sort;

// #10165
// vector ? arrayList?
import java.io.*;
import java.util.*;


public class BusLine {

	
	public static void main(String[] args) throws Exception{ 
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		int m = Integer.parseInt(br.readLine());
		
				
		List<Course> list = new ArrayList<>();
		int back =0;
		
		for(int i=0; i<m; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			
			
				list.add(new Course(i+1, start, end));
			if(list.get(i).s > list.get(i).e) {
				back = Math.max(back, list.get(i).e);
				list.get(i).e += n;
			}
		}
		
		Collections.sort(list);
		
		
		Deque<Course> q = new ArrayDeque<>();
		
		for (Course b : list) {
			if (q.isEmpty() || q.getLast().e < b.e) {
				q.addLast(b);
			}
		}

		while (!q.isEmpty() && q.getFirst().e <= back) {
			q.removeFirst();
		}

		List<Course> res = new ArrayList<>();
		while (!q.isEmpty()) {
			res.add(q.pop());
		}

		Collections.sort(res, new Comparator<Course>() {

			@Override
			public int compare(Course o1, Course o2) {
				return o1.idx - o2.idx;
			}

		});

		for (Course b : res) {
			System.out.print(b.idx+" ");
		}
	}

}

class Course implements Comparable<Course> {

	int idx;
	int s;
	int e;

	public Course(int idx, int s, int e) {
		this.idx = idx;
		this.s = s;
		this.e = e;
	}

	@Override
	public int compareTo(Course o) {
		if(this.s == o.s) {
			return o.e - this.e;
		}
		return this.s - o.s;
	}
}