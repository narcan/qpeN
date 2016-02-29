/* *****************************************************************************
 * Pedro Avila
 * CSc 492
 * Senior Project Experiment
 *
 * Name: E11transM
 * Description: This code reads in ASCII characters from a decrypted textfile
 *					as M values (delimited by " "). Using the M's, it ignores
 *					the first digit of every M and uses the rest of that M value
 *					to translate ASCII characters and output them.
 *					
 * Modification History:
 *		2004.03.29	PRA	Original Write in Java.
 ******************************************************************************/

import java.io.*;
import java.lang.*;

public class E11transM
{
	public static void main(String args[])
	{
		try
		{
			FileInputStream fistream = new FileInputStream("11test.txt");
			BufferedReader Mmessage = new BufferedReader(
				new InputStreamReader(fistream));
			
			int charCount = 0;
			Integer i = new Integer(0);
			char mChar = '0';
			StringBuffer mStr = new StringBuffer();
			StringBuffer mCharBuf = new StringBuffer();
			StringBuffer mBuf = new StringBuffer();

			/* *********************** Translating M ***************************
			 * - If at the beggining of the current M value, ignore the 1st
			 *		digit.
			 * - Read in 3 chars from the M file 33 times.
			 * - Convert the 3 chars (numerical values) into their 3 digit int
			 *		value and output as chars.
			 * - Repeat until there is no more of the M file to read.
			 * ****************************************************************/

			while(Mmessage.ready())
			{
				while(mStr.length() < 3)
				{
					mChar = (char)Mmessage.read();
					charCount++;
					
					if(charCount != 1)
					{
						mStr.append(mChar);
					}
				}

				i = new Integer(mStr.toString());
				mCharBuf.append((char)i.intValue());
				mStr = new StringBuffer();
				
				if(charCount >= 100)
					charCount = 0;
			}

			Mmessage.close();
			
			System.out.println("message: " + mCharBuf);

		}
		catch(Exception e)
		{
			System.err.println("File error");
		}
	}
}
