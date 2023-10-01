package com.marvin.domain.backup.upload;

import com.marvin.domain.backup.export.ExportConfig;
import com.marvin.domain.backup.google.GoogleDrive;
import com.marvin.domain.backup.google.GoogleDriveException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@ApplicationScoped
public class Uploader {

    private static final Logger LOGGER = Logger.getLogger(Uploader.class.getName());

    private static final DateTimeFormatter FILE_DTF = DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss");

    @Inject
    private GoogleDrive googleDrive;

    @Inject
    private ExportConfig exportConfig;

    public void zipAndUploadCostFiles(List<Path> filesToZipAndUpload) {

        LOGGER.log(Level.INFO, "Going to zip and upload files!");

        String costExportFolder = exportConfig.getCostExportFolder();

        final Path dirPath = Paths.get(costExportFolder);
        final Path zipFilePath = dirPath.resolve(costExportFolder + "/files_" + LocalDateTime.now().format(FILE_DTF) + ".zip");

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFilePath))) {
            zipOutputStream.setLevel(9);
            filesToZipAndUpload.stream()
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(dirPath.relativize(path).toString());
                        LOGGER.log(Level.INFO, "Added zip entry " + zipEntry.getName());
                        try {
                            zipOutputStream.putNextEntry(zipEntry);
                            Files.copy(path, zipOutputStream);
                            zipOutputStream.closeEntry();
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Could not handle files!", e);
        }

        try {
            String parentId = googleDrive.getFileId("backup");
            googleDrive.uploadFile(zipFilePath, parentId);
            for (Path path : filesToZipAndUpload) {
                try {
                    Files.delete(path);
                    LOGGER.log(Level.INFO, "Deleted file " + path + "!");
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Could not delete file " + path + "!", e);
                }
            }
        } catch (GoogleDriveException e) {
            LOGGER.log(Level.SEVERE, "Could not upload file!", e);
            throw new IllegalStateException(e);
        }
    }
}
