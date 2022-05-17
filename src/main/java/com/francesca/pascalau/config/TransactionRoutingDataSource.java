package com.francesca.pascalau.config;

import com.francesca.pascalau.config.util.DataSourceType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class TransactionRoutingDataSource extends AbstractRoutingDataSource {

    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ? DataSourceType.READ_ONLY
                : DataSourceType.READ_WRITE;
    }
}