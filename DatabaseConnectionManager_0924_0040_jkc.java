// 代码生成时间: 2025-09-24 00:40:30
import play.db.Database;
import play.db.Databases;
import play.db.evolutions.Evolutions;
import play.libs.CompositeClassLoader;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Singleton
public class DatabaseConnectionManager {

    // A map to hold and manage database connections
    private ConcurrentMap<String, Connection> connectionPool = new ConcurrentHashMap<>();

    private Database db;

    @Inject
    public DatabaseConnectionManager(Database db) {
        this.db = db;
        // Initialize evolutions to apply database schema changes
        Evolutions.applyEvolutions(db);
    }

    public Connection getConnection() throws SQLException {
        // Get the current thread's class loader
        CompositeClassLoader classLoader = new CompositeClassLoader(Thread.currentThread().getContextClassLoader());
        // Get the connection using the class loader
        return db.withConnection(classLoader, connection -> {
            connectionPool.putIfAbsent(Thread.currentThread().getId() + classLoader.hashCode(), connection);
            return connection;
        });
    }

    public void releaseConnection(Connection connection) throws SQLException {
        // Remove the connection from the pool when it's no longer needed
        String key = Thread.currentThread().getId() + Thread.currentThread().getContextClassLoader().hashCode();
        connectionPool.remove(key);
        connection.close();
    }

    // This method is for cleaning up connections when the application stops
    public void closePool() throws SQLException {
        for (Connection connection : connectionPool.values()) {
            connection.close();
        }
        connectionPool.clear();
    }
}
