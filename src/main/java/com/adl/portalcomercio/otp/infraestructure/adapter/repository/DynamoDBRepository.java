package com.adl.portalcomercio.otp.infraestructure.adapter.repository;

import com.adl.portalcomercio.otp.domain.entity.OtpModel;
import com.adl.portalcomercio.otp.domain.repository.DBRepository;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import javax.inject.Singleton;
import java.text.SimpleDateFormat;
import java.util.Date;

@Singleton
public class DynamoDBRepository implements DBRepository {

    private final AmazonDynamoDBAsync client;
    private final String ATTEMPT_OTP_CREATE = "INTENTOS_GENERACION_OTP";
    private final String ID_CARD = "ID_CARD";
    private final String CARD_NUMBER = "CARD_NUMBER";
    private final String ATTEMPT_NUMBER = "numeroIntentos";
    private final String DATE_TIME_LOCK_CREATE_OTP = "dateTimeLockCreateOtp";

    public DynamoDBRepository(AmazonDynamoDBAsync client) {
        this.client = client;
    }

    @Override
    public int OtpValidation(OtpModel otpModel) throws Exception {
        int attemptNumber = getAttemptNumber(otpModel.getIdCard());
        int maxNumberOtpCreated = 4; // consumir servicio de constantes
        int maxMinutesOtpCreate = 1; // consumir servicio de constantes
        int minutesNextLocked = getMinutesNextLocked(otpModel.getIdCard());
        if (attemptNumber > maxNumberOtpCreated && minutesNextLocked < maxMinutesOtpCreate) {
            if (minutesNextLocked == 0) {
                this.insertMaxTimeToCreateOpt(otpModel.getIdCard(), attemptNumber);
            }
            throw new Exception("Superaste el numero maximo de solicitudes de codigo. " +
                    "Por favor verifica la cobertura de tu operador. " +
                    "Puedes intentar nuevamente en "+maxMinutesOtpCreate+" minutos");
        }
        if (minutesNextLocked > maxMinutesOtpCreate) {
            attemptNumber = 1;
        }
        return attemptNumber;
    }

    private int getMinutesNextLocked(String idCard) {
        Item outcome = null;
        long minutesNextLock = 0;
        Date dateTimeLocked = null;
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable("IntentosGeneracionOtp");
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("idCard", Integer.parseInt(idCard), "cardNumber", idCard);
        try {
            outcome = table.getItem(spec);
            if (outcome != null) {
                String savedDate = outcome.getString("dateTimeLockCreateOtp");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateTimeLocked = format.parse(savedDate);
            }
            long diff = new Date().getTime() - dateTimeLocked.getTime();
            minutesNextLock = diff / (60 * 1000);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return (int)minutesNextLock;
    }

    private void insertMaxTimeToCreateOpt (String idCard, int attemptNumber) {
        DynamoDB dynamoDB = new DynamoDB(client);
        try {
            Table table = dynamoDB.getTable("IntentosGeneracionOtp");

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Item item = new Item().withPrimaryKey("idCard", Integer.parseInt(idCard))
                    .withPrimaryKey("cardNumber", idCard)
                    .withString("dateTimeLockCreateOtp", String.valueOf(format.format(new Date())))
                    .withNumber("numeroIntentos", attemptNumber);
            table.putItem(item);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public int getAttemptNumber(String idCard) {
        Item outcome = null;
        int attemptNumber = 0;
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable("IntentosGeneracionOtp");
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("idCard", Integer.parseInt(idCard), "cardNumber", idCard);
        try {
            outcome = table.getItem(spec);
            attemptNumber = outcome.getInt(ATTEMPT_NUMBER);
            if (outcome == null) {
                attemptNumber = 1;
            } else {
                attemptNumber = outcome.getInt(ATTEMPT_NUMBER) + 1;
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            if (e.getMessage().equals("value of numeroIntentos is null")) {
                attemptNumber = 1;
            }
        }
        return attemptNumber;
    }

    @Override
    public void incrementNumberOtpCreated(OtpModel otpModel, int attemptNumber) {
        DynamoDB dynamoDB = new DynamoDB(client);
        try {
            Table table = dynamoDB.getTable("IntentosGeneracionOtp");

            Item item = new Item().withPrimaryKey("idCard", Integer.parseInt(otpModel.getIdCard()))
                    .withPrimaryKey("cardNumber", otpModel.getIdCard())
                    .withNumber("numeroIntentos", attemptNumber);
            table.putItem(item);
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
