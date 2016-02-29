/* *****************************************************************************
 * Pedro Avila
 * CSc 492
 * Senior Project Experiment
 *
 * Name: EqpeN
 * Description: This code does it all.
 *					
 * Modification History:
 *		2004.04.11	PRA	Original Write in Java.
 *		2004.04.13	PRA	Traded base N method of ensuring correct length of M for
 *									length of N method...it worked much better.
 *		2004.04.14	PRA	Debugged until operational.
 *		2004.04.15	PRA	Restructured to add digital signing and facilitate
 *									the breakdown into several modules for file I/O.
 ******************************************************************************/

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;

public class EqpeN
{
	/* *******************************
	 * Private variable declarations
	 * ******************************/
	private static int messageASCIILength = 3;
	private static int criticalNMDifference = 3;
	private static BigInteger ZERO = BigInteger.ZERO;
	private static BigInteger ONE = BigInteger.ONE;

	/* ******************************
	 * Public variable declarations
	 * *****************************/
	public static int pABitSize = 512;
	public static int qABitSize = 500;
	public static int dABitSize = 1024;
	public static int pBBitSize = 512;
	public static int qBBitSize = 500;
	public static int dBBitSize = 1024;

	public static void main(String args[])
	{
		try
		{
			/* ****************************
			 * Main variable declarations
			 * ***************************/
			FileInputStream fistream = new FileInputStream("test.txt");
			BufferedReader message = new BufferedReader(
				new InputStreamReader(fistream));

			
			/*************************** Key Generation ***************************
			 *	- choose random prime numbers p & q
			 *	- calculate N from p & q
			 *	- calculate phiN, Euler's totient function
			 *	- choose a value of d that is > max(p,q) AND gcd(d,phiN) == 1
			 *	- calculate e from d
			 *********************************************************************/
			 
			 
			/* **************************************
			 * Key Generation variable declarations
			 * **************************************/
			BigInteger pA = BigInteger.ZERO;
			BigInteger qA = BigInteger.ZERO;
			BigInteger dA = BigInteger.ZERO;
			BigInteger NA = BigInteger.ZERO;
			BigInteger eA = BigInteger.ZERO;
			BigInteger pB = BigInteger.ZERO;
			BigInteger qB = BigInteger.ZERO;
			BigInteger dB = BigInteger.ZERO;
			BigInteger NB = BigInteger.ZERO;
			BigInteger eB = BigInteger.ZERO;
			
			
			/* At present, large values of p and q are considered >= 512 bits.
			 *		For more, see: "Laws of Cryptography link" */
			 
			/* P code */
			Random pARnd = new Random(System.currentTimeMillis());
			pA = BigInteger.probablePrime(pABitSize, pARnd);
			
			/* Q code */
			Random diffRndA = new Random(System.currentTimeMillis());
			long qARndSeed = diffRndA.nextLong();
			Random qARnd = new Random(qARndSeed);
			qA = BigInteger.probablePrime(qABitSize, qARnd);
			
			/* D code */
			Random dARndGen = new Random(System.currentTimeMillis());

			NA = pA.multiply(qA);
			
			BigInteger phiNA = (pA.subtract(ONE)).multiply(qA.subtract(ONE));
			
			/* Choose d until it is coprime with phiN AND > maxbetween p and q. */
			while(dA.compareTo(pA.max(qA)) <= 0)
				dA = BigInteger.probablePrime(dABitSize, dARndGen);
			/*
			 * Here we can assert that (d.gcd(phiN)compareTo(ONE) == 0) because
			 *		Since d is prime and > max(p,q), d must be > (p-1) and (q-1), and
			 *		since phiN = (p-1)(q-1), d is greater than the factors of phiN,
			 *		and cannot, therefore be a factor of phiN. The proof for this
			 *		assertion made in section VII.C of the Rivest Shamir Adleman is
			 *		available in Appendix G: Notes.
			 */
			
			eA = dA.modInverse(phiNA);
			
			System.out.println("p: " + pA);
			System.out.println("q: " + qA);
			System.out.println("N: " + NA);
			System.out.println("d: " + dA);
			System.out.println("e: " + eA);
			System.out.println("\n");

			/* Now the set of keys for person B... */
			
			/* P code */
			Random pBRnd = new Random(System.currentTimeMillis());
			pB = BigInteger.probablePrime(pBBitSize, pBRnd);
			
			/* Q code */
			Random diffRndB = new Random(System.currentTimeMillis());
			long qBRndSeed = diffRndB.nextLong();
			Random qBRnd = new Random(qBRndSeed);
			qB = BigInteger.probablePrime(qBBitSize, qBRnd);
			
			/* D code */
			Random dBRndGen = new Random(System.currentTimeMillis());

			NB = pB.multiply(qB);
			
			BigInteger phiNB = (pB.subtract(ONE)).multiply(qB.subtract(ONE));
			
			/* Choose d until it is coprime with phiN AND > maxbetween p and q. */
			while(dB.compareTo(pB.max(qB)) <= 0)
				dB = BigInteger.probablePrime(dBBitSize, dBRndGen);
			
			eB = dB.modInverse(phiNB);
			
			System.out.println("p: " + pB);
			System.out.println("q: " + qB);
			System.out.println("N: " + NB);
			System.out.println("d: " + dB);
			System.out.println("e: " + eB);
			System.out.println("\n");


			/* ************************** Encipher ********************************
			 *	- Convert the message to message strings that fit into the 
			 *		enciphering equation.
			 *	- Encipher the plaintext.
			 *	- Digitally sign the unsigned ciphertext and append to the 
			 *		ciphertext buffer (aka. output the text that is to be sent).
			 * *******************************************************************/
			
			
			/* ********************************
			 * Encipher variable declarations
			 * *******************************/
			int nLength = 0;
			double rndmNZdigit = 0;
			Integer iM = new Integer(0);
			String messageStr = new String("");
			StringBuffer messageBuf = new StringBuffer();
			BigInteger plainText = BigInteger.ZERO;
			BigInteger unsignedCT = BigInteger.ZERO;
			BigInteger cipherText = BigInteger.ZERO;
			StringBuffer cipherTextBuf = new StringBuffer();
						
			
					/* ******************** Message Conversion **********************
					 * - If the length of the message buffer is critical (defined as:
					 *		length >= length of N - 3:
					 *		- Set plaintext = contents of the message buffer
					 *		-  Encipher the plaintext and sign it. Append the result to 
					 *				ciphertext buffer (aka, output to ciphertext file.
					 *		- Empty the message buffer to get another message string.
					 * *************************************************************/
			
			nLength = Math.min(NA.toString().length(), NB.toString().length());
			
			/* Read all the characters from the file into a string buffer. */
			while(message.ready())
			{
				/* If the M buffer is empty, append a random non-zero digit. */
				if(messageBuf.length() == 0)
				{
					rndmNZdigit = Math.ceil(9 * Math.random());
					messageBuf.append((int)rndmNZdigit);
				}
				
				messageStr = iM.toString(message.read());

				/* Loop to add "0" to the front of the string while the length is 
				 *		less than 3 bits for proper delimitation. */
				while(messageStr.length() < messageASCIILength)
					messageStr = "0" + messageStr;

				messageBuf.append(messageStr);
				
					/* ******************** Encipher & Sign *************************
					 *	- If the length of the M buffer is critical (defined as >= 3 
					 *		less than the length of N:
					 *		- Set M equal to the contents of the M buffer.
					 *		- Encrypt M and append the result to the C buffer.
					 *		- Empty the M buffer to get another M.
					 ***************************************************************/
				if(messageBuf.length() >= nLength - criticalNMDifference || !message.ready())
				{
					plainText = new BigInteger(messageBuf.toString());
					
					unsignedCT = plainText.modPow(eB, NB);		// enciphers message
					cipherText = unsignedCT.modPow(dA, NA);	// signs message
					
					cipherTextBuf.append(cipherText.toString() + " ");
					messageBuf = new StringBuffer();
				}
			}
			
			message.close();


			/* ************************** Decipher ********************************
			 * - While there are characters to read from the signedCT buffer:
			 *		- Read in a signedCT character from the buffer until a space or 
			 *			the end of the buffer is reached.
			 * 	- Verifiy the digital signature and decipher the message.
			 *		- Translate the deciphered plaintext.
			 * *******************************************************************/
			
			
			/* ********************************
			 * Decipher variable declarations
			 * *******************************/
			int i = 0, j = 0;
			boolean newSetFlag = true;			
			StringBuffer signedCTBuf = new StringBuffer();
			char signedCTChar = '0';
			String signedCTStr = new String("");
			BigInteger signedCT = BigInteger.ZERO;
			BigInteger verifiedCT = BigInteger.ZERO;
			BigInteger decipheredPT = BigInteger.ZERO;
			StringBuffer decipheredPTBuf = new StringBuffer();
			char decipheredPTChar = '0';
			String ptTranslationStr = new String("");
			Integer iT = new Integer(0);
			StringBuffer ptTranslationBuf = new StringBuffer();
									
			
					/* ******************** Verification & Deciphering **************
					 *	- While there are characters in the signed ciphertext buffer 
					 *		to be read, read each one into a signedCT string.
					 *	- If the signed character is a space or there is no more of 
					 *		the buffer to read,
					 *		- verify the signature of the message.
					 *		- decipher the message.
					 *		- append the deciphered message to the deciphered plaintext 
					 *			buffer to be translated.
					 * *************************************************************/
			signedCTBuf = cipherTextBuf; // import the file containing ciphertext.
			System.out.println("\nciphertext: " + signedCTBuf);

			while(i < signedCTBuf.length())
			{
				signedCTChar = signedCTBuf.charAt(i);
				i++;

				if(signedCTChar == ' ' || i >= signedCTBuf.length())
				{
					signedCT = new BigInteger(signedCTStr);
					signedCTStr = "";
						
					verifiedCT = signedCT.modPow(eA, NA);		// verifiy signature
					decipheredPT = verifiedCT.modPow(dB, NB);	// decipher message
					
					decipheredPTBuf.append(decipheredPT.toString() + " ");
				}
				else
				{
					signedCTStr += signedCTChar;
				}
			}
			
			
					/* ******************** Translate *******************************
					 *	- While there are digits in the deciphered ciphertext buffer:
					 *		- If the newSetFlag is true, skip the first digit and 
					 *			change the flag.
					 *		- Read in three deciphered plaintext characters into the 
					 *			ptTranslationStr until the character read is a space or 
					 *			the end of the deciphered plaintext buffer has been 
					 *			reached. In this case, set the flag, increment the index 
					 *			and break.
					 *		- Cast the int values to char, and output.
					 * *************************************************************/
					
			System.out.println("\ndeciphered plaintext: " + decipheredPTBuf);
			
			while(j < decipheredPTBuf.length())
			{
				if(newSetFlag == true)
				{
					j++;
					newSetFlag = false;
				}
				
				while(ptTranslationStr.length() < messageASCIILength)
				{
					decipheredPTChar = (char)decipheredPTBuf.charAt(j);
					
					if(decipheredPTChar == ' ' || j >= decipheredPTBuf.length())
					{
						newSetFlag = true;
						j++;
						break;
					}
					else
					{
						ptTranslationStr += decipheredPTChar;
						j++;
					}
				}
				
				/* If not a new set, read the decimal ASCII stored in 
				 *		ptTranslationStr and convert it to a character, appending it 
				 *		to the ptTranlation buffer. */
				if(newSetFlag == false)
				{
					iT = new Integer(ptTranslationStr);
					ptTranslationBuf.append((char)iT.intValue());
					ptTranslationStr = "";
				}
			}
			
			System.out.println("\nplaintext: " + ptTranslationBuf);
		}
		catch(Exception e)
		{
			System.err.println("Main Error");
			e.printStackTrace();
		}
	}
}
