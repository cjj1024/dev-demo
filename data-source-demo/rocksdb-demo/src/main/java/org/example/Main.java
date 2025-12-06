package org.example;


import org.rocksdb.*;

public class Main {
    static {
        RocksDB.loadLibrary();
    }
    public static void main(String[] args) throws RocksDBException {
        String dbPath = "rocksdb_data";
        final Options options = new Options().setCreateIfMissing(true);
        try (final RocksDB db = RocksDB.open(options, dbPath)) {
            try (RocksIterator iterator = db.newIterator()) {
                for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
                    System.out.println(
                            new String(iterator.key()) + " => " + new String(iterator.value())
                    );
                }
            }


            db.put("key1".getBytes(), "value1".getBytes());
            db.put("key2".getBytes(), "value2".getBytes());

            byte[] value1 = db.get("key1".getBytes());
            System.out.println("key1 = " + new String(value1));

            db.delete("key2".getBytes());

            try (WriteBatch batch = new WriteBatch();
                 WriteOptions writeOptions = new WriteOptions()) {
                batch.put("key3".getBytes(), "value3".getBytes());
                batch.put("key4".getBytes(), "value4".getBytes());
                batch.delete("key1".getBytes());
                db.write(writeOptions, batch);
            }

            try (RocksIterator iterator = db.newIterator()) {
                for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
                    System.out.println(
                            new String(iterator.key()) + " => " + new String(iterator.value())
                    );
                }
            }
        }
    }
}