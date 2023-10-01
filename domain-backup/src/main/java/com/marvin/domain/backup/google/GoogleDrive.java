package com.marvin.domain.backup.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.marvin.domain.backup.export.ExportConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class GoogleDrive {

    private static final Logger LOGGER = Logger.getLogger(GoogleDrive.class.getName());

    private static final String APPLICATION_NAME = "WildFly Domain App";

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

    @Inject
    private ExportConfig exportConfig;

    public String getFileId(String fileName) throws GoogleDriveException {

        LOGGER.log(Level.INFO, "Trying to get file ID for " + fileName + "!");

        try {
            final NetHttpTransport netHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
            final Drive service = new Drive.Builder(netHttpTransport, JSON_FACTORY, getCredentials())
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            final FileList result = service.files().list()
                    .setQ("mimeType = 'application/vnd.google-apps.folder' and name = 'backup'")
                    .setFields("files(id)")
                    .execute();

            if (result == null || result.isEmpty()) {
                throw new GoogleDriveException("No file with name " + fileName + " found!");
            }

            return result.getFiles().get(0).getId();
        } catch (Exception e) {
            throw new GoogleDriveException(e);
        }
    }

    public void uploadFile(Path path, String parent) throws GoogleDriveException {

        LOGGER.log(Level.INFO, "Trying to upload file to {0}/{1} !", new Object[]{parent, path.getFileName()});

        try {
            final NetHttpTransport netHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
            final Drive service = new Drive.Builder(netHttpTransport, JSON_FACTORY, getCredentials())
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            final File fileMetadata = new File();
            fileMetadata.setName(path.getFileName().toString());
            fileMetadata.setParents(List.of(parent));

            final var filePath = new java.io.File(path.toAbsolutePath().toString());
            final FileContent mediaContent = new FileContent("application/zip", filePath);

            final File file = service.files()
                    .create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            LOGGER.log(Level.INFO, "Uploaded file {0}. File ID: {1}.", new Object[]{path.getFileName(), file.getId()});
        } catch (Exception e) {
            throw new GoogleDriveException(e);
        }
    }

    private Credential getCredentials() throws IOException {
        return GoogleCredential
                .fromStream(new FileInputStream(exportConfig.getCredentials()))
                .createScoped(SCOPES);
    }
}
