package com.yada.ssp.mtnServer.service;

import com.yada.ssp.mtnServer.dao.ApiOrgDao;
import com.yada.ssp.mtnServer.dao.TransDao;
import com.yada.ssp.mtnServer.model.ApiOrg;
import com.yada.ssp.mtnServer.model.Trans;
import com.yada.ssp.mtnServer.net.HttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NotifyService.class)
public class NotifyServiceTest {

    @MockBean
    private ApiOrgDao apiOrgDao;
    @MockBean
    private TransDao transDao;
    @MockBean
    private HttpClient httpClient;
    @MockBean
    private NotifyErrService notifyErrService;
    @Autowired
    private NotifyService notifyService;

    private String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDWoiPFWkt9tSnHMlzCZrJ5P+TZuwDz9wVcv8U/LSe+i+DlPun/9T9jxn6+q7Feiowu7lifIXOMatOMydKTB/0xAKOxUXKLebkB147AC6+3ZukrijPnF5LCej+1voH7TE2Ns4j3aG3zpVz6zaWdXOm0I9eEA9kXWv5awKcbHD5utxpUcpViD0GRy5p8C7uKYgg9t4hpaCBLI3ZaKmFK3w/wB8V6iibAzvJycNXyKoSLT+gEc3ah0RZQoqKBdCRgyb8aLds21KzMWh01X8lghU9LvLwkmasngLR+/4gQU1ljOOvuRlZmxcC568KqUu4buaCb13hiI9otkLPMghn9GmW1AgMBAAECggEAeRv8ezu8JS64aEIuvTMdufUnuQQgQYk9aVx8fG2KY6aiPDkH6PoFztMOaaCP8PzUpRawwvopLS6KOIMZYwW7BI+Lzl5a+ACzOCrdzdfKSv6yy3KsXtWaZkp88JyS0632hOKtgo1WnYjNsmef7++hn5gp38AcG2Wo6KSUpmOFhwqZylEXj/sr1+etjJcuNakM5pLPhyhBDJVL4YfuLMVwTnTypMkHeefOhTkq7eKcScB/bfRp0EQ/nRnJwDsFd8geXhgRz06xxgus74hw72/gGRmMyTHnuM5sTX9QVK/Kcp/dlhBtvHQz+PxSZGQYD4bdu/vTqOuWLswXqE215DPJAQKBgQD31tXqE6i1/vmzC+Q0dc23Uzbg8ydiIACmq7iFt03oaeDf9dx7TrYAYYBzitAhAHsw4U/jSIsIzx7+/hkGkg7JB0rW5tXCOgviHX0ZePWWO4gME7MC8lhAd+9Y8dwti/6HBf//z9KuKivmacOagkgZwvxcBea6svd5cs6Qw8q7JQKBgQDds2Ub1Wa2ataEsQA3Gp78p++t2Ed/eJkhUlG9FYywUQhYncS03i3SRSpsRKVjQ0zwoj13yrU7ROrZhglcpVl5rA33OBhiWynaNY9yIKdm0Lo6B4z18bdpj6LZRyt/q9k56wel47jkOhKTnVyYG9GFf4ry5vlew2Gp253In0jDUQKBgAfPJCRhBDLf2txSZplkkNvS6mrCHp6P5ZVa2dCUywakt2o3JABapY9zgwmg+RfhHQCYSN0ffwoDBLYCKaW0WnTpHumQknGxRIiWZ0ezMQHP1GSVBlH0cuVtIgSKcmaY/6cfgEZ+NOQ5/fIfqQMoUb9GDo+TvAZr9zJDdNDI6o1lAoGABur7I7Q0AUsyKG/RfuawwVeILheKm5qqxJRqAolym2nb5c/+fFpWebI40aoOsxs9gmC9pEhKAXB0F5eMITzzns1Unjs834zSsIFtWXVVY+rtdLQZnO8O9xdJUJhc7h3xqFICKhYCBfUd8Uc+xWxQzGafclbsvx0/peo5cTIvNhECgYAR8dATRz5r42DN6Jlz7Rzg1k09fBIl+twTimxOZdauMZmv66fx3/2jLaOd/CObg+aGHW8FZF8cLNzNzZqo8uQDW0HjBahtrCbgBzglT6tWcUFofXc22FpbFgc9BJj9ATbhhWbhRYJBaBUDXEN+A+hmARnLDqMmM1r5jNOXIJWSxQ==";
    private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1qIjxVpLfbUpxzJcwmayeT/k2bsA8/cFXL/FPy0nvovg5T7p//U/Y8Z+vquxXoqMLu5YnyFzjGrTjMnSkwf9MQCjsVFyi3m5AdeOwAuvt2bpK4oz5xeSwno/tb6B+0xNjbOI92ht86Vc+s2lnVzptCPXhAPZF1r+WsCnGxw+brcaVHKVYg9BkcuafAu7imIIPbeIaWggSyN2WiphSt8P8AfFeoomwM7ycnDV8iqEi0/oBHN2odEWUKKigXQkYMm/Gi3bNtSszFodNV/JYIVPS7y8JJmrJ4C0fv+IEFNZYzjr7kZWZsXAuevCqlLuG7mgm9d4YiPaLZCzzIIZ/RpltQIDAQAB";

    @Test
    public void testSendOrgNull() {
        Mockito.when(apiOrgDao.findByMerchants_MerNo(Mockito.anyString())).thenReturn(Optional.empty());
        notifyService.send("merNo", "lsId");

        Mockito.verify(apiOrgDao, Mockito.times(1)).findByMerchants_MerNo("merNo");
    }

    @Test
    public void testSendTransNull() {
        ApiOrg apiOrg = new ApiOrg();
        apiOrg.setOrgId("0001");
        apiOrg.setPublicKey(publicKey);
        apiOrg.setPrivateKey(privateKey);
        apiOrg.setNotifyUrl("notifyUrl");
        Mockito.when(apiOrgDao.findByMerchants_MerNo(Mockito.anyString())).thenReturn(Optional.of(apiOrg));
        Mockito.when(transDao.findById(Mockito.anyString())).thenReturn(Optional.empty());
        notifyService.send("merNo", "lsId");

        Mockito.verify(apiOrgDao, Mockito.times(1)).findByMerchants_MerNo("merNo");
        Mockito.verify(transDao, Mockito.times(1)).findById("lsId");
        Mockito.verify(notifyErrService, Mockito.times(1)).next("merNo", "lsId");
    }

    @Test
    public void testSendIOException() throws IOException {
        ApiOrg apiOrg = new ApiOrg();
        apiOrg.setOrgId("0001");
        apiOrg.setPublicKey(publicKey);
        apiOrg.setPrivateKey(privateKey);
        apiOrg.setNotifyUrl("notifyUrl");
        Mockito.when(apiOrgDao.findByMerchants_MerNo(Mockito.anyString())).thenReturn(Optional.of(apiOrg));
        Mockito.when(transDao.findById(Mockito.anyString())).thenReturn(Optional.of(new Trans()));
        Mockito.when(httpClient.postJson(Mockito.anyString(), Mockito.anyString())).thenThrow(new IOException("Test IOException"));

        notifyService.send("merNo", "lsId");

        Mockito.verify(apiOrgDao, Mockito.times(1)).findByMerchants_MerNo("merNo");
        Mockito.verify(transDao, Mockito.times(1)).findById("lsId");
        Mockito.verify(httpClient, Mockito.times(1)).postJson(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(notifyErrService, Mockito.times(1)).next("merNo", "lsId");
    }

    @Test
    public void testSendPublicKeyNull() throws IOException {
        ApiOrg apiOrg = new ApiOrg();
        apiOrg.setOrgId("0001");
        apiOrg.setPrivateKey(privateKey);
        apiOrg.setNotifyUrl("notifyUrl");
        Mockito.when(apiOrgDao.findByMerchants_MerNo(Mockito.anyString())).thenReturn(Optional.of(apiOrg));
        Mockito.when(transDao.findById(Mockito.anyString())).thenReturn(Optional.of(new Trans()));
        Mockito.when(httpClient.postJson(Mockito.anyString(), Mockito.anyString())).thenReturn("{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102113539\",\"orgId\":\"0001\"},\"certificateSignature\":{\"signature\":\"ybioQd1qVK355al58wmyL8lPoyX95qXYdoIlkuIU29uQSTlU0Jljk7jsPXsHIVGyTpSFkw9Khb5mO3Q2l39eL9b4Igp4ZJY+FdB4sQIIFBxuA0cDkY2i8lTROhiO0N6TtKgiShEqUAKX0nQW9kitUw8TXB1iAuoIkHdCdyYPSshEGl2ngm5smZ2VPgMN8foFvlHDvGFeEXQlL8WWi+8vDh0jhr2nO511RBmSIp55DlXrLAekwlqjhFLgsqWqWMidwlhnTMYXpzHFJjHabfTk1CqNxoME8Qi6ATIXV2vsmaYnDitq1smtIfl3eb6YOvv+wYJ3Rl68XGuJ+vJMS2wkrQ==\"}}");

        notifyService.send("merNo", "lsId");

        Mockito.verify(apiOrgDao, Mockito.times(1)).findByMerchants_MerNo("merNo");
        Mockito.verify(transDao, Mockito.times(1)).findById("lsId");
        Mockito.verify(httpClient, Mockito.times(1)).postJson(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(notifyErrService, Mockito.times(1)).next("merNo", "lsId");
    }

    @Test
    public void testSendSignFail() throws IOException {
        ApiOrg apiOrg = new ApiOrg();
        apiOrg.setOrgId("0001");
        apiOrg.setPublicKey(publicKey);
        apiOrg.setPrivateKey(privateKey);
        apiOrg.setNotifyUrl("notifyUrl");
        Mockito.when(apiOrgDao.findByMerchants_MerNo(Mockito.anyString())).thenReturn(Optional.of(apiOrg));
        Mockito.when(transDao.findById(Mockito.anyString())).thenReturn(Optional.of(new Trans()));
        Mockito.when(httpClient.postJson(Mockito.anyString(), Mockito.anyString())).thenReturn("{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102113500\",\"orgId\":\"0001\"},\"certificateSignature\":{\"signature\":\"ybioQd1qVK355al58wmyL8lPoyX95qXYdoIlkuIU29uQSTlU0Jljk7jsPXsHIVGyTpSFkw9Khb5mO3Q2l39eL9b4Igp4ZJY+FdB4sQIIFBxuA0cDkY2i8lTROhiO0N6TtKgiShEqUAKX0nQW9kitUw8TXB1iAuoIkHdCdyYPSshEGl2ngm5smZ2VPgMN8foFvlHDvGFeEXQlL8WWi+8vDh0jhr2nO511RBmSIp55DlXrLAekwlqjhFLgsqWqWMidwlhnTMYXpzHFJjHabfTk1CqNxoME8Qi6ATIXV2vsmaYnDitq1smtIfl3eb6YOvv+wYJ3Rl68XGuJ+vJMS2wkrQ==\"}}");

        notifyService.send("merNo", "lsId");

        Mockito.verify(apiOrgDao, Mockito.times(1)).findByMerchants_MerNo("merNo");
        Mockito.verify(transDao, Mockito.times(1)).findById("lsId");
        Mockito.verify(httpClient, Mockito.times(1)).postJson(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(notifyErrService, Mockito.times(1)).next("merNo", "lsId");
    }

    @Test
    public void testSendRespErr() throws IOException {
        ApiOrg apiOrg = new ApiOrg();
        apiOrg.setOrgId("0001");
        apiOrg.setPublicKey(publicKey);
        apiOrg.setPrivateKey(privateKey);
        apiOrg.setNotifyUrl("notifyUrl");
        Mockito.when(apiOrgDao.findByMerchants_MerNo(Mockito.anyString())).thenReturn(Optional.of(apiOrg));
        Mockito.when(transDao.findById(Mockito.anyString())).thenReturn(Optional.of(new Trans()));
        Mockito.when(httpClient.postJson(Mockito.anyString(), Mockito.anyString())).thenReturn("{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102113539\",\"orgId\":\"0001\"},\"certificateSignature\":{\"signature\":\"ybioQd1qVK355al58wmyL8lPoyX95qXYdoIlkuIU29uQSTlU0Jljk7jsPXsHIVGyTpSFkw9Khb5mO3Q2l39eL9b4Igp4ZJY+FdB4sQIIFBxuA0cDkY2i8lTROhiO0N6TtKgiShEqUAKX0nQW9kitUw8TXB1iAuoIkHdCdyYPSshEGl2ngm5smZ2VPgMN8foFvlHDvGFeEXQlL8WWi+8vDh0jhr2nO511RBmSIp55DlXrLAekwlqjhFLgsqWqWMidwlhnTMYXpzHFJjHabfTk1CqNxoME8Qi6ATIXV2vsmaYnDitq1smtIfl3eb6YOvv+wYJ3Rl68XGuJ+vJMS2wkrQ==\"}}");

        notifyService.send("merNo", "lsId");

        Mockito.verify(apiOrgDao, Mockito.times(1)).findByMerchants_MerNo("merNo");
        Mockito.verify(transDao, Mockito.times(1)).findById("lsId");
        Mockito.verify(httpClient, Mockito.times(1)).postJson(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(notifyErrService, Mockito.times(1)).next("merNo", "lsId");
        Mockito.verify(notifyErrService, Mockito.times(0)).delete("lsId");
    }

    @Test
    public void testSendSuccess() throws IOException {
        ApiOrg apiOrg = new ApiOrg();
        apiOrg.setOrgId("0001");
        apiOrg.setPublicKey(publicKey);
        apiOrg.setPrivateKey(privateKey);
        apiOrg.setNotifyUrl("notifyUrl");
        Mockito.when(apiOrgDao.findByMerchants_MerNo(Mockito.anyString())).thenReturn(Optional.of(apiOrg));
        Mockito.when(transDao.findById(Mockito.anyString())).thenReturn(Optional.of(new Trans()));
        Mockito.when(httpClient.postJson(Mockito.anyString(), Mockito.anyString())).thenReturn("{\"msgInfo\":{\"versionNo\":\"1.0.0\",\"timeStamp\":\"20190102113539\",\"orgId\":\"0001\"},\"msgResponse\":{\"respCode\":\"00\"},\"certificateSignature\":{\"signature\":\"TTfkeg0aprOt/Y/O6S0HPXKNijIP49J8uPfqxv20IOJe16xxVPeBrkLDoT83C7j4VAsCo4bkpoLAxl19vX+uc0XKZ5X/bczLsfNIqGzDgTEZcJ+QfiQG4NICG9HSrbbVOlNwQ34jgDdvGD9O9yP2yU31GU90oJqqHBr6naiDUKBqdw8wqPE4v8w5bzVfdMMUS8xTI2RVY+QV1cvHlgzdYok5N3LcWTl0w9YXdQh9r8NuBIfVanv4omqv4U9HTbKEFnRTp7XVjK1iiCHV6/PkrIWkvpod77lxoTYuM5u+TQzd/e5XnMVvM8aQLAAuosAUYBzoPrYOdL6qSaMOp2eemg==\"}}");

        notifyService.send("merNo", "lsId");

        Mockito.verify(apiOrgDao, Mockito.times(1)).findByMerchants_MerNo("merNo");
        Mockito.verify(transDao, Mockito.times(1)).findById("lsId");
        Mockito.verify(httpClient, Mockito.times(1)).postJson(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(notifyErrService, Mockito.times(0)).next("merNo", "lsId");
        Mockito.verify(notifyErrService, Mockito.times(1)).delete("lsId");
    }
}