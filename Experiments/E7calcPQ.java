/* *****************************************************************************
 * Pedro Avila
 * CSc 492
 * Senior Project Experiment
 *
 * Name: E7calcPQ
 * Description: This code generates random prime numbers p & q and guarantee's
			enough randomization by seeding one random # generator
			with the system time, and another generator with a
			random number chosen from another random number
			generator seeded with a different system time.
 * Modification History:
 *		2004.03.22	PRA	Original Write in Java
 *		2004.03.25	PRA	Modified seeding of q generator using System time
 ******************************************************************************/

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;

class E7calcPQ
{
	public static void main(String args[])
	{
		try
		{
			// P code
			Random pRnd = new Random(System.currentTimeMillis());
			
			BigInteger p = BigInteger.probablePrime(250, pRnd);
			
			System.out.println("p: " + p);

			// Q code
			Random diffRnd = new Random(System.currentTimeMillis());
			long qRndSeed = diffRnd.nextLong();

			Random qRnd = new Random(qRndSeed);
			
			BigInteger q = BigInteger.probablePrime(300, qRnd);

			System.out.println("q: " + q);
		}
		catch(Exception e)
		{
			System.err.println("Error");
		}
	}
}
