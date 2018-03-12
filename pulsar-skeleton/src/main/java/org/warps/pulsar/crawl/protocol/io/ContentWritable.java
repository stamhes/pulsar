package org.warps.pulsar.crawl.protocol.io;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.VersionMismatchException;
import org.apache.hadoop.io.Writable;
import org.warps.pulsar.common.config.ImmutableConfig;
import org.warps.pulsar.crawl.protocol.Content;
import org.warps.pulsar.persist.io.MetadataWritable;
import org.warps.pulsar.persist.metadata.MultiMetadata;

import java.io.*;
import java.util.zip.InflaterInputStream;

/**
 * Created by vincent on 17-3-17.
 * Copyright @ 2013-2017 Warpspeed Information. All rights reserved
 */
public class ContentWritable implements Writable, Configurable {

    public final static int VERSION = -1;

    private ImmutableConfig immutableConfig;
    private Configuration hadoopConfig;
    private int version;
    private String url;
    private String base;
    private byte[] rawContent;
    private String contentType;
    private MetadataWritable metadataWritable = new MetadataWritable(new MultiMetadata());

    private Content content;

    public ContentWritable() {
        this.content = new Content();
    }

    public ContentWritable(Content content) {
        this.content = content;
    }

    public ContentWritable(ImmutableConfig conf) {
        this.immutableConfig = conf;
        this.hadoopConfig = conf.unbox();
        this.content = new Content();
    }

    public static Content read(DataInput in, ImmutableConfig conf) throws IOException {
        ContentWritable contentWritable = new ContentWritable(conf);
        contentWritable.readFields(in);
        return contentWritable.get();
    }

    public final void write(DataOutput out) throws IOException {
        out.writeInt(VERSION);

        Text.writeString(out, content.getUrl()); // write url
        Text.writeString(out, content.getBaseUrl()); // write base

        out.writeInt(content.getContent().length); // write rawContent
        out.write(content.getContent());

        Text.writeString(out, content.getContentType()); // write contentType

        new MetadataWritable(content.getMetadata()).write(out); // write metadata
    }

    @Override
    public Configuration getConf() {
        return hadoopConfig;
    }

    @Override
    public void setConf(Configuration conf) {
        this.hadoopConfig = hadoopConfig;
    }

    public Content get() {
        return content;
    }

    @Override
    public final void readFields(DataInput in) throws IOException {
        int sizeOrVersion = in.readInt();
        if (sizeOrVersion < 0) { // version
            version = sizeOrVersion;
            switch (version) {
                case VERSION:
                    url = Text.readString(in);
                    base = Text.readString(in);

                    rawContent = new byte[in.readInt()];
                    in.readFully(rawContent);

                    contentType = Text.readString(in);
                    metadataWritable.readFields(in);
                    break;
                default:
                    throw new VersionMismatchException((byte) VERSION, (byte) version);
            }
        } else { // size
            byte[] compressed = new byte[sizeOrVersion];
            in.readFully(compressed, 0, compressed.length);
            ByteArrayInputStream deflated = new ByteArrayInputStream(compressed);
            DataInput inflater = new DataInputStream(new InflaterInputStream(deflated));
            readFieldsCompressed(inflater);
        }

        this.content = new Content(url, base, rawContent, contentType, metadataWritable.get(), immutableConfig);
    }

    private void readFieldsCompressed(DataInput in) throws IOException {
        byte oldVersion = in.readByte();
        switch (oldVersion) {
            case 0:
            case 1:
                url = Text.readString(in); // read url
                base = Text.readString(in); // read base

                rawContent = new byte[in.readInt()]; // read rawContent
                in.readFully(rawContent);

                contentType = Text.readString(in); // read contentType
                // reconstruct metadata
                int keySize = in.readInt();
                String key;
                for (int i = 0; i < keySize; i++) {
                    key = Text.readString(in);
                    int valueSize = in.readInt();
                    for (int j = 0; j < valueSize; j++) {
                        metadataWritable.get().put(key, Text.readString(in));
                    }
                }
                break;
            case 2:
                url = Text.readString(in); // read url
                base = Text.readString(in); // read base

                rawContent = new byte[in.readInt()]; // read rawContent
                in.readFully(rawContent);

                contentType = Text.readString(in); // read contentType
                metadataWritable.readFields(in); // read meta data
                break;
            default:
                throw new VersionMismatchException((byte) 2, oldVersion);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof ContentWritable)) {
            return false;
        }

        ContentWritable c = (ContentWritable) other;
        return c.content.equals(content);
    }
}
