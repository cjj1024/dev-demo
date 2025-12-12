import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

public class MMapDemo {
    private static final int NUM_LOGS = 10_000_000;
    private static final int LOG_SIZE = 100;
    private static final String LOG_LINE = "This is a test log line to compare mmap vs write. Fill to 100 bytes.........\n";

    public static void main(String[] args) throws IOException {
        try (FileChannel channel = FileChannel.open(new File("write.log").toPath(),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {

            long start = System.currentTimeMillis();

            ByteBuffer buffer = ByteBuffer.allocate(LOG_SIZE);
            for (int i = 0; i < NUM_LOGS; i++) {
                buffer.clear();
                buffer.put(LOG_LINE.getBytes());
                buffer.flip();
                channel.write(buffer);
            }

            channel.force(true); // flush to disk
            long end = System.currentTimeMillis();
            System.out.println("FileChannel write: " + (end - start) + " ms");
        }

        try (FileChannel channel = FileChannel.open(new File("mmap.log").toPath(),
                StandardOpenOption.READ, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {

            long start = System.currentTimeMillis();

            long fileSize = NUM_LOGS * LOG_SIZE;
            MappedByteBuffer mmap = channel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);

            byte[] bytes = LOG_LINE.getBytes();
            for (int i = 0; i < NUM_LOGS; i++) {
                mmap.put(bytes);
            }

            mmap.force(); // flush to disk
            long end = System.currentTimeMillis();
            System.out.println("MappedByteBuffer mmap: " + (end - start) + " ms");
        }
    }
}
