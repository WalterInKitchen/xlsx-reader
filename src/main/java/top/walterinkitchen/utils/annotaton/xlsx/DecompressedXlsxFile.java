package top.walterinkitchen.utils.annotaton.xlsx;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * represent an decompressed xlsx file
 *
 * @author walter
 * @since 1.0
 */
public class DecompressedXlsxFile implements XlsxFileContainer {
    private final File decompressed;

    /**
     * constructr
     *
     * @param decompressed decompressed folder
     */
    DecompressedXlsxFile(File decompressed) {
        this.decompressed = decompressed;
    }

    @Override
    public Optional<File> getSheet(String sheetName) {
        return Optional.empty();
    }

    @Override
    public Optional<File> getSheetAt(int index) {
        return Optional.empty();
    }

    @Override
    public Optional<File> getSharedStrings() {
        return Optional.empty();
    }

    @Override
    public void close() throws IOException {

    }
}
