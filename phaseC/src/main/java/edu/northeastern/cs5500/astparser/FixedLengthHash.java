/*
 * Created on Jun 22, 2005
 */
package edu.northeastern.cs5500.astparser;

import java.util.List;
import java.util.ListIterator;
import java.util.LinkedList;

/**
 * @author augsten
 */
public abstract class FixedLengthHash {
	
	private int length;
	
	/**
	 * @param length length of the hash value (in bytes) produced by this function.
	 */
	public FixedLengthHash(int length) {
		this.length = length;
	}
	
	/**
	 * @param s
	 * @return hash value of s if s not null; null node otherwise
	 */
	public final  HashValue h(String s) {
		if (s == null) {
			return this.getNullNode();
		} else {
			return this.getHashValue(s);
		}
	}
	
	/**
	 * Compute the hash value of a string function. 
	 * 
	 * @param s original string 
	 * @return hash value of <code>s</code>
	 */
	 public abstract HashValue getHashValue(String s);
	
	/**
	 * Hash two strings to one hash value. Use this to hash 
	 * a node represented as a (label,value) pair to a hash value.
	 * 
	 * The node value can be null. In this case, the result
	 * is equivalent {to @link #h(String)}.
	 * 
	 *
	 * @return
	 */
	public HashValue h(String lbl, String val) {
		if (val == null) {
			return this.getHashValue(lbl);
		} else {
			int l1;
			int l2;
			if (length % 2 == 0) {
				l1 = length / 2;
				l2 = l1;
			} else {
				l1 = length / 2;
				l2 = l1 + 1;
			}
			length = l1;
			String res = getHashValue(lbl).toString();
			length = l2;
			res += getHashValue(val).toString();
			length = l1 + l2;
			return new HashValue(res);
		}
	}
	
	/**
	 * @return hash value of the null-node-label
	 */
	 public abstract HashValue getNullNode();

	/**
	 * @param length
	 * @return linked list of <code>length</code> hashed null node labels
	 */
	public List getEmptyRegister(int length) {
		LinkedList reg = new LinkedList();
		for (int i = 0; i < length; i++) {
			reg.add(this.getNullNode());
		}
		return reg;
	}
	
	/**
	 * @param anc linked list of hash values
	 * @return concatenation of the hash values in anc
	 */
	public String concatLst(List anc) {
		StringBuilder sb = new StringBuilder();
		for (ListIterator it = anc.listIterator(anc.size()); it.hasPrevious();) {
			String s = ((HashValue)it.previous()).toString();
			sb.append(s);
		}		
		return sb.toString();
	}

	/**
	 * @return length of the hash values produced by this function
	 */
	public int getLength() {
		return this.length;
	}
	/**
	 * @param length set length of hash values produced by this function
	 */
	public void setLength(int length) {
		this.length = length;
	}
	

	
}
