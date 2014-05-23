package com.lambdaworks.redis.support;

import com.lambdaworks.redis.RedisConnectionPool;

/**
 * Execution-Template which allocates a connection around the run()-call. Use this class as adapter template and implement your
 * redis calls within the run-method.
 * 
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 15.05.14 21:08
 */
public abstract class WithConnection<T> {
    private RedisConnectionPool<T> pool;

    /**
     * Performs connection handling and invokes the run-method with a valid Redis connection.
     * 
     * @param pool the connection pool.
     */
    public WithConnection(RedisConnectionPool<T> pool) {
        this.pool = pool;
        T connection = pool.allocateConnection();
        try {
            run(connection);
        } finally {
            pool.freeConnection(connection);
        }
    }

    /**
     * Execution method. Will be called with a valid redis connection.
     * 
     * @param connection
     */
    protected abstract void run(T connection);
}