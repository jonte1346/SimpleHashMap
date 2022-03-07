package map;

public class SimpleHashMap<K, V> implements Map<K, V> {
	private static final double loadFactor = 0.75;
	private Entry<K, V>[] map;
	private int capacity;
	private int size;
	private boolean rehashing;

	/**
	 * Constructs an empty hashmap with the default initial capacity (16) and the
	 * default load factor (0.75).
	 */
	public SimpleHashMap() {
		this(16);
	}

	/**
	 * Constructs an empty hashmap with the specified initial capacity and the
	 * default load factor (0.75).
	 */
	public SimpleHashMap(int capacity) {
		this.capacity = capacity;
		map = (Entry<K, V>[]) new Entry[capacity];
		size = 0;
		rehashing = false;
	}

	public String show() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < map.length; i++) {
			sb.append(i + "\t");
			if (map[i] != null) {
				Entry<K, V> e = map[i];
				while (e != null) {
					sb.append(e.toString() + ", ");
					e = e.next;
				}
			} else {
				sb.append("empty");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private int index(K key) {
		int index = key.hashCode() % capacity;
		index = Math.abs(index);
		return index;
	}

	private Entry<K, V> find(int index, K key) {
		Entry<K, V> e = map[index];
		while (e != null) {
			if (e.key.equals(key)) {
				return e;
			}
			e = e.next;
		}
		return null;
	}

	@Override
	public V get(Object object) {
		K key = (K) object;
		Entry<K, V> e = find(index(key), key);
		if (e != null) {
			return e.value;
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public V put(K key, V value) {
		int index = index(key);
		Entry<K, V> e = find(index, key);
		// Om objekt redan finns
		if (e != null) {
			return e.setValue(value);
		} else {
			Entry<K, V> newEntry = new Entry<K, V>(key, value);
			// Om det finns lista i map[index]

			newEntry.next = map[index];
			map[index] = newEntry;

			if (!rehashing)
				size++;
			// Om fyllnadsgraden överstiger loadFactor
			if (((double) size / capacity) > loadFactor)
				rehash();
			return null;
		}
	}

	private void rehash() {
		rehashing = true;
		// Kvadrerar kapaciteten
		capacity *= 2;
		Entry<K, V>[] oldArray = map;
		// Skapar ny map
		map = (Entry<K, V>[]) new Entry[capacity];
		// Går igenom gammal map
		for (int i = 0; i < oldArray.length; i++) {
			Entry<K, V> head = oldArray[i];
			// Lägger till i den nya listan för varje element
			while (head != null) {
				put(head.key, head.value);
				head = head.next;
			}
		}
		rehashing = false;

	}

	@Override
	public V remove(Object object) {
		if (isEmpty() != true) {
			K key = (K) object;
			int index = index(key);
			Entry<K, V> toRemove = find(index, key);
			if (toRemove != null) {
				Entry<K, V> e = map[index];
				// Om första objektet i listan
				if (e.key.equals(key)) {
					map[index] = e.next;
					size--;
					return e.value;
				}
				// Key finns senare i listan, letar
				while (e.next != null) {
					if (e.next.key.equals(key)) {
						Entry<K, V> temp = e.next;
						e.next = e.next.next;
						size--;
						return temp.value;
					}
					e = e.next;
				}
			}
		}
		return null;
	}

	@Override
	public int size() {
		return size;
	}

	private static class Entry<K, V> implements Map.Entry<K, V> {
		private K key;
		private V value;
		private Entry<K, V> next;

		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			V temp = this.value;
			this.value = value;
			return temp;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}

	}
}
