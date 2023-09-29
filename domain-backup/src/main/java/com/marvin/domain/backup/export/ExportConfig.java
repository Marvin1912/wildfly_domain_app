package com.marvin.domain.backup.export;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExportConfig {

    private final String costExportFolder;
    private final String parentFolderName;
    private final String credentials;

    public ExportConfig() {
        this.credentials = "/home/app/google/credentials.json";
        this.costExportFolder = "/home/app/export";
        this.parentFolderName = "backup";
    }

    public String getCostExportFolder() {
        return costExportFolder;
    }

    public String getParentFolderName() {
        return parentFolderName;
    }

    public String getCredentials() {
        return credentials;
    }
}
