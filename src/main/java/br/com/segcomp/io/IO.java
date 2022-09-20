package br.com.segcomp.io;

import br.com.segcomp.aes.block.Block;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

public class IO {

    public Optional<Block[]> openFileFromResources(String filename) {
        Path inputPath = Paths.get("src/main/java/br/com/segcomp/aes/resources/", filename);
        File file = new File(inputPath.toString());
        byte[] array;
        Block[] fileBlocks = null;
        try (FileInputStream fileStream = new FileInputStream(file)) {
            array = new byte[(int)file.length()];
            int bytesRead = fileStream.read(array);
            System.out.println("Bytes lidos: " + bytesRead);
            fileBlocks = Block.createBlockArray(array, 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(fileBlocks);
    }

    public void writeFileToResources(byte[] bytes, String extension) throws FileNotFoundException {
        Path outputPath = Paths.get("src/main/java/br/com/segcomp/aes/resources/output_" + String.valueOf(Timestamp.from(Instant.now())).replace(":", "") + extension);
        try (FileOutputStream output = new FileOutputStream(outputPath.toString())){
            output.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
