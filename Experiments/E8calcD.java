/* *****************************************************************************
 * Pedro Avila
 * CSc 492
 * Senior Project Experiment
 *
 * Name: E8calcD
 * Description: This code calculates e from p, q, and d
 * Modification History:
 *		2004.03.26	PRA	Original Write in Java
 *		2004.03.27	PRA	Fine tuned bit values and confirmed calculation of C
 *		2004.03.28	PRA	Verified calculation of C -> M using calculated D
 ******************************************************************************/

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;

class E8calcD
{
	public static void main(String args[])
	{
		try
		{
			//boolean dExists = true;
			//Random pRnd = new Random(0);
			//Random qRnd = new Random(1);
			Random dRndGen = new Random(System.currentTimeMillis());

			BigInteger ONE = new BigInteger("1");
			/*
			 * At present, large values of p and q are considered >= 512 bits
			 *	see: "Laws of Cryptography link"
			 */
			BigInteger p = new BigInteger("13");
			BigInteger q = new BigInteger("23");
			BigInteger d = new BigInteger("0");
			BigInteger N = new BigInteger("0");
			BigInteger M = new BigInteger("5097");			
			BigInteger C = new BigInteger("0");

			N = p.multiply(q);
			
			BigInteger phiN = (p.subtract(ONE)).multiply(q.subtract(ONE));
			
			/*******************************************************************
			 * Keep choosing a value of d until it is coprime with phiN AND
			 *	greater than the max between p and q.
			 ******************************************************************/
			while(d.compareTo(p.max(q)) <= 0)
			{
System.out.println("choosing d...");
				d = BigInteger.probablePrime(120, dRndGen);
			}
			
			if(d.gcd(phiN) != ONE)
		System.out.println("d.gcd(phiN) != ONE");

			BigInteger e = d.modInverse(phiN);
			
			C = M.modPow(e, N);
			
			System.out.println("\nM: " + M);
			
			M = C.modPow(d, N);
			
			System.out.println("C = E(M): " + C);
			System.out.println("M = D(E(M)): " + M + "\n");
			System.out.println("p: " + p);
			System.out.println("q: " + q);
			System.out.println("e: " + e);
			System.out.println("d: " + d);
			System.out.println("N: " + N);
		}
		catch(Exception e)
		{
			System.err.println("Error");
			e.printStackTrace();
		}
	}
}
