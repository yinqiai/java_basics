package com.transsnet.enum_study;

/**
 * @author yinqi
 * @date 2021/2/19
 */
public enum SentinelCheckpoint1 {
    /**
     * Start from the first available record in the shard.
     */
    TRIM_HORIZON,
    /**
     * Start from the latest record in the shard.
     */
    LATEST,
    /**
     * We've completely processed all records in this shard.
     */
    SHARD_END,
    /**
     * Start from the record at or after the specified server-side timestamp.
     */
    AT_TIMESTAMP
}
