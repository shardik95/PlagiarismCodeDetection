/*
 * Created on Jun 22, 2005
 */
package edu.northeastern.cs5500.astparser;

/**
 * Simple class that stores a hash value.
 * 
 * @author augsten
 */
public class HashValue implements Comparable {

	
	/**
	 * Mask used to retrieve the value of the least significant byte of an integer number.  
	 */
	public static final int LSBYTEMASK = 255;
		
	private String hashvalue1;
	
	/**
	 * Initialize the hash value with a string.
	 * 
	 * @param hashvalue1 hash value as a string
	 */
	public HashValue(String hashvalue1) {
		this.hashvalue1 = hashvalue1;
	}

	/**
	 * Initialize the hash value with an integer number.
	 * 
	 * @param h hash value as an integer 
	 * @param length length of the hash value in bytes
	 */
	public HashValue(long h, int length) {
		StringBuilder s = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			char c = (char)(h & LSBYTEMASK);
			s.append(c);
			h = h >>> 8;
		}
		hashvalue1 = s.toString();
		
	}
	
	/**
	 * @return hash value as a string
	 */
	@Override
	public String toString() {
		if (hashvalue1.charAt(hashvalue1.length() -1) == ' ') {
			return hashvalue1.substring(0, hashvalue1.length() - 1) + (char)0;
		} else {
			return hashvalue1;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object arg0) {
		return this.hashvalue1.compareTo(((HashValue)arg0).toString());
	}
	
	public static HashValue maxValue(int length) {
		StringBuilder s=new StringBuilder();
		for (int i = 0; i < length; i++) {
			s.append(Character.MAX_VALUE);
		}
		return new HashValue(s.toString());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if(arg0 !=null && this.getClass() != arg0.getClass()) {

				return false;

		}
		if(arg0 !=null) {
			return (this.toString()).equals(((HashValue) arg0).toString());
		}

		return false;
	}

	@Override
	public int hashCode(){
		return this.hashCode();
	}
	
	
	
}
