import java.math.*;
import java.lang.*;

class modTest
{
	public static void main(String args[])
	{
		BigInteger epavila = new BigInteger("23561");
		BigInteger dpavila = new BigInteger("16416761");
		BigInteger Npavila = new BigInteger("215489");
		BigInteger epravila = new BigInteger("121433");
		BigInteger dpravila = new BigInteger("13333217");
		BigInteger Npravila = new BigInteger("135419");
		BigInteger pt = new BigInteger("6097");
		BigInteger uct = new BigInteger("0");
		BigInteger sct = new BigInteger("0");
		BigInteger vct = new BigInteger("0");
		BigInteger dpt = new BigInteger("0");
		
		uct = pt.modPow(epravila, Npravila);		// enciphers message
		sct = uct.modPow(dpavila, Npavila);			// signs message
		vct = sct.modPow(epavila, Npavila);			// verifiy signature
		dpt = vct.modPow(dpravila, Npravila);		// decipher message
		
		System.out.println("Plaintext: " + pt);
		System.out.println("UnsignedCT: " + pt + "^" + epravila + " mod " + Npravila + " = " + uct);
		System.out.println("SignedCT: " + uct + "^" + dpavila + " mod " + Npavila + " = " + sct);
		System.out.println("UnsignedCT: " + sct + "^" + epavila + " mod " + Npavila + " = " + vct);
		System.out.println("DecipheredPT: " + vct + "^" + dpravila + " mod " + Npravila + " = " + dpt);
	}
}
