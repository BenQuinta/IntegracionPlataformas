package cl.duoc.miprimeraapi.service;

import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCreateResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCommitResponse;

import org.springframework.stereotype.Service;

import java.util.UUID;

import static cl.transbank.common.IntegrationApiKeys.WEBPAY;
import static cl.transbank.common.IntegrationCommerceCodes.WEBPAY_PLUS;
import static cl.transbank.common.IntegrationType.TEST;

@Service
public class WebpayService {

    private static final WebpayOptions options = new WebpayOptions(
        WEBPAY_PLUS,
        WEBPAY,
        TEST
    );

    private static final String RETURN_URL = "http://localhost:8080/webpay/confirmacion";

    public WebpayPlusTransactionCreateResponse crearTransaccion(double monto) throws Exception {
        String buyOrder = UUID.randomUUID().toString().substring(0, 26);
        String sessionId = UUID.randomUUID().toString().substring(0, 26);

        WebpayPlus.Transaction tx = new WebpayPlus.Transaction(options);
        return tx.create(buyOrder, sessionId, monto, RETURN_URL);
    }

    public WebpayPlusTransactionCommitResponse confirmarTransaccion(String token) throws Exception {
        WebpayPlus.Transaction tx = new WebpayPlus.Transaction(options);
        return tx.commit(token);
    }
}