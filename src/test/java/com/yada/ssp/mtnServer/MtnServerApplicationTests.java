package com.yada.ssp.mtnServer;

import com.yada.ssp.mtnServer.util.SignUtil;
import org.junit.Test;

public class MtnServerApplicationTests {

	@Test
	public void contextLoads() {
		String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDWoiPFWkt9tSnHMlzCZrJ5P+TZuwDz9wVcv8U/LSe+i+DlPun/9T9jxn6+q7Feiowu7lifIXOMatOMydKTB/0xAKOxUXKLebkB147AC6+3ZukrijPnF5LCej+1voH7TE2Ns4j3aG3zpVz6zaWdXOm0I9eEA9kXWv5awKcbHD5utxpUcpViD0GRy5p8C7uKYgg9t4hpaCBLI3ZaKmFK3w/wB8V6iibAzvJycNXyKoSLT+gEc3ah0RZQoqKBdCRgyb8aLds21KzMWh01X8lghU9LvLwkmasngLR+/4gQU1ljOOvuRlZmxcC568KqUu4buaCb13hiI9otkLPMghn9GmW1AgMBAAECggEAeRv8ezu8JS64aEIuvTMdufUnuQQgQYk9aVx8fG2KY6aiPDkH6PoFztMOaaCP8PzUpRawwvopLS6KOIMZYwW7BI+Lzl5a+ACzOCrdzdfKSv6yy3KsXtWaZkp88JyS0632hOKtgo1WnYjNsmef7++hn5gp38AcG2Wo6KSUpmOFhwqZylEXj/sr1+etjJcuNakM5pLPhyhBDJVL4YfuLMVwTnTypMkHeefOhTkq7eKcScB/bfRp0EQ/nRnJwDsFd8geXhgRz06xxgus74hw72/gGRmMyTHnuM5sTX9QVK/Kcp/dlhBtvHQz+PxSZGQYD4bdu/vTqOuWLswXqE215DPJAQKBgQD31tXqE6i1/vmzC+Q0dc23Uzbg8ydiIACmq7iFt03oaeDf9dx7TrYAYYBzitAhAHsw4U/jSIsIzx7+/hkGkg7JB0rW5tXCOgviHX0ZePWWO4gME7MC8lhAd+9Y8dwti/6HBf//z9KuKivmacOagkgZwvxcBea6svd5cs6Qw8q7JQKBgQDds2Ub1Wa2ataEsQA3Gp78p++t2Ed/eJkhUlG9FYywUQhYncS03i3SRSpsRKVjQ0zwoj13yrU7ROrZhglcpVl5rA33OBhiWynaNY9yIKdm0Lo6B4z18bdpj6LZRyt/q9k56wel47jkOhKTnVyYG9GFf4ry5vlew2Gp253In0jDUQKBgAfPJCRhBDLf2txSZplkkNvS6mrCHp6P5ZVa2dCUywakt2o3JABapY9zgwmg+RfhHQCYSN0ffwoDBLYCKaW0WnTpHumQknGxRIiWZ0ezMQHP1GSVBlH0cuVtIgSKcmaY/6cfgEZ+NOQ5/fIfqQMoUb9GDo+TvAZr9zJDdNDI6o1lAoGABur7I7Q0AUsyKG/RfuawwVeILheKm5qqxJRqAolym2nb5c/+fFpWebI40aoOsxs9gmC9pEhKAXB0F5eMITzzns1Unjs834zSsIFtWXVVY+rtdLQZnO8O9xdJUJhc7h3xqFICKhYCBfUd8Uc+xWxQzGafclbsvx0/peo5cTIvNhECgYAR8dATRz5r42DN6Jlz7Rzg1k09fBIl+twTimxOZdauMZmv66fx3/2jLaOd/CObg+aGHW8FZF8cLNzNzZqo8uQDW0HjBahtrCbgBzglT6tWcUFofXc22FpbFgc9BJj9ATbhhWbhRYJBaBUDXEN+A+hmARnLDqMmM1r5jNOXIJWSxQ==";

		String data = "{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102113539\",\"orgId\":\"0001\"},\"msgResponse\":{\"respCode\":\"00\"},\"certificateSignature\":{\"signature\":\"00000000\"}}";
		String sign = SignUtil.sign(data, privateKey);

		System.out.println(data.replaceFirst("00000000", sign));
	}
}

