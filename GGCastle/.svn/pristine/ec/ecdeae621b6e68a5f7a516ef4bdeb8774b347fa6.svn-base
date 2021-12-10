package com.sga.sol.otp;

import java.security.SecureRandom;


import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import de.taimos.totp.TOTP;

@Service
public class TOTPTokenService {

	public String generateSecretKey() {// 시크릿 키 생성
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    public boolean validate(String inputCode, String decAPIKey) {// 생성된 코드와 입력 받은 코드가 같은지 검증
        String code = getTOTPCode(decAPIKey);
        return code.equals(inputCode);
    }
    
    public String getTOTPCode(String decAPIKey) {// 키 해독
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(decAPIKey);// 테스트용 키를 해독
        String hexKey = Hex.encodeHexString(bytes);// 해독한 bytes를 16진수로 변경
        return TOTP.getOTP(hexKey);// 16진수를 알고리즘으로 변경
    }

}
