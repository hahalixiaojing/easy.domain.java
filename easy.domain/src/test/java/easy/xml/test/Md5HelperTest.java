package easy.xml.test;

import java.io.UnsupportedEncodingException;

import org.junit.*;

import easy.core.cryptography.DESHelper;
import easy.core.cryptography.MD5Helper;
import easy.core.cryptography.SHA256Helper;
/**
 * sha256加密
 * @author 晓静
 *
 */
public class Md5HelperTest {
	@Test
	public void md5Test(){
		String text = MD5Helper.encrypt("1232132132132132132131afdf我伴侣要在");
	
		Assert.assertEquals(text.toUpperCase(), "519AC5DDC4180ED3D3CE87C2E42BD86C");
	}
	
	@Test
	public void sha256Test(){
		String text1 = SHA256Helper.encrypt("123213李");
		String text2 = SHA256Helper.encrypt("123213李");
		
		Assert.assertEquals(text1, text2);
	}
	
	@Test
	public void desTest() throws UnsupportedEncodingException{
		
		String password ="00000000";
		String plainText = "abc李晓静123";
		String safeText = DESHelper.encrypt(plainText, password);
		
		String toPlainText = DESHelper.decrypt(safeText, password);
		
		Assert.assertEquals(plainText, toPlainText);
	}
}
