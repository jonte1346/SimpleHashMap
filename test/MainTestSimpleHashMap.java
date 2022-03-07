package test;

import java.util.Random;

import map.SimpleHashMap;

public class MainTestSimpleHashMap {

	public static void main(String[] args) {
		Random rand = new Random();
		// Tom map
		SimpleHashMap<Integer, Integer> map = new SimpleHashMap<Integer, Integer>(10);
		System.out.println(map.show());
		System.out.println(map.size());
		System.out.println(map.isEmpty());
		System.out.println();

		// Random map
		for (int i = 0; i < 20; i++) {
			Integer temp = rand.nextInt();
			map.put(temp, temp);
		}

		System.out.println(map.show());
		System.out.println(map.size());
		System.out.println(map.isEmpty());
		System.out.println();

	}

}
