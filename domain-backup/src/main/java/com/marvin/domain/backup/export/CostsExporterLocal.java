package com.marvin.domain.backup.export;

import jakarta.ejb.Local;

import java.nio.file.Path;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Local
public interface CostsExporterLocal {

    void exportCosts() throws Exception;

    <T> void exportCost(Path path, Supplier<Stream<T>> costs);

}
