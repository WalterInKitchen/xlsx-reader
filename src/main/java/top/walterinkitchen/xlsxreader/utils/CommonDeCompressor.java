package top.walterinkitchen.xlsxreader.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import top.walterinkitchen.xlsxreader.ReaderException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * decompressor powered by commons compress
 *
 * @author walter
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonDeCompressor implements DeCompressor {
    @Override
    public File decompressZip(File src) {
        ZipFile zipFile = null;
        try {
            File folder = Files.createTempDirectory(FilenameUtils.getBaseName(src.getName())).toFile();
            folder.deleteOnExit();
            zipFile = new ZipFile(src);
            zipFile.extractAll(folder.getAbsolutePath());
            return folder;
        } catch (IOException exc) {
            throw new ReaderException("extract xlsx failed", exc);
        } finally {
            IOUtils.closeQuietly(zipFile);
        }
    }
}
