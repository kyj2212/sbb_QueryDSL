package site.yejin.sbb.base;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RepositoryUtil {


    void truncateTable();

    @Query(value = "SET FOREIGN_KEY_CHECKS=?",nativeQuery = true)
    void setForeignKeyChecks(int i);

    default void truncate(){
       // setForeignKeyChecks(0);
        this.truncateTable();
      //  setForeignKeyChecks(1);
    }


}
