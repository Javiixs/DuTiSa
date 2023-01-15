package com.github.badaccuracy.id.dutisa.googleapi;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SheetAPI {

    private final String applicationName = "NAR23-1 Spreadsheet Sync";
    private final String spreadsheetId = "1pPsYMbCEXDfWKYABKIwxcUy0xevOW3Ww-8dEumLvdbc";
    private final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    private final List<String> scope = Collections.singletonList(SheetsScopes.SPREADSHEETS);

    private Credential getCredential(NetHttpTransport httpTransport) {
        try (InputStream stream = SheetAPI.class.getResourceAsStream("/credentials.json")) {
            if (stream == null) {
                return null;
            }

            GoogleClientSecrets secrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(stream));
            String TOKENS_DIRECTORY_PATH = "tokens";
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, jsonFactory, secrets, scope)
                    .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                    .setAccessType("offline")
                    .build();

            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
            return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SneakyThrows
    public void doWrite(String sheetName, List<List<Object>> values) {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        String range = sheetName + "!A2:C";
        Sheets service = new Sheets.Builder(httpTransport, jsonFactory, this.getCredential(httpTransport))
                .setApplicationName(applicationName)
                .build();

        pushUpdate(range, service, values);
    }

    @SneakyThrows
    private void writeHeader(String sheetName) {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        String range = sheetName + "!A2:C2";
        Sheets service = new Sheets.Builder(httpTransport, jsonFactory, this.getCredential(httpTransport))
                .setApplicationName(applicationName)
                .build();

        List<List<Object>> values = Arrays.asList(
                Arrays.asList("Sender", "Date", "Comment")
        );

        pushUpdate(range, service, values);
    }

    private void pushUpdate(String range, Sheets service, List<List<Object>> values) {
        UpdateValuesResponse response;
        try {
            ValueRange body = new ValueRange()
                    .setValues(values);

            response = service.spreadsheets().values()
                    .update(spreadsheetId, range, body)
                    .setValueInputOption("raw")
                    .execute();

            System.out.printf("%d cells updated.", response.getUpdatedCells());
        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 401) {
                System.out.println("Invalid credentials");
            } else {
                System.out.println("GoogleJsonResponseException: " + error.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
