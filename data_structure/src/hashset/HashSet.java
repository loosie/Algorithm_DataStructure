package hashset;

public class HashSet<E> implements Set<E> {

	// java.util.HashSet
	private final static int INITIAL_CAPACITY = 1 << 4;

	private final static float LOAD_FACTOR = 0.75f;

	Node<E>[] table;
	private int size;

	public HashSet() {
		table = new Node[INITIAL_CAPACITY];
		size = 0;
	}

	public HashSet(int initialCapacity) {
		table = new Node[initialCapacity];
		size = 0;
	}

	/**
	 * 보조 해시 함수 역할. 상속 방지를 위해 'private static final'로 선언
	 * @param key
	 * @return
	 */
	private static final int hash(Object key) {
		int hash;
		return (key == null) ? 0 : (hash = key.hashCode()) ^ (hash >>> 16);
	}

	@Override
	public boolean add(E e) {
		// e에 대한 hash 값과 e를 보낸다. 정상적으로 저장되면 null을 반환한다.
		return add(hash(e), e) == null;
	}

	private E add(int hash, E key) {
		int idx = hash % table.length;

		// 해당 idx 비어있을 경우 새로운 노드 생성
		if (table[idx] == null) {
			table[idx] = new Node<>(hash, key, null);
		}
		/**
		 * table[idx]에 요소가 이미 존재할 경우 (== 해시 충돌)
		 *  1. 객체가 같은 경우
		 *  2. hash 모듈러 값이 같은 경우 (객체가 같지 않으나 얻어진 idx의 값이 같을 경우)
		 */
		else {

			Node<E> tmp = table[idx]; // 현재 위치 노드
			Node<E> prev = null; // tmp의 이전 노드

			// 마지막 노드까지 탐색
			while (tmp != null) {

				/**
				 * 만약 현재 노드 객체가 같은 경우, 중복을 허용하지 않으므로 key 반환
				 *
				 * 객체 값이 중복이 되는 경우
				 * 1. hash 값이 같다.
				 * 2. key의 값이 같거나 객체 주소가 같다.
				 */
				if ((tmp.hash == hash) &&
					(tmp.key == key || tmp.key.equals(key))) {
					return key;
				}

				prev = tmp;
				tmp = tmp.next;
			}

			// 마지막 노드 포인터에 새로운 노드 연결
			prev.next = new Node<>(hash, key, null);
		}

		size++;

		// 데이터의 개수가 현재 table의 크기 LOAD_FACTOR(75)% 이상이 되는 경우 resizing
		if (size >= LOAD_FACTOR * table.length) {
			resize();
		}

		// 정상적으로 값이 추가된 경우 null 반환
		return null;
	}

	private void resize() {
		int newCapacity = table.length * 2;

		// 기존 테이블의 두 배 용적으로 생성
		final Node<E>[] newTable = new Node[newCapacity];

		// 0번째 index부터 차례대로 순회
		for (int i = 0; i < table.length; i++) {

			// 각 인덱스의 첫 번째 노드(head)
			Node<E> value = table[i];

			if (value == null) {
				continue;
			}

			table[i] = null; // 초기화
			Node<E> nextNode;

			// 현재 인덱스에 연결된 노드들을 순회한다.
			while (value != null) {
				int idx = value.hash % newCapacity; // 새로운 인덱스를 구한다.

				 // 새로 담을 idx에 노드가 존재할 경우 (해시 충돌)
				if (newTable[idx] != null) {
					Node<E> tail = newTable[idx];

					// 마지막 노드로 이동
					while(tail.next != null){
						tail = tail.next;
					}

					/**
					 *  반드시 value가 참조하고 있는 다음 노드와의 연결을 끊어야 한다.
					 *  자칫하면 엉켜버린 잘못된 참조 관계가 발생할 수 있다.
					 */
					nextNode = value.next; // 다음 이동할 노드 저장
					value.next = null; // value next 포인터 초기화
					tail.next = value; // value 노드 tail 꼬리에 저장
				} else {

					/**
					 *  반드시 value가 참조하고 있는 다음 노드와의 연결을 끊어야 한다.
					 *  자칫하면 엉켜버린 잘못된 참조 관계가 발생할 수 있다.
					 */
					nextNode = value.next; // 다음 이동할 노드 저장
					value.next = null; // value next 포인터 초기화
					newTable[idx] = value; // value 노드 table에 새롭게 저장
				}

				// 다음 노드로 이동
				value = nextNode;
			}
		}

		table = null;
		table = newTable; // 새로 생성한 table을 table 변수에 연결
	}

	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public boolean contains(Object o) {
		return false;
	}
}
