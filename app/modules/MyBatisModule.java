package modules;

import com.google.inject.Provider;
import com.google.inject.name.Names;
import dao.VacancyDao;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import play.Logger;
import play.db.Database;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Properties;

public class MyBatisModule extends org.mybatis.guice.MyBatisModule {

    private static final Logger.ALogger logger = Logger.of(MyBatisModule.class);

    @Override
    protected void initialize() {

        logger.debug("initialize MyBatisModule");

        bindDataSourceProviderType(PlayDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);

        final Properties myBatisProperties = new Properties();
        myBatisProperties.setProperty("mybatis.environment.id", "default");
        Names.bindProperties(binder(), myBatisProperties);

        addMapperClass(VacancyDao.class);
    }


    static class PlayDataSourceProvider implements Provider<DataSource> {

        final Database db;

        @Inject
        public PlayDataSourceProvider(final Database db) {
            this.db = db;
        }

        @Override
        public DataSource get() {
            // use db as configured in conf/application.conf
            return db.getDataSource();
        }

    }

}
