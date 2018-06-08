package cn.edu360.day07.test;

import ch.hsr.geohash.GeoHash;

/**
 * geohash用法
 * @author root
 *
 */
public class TestGeoHash {
	public static void main(String[] args) {
		String base32 = GeoHash.withCharacterPrecision(41.083778, 113.983896, 8).toBase32();
		System.out.println(base32);
	}

}
