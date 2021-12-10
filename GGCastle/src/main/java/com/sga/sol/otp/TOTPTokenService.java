package com.sga.sol.otp;

import java.security.SecureRandom;


import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import de.taimos.totp.TOTP;

@Service
public class TOTPTokenService {

	public String generateSecretKey() {// ��ũ�� Ű ����
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    public boolean validate(String inputCode, String decAPIKey) {// ������ �ڵ�� �Է� ���� �ڵ尡 ������ ����
        String code = getTOTPCode(decAPIKey);
        return code.equals(inputCode);
    }
    
    public String getTOTPCode(String decAPIKey) {// Ű �ص�
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(decAPIKey);// �׽�Ʈ�� Ű�� �ص�
        String hexKey = Hex.encodeHexString(bytes);// �ص��� bytes�� 16������ ����
        return TOTP.getOTP(hexKey);// 16������ �˰������� ����
    }

}
