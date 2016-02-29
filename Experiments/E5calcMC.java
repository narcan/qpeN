/* *****************************************************************************
 * Pedro Avila
 * CSc 492
 * Senior Project Experiment
 *
 * Name: E5calcMC
 * Description: This code reads in ASCII characters from a textfile as several
 *					integers M of size ethRootN < M, and outputs those
 *					integers. Using each M, it calculates C from hardcoded
 *					values of p, q, N, e, and d and outputs the C values.
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

public class E5calcMC
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
			 * p: 11768683740856488545342184179370880934722814668913767795511297
			 *		683943548586519985787950858001297977310173110996763173093591
			 *		148833987835653326488053279071057
			 *
			 * q: 12770541060734587306428474290972351811026146509533661596325185
			 *		126855795035855987364635240007832980868493729320896469157316
			 *		293631054051205867414229823183201
			 *
			 * N: 15029245894340731222511638328742234273990315715191672317019498
			 *		800349046278571818989127967253947989603529325538680919254270
			 *		007743596218967086739058329474172445482083508758643333803740
			 *		254130464535968881650938237096976415140096654522415778926494
			 *		086351730676954595143662314866866180702709147476153039431200
			 *		7713457
			 *
			 * d: 64411053832888848096478449980323861174244210207964309930083566
			 *		287210198336736367096262716802634241157982823737203939661157
			 *		176043983795573228881678554889299963826871498504534957448113
			 *		799173671261740416312265562485692003114981252649221987148075
			 *		509022720824662476193841502750466156240878394815287068012388
			 *		053943
			 */
			BigInteger M = new BigInteger("0");
			BigInteger C = new BigInteger("0");
			BigInteger e = new BigInteger("3");
			BigInteger N = new BigInteger("150292458943407312225116383287422342739903157151916723170194988003490462785718189891279672539479896035293255386809192542700077435962189670867390583294741724454820835087586433338037402541304645359688816509382370969764151400966545224157789264940863517306769545951436623148668661807027091474761530394312007713457");
			BigInteger d = new BigInteger("64411053832888848096478449980323861174244210207964309930083566287210198336736367096262716802634241157982823737203939661157176043983795573228881678554889299963826871498504534957448113799173671261740416312265562485692003114981252649221987148075509022720824662476193841502750466156240878394815287068012388053943");
			
			FileInputStream fistream = new FileInputStream("5test.txt");
			BufferedReader message = new BufferedReader(
				new InputStreamReader(fistream));
			
			Random rndm = new Random(System.currentTimeMillis());
			double rndmNZdigit = 0;
			
			Integer i = new Integer(0);
			String mStr = new String("000");
			int mCalcCount = 0;
			StringBuffer mBuf = new StringBuffer();
			StringBuffer cBuf = new StringBuffer();
			StringBuffer totalM = new StringBuffer();


			/* ********************* Calculating M -> C ************************
			 * - If the size of the M buffer is 0, append a random non-zero
			 *		digit to the M buffer.
			 * - Read in a character from the message one at a time as an
			 *		Integer value and convert it to a string.
			 * - If the string is not 3 digits long, add 0's to the begining of
			 *		it util it is.
			 * - If the size of the M buffer == 100, or there is no more
			 *		of the message file to read
			 *			- Calculate the corresponding C value and append the
			 *				value to the C buffer with a delimiter.
			 *			- Empty the M buffer.
			 * - Repeat until there is no more of the message to be read.
			 *
			 * - Output the C buffer that will be stored in the encrypted file.
			 * ****************************************************************/

			while(message.ready())
			{
				/*
				 * If the M buffer is empty, append a random non-zero digit.
				 */
				if(mBuf.length() == 0)
				{
					rndmNZdigit = Math.ceil(9 * Math.random());
					mBuf.append((int)rndmNZdigit);
				}
				
				mStr = i.toString(message.read());

				/*
				 * Loop to add "0" to the front of the string
				 * while the length is less than 3 bits for proper delimitation
				 */
				while(mStr.length() < 3)
					mStr = "0" + mStr;

				mBuf.append(mStr);
				
				/*
				 * Check if
				 *		- The length of the M buffer equals 100 OR
				 *		- There is no more of the message to be read
				 *
				 * If either of these facts are true, then calculate C, append
				 *	it to the C buffer (with/without a delimiter, depending on
				 *	whether or not there will be another C). Empty the M buffer.
				 */
				if(mBuf.length() == 100 || !message.ready())
				{
					totalM.append(mBuf + " ");
					
					mCalcCount++;
					
					M = new BigInteger(mBuf.toString());
					C = M.modPow(e, N);

					if(message.ready())
						cBuf.append(C.toString() + " ");
					else
						cBuf.append(C.toString());
					
					mBuf = new StringBuffer();
				}
			}

			System.out.println("M:\n" + totalM);
			System.out.println("C:\n" + cBuf);
			System.out.println("# M->C calcs: " + mCalcCount);
			
			message.close();
		}
		catch(Exception e)
		{
			System.err.println("File error");
		}
	}
}
