/* *****************************************************************************
 * Pedro Avila
 * CSc 492
 * Senior Project Experiment
 *
 * Name: E10calcCM
 * Description: This code reads in ASCII characters from an encrypted textfile
 *					as C values (delimited by " "). Using the C's, it
 *					calculates the M's again and outputs their int values. It
 *					then tranlates the M int values into ascii and outputs the
 *					decrypted message.
 *					
 * Modification History:
 *		2004.03.29	PRA	Original Write in Java.
 ******************************************************************************/

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;

public class E10calcCM
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
			BigInteger e = new BigInteger("7");
			BigInteger N = new BigInteger("299");
			BigInteger d = new BigInteger("151");
			
			FileInputStream fistream = new FileInputStream("test.txt");
			BufferedReader Cmessage = new BufferedReader(
				new InputStreamReader(fistream));
			
			//Random rndm = new Random(System.currentTimeMillis());
			//double rndmNZdigit = 0;
			
			Integer i = new Integer(0);
			StringReader mStr = new StringReader("0");
			char cChar = '0';
			int cCalcCount = 0;
			StringBuffer mBuf = new StringBuffer();
			StringBuffer mCharBuf = new StringBuffer();
			StringBuffer cBuf = new StringBuffer();


			/* ********************* Calculating C -> M ************************
			 * - Read in a character from the message one at a time as a char
			 *		until coming to " ".
			 * - Calculate the correct value of M and append the resulting M to
			 		the M buffer.
			 * - Repeat until there is no more of the encrypted message to read.
			 * - Translate every 3 digits to its corresponding ASCII
			 *		representation and output to decryption file.
			 * ****************************************************************/

			while(Cmessage.ready())
			{
				cChar = (char)Cmessage.read();
				
				if(cChar == ' ' || !Cmessage.ready())
				{
					cCalcCount++;
					C = new BigInteger(cBuf.toString());
					M = C.modPow(d, N);
					
					mBuf.append(M.toString());
					
					cBuf = new StringBuffer();
				}
				else
					cBuf.append(cChar);
			}
			
			Cmessage.close();			

			System.out.println("M:\n" + mBuf);
			System.out.println("# C->M calcs: " + cCalcCount);
		}
		catch(Exception e)
		{
			System.err.println("File error");
		}
	}
}
