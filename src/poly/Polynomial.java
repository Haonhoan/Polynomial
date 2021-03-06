package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node front = null;
		Node end = null;
		float sum = 0;
		
		if((poly1 == null) && (poly2 == null)) {
			return null;
		}
		if((poly1 != null) && (poly2 == null)) {
			return poly1;
		}
		if((poly1 == null) && (poly2 != null)) {
			return poly2;
		}
		
		if(poly1.term.degree == poly2.term.degree) {
			sum = poly1.term.coeff + poly2.term.coeff;
			front = new Node(sum, poly1.term.degree, null);
			end = front;
			poly1 = poly1.next;
			poly2 = poly2.next;
		}
		else if(poly1.term.degree < poly2.term.degree) {
			front = new Node(poly1.term.coeff, poly1.term.degree, null);
			end = front;
			poly1 = poly1.next;
		}
		else {
			front = new Node(poly2.term.coeff, poly2.term.degree, null);
			end = front;
			poly2 = poly2.next;
		}
		
		while(poly1 != null && poly2 != null) {
			if(poly1.term.degree == poly2.term.degree) {
				sum = poly1.term.coeff + poly2.term.coeff;
				if(sum != 0) {
					end.next = new Node(sum, poly1.term.degree, null);
					end = end.next;
				}
				poly1 = poly1.next;
				poly2 = poly2.next;
			}
			else if(poly1.term.degree < poly2.term.degree) {
				end.next = new Node(poly1.term.coeff, poly1.term.degree, null);
				end = end.next;
				poly1 = poly1.next;
			}
			else {
				end.next = new Node(poly2.term.coeff, poly2.term.degree, null);
				end = end.next;
				poly2 = poly2.next;
			}
		}
		
		while(poly1 != null) {
			end.next = new Node(poly1.term.coeff, poly1.term.degree, null);
			end = end.next;
			poly1 = poly1.next;
		}
		
		while(poly2 != null) {
			end.next = new Node(poly2.term.coeff, poly2.term.degree, null);
			end = end.next;
			poly2 = poly2.next;
		}
		
		return front;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node front1 = null;
		Node front2 = null;
		Node front3 = null;
		Node result = null;
		Node end = null;
		float product = 0;
		int exponent = 0;
		front1 = poly1;
		front2 = poly2;
		end = front3;
		
		if((poly1 == null) && (poly2 == null)) {
			return null;
		}
		if((poly1 != null) && (poly2 == null)) {
			return null;
		}
		if((poly1 == null) && (poly2 != null)) {
			return null;
		}
		
		for(Node LL1 = front1; LL1 != null; LL1 = LL1.next) {
			end = null;
			for(Node LL2 = front2; LL2 != null; LL2 = LL2.next) {
				if(front3 == null) {
					product = LL1.term.coeff * LL2.term.coeff;
					exponent = LL1.term.degree + LL2.term.degree;
					front3 = new Node(product, exponent, null);
					end = front3;
				}
				else {
					product = LL1.term.coeff * LL2.term.coeff;
					exponent = LL1.term.degree + LL2.term.degree;
					end.next = new Node(product, exponent, null);
					end = end.next;
				}
			}
			result = add(result, front3);
			front3 = null;
		}
		
		return result;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		double answer = 0;
		double finalAns = 0;
		
		for(Node part = poly; part != null; part = part.next) {
			 answer = Math.pow(x, part.term.degree) * part.term.coeff;
			 finalAns = finalAns + answer;
		}
		
		float result = (float)finalAns;
		
		return result;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
