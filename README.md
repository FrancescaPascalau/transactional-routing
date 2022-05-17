# Transactional Routing

![](transactional-routing.png)

To route the read-write transactions to the Primary node and read-only transactions to the Replica node, we can define a
`ReadWriteDataSource` that connects to the Primary node and a `ReadOnlyDataSource` that connect to the Replica node.

The read-write and read-only transaction routing is done by the Spring `AbstractRoutingDataSource` abstraction, which is
implemented by the `TransactionRoutingDatasource`.

Inside the `TransactionRoutingConfiguration.java` class the `actualDataSource` acts as a facade for the read-write and
read-only data sources and is implemented using the `TransactionRoutingDataSource` utility.

The `readWriteDataSource` is registered using the `DataSourceType.READ_WRITE` key and the `readOnlyDataSource` using the
`DataSourceType.READ_ONLY` key.
So, when executing a read-write `@Transactional` method, the `readWriteDataSource` will be used while when executing a
`@Transactional(readOnly = true)` method, the `readOnlyDataSource` will be used instead.

Basically, we inspect the Spring `TransactionSynchronizationManager` class that stores the current transactional context
to check whether the currently running Spring transaction is read-only or not.
The `determineCurrentLookupKey` method returns the discriminator value that will be used to choose either the read-write
or the read-only JDBC DataSource.

### References

https://vladmihalcea.com/read-write-read-only-transaction-routing-spring/