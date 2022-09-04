package top.walterinkitchen.utils.xlsx;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import top.walterinkitchen.utils.utils.DeCompressorFactory;

import java.io.File;

/**
 * XlsxFileContainerFactory
 *
 * @author walter
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XlsxFileContainerFactory {
    /**
     * create a decompressed xlsx file
     *
     * @param sourceFile the original file
     * @return container instance
     */
    static public XlsxFileContainer createDeCompressedXlsxFile(File sourceFile) {
        File deCompressed = DeCompressorFactory.getInstance().decompressZip(sourceFile);
        return new DecompressedXlsxFile(deCompressed);
    }
}
