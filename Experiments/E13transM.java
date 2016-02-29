/* *****************************************************************************
 * Pedro Avila
 * CSc 492
 * Senior Project Experiment
 *
 * Name: E13transM
 * Description: This code does what E11 does but with a different algorithm.
 *					
 * Modification History:
 *		2004.04.02	PRA	Original Write in Java.
 ******************************************************************************/

import java.io.*;
import java.lang.*;

public class E13transM
{
	public static void main(String args[])
	{
		try
		{
			FileInputStream fistream = new FileInputStream("12test.txt");
			BufferedReader message = new BufferedReader(
				new InputStreamReader(fistream));
			
			Integer i = new Integer(0);
			String mStr = new String("000");
			StringBuffer mBuf = new StringBuffer();
			char[] mChars = {'0','0','0'};
			char asciiM = '0';
			String mCharStr = new String("0");
			String zeroASCII = new String("048");

			/* *********************** Translating M ***************************
			 * - If at the beggining of the current M value, ignore the 1st
			 *		digit.
			 * - Read in 3 chars from the M file 33 times.
			 * - Convert the 3 chars (numerical values) into their 3 digit int
			 *		value and output as chars.
			 * - Repeat until there is no more of the M file to read.
			 * ****************************************************************/
			
			/*
			 * Clear the M buffer.
			 */
			mBuf = new StringBuffer();
			
			/*
			 * Skip the first int, any zeros after it and the first non-zero
			 *	digit it comes to.
			 */
			message.skip(3);
			message.read(mChars, 0, 3);			
			mCharStr = new String(mChars);
System.out.println("skipping the zeros");
			while(mCharStr == zeroASCII)
			{
				message.read(mChars, 0, 3);
				mCharStr = new String(mChars);
			}
			
			/*
			 * Translate the int values to ascii
			 */
System.out.println("starting translation");
			while(message.ready())
			{
				message.read(mChars, 0, 3);
				
				mCharStr = new String(mChars);
				i = new Integer(mCharStr);
				asciiM = (char)i.intValue();
				
				mBuf.append(asciiM);
			}
			
			System.out.println("M: " + mBuf);
		}
		catch(Exception e)
		{
			System.err.println("File error");
		}
	}
}
