/* *****************************************************************************
 * Pedro Avila
 * CSc 492
 * Senior Project Experiment
 *
 * Name: E12MCM
 * Description: This code reads in ASCII characters from a textfile as one
 *					string. If the BigInteger value of that string is < the
 *					value of 'N', it is padded, else not, and C is calculated.
 *					The encyphered C is decyphered. M -> C -> M are displayed.
 *					
 * Modification History:
 *		2004.03.20	PRA	Original Write in Java.
 *		2004.03.27	PRA	Verified M input and tuned algorithm documentation.
 *		2004.03.28	PRA	Added random digit to M. Hard coded all values.
 *		2004.03.29	PRA	Added random digit to M every time the buffer was empty.
 ******************************************************************************/

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;

public class E12MCM
{
	public static void main(String args[])
	{
		try
		{
			/* *****************************************************************
			 * Variable declarations
			 * ****************************************************************/
			/* Hardcoded valid p,q,N,d,e values from a run of E8calcD
			 *
			 * p: 11601104794364913911
			 *
			 * q: 12114841635515079911
			 *
			 * N: 140545547380745668457156637643800541921
			 *
			 * d: 80311741360426096247680394979383170343
			 */
			BigInteger mBaseMod = new BigInteger("0");
			BigInteger decryptedM = new BigInteger("0");
			BigInteger M = new BigInteger("0");
			BigInteger C = new BigInteger("0");
			BigInteger e = new BigInteger("7");
			BigInteger N = new BigInteger("299");
			BigInteger d = new BigInteger("151");
			
			FileInputStream fistream = new FileInputStream("test.txt");
			BufferedReader message = new BufferedReader(
				new InputStreamReader(fistream));
			
			Random rndm = new Random(System.currentTimeMillis());
			double rndmNZdigit = 0;
			int numZeros = 0;
			int j = 0;
			
			Integer i = new Integer(0);
			String mStr = new String("000");
			StringBuffer mBuf = new StringBuffer();


			/* ******************* Calculating M -> C -> M *********************
			 * - If the size of the M buffer is 0, append a random non-zero
			 *		digit to the M buffer.
			 * - Read in a character from the message one at a time as an
			 *		Integer value and convert it to a string.
			 * - If the string is not 3 digits long, add 0's to the begining of
			 *		it util it is.
			 * - After the message has been read as a string of integers, check
			 *		the value of M; if it's <= ethRoot(N), pad M to be larger.
			 * - Calculate C, and then calculate M again.
			 * ****************************************************************/

			/*
			 * If the M buffer is empty, append a random non-zero digit.
			 */
			if(mBuf.length() == 0)
			{
				rndmNZdigit = Math.ceil(9 * Math.random());
				mBuf.append((int)rndmNZdigit);
			}

			/*
			 * Read all the characters from the file into a string buffer.
			 */
			while(message.ready())
			{
				mStr = i.toString(message.read());

				/*
				 * Loop to add "0" to the front of the string
				 * while the length is less than 3 bits for proper delimitation
				 */
				while(mStr.length() < 3)
					mStr = "0" + mStr;

				mBuf.append(mStr);
			}
			
			message.close();
			
			mStr = mBuf.toString();

			M = new BigInteger(mStr);
			System.out.println("M: " + M.toString());
			
			mBaseMod = toBaseB(M, N);	// consider making this a private method
			System.out.println("M (base N): " + mBaseMod.toString());
			
			C = mBaseMod.modPow(e, N);
			System.out.println("C: " + C.toString());
			
			decryptedM = C.modPow(d, N);
			System.out.println("M (still base N): " + mBaseMod.toString());
			
			M = toBaseB(decryptedM, TEN);
			System.out.println("M: " + M.toString());
		}
		catch(Exception e)
		{
			System.err.println("Error");
			e.printStackTrace();
		}
	}
	
	private static BigInteger ZERO = new BigInteger("0");
	private static BigInteger ONE = new BigInteger("1");
	private static BigInteger TEN = new BigInteger("10");
		
	public static BigInteger toBaseB(BigInteger num, BigInteger base)
	{
		BigInteger numBaseB = new BigInteger("0");
		BigInteger [] qR = {ZERO, ZERO};
		StringBuffer numBaseBBuf = new StringBuffer();
		
		qR = num.divideAndRemainder(base);
		numBaseBBuf.append(qR[1].toString());
		
		while(qR[0] != ZERO)
		{
			qR = qR[0].divideAndRemainder(base);
			numBaseBBuf.append(qR[1].toString());
		}
		
		numBaseB = new BigInteger(numBaseBBuf.toString());
		
		return numBaseB;
	}
}
